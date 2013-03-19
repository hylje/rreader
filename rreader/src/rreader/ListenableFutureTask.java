/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.listenable.AbstractListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Partial implementation for a ListenableFuture.
 * 
 * Used for chaining asynchronous events across classes.
 *
 * @author leohonka
 */
public class ListenableFutureTask<T> extends AbstractListenableFuture {
    T content;

    /**
     * Complete the task with result content
     *
     * @param v T type object that completes this task
     */
    @Override
    public void content(Object v) {
        content = (T)v;
        done();
    }
    
    @Override
    public T get() throws InterruptedException, ExecutionException {
        return content;
    }
    
    // --- stubs follow ---
    
    @Override
    public boolean isDone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void abort(Throwable t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void touch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getAndSetWriteHeaders(boolean writeHeader) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getAndSetWriteBody(boolean writeBody) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void done(Callable callable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
