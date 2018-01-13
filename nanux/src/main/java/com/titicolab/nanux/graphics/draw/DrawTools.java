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

package com.titicolab.nanux.graphics.draw;

import com.titicolab.nanux.graphics.texture.TextureManager;

/**
 * Created by campino on 16/01/2017.
 *
 */

public class DrawTools {



    private static float scalePixel;

    public final Drawer<Image>          ui;
    public final Drawer<Image>          images;
    public final Drawer.Brush<Geometry> geometry;
    public final Drawer.Text<Image>     text;


    public DrawTools(Drawer<Image> images,
                     Drawer<Image> ui,
                     Drawer.Brush<Geometry> geometry,
                     Drawer.Text<Image> text) {

        this.images = images;
        this.ui = ui;
        this.geometry = geometry;
        this.text = text;
    }

    public interface Builder{
        DrawTools build(TextureManager manager);
    }


    public static float getScalePixel() {
        return scalePixel;
    }

    public static void setScalePixel(float scalePixel) {
        DrawTools.scalePixel = scalePixel;
    }

}
