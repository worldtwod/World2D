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

package com.titicolab.nanux.animation;

import com.titicolab.nanux.graphics.texture.Texture;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.list.FlexibleList;

/**
 * Created by campino on 04/12/2017.
 *
 */

public class AnimationBuilder {

    private final TextureManager mTextureManager;


    private final AnimationSheet mAnimationSheet;


    public AnimationBuilder(TextureManager textureManager, AnimationSheet animationSheet) {
        this.mTextureManager = textureManager;
        this.mAnimationSheet = animationSheet;
    }


    public Animation build(String key){

        if(mAnimationSheet==null)
            throw  new RuntimeException("Then AnimationSheet is null, " +
                    "you must set it by override onDefineAnimations() ");


        AnimationMap map = mAnimationSheet.findByKey(key);

        if(map==null)
            throw  new RuntimeException("Can not build the animation width key=[" + key +
                    "] please check that you have defined it onDefineAnimations() ");

        FlexibleList<ClipMap> listClipMaps = map.getListClipMaps();
        Animation animation = new Animation();
        animation.setKey(map.getKey());

        int size = listClipMaps.size();
        for (int i = 0; i < size; i++) {
            animation.add(build(listClipMaps.get(i)));
        }
        return animation;
    }

    AnimationSheet getAnimationSheet() {
        return mAnimationSheet;
    }


    private Clip  build(ClipMap clipMap){

        Clip clip=null;

        Texture texture = mTextureManager.getTexture(clipMap.getResources());
        if(texture == null) throw new RuntimeException
                ("The texture can no be instance, resource: " + clipMap.getResources());


        if(clipMap.getType()== ClipMap.TYPE_CLIP_AREA && clipMap.getArea().isAreaFromParent()){
            clip = clip(texture,0,0,texture.getWidth(),texture.getHeight(),clipMap.getKey());

        }else if(clipMap.getType()== ClipMap.TYPE_CLIP_AREA){
            clip = clip(texture,
                    clipMap.getArea().left,
                      clipMap.getArea().top,
                        clipMap.getArea().width,
                            clipMap.getArea().height,
                            clipMap.getKey());
        }else if(clipMap.getType()== ClipMap.TYPE_CLIP_GRID){
            clip = clip(texture,clipMap);
        }
        return clip;
    }



    private Clip clip(Texture texture, ClipMap clipMap) {
        GridFrame gridFrame = new GridFrame(texture);
        if(clipMap.getArea().isAreaFromParent()){
            gridFrame.setLeftTop(0,0);
            gridFrame.setGridSize(texture.getWidth(),texture.getHeight());
        }else{
            gridFrame.setLeftTop(clipMap.getArea().left,clipMap.getArea().top);
            gridFrame.setGridSize(clipMap.getArea().width,clipMap.getArea().height);
        }

        gridFrame.setGridShape(clipMap.getGrid().columns,clipMap.getGrid().rows);
        gridFrame.createFrames();
        return new Clip(gridFrame,clipMap.getCells().index,clipMap.getKey());
    }


    private Clip clip(Texture texture, float left, float top, float width, float height, int key){
        GridFrame gridFrame = new GridFrame(texture);
        gridFrame.setLeftTop(left,top);
        gridFrame.setGridSize(width,height);
        gridFrame.setGridShape(1,1,1);
        gridFrame.createFrames();
        return new Clip(gridFrame,0,key);
    }


}
