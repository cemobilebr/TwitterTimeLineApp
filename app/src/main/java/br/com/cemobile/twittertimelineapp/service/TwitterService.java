package br.com.cemobile.twittertimelineapp.service;

import android.support.annotation.UiThread;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.Observable;
import java.util.Observer;

import br.com.cemobile.twittertimelineapp.Constant;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 * Created by celso on 26/04/16.
 */

@EBean
public class TwitterService extends Observable {

    private RequestToken requestToken;

    private Twitter twitter;

    public TwitterService() {
        init();
    }

    @Background
    public void getAuthorizationToken(Observer observer) {
        addObserver(observer);

        try {
            requestToken = twitter.getOAuthRequestToken();
            handleResult();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Constant.TWITTER_API_KEY, Constant.TWITTER_API_SECRET);
    }

    @UiThread
    protected void handleResult() {
        this.setChanged();
        this.notifyObservers(requestToken);
        this.deleteObservers();
    }

}
