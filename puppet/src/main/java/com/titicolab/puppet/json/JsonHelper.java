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

package com.titicolab.puppet.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.titicolab.puppet.animation.CutterProgram;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by campino on 05/02/2017.
 *
 */

public class JsonHelper {





    public interface Serializable {
        String toJson();

    }


    public static String getJsonFromBuildCommand(CutterProgram.BuildCommand buildCommand) {
        Gson gson = new Gson();
        return gson.toJson(buildCommand, CutterProgram.BuildCommand.class);
    }

    public static CutterProgram.BuildCommand getBuildCommandFromJson(String params) {
        Gson gson = new Gson();
        return gson.fromJson(params,CutterProgram.BuildCommand.class);
    }


    public static String getJsonCutterProgramFrom(CutterProgram cutterProgram){
       /* Gson gson = new GsonBuilder()
                .registerTypeAdapter(SparseArray.class, new SparseArraySerializer())
                .registerTypeAdapter(SparseArray.class, new SparseArrayDeSerializer())
                .create();*/

        Type sparseArrayType = new TypeToken<HashMap<Integer,CutterProgram.BuildCommand>>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(sparseArrayType, new SparseArrayTypeAdapter<>(CutterProgram.BuildCommand.class))
                .create();

        return gson.toJson(cutterProgram,CutterProgram.class);
    }

    public static CutterProgram getCutterProgramFromJson(String jsonCutterProgram){
       /* Gson gson = new GsonBuilder()
                .registerTypeAdapter(SparseArray.class, new SparseArraySerializer())
                .registerTypeAdapter(SparseArray.class, new SparseArrayDeSerializer())
                .create();*/


        Type sparseArrayType = new TypeToken<HashMap<Integer,CutterProgram.BuildCommand>>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(sparseArrayType, new SparseArrayTypeAdapter<>(CutterProgram.BuildCommand.class))
                .create();

            return gson.fromJson(jsonCutterProgram, CutterProgram.class);
    }


    private static class SparseArraySerializer implements JsonSerializer<HashMap<Integer,CutterProgram.BuildCommand>> {

        @Override
        public JsonElement serialize(HashMap<Integer,CutterProgram.BuildCommand> src,
                                     Type typeOfSrc,
                                     JsonSerializationContext context) {
            Type listType = new TypeToken<HashMap<Integer,CutterProgram.BuildCommand>>(){}.getType();
            Gson gson = new Gson();
            JsonElement json = gson.toJsonTree(src, listType);
            return json;
        }
    }

    private static class SparseArrayDeSerializer implements JsonDeserializer<HashMap<Integer,CutterProgram.BuildCommand>> {

        @Override
        public HashMap<Integer,CutterProgram.BuildCommand> deserialize
                (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Type listType = new TypeToken<HashMap<Integer,CutterProgram.BuildCommand>>(){}.getType();
            Gson gson = new Gson();
            return gson.fromJson(json,listType);
        }
    }




}
