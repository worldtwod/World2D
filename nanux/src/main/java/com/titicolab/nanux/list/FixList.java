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

import java.lang.reflect.Array;

/**
 * Created by campino on 30/05/2016.
 *
 */
public  class FixList<T> implements InterfaceList<T>{
    private T[] mList;
    private   int index;


    public FixList(int capacity) {
        setCapacity(capacity);
    }

    protected FixList<T> setCapacity(Class<T> c, int capacity){
        mList =  (T[]) Array.newInstance(c, capacity);
        return this;
    }

    protected FixList<T> setCapacity(int capacity){
        mList = (T[]) new  Object[capacity];
        return this;
    }

    @Override
    public  void add(T item){
        if (index>= mList.length)
            throw new IllegalArgumentException
                    ("It list has fix capacity, " +
                            "you cannot add more objects to this list," +
                            " current limit:" + mList.length);
        mList[index++]=item;
    }

    @Override
    public  void reset(){
        index=0;
    }

    @Override
    public  void clear(){
        for (int i = 0; i < mList.length; i++) {
            mList[i]=null;
        }
        index=0;
    }

    @Override
    public T get(int i) {
        if (i>= mList.length || i<0)
            throw new  IndexOutOfBoundsException
        ("The index is negative or >= to :" + mList.length);
        return mList[i];
    }

    @Override
    public void set(int i,T item) {
        if (i>= mList.length || i<0)
            throw new  IndexOutOfBoundsException
                    ("The index is negative or >= to :" + mList.length);

       // shiftRight(i);
        mList[i]=item;
        //index++;
    }

    @Override
    public void add(int i, T item) {
        if (i>= mList.length || i<0)
            throw new  IndexOutOfBoundsException
                    ("The index is negative or >= to :" + mList.length);

        shiftRight(i);
        mList[i]=item;
        index++;
    }


    @Override
    public int size(){
        return index;
    }

    @Override
    public int capacity() {
        return mList.length;
    }

    @Override
    public  void remove(T item){
        if (item == null)
            throw new IllegalArgumentException
                    ("Can not remove a null object");

        int k = indexOf(item);
        int c;
        if (k < 0)
            return;

        for (c = k; c < index - 1; c++)
            mList[c] = mList[c + 1];

        mList[c] = null;
        index--;
    }


    public int indexOf(T item) {
        if (item == null)
            throw new IllegalArgumentException
                    ("Can not find a null object");

        for (int i = 0; i < index; i++)
            if (mList[i].equals(item))
                return i;

        return -1;
    }

    protected void shiftRight(int position){
        if(mList.length==1)return;
        if(mList.length==2 && position==1)return;
        for (int i =  mList.length-2; i >= position; i--) {
            mList[i+1]=mList[i];
        }
    }

    protected void doResize(int resize){
        T[] temList = mList;
        setCapacity(mList.length + resize);
        int length = temList.length;
        for (int i=0,index = 0; i < length; i++) {
            mList[index++] = temList[i];
        }
    }


    protected T getCurrent(){
       return mList[index-1];
    }

    public boolean isEmpty() {
        return index==0;
    }





}
