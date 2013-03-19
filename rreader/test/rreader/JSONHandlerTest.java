/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.Response;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author leohonka
 */
public class JSONHandlerTest {
    JSONHandler instance;
    
    public JSONHandlerTest() {
    }
    
    @Before
    public void setUp() {
        instance = new JSONHandler();
    }

    @Test
    public void testOnCompletedParsesJSON() throws Exception {
        Response resp = mock(Response.class);
        when(resp.getResponseBody()).thenReturn("{\"data\": \"nope\"}");
        
        JSONObject ret = instance.onCompleted(resp);
        
        assertEquals(ret.get("data"), "nope");
    }
}
