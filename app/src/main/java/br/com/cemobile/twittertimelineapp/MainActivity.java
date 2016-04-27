package br.com.cemobile.twittertimelineapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import br.com.cemobile.twittertimelineapp.control.fragment.LoginFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;

    protected LoginFragment_ loginFragment;

    @AfterViews
    protected void afterViews() {
        this.fragmentManager = this.getSupportFragmentManager();

        if (this.fragmentManager.findFragmentById(R.id.fragmentLayout) == null) {
            this.loginFragment = new LoginFragment_();

            this.fragmentManager.beginTransaction()
                    .add(R.id.fragmentLayout, this.loginFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
