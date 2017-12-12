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

package com.titicolab.opengl.shader;

import android.content.Context;

import com.titicolab.opengl.R;
import com.titicolab.opengl.util.TextResourceReader;
import com.titicolab.puppet.draw.DrawTools;

/**
 * Created by campino on 11/12/2017.
 *
 */

public class AndroidDrawToolsBuilder implements DrawTools.Builder {


    private final Context mContext;


    public AndroidDrawToolsBuilder(Context context) {
        mContext = context;
    }

    @Override
    public DrawTools build() {
        return new DrawTools(setUpDrawerImage(mContext),
                setUpDrawerImage(mContext),
                setUpDrawerGeometry(mContext),
                null);
    }


    private static DrawerImage setUpDrawerImage(Context context) {
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                context, R.raw.image_vertex);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                context, R.raw.image_fragment);
        ImageShaderProgram program = new ImageShaderProgram(vertexSh, fragmentSh);
        program.buildProgram();
        return  new DrawerImage(100,program);
    }


    private static DrawerGeometry setUpDrawerGeometry(Context context) {
        String vertexSh =  TextResourceReader.readTextFileFromResource(
                context, R.raw.geometry_vertex_shader);
        String fragmentSh = TextResourceReader.readTextFileFromResource(
                context, R.raw.geometry_fragment_shader);

        GeometryShaderProgram shader = new GeometryShaderProgram(vertexSh, fragmentSh);
        shader.buildProgram();
        return new DrawerGeometry(100,shader);
    }



}
