/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import rreader.handlers.JSONHandler;
import rreader.handlers.StringHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * Provides HTTP request methods for the application.
 * 
 * Requests are processed to JSON responses unless noted otherwise.
 *  
 * @author leohonka
 */
public class Http {
    public AsyncHttpClient http_client;
    
    public Http() {
        this.http_client = new AsyncHttpClient();
    }   
    /**
     * Prepare a HTTP GET request
     * 
     * @param url
     * @return
     * @throws IOException 
     */
    public ListenableFuture<JSONObject> 
            get(String url) throws IOException {
        return this.http_client
                .prepareGet(url)
                .addHeader("User-Agent", "RReader 0.1 by /u/hylje")
                .execute(new JSONHandler());
    }
    /**
     * Prepare a HTTP GET request to get an unprocessed string (of HTML)
     * 
     * @param url
     * @return
     * @throws IOException 
     */
    public ListenableFuture<String> get_html(String url) throws IOException {
        return this.http_client.prepareGet(url).execute(new StringHandler());
    }
    
    /**
     * Prepare a HTTP POST request.
     * 
     * Not used in the application at the moment, but is needed for e.g. 
     * logged-in user capabilities.
     * 
     * @param url
     * @param parameter_map
     * @return
     * @throws IOException 
     */
    public ListenableFuture<JSONObject> 
            post(String url, 
                 Map<String, String> parameter_map) 
            throws IOException {
        ///fgadgoarg
        AsyncHttpClient.BoundRequestBuilder req = this.http_client.preparePost(url);
        for (String key : parameter_map.keySet()) {
            req.addParameter(key, parameter_map.get(key));
        }   
        return req.execute(new JSONHandler());
    }
}
