/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import java.io.IOException;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author leohonka
 */
public class Http {
    public AsyncHttpClient http_client;
    
    public Http() {
        this.http_client = new AsyncHttpClient();
    }   
    
    public ListenableFuture<JSONObject> 
            get(String url) throws IOException {
        return this.http_client.prepareGet(url).execute(new JSONHandler());
    }
    
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
