package com.teamjaj.agourd.valoulou.jajmeup.services.tasks;

import android.content.Context;

import com.teamjaj.agourd.valoulou.jajmeup.services.FriendshipService;

import java.util.TimerTask;

public class DataFetchingTask extends TimerTask {

    private Context context;

    public DataFetchingTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        FriendshipService service = new FriendshipService();
        service.getPendingFriendshipRequest(context);
    }
}
