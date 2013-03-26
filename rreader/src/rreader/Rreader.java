/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import java.util.LinkedList;
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
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Reddit reddit = new Reddit(executor);
        
        System.out.println("Fetching some Reddit posts...");
        
        final ListenableFuture<List<Post>> posts_future = reddit.get_posts("programming");
        
        posts_future.addListener(new PostHandler(posts_future, reddit, executor), executor);
        
        executor.awaitTermination(5L, TimeUnit.SECONDS);
    }
}
