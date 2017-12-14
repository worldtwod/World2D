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

package com.titicolab.nanux.list;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by campino on 02/06/2016.
 *
 */


public class ListUnitTest {




    @Test
    public void resize_list_resize() throws Exception {

        FlexibleList<Integer> list = new FlexibleList<>(5);

        assertThat(list.capacity(),is(5));

        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);

        assertThat(list.size(),is(10));
        assertThat(list.capacity(),is(10));
    }

    @Test
    public void resize_list_constructor() throws Exception {

        FlexibleList<Integer> list = new FlexibleList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        assertThat(list.size(),is(5));
        assertThat(list.capacity(),is(5));
    }

    @Test
    public void resize_list_remove() throws Exception {

        FixList<Integer> list = new FixList<>(5);

        list.add(10); // 0
        list.add(20); // 1
        list.add(30); // 2
        list.add(40); // 3
        list.add(50); // 4


        assertThat(list.size(),is(5));

        //remove
        assertThat(list.get(3),is(40));
        list.remove(list.get(3));
        assertThat(list.get(3),is(50));

        assertThat(list.size(),is(4));
        assertThat(list.capacity(),is(5));
    }

    @Test
    public void resize_list_add() throws Exception {

        FlexibleList<Integer> list = new FlexibleList<>(5);

        list.add(10); // 0
        list.add(20); // 1
        list.add(30); // 2
        list.add(40); // 3
        list.add(50); // 4


        assertThat(list.size(),is(5));

        //remove
        list.add(2,-10);

        assertThat(list.get(0),is(10));
        assertThat(list.get(1),is(20));
        assertThat(list.get(2),is(-10));
        assertThat(list.get(3),is(30));
        assertThat(list.get(4),is(40));
        assertThat(list.get(5),is(50));

        assertThat(list.size(),is(6));
        assertThat(list.capacity(),is(6));

    }

    @Test
    public void resize_list_set() throws Exception {

        FlexibleList<Integer> list = new FlexibleList<>(5);

        list.add(10); // 0
        list.add(20); // 1
        list.add(30); // 2
        list.add(40); // 3
        list.add(50); // 4


        assertThat(list.size(),is(5));

        //remove
        list.set(2,-10);
        assertThat(list.get(0),is(10));
        assertThat(list.get(1),is(20));
        assertThat(list.get(2),is(-10));
        assertThat(list.get(3),is(40));
        assertThat(list.get(4),is(50));

        assertThat(list.size(),is(5));
        assertThat(list.capacity(),is(5));

    }

}
