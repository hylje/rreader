/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader.handlers;

import com.ning.http.client.ListenableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rreader.Post;

/**
 * Callback is called after all other handlers in containing_list are done
 *
 * @author leohonka
 */
public class ContentListHandler implements Runnable {
    List<ContentListHandler> containing_list;
    ListenableFuture<Post> request_future;
    Boolean done;
    Runnable finished;
    
    public ContentListHandler(ListenableFuture<Post> request_future, 
                         List<ContentListHandler> containing_list,
                         Runnable finished) {
        this.request_future = request_future;
        this.containing_list = containing_list;
        this.finished = finished;
        this.done = false;
    }

    @Override
    public void run() {
        try {
            Post post = request_future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ContentListHandler.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        System.out.print(".");
        
        for (ContentListHandler h : containing_list) {
            if (h.done == false) {
                return;
            }
        }
        finished.run();
    }
    
}
