package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

/**
 * Created by ektoplasma on 29/08/16.
 */
public class InstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = this;

        Caller.setCtx(ctx);
        Caller.storePersistantCookieString();
        if(Caller.getCookieInstance() != null)
        {
            Caller.checkCookie();
        }
        else
        {
            Intent signIntent = new Intent(ctx, SignActivity.class);
            ctx.startActivity(signIntent);
        }
    }
}
