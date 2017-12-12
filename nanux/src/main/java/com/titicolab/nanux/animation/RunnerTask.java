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


import com.titicolab.nanux.list.FlexibleList;

/**
 * Created by campino on 15/06/2016.
 *
 */
public class RunnerTask implements Updatable{


    private static final int DEFAULT_RENDER_LISTENERS_SIZE = 10;
    private static final long GL_THREAD_RUNNABLE_TIME_OUT = 60000;
    private final FlexibleList<RunnableTask> mRunnableList;

    private Thread mRunnerThread;

    public RunnerTask() {
        mRunnableList = new FlexibleList<>  (DEFAULT_RENDER_LISTENERS_SIZE);
    }

    public RunnerTask(Thread thread) {
        mRunnableList = new FlexibleList<> (DEFAULT_RENDER_LISTENERS_SIZE);
          setRunnerThread(thread);
    }


    public Thread getRunnerThread() {
        if(mRunnerThread==null)
            throw new RuntimeException("The name of update thread is null, " +
                    "it needs be set before");
        return mRunnerThread;
    }


    /** Set the name of thread where .update() will be called for run the task
     * @param runnerThread name of update thread
     */
    public void setRunnerThread(Thread runnerThread) {
        mRunnerThread = runnerThread;
    }


    @Override
    public void update() {
        updateList();
    }

    private void updateList() {
        FlexibleList<RunnableTask> list;

        if(mRunnableList.size()==0)
            return;

        synchronized (mRunnableList) {
            int size = mRunnableList.size();
            list = new FlexibleList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(mRunnableList.get(i));
            }
            mRunnableList.clear();
        }

        for (int i = 0; i < list.size(); i++) {
                executeTask(list.get(i));
        }
    }

    private void executeTask(RunnableTask runnableTask) {
        try{
            runnableTask.run();
        }catch (Exception e){
            runnableTask.onError(e);
        }finally {
            runnableTask.finished();
        }
    }


    /**
     * Run the runnable in the looper of the update() method.
     * It method block the thread from it is called. this is the threat will be blocked
     * until the runner is executed.
     * It guests that update() is running in a different thread, render thread.
     * @param runnable code to execute in the update looper;
     */
     public void runAndWait(RunnableTask runnable) {

        if(mRunnerThread.getName()
                .equals(Thread.currentThread().getName())){
            executeTask(runnable);
            return;
        }

        mRunnableList.add(runnable);
        if(!runnable.waitSyncFlat(GL_THREAD_RUNNABLE_TIME_OUT)){
            throw  new RuntimeException("Error executing runnable, likely the update()" +
                    "function is not being called, it reaches timeout: " +
                    GL_THREAD_RUNNABLE_TIME_OUT);
        }

        if(runnable.getError()!=null){
            throw  new RuntimeException(runnable.getError());
        }
    }

    /**
     * Just queue a task for be executed in the next update looper cycle.It guests that update()
     * is running in a different thread, render thread.
     * @param runnable task to be executed in update.
     */
    public void queueTask(RunnableTask runnable) {
        if(mRunnerThread.getName()
                .equals(Thread.currentThread().getName())){
            executeTask(runnable);
            return;
        }
        mRunnableList.add(runnable);
    }



}
