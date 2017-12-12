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

package com.titicolab.puppet.animation;


import com.titicolab.nanux.graphics.textures.Texture;
import com.titicolab.puppet.model.UvCoordinates;

/**
 * Created by campino on 25/05/2016.
 * The Frames have the basic render information:
 *
 *  Texture reference
 *  Uv coordinates
 *  Uv size
 *  FrameGroup: Normally the frames are set in a groupFrame
 *
 */
public class Frame extends UvCoordinates{

    /**
     * Constructor for build a frame from a texture, it will create his own group
     * @param texture a GL texture reference
     */
    public Frame(Texture texture){
        super(texture);
    }

    public Frame(Texture texture, float left, float top, float right, float bottom) {
        super(texture, left, top, right, bottom);
    }


}
