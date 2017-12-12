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
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by:
 * https://gist.github.com/dmarcato/6455221
 */

public class  SparseArrayTypeAdapter<T> extends TypeAdapter<HashMap<Integer,T>> {

    private final Gson gson = new Gson();
    private final Class<T> classOfT;
    private final Type typeOfSparseArrayOfT = new TypeToken<HashMap<Integer,T>>() {}.getType();
    private final Type typeOfSparseArrayOfObject = new TypeToken<HashMap<Integer,Object>>() {}.getType();

    public SparseArrayTypeAdapter(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    @Override
    public void write(JsonWriter jsonWriter, HashMap<Integer,T> tSparseArray) throws IOException {
        if (tSparseArray == null) {
            jsonWriter.nullValue();
            return;
        }
        gson.toJson(gson.toJsonTree(tSparseArray, typeOfSparseArrayOfT), jsonWriter);
    }

    @Override
    public HashMap<Integer,T> read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        HashMap<Integer,Object> temp = gson.fromJson(jsonReader, typeOfSparseArrayOfObject);
        HashMap<Integer,T> result = new HashMap<>(temp.size());
        JsonElement tElement;

        for (Integer key : temp.keySet()) {
            tElement = gson.toJsonTree(temp.get(key));
            result.put(key, gson.fromJson(tElement, classOfT));
        }
        return result;
    }

}
