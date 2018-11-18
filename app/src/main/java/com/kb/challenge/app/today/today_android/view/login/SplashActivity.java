package com.kb.challenge.app.today.today_android.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kb.challenge.app.today.today_android.MainActivity;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;

public class SplashActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreference.Companion.getInstance().load(this);
        if (SharedPreference.Companion.getInstance().getPrefStringData("data").isEmpty()) {
            intent = new Intent(this,LoginActivity.class);
        }
        else {
            Log.v("token 존재", SharedPreference.Companion.getInstance().getPrefStringData("data"));
            intent = new Intent(this, MainActivity.class);

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);// 3 초
    }
}
