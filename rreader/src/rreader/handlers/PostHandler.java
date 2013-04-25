package rreader.handlers;

import com.ning.http.client.ListenableFuture;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import rreader.Post;
import rreader.Reddit;
import rreader.Rreader;

/**
 * Console utility class
 *
 * @author leohonka
 */
public class PostHandler implements Runnable {
    private final ListenableFuture<List<Post>> posts_future;
    private final Reddit reddit;
    private final ExecutorService executor;

    public PostHandler(ListenableFuture<List<Post>> posts_future, Reddit reddit, ExecutorService executor) {
        this.posts_future = posts_future;
        this.reddit = reddit;
        this.executor = executor;
    }

    @Override
    public void run() {
        final List<Post> posts;
        try {
            posts = posts_future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Rreader.class.getName()).log(Level.SEVERE, "Something bad happened", ex);
            return;
        }
        System.out.format("Got %d posts, processing...", posts.size());
        List<ChordHandler> handlers = new LinkedList<>();
        Runnable finished = new PrintAllPosts(posts);
        for (Post p : posts) {
            ListenableFuture<Post> futu = p.get_text_content(reddit);
            ChordHandler handler = new ChordHandler(futu, handlers, finished);
            futu.addListener(handler, executor);
            handlers.add(handler);
        }
    }
    
}
