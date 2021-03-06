/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import rreader.handlers.BoilerpipeHandler;

/**
 * Representation of a Reddit submission
 * 
 * Only relevant fields are included from the full data.
 *
 * @author leohonka
 */
public class Post {
    public String id;
    public String url;
    public String title;
    public String author;
    public String text_content;
    
    /**
     * Create a basic reddit Post with core information only
     *
     * @param id Reddit Base36 ID
     * @param url External URL to post content
     * @param title Reddit user provided title for this post
     * @param author Reddit user responsible for this post
     * @param text_content Cached text to display. Populated with an 
     *                      asynchronous call to get_text_content.
     */
    public Post(String id, String url, String title, String author) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.author = author;
        this.text_content = "";
    }
    
    /**
     * Populate text_content field
     *
     * @param reddit Reddit class to access API with
     * @return a ListenableFuture<Post> that runs when content is available
     */
    public ListenableFuture<Post> get_text_content(Reddit reddit) {
        ListenableFuture<Post> ret = new ListenableFutureTask<Post>();
        
        if (!text_content.isEmpty()) { 
            ret.content(this);
        } else {
            try {
                ListenableFuture<String> html = reddit.http.get_html(url);
                html.addListener(
                        new BoilerpipeHandler(this, ret, html), 
                        reddit.executor);
            } catch (IOException ex) {
                text_content = "<connection error:" + ex.getMessage() + ">";
                ret.content(this);
            }
        }
        
        return ret;
    }
    
    @Override
    public String toString() {
        String s_content = text_content.isEmpty() ? "no" : "YES!";
        
        return String.format(
            "<Post: id=%s, title=%s, text_content=%s>",
            id, 
            title, 
            s_content);        
    }
}
