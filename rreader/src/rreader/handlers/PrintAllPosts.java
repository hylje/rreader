/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader.handlers;

import java.util.List;
import rreader.Post;

/**
 * Utility class for printing out a list of posts after an async request
 *
 * @author leohonka
 */
public class PrintAllPosts implements Runnable {
    private final List<Post> posts;
    
    public PrintAllPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void run() {
        System.out.println("Got all content");
        for (Post p : posts) {
            System.out.println(p.toString());
            if (!p.text_content.isEmpty()) {
                System.out.println(p.text_content);
            }
        }
    }
    
}
