package rreader.handlers;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Handler class that turns a HTTP response into a JSON object.
 * 
 * Meant to be passed into BoundRequestBuilder.execute(...).
 * 
 * @author leohonka
 */

public class JSONHandler extends AsyncCompletionHandler<JSONObject> {
    static final JSONParser parser = new JSONParser();

    @Override
    public JSONObject onCompleted(Response response) throws IOException, ParseException {
        try {
            return (JSONObject) parser.parse(response.getResponseBody());
        } catch (ParseException ex) {
            Logger.getLogger(JSONHandler.class.getName()).log(
                    Level.SEVERE, 
                    "input:" + response.getResponseBody(), 
                    ex);
            throw ex;
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        // Something wrong happened.
    }
}