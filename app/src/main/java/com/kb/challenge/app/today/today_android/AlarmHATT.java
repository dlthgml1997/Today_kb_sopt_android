package com.kb.challenge.app.today.today_android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by shineeseo on 2018. 11. 18..
 */

public class AlarmHATT {
    private Context context;
    public AlarmHATT(Context context) {
        this.context=context;
    }
    public void Alarm(int hours, int minutes, int seconds) {
        Log.v("alarm!!!", "alarm!!!");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BroadcastD.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hours, minutes, seconds);

        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
}

