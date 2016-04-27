package br.com.cemobile.twittertimelineapp.control.event;

import android.content.Context;

import de.greenrobot.event.EventBus;

/**
 * Created by celso on 27/04/16.
 */
public class EventManager {

    private static EventManager instance;

    private Context context;

    private EventManager() { }

    public EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }

        return instance;
    }

    public void init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }



}
