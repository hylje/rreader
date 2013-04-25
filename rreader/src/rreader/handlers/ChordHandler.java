package rreader.handlers;

import com.ning.http.client.ListenableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rreader.Post;

/**
 * When all handlers in the list this handler belongs in are called,
 * the finished callback is called.
 *
 * @author leohonka
 */
public class ChordHandler implements Runnable {
    List<ChordHandler> containing_list;
    ListenableFuture<Post> request_future;
    Boolean done;
    Runnable finished;
    
    public ChordHandler(ListenableFuture<Post> request_future, 
                         List<ChordHandler> containing_list,
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
            Logger.getLogger(ChordHandler.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        System.out.print(".");
        
        for (ChordHandler h : containing_list) {
            if (h.done == false) {
                return;
            }
        }
        finished.run();
    }
    
}
