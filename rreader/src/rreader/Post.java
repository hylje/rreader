/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

/**
 *
 * @author leohonka
 */
public class Post {
    public String id;
    public String url;
    public String title;
    public String author;
    
    /**
     * Create a basic reddit Post with core information only
     *
     * @param id Reddit Base36 ID
     * @param url External URL to post content
     * @param title Reddit user provided title for this post
     * @param author Reddit user responsible for this post
     */
    public Post(String id, String url, String title, String author) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.author = author;
    }
}
