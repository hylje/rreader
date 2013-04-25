/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader.handlers;

import com.ning.http.client.ListenableFuture;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rreader.Post;
import rreader.Reddit;

/**
 * Processes JSON request results into cached Post objects
 * 
 * @author leohonka
 */
public class PostProcessor implements Runnable {
    private final ListenableFuture<JSONObject> json_future;
    private final ListenableFuture<List<Post>> ret;

    public PostProcessor(ListenableFuture<JSONObject> json_future, ListenableFuture<List<Post>> ret) {
        this.json_future = json_future;
        this.ret = ret;
    }

    @Override
    public void run() {
        List<Post> posts = new LinkedList<>();
        JSONObject json_posts;
        try {
            json_posts = json_future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Reddit.class.getName()).log(Level.SEVERE, null, ex);
            ret.content(posts);
            return;
        }
        JSONObject data = (JSONObject) json_posts.get("data");
        JSONArray children = (JSONArray) data.get("children");
        for (Object post_o : children) {
            JSONObject post = (JSONObject) post_o;
            JSONObject post_data = (JSONObject) post.get("data");
            Post new_post = new Post((String) post_data.get("id"), (String) post_data.get("url"), (String) post_data.get("title"), (String) post_data.get("author"));
            String selftext = (String) post_data.get("selftext");
            new_post.text_content = selftext != null ? selftext : "";
            posts.add(new_post);
        }
        ret.content(posts);
    }
    
}
