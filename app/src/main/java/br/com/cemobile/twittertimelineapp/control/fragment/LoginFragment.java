package br.com.cemobile.twittertimelineapp.control.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Observable;
import java.util.Observer;

import br.com.cemobile.twittertimelineapp.Constant;
import br.com.cemobile.twittertimelineapp.R;
import br.com.cemobile.twittertimelineapp.control.dialog.AuthorizationDialog;
import br.com.cemobile.twittertimelineapp.control.event.RequestTokenEvent;
import br.com.cemobile.twittertimelineapp.service.TwitterService;
import de.greenrobot.event.EventBus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by celso on 26/04/16.
 */
@EFragment(R.layout.login_fragment)
public class LoginFragment extends Fragment implements Observer {

    private RequestToken requestToken;

    private String oauthUrl;

    @ViewById
    protected Button loginButton;

    private Twitter twitter;

    @Click(R.id.loginButton)
    protected void click() {
        TwitterService service = new TwitterService();
        service.getAuthorizationToken(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        requestToken = (RequestToken) data;
        if (requestToken != null) {
            AuthorizationDialog dialog = new AuthorizationDialog();

            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentLayout, dialog)
                    .addToBackStack(null)
                    .commit();

            EventBus.getDefault().post(requestToken);
        }
    }
}
