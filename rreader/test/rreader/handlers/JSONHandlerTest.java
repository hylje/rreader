/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader.handlers;

import rreader.handlers.JSONHandler;
import com.ning.http.client.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
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
    
    @Test(expected=ParseException.class)
    public void testOnCompletedInvalidJSON() throws Exception {
        Response resp = mock(Response.class);
        when(resp.getResponseBody()).thenReturn("[[null, 123.45, \"a\\tb c\"]}, true");
        
        instance.onCompleted(resp);
    }
    
}
