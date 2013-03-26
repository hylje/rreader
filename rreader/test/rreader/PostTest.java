/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.matchers.JUnitMatchers;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

/**
 *
 * @author leohonka
 */
public class PostTest {
    
    Post post;
    
    public PostTest() {
    }
    
    @Before
    public void setUp() {
        post = new Post("abcde", 
                        "http://www.google.com/", 
                        "Fake Reddit Post", 
                        "hylje");
    }
   
    @Test
    public void testGetPostContentIOFail() throws Exception {
        Reddit reddit = mock(Reddit.class);
        reddit.http = mock(Http.class);
        
        when(reddit.http.get_html(any(String.class))).thenThrow(new IOException());
        
        ListenableFuture<Post> postfutu = post.get_text_content(reddit);
        
        Post returned_post = postfutu.get();
        
        assertEquals(returned_post, post);
        assertThat(returned_post.text_content, 
                   JUnitMatchers.containsString("connection error"));
    }
    
    @Test
    public void testGetPostContentTrivial() throws Exception {
        Reddit reddit = mock(Reddit.class);
        post.text_content = "fofof";
        
        ListenableFuture<Post> postfutu = post.get_text_content(reddit);
        
        Post returned_post = postfutu.get();
        
        assertEquals(returned_post, post);
        assertEquals(returned_post.text_content, "fofof");
    }
}
