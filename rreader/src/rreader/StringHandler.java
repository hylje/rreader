/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rreader;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
/**
 *
 * @author leohonka
 */
class StringHandler extends AsyncCompletionHandler<String> {
    @Override
    public String onCompleted(Response response) throws Exception {
        return response.getResponseBody();
    }

    @Override
    public void onThrowable(Throwable t) {
        // Something wrong happened.
    }
}
