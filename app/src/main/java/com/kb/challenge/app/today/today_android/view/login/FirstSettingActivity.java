package com.kb.challenge.app.today.today_android.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.login.adpater.PagerAdapter2;
import com.kb.challenge.app.today.today_android.view.main.CustomViewPager;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;

public class FirstSettingActivity extends AppCompatActivity {
    // private  CustomViewPager mPager;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstsetting);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, new SetNameFragment());
        fragmentTransaction.commit();

/*
        mPager = (CustomViewPager) findViewById(R.id.viewpager1);
        mPager.setAdapter(new PagerAdapter2(getSupportFragmentManager()));
        mPager.setPagingEnabled(false);*/
        Button btn_act_first_set_next = (Button) findViewById(R.id.btn_act_first_set_next);
        btn_act_first_set_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position == 0) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, new PickTimeFragment());
                    fragmentTransaction.commit();
                    position++;
                } else if (position == 1) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_container, new SetTitleFragment());
                    fragmentTransaction.commit();

                    Button btn_act_first_set_next = (Button) findViewById(R.id.btn_act_first_set_next);
                    btn_act_first_set_next.setText("완료");
                    position++;
                } else {
                    Intent intent = new Intent(FirstSettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
