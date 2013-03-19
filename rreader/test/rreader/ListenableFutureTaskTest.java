/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author leohonka
 */
public class ListenableFutureTaskTest {
    ListenableFutureTask<String> instance;
    
    public ListenableFutureTaskTest() {
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
        
        instance = new ListenableFutureTask<>();
        instance.addListener(new Runnable() {
            @Override
            public void run() {
                String str;
                try {
                    str = instance.get();
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ListenableFutureTaskTest.class.getName()).log(Level.SEVERE, null, ex);
                    str = "";
                }
                System.out.println("Listener got message: " + str);
            }
        }, executor);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testContentAssignNull() {
        Object v = null;
        instance.content(v); 
    }
    
    @Test
    public void testContentAssignStr() {
        Object v = (Object)"Test string";
        instance.content(v);
    }

    @Test
    public void testGet_0argsReturnsNull() throws Exception {
        Object expResult = null;
        Object result = instance.get();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGet_0argsReturnsStr() throws Exception {
        String expResult = "Return of the Test String";
        instance.content((Object)expResult);
        String result = instance.get();
        assertEquals(expResult, result);
    }
    
    // rest of the methods are stubs
}
