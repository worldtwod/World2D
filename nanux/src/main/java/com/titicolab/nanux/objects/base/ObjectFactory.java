/*
 * Copyright  2017   Fredy Campiño
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.titicolab.nanux.objects.base;

import com.titicolab.nanux.animation.Animation;
import com.titicolab.nanux.animation.AnimationBuilder;
import com.titicolab.nanux.animation.AnimationSheet;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.list.GameObjectCollection;
import com.titicolab.nanux.list.GameObjectList;
import com.titicolab.nanux.objects.factory.RequestCollection;
import com.titicolab.nanux.objects.factory.RequestLayersBuilder;
import com.titicolab.nanux.objects.factory.RequestObject;
import com.titicolab.nanux.objects.factory.RequestObjectBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by campino on 03/12/2017.
 *
 */

public class ObjectFactory {

    private final TextureManager mTextureManager;
    private final Scene mScene;



    public ObjectFactory(Scene scene, TextureManager textureManager) {
        mScene = scene;
        mTextureManager = textureManager;
    }


   public GameObjectList factoryGroupLayer(BaseGroupLayer group) {

        RequestCollection.RequestList requestLayersList = group
                .onLayersRequest(new RequestLayersBuilder());
        int size = requestLayersList.size();
        GameObjectList listLayers = new GameObjectList(size);

        for (int i=0; i< size ; i++) {
            RequestObject request = requestLayersList.get(i);
            BaseLayer baselayer = (BaseLayer) instantiate(request);
            listLayers.add(baselayer);

            if(GroupLayer.class.isAssignableFrom(baselayer.getClass())) {
                ((GroupLayer)baselayer).onAttachScene(mScene);
                ((GroupLayer)baselayer).onAttachGroupLayers(group);
            }


            if(BaseGroupLayer.class.isAssignableFrom(baselayer.getClass())) {
                BaseGroupLayer groupLayer = (BaseGroupLayer) baselayer;
                factoryGroupLayer(groupLayer);

            }else if(Layer.class.isAssignableFrom(baselayer.getClass())) {

                @SuppressWarnings("ConstantConditions")
                Layer layer = (Layer) baselayer;
                layer.onAttachScene(mScene);
                layer.onAttachGroupLayers(group);

                AnimationSheet animationSheet = layer
                        .onDefineAnimations(new AnimationSheet.Builder());


                RequestCollection requestObjectsList =
                        layer.onRequestObjects(new RequestObjectBuilder());

                GameObjectCollection collection = factoryAnimatedCollection(requestObjectsList,
                        new AnimationBuilder(mTextureManager, animationSheet));

                //  onAttachObjects
                layer.onAttachObjects(collection);
                layer.onGroupObjectsCreated();

            }else{
                throw new RuntimeException("The class of BaseLayer is unknown");
            }
        }

        group.onAttachLayers(listLayers);
        group.onGroupLayersCreated();

        return listLayers;
    }





    public  GameObjectCollection factoryAnimatedCollection(
            RequestCollection requestCollection, AnimationBuilder builder) {

        int size = requestCollection.size();
        GameObjectCollection collection = new GameObjectCollection(size);

        for (int i=0; i< size ; i++) {
            GameObjectList gameList = factoryAnimatedList(requestCollection.get(i), builder);
            collection.add(gameList);
        }
        return collection;
    }


    public  GameObjectList factoryAnimatedList(
                        RequestCollection.RequestList requestList,
                        AnimationBuilder builder) {

        int size = requestList.size();
        GameObjectList gameObjectList = new GameObjectList(size);
        if(size<0) return null;
        Animation animation=null;

        for (int count = 0; count < size; count++) {
             RequestObject request = requestList.get(count);
             Animated animated= (Animated) instantiate(request);

             if(count==0 || animated.hasCustomClips()) {
                 AnimationSheet animationSheet = animated
                         .onDefineAnimations(new AnimationSheet.Builder());
                 // if the Animated has own animationSheet the use it, otherwise
                 // use default, it that one defined in layer or scene
                 builder = animationSheet==null? builder:
                         new AnimationBuilder(mTextureManager, animationSheet);
                 animation = animated.onBuildClips(builder);
             }
             //onAttachAnimation
             animated.onAttachAnimation(animation);
             gameObjectList.add(animated);
             //animated.onCreated();
         }
    return gameObjectList;
    }


    /**
     * Instantiate a object type GameObject, it is responsible for call to
     * event:
     *  onAttachParameters()
     *
     * @param request data for instantiation
     * @return the new object
     */
    private static GameObject instantiate(RequestObject request){
        GameObject gameObject = null;
        Class<? extends GameObject> c = request.getType();

        try {
            gameObject = c.newInstance();
            assert gameObject != null;

            //onAttachParameters
            gameObject.onAttachParameters(request);
        }catch (InstantiationException e) {
            errorInstantiation(c, e);
        }catch (IllegalAccessException e){
            errorInstantiation(c, e);
        } catch (Throwable tr) {
            throw new RuntimeException("Error instantiation: " + tr.getMessage());
        }
        return gameObject;
    }


    /******************************************* ERROR MANAGEMENT  ********************************
     * ********************************************************************************************
     */
    private static void errorInstantiation(Class<? extends GameObject> c, Exception tr) {
        if(isInnerClassNotStatic(c)) {
            String errorMessage = "the GameObject " +
                    c.getSimpleName() +
                    " is a inner and not static class, " +
                    " tile static modifier to class, such as: [public static class]";
             throw new RuntimeException(errorMessage);

        }else if(tr.getClass().equals(IllegalAccessException.class)){

            String errorMessage = "the class of  " +
                    c.getSimpleName() +
                    " musts be public with public constructor";
            throw new RuntimeException(errorMessage);

        }else{
            String errorMessage = "the GameObject " +
                    c.getSimpleName() +
                    " cannot be instantiate, the class has no zero argument constructor";
            throw new RuntimeException(errorMessage);
        }
    }

    private static boolean isInnerClassNotStatic(Class<?> clazz) {
        return clazz.isMemberClass() &&
                !Modifier.isStatic(clazz.getModifiers());
    }


}
