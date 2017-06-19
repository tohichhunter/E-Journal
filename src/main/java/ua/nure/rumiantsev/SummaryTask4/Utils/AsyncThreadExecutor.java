/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *<p>Class for running some actions in concurrent thread</p>
 * Enabled to return result of running code. 
 * Code placed in <b>task</b> method runs after <b>execute</b> method invocation.
 * Can be specified for different purposes via Java generic mechanism.
 * Preferred use for long term blocking operations, example sake - DB access
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 * @param <T> - type of certain object, that use to be returned after finish
 */
public abstract class AsyncThreadExecutor<T> {
    
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    
    /**
     * Method, defining at instantiation time, as analogue of Thread <b>tun</b>
     * for specifying action to be running
     * @return 
     */
    public abstract T task();
    
    /**
     * Begins the running of the task.
     * @return result of computation.
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public T execute() throws InterruptedException, ExecutionException{
        Future<T> future = ES.submit(new InnerRunner());
        return future.get();
    }
    
    private class  InnerRunner implements Callable<T>{

        public InnerRunner(){
            
        }
        @Override
        public T call() throws Exception {            
            return task();
        }
        
    }
}
