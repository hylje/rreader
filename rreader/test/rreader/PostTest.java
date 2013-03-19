/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

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
public class PostTest {
    
    Post post;
    
    public PostTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        post = new Post("abcde", 
                        "http://www.google.com/", 
                        "Fake Reddit Post", 
                        "hylje");
    }
    
    @After
    public void tearDown() {
    }
}
