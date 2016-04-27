package br.com.cemobile.twittertimelineapp.control.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.cemobile.twittertimelineapp.R;
import br.com.cemobile.twittertimelineapp.control.event.RequestTokenEvent;
import br.com.cemobile.twittertimelineapp.util.GenericUtil;
import de.greenrobot.event.EventBus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by celso on 26/04/16.
 */

@EFragment(R.layout.auth_dialog)
public class AuthorizationDialog extends DialogFragment {

    private Context context;

    private String oauthUrl;

    private String oauthVerifier;

    private RequestToken requestToken;

    @ViewById
    protected WebView webView;

    @ViewById
    protected ProgressBar progressBar;

    @AfterViews
    protected void afterViews() {
        if (context != null) {
            GenericUtil.registerOnEventBus(context);
        }
    }

    public void closeDialog() {
        this.dismiss();
    }

    @Background
    protected void getAccessToken() {
        progressBar.setVisibility(View.VISIBLE);
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            User user = twitter.showUser(accessToken.getUserId());
            Log.i("CELSO", "url=" + user.getOriginalProfileImageURL());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
    }

    public AuthorizationDialog() {
        setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
        setCancelable(true);
        //setShowsDialog(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(oauthUrl);
        webView.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("oauth_verifier") && !authComplete) {
                    authComplete = true;
                    Log.e("Url", url);
                    Uri uri = Uri.parse(url);
                    oauthVerifier = uri.getQueryParameter("oauth_verifier");

                    closeDialog();
                    getAccessToken();
                } else if (url.contains("denied")) {
                    closeDialog();
                    Toast.makeText(context, getContext().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (context != null) {
            GenericUtil.registerOnEventBus(context);
        }
    }

    @Override
    public void onPause() {
        GenericUtil.unregisterOnEventBus(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    public void onEvent(RequestTokenEvent event) {
        this.requestToken = event.getRequestToken();
    }

}
