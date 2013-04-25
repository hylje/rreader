package rreader.handlers;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
/**
 * Dummy handler that doesn't process the response content
 *
 * @author leohonka
 */
public class StringHandler extends AsyncCompletionHandler<String> {
    @Override
    public String onCompleted(Response response) throws Exception {
        return response.getResponseBody();
    }

    @Override
    public void onThrowable(Throwable t) {
        // Something wrong happened.
    }
}
