package rreader;

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
        final ListenableFuture<JSONObject> json_future = http.get("/r/" + subreddit + ".json");
        final ListenableFuture<List<Post>> ret = new ListenableFutureTask<List<Post>>();
        
        json_future.addListener(new Runnable() {
            @Override
            public void run() {
                List<Post> posts = new LinkedList<>();  
                JSONObject json_posts = null;
                try {
                    json_posts = json_future.get();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Reddit.class.getName()).log(Level.SEVERE, null, ex);
                    ret.content(posts);
                    return;
                }
                JSONObject data = (JSONObject)json_posts.get("data");
                JSONArray children = (JSONArray)data.get("children");
                
                for (Object post_o : children) {
                    JSONObject post = (JSONObject)post_o;
                    
                    Post new_post = new Post(
                        (String)post.get("id"),
                        (String)post.get("url"),
                        (String)post.get("title"),
                        (String)post.get("author"));
                    
                    new_post.text_content = (String)post.get("selftext");
                    
                    posts.add(new_post);
                }
                
                ret.content(posts);
            }
        }, executor);
        
        return ret;
    }
}
