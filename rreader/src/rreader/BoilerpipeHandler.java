/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.ListenableFuture;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leohonka
 */
class BoilerpipeHandler implements Runnable {
    Post post;
    ListenableFuture<Post> return_future;
    ListenableFuture<String> request_future;
    
    BoilerpipeHandler(Post _post, ListenableFuture<Post> ret, ListenableFuture<String> html) {
        post = _post;
        return_future = ret;
        request_future = html;
    }

    @Override
    public void run() {
        String html;
        
        try {
            html = request_future.get();
            post.text_content = ArticleExtractor.getInstance().getText(html);
        } catch (InterruptedException | ExecutionException | BoilerpipeProcessingException ex) {
            Logger.getLogger(BoilerpipeHandler.class.getName()).log(Level.SEVERE, null, ex);
            post.text_content = "<execution error: " + ex.getMessage() + ">";
        }
        
        return_future.content(post);
    }
}
