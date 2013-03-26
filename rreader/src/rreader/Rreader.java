/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leohonka
 */
public class Rreader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // TODO set up event loop ("executor") over http client as well as the UI
        Reddit reddit = new Reddit(executor);
        
        System.out.println("Fetching some Reddit posts...");
        
        final ListenableFuture<List<Post>> posts_future = reddit.get_posts("programming");
        
        System.out.println("...");
        
        posts_future.addListener(new Runnable() {
            @Override
            public void run() {
                List<Post> posts;
                try {
                    posts = posts_future.get();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Rreader.class.getName()).log(
                            Level.SEVERE, 
                            "Something bad happened", 
                            ex);
                    return;
                }
        
                System.out.println("Posts:");
        
                for (Post p : posts) {
                    System.out.println(p.toString());
                }
            }
        }, executor);
        
        executor.awaitTermination(5L, TimeUnit.SECONDS);
    }
}
