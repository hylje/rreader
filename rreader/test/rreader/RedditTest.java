/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.ListenableFuture;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author leohonka
 */
public class RedditTest {
    Reddit instance;
    
    public RedditTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Executor executor = Executors.newSingleThreadExecutor();    
        instance = new Reddit(executor);
        
        Http mockHttp = mock(Http.class);
        instance.http = mockHttp;
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetPosts() throws Exception {
        ListenableFuture futu;
        futu = mock(ListenableFuture.class);
        
        // mock the get method to return our special future
        when(instance.http.get(any(String.class))).thenReturn(futu);
        
        // the special future's addListener method 
        // needs to be hacked functional
        when(futu.addListener(any(Runnable.class), 
                              any(Executor.class))).thenAnswer(
            new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    Object[] args = invocation.getArguments();
                    Runnable run = (Runnable)args[0];
                    run.run();
                    return null;
                }
            }
        );
        
        // finally as the listener runs we feed it our special JSON
        when(futu.get()).thenReturn(new JSONObject() {{
            put("data", new JSONObject() {{
                put("children", new JSONArray() {{
                    add(new JSONObject() {{
                        put("data", new JSONObject() {{
                            put("author", "hylje");
                            put("url", "http://www.google.com/");
                            put("id", "abcde");
                            put("title", "Yet another fake Reddit post");
                        }});
                    }});
                    add(new JSONObject() {{
                        put("data", new JSONObject() {{
                            put("author", "hylje");
                            put("url", "http://www.bash.org/");
                            put("id", "abcdf");
                            put("title", "You gotta be kidding me");
                        }});
                    }});
                }});
            }});
        }});
        
        ListenableFuture<List<Post>> post_futu = instance.get_posts("foo");
        List<Post> posts = post_futu.get();
        assertEquals(posts.get(0).author, "hylje");
        assertEquals(posts.get(0).id, "abcde");
        assertEquals(posts.get(1).title, "You gotta be kidding me");
    }
}
