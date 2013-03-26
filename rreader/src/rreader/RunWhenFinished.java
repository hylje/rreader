/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import java.util.List;

/**
 *
 * @author leohonka
 */
class RunWhenFinished implements Runnable {
    private final List<Post> posts;
    
    public RunWhenFinished(List<Post> posts) {
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
