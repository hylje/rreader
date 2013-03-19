/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Handler class that turns a HTTP response into a JSON object.
 * 
 * Meant to be passed into BoundRequestBuilder.execute(...).
 * 
 * @author leohonka
 */

class JSONHandler extends AsyncCompletionHandler<JSONObject> {

    @Override
    public JSONObject onCompleted(Response response) throws Exception {
        return (JSONObject) JSONValue.parse(response.getResponseBody());
    }

    @Override
    public void onThrowable(Throwable t) {
        // Something wrong happened.
    }
}