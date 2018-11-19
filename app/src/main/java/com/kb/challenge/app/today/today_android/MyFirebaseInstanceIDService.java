package com.kb.challenge.app.today.today_android;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by shineeseo on 2018. 11. 17..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    // 토큰 재생성
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token = " + token);
    }
}
