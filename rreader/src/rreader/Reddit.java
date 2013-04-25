package rreader;

import rreader.handlers.PostProcessor;
import com.ning.http.client.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;

/**
 * Wrapper over Reddit API.
 * 
 * Provides public raw JSON methods as well as some high level wrapper functions.
 * 
 * @author leohonka
 */
public class Reddit {
    public Http http;
    public Boolean logged_in = false;
    public Executor executor;
    
    public Reddit(Executor executor) {
        this.http = new Http();
        this.executor = executor;
    }

    /**
     * High level wrapper to Reddit posts. 
     *
     * @param subreddit The subreddit name to get posts from, or a plus 
     *                   delimited list thereof. 
     * @return Future over a list of found posts.
     * @throws IOException
     */
    public ListenableFuture<List<Post>> 
            get_posts(String subreddit) 
           throws IOException {
        final ListenableFuture<JSONObject> json_future = http.get("http://www.reddit.com/r/" + subreddit + ".json");
        final ListenableFuture<List<Post>> ret = new ListenableFutureTask<List<Post>>();
        
        json_future.addListener(new PostProcessor(json_future, ret), executor);
        
        return ret;
    }
}
