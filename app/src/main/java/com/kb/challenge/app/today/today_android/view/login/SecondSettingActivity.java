package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.login.adpater.PagerAdapter2;
import com.kb.challenge.app.today.today_android.view.main.CustomViewPager;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

public class SecondSettingActivity extends AppCompatActivity {
    private  CustomViewPager mPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondsetting);
     /*   FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container,new SetTitleFragment());
        fragmentTransaction.commit();
*/
      /*  mPager = (CustomViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(new PagerAdapter2(getSupportFragmentManager()));
        mPager.setPagingEnabled(false);*/
        Button btn_act_second_set_next = (Button)findViewById(R.id.btn_act_second_set_next);
        btn_act_second_set_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondSettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
