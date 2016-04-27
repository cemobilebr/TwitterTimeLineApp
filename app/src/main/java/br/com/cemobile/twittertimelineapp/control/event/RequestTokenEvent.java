package br.com.cemobile.twittertimelineapp.control.event;

import twitter4j.auth.RequestToken;

/**
 * Created by celso on 27/04/16.
 */
public class RequestTokenEvent {

    private final RequestToken requestToken;

    public RequestTokenEvent(RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }
}
