
package com.kb.challenge.app.today.today_android.view.main;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.coin.CoinFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFollowerFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFollowingFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFragment;
import com.kb.challenge.app.today.today_android.view.login.SplashActivity;
import com.kb.challenge.app.today.today_android.view.main.adapter.PagerAdapter;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;
import com.kb.challenge.app.today.today_android.view.setting.SettingFragment;

import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        CommunityFragment.OnFragmentInteractionListener,
        CoinFragment.OnFragmentInteractionListener,
        SettingFragment.OnFragmentInteractionListener,
        MainGoodFragment.OnFragmentInteractionListener,
        MainDepositFragment.OnFragmentInteractionListener,
        CommunityFollowingFragment.OnFragmentInteractionListener,
        CommunityFollowerFragment.OnFragmentInteractionListener,
        MainBadFragment.OnFragmentInteractionListener,
        RecordFeelingFragment.OnFragmentInteractionListener {

    Long backKeyPressedTime = 0L;
    private PagerAdapter adapter;
    private CustomViewPager viewPager;


    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStack();
        else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tablayout, viewPager 적용
        adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        final ImageView mainIcon = new ImageView(this);
        mainIcon.setImageResource(R.drawable.ic_home_24_px);
        mainIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView commuIcon = new ImageView(this);
        commuIcon.setImageResource(R.drawable.ic_community_24_px);
        commuIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView coinIcon = new ImageView(this);
        coinIcon.setImageResource(R.drawable.ic_coin_24_px);
        coinIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView settingIcon = new ImageView(this);
        settingIcon.setImageResource(R.drawable.ic_setting_24_px);
        settingIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //tablayout icon 선택시
        final ImageView mainIconAct = new ImageView(this);
        mainIconAct.setImageResource(R.drawable.ic_home_active_24_px);
        mainIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView commuIconAct = new ImageView(this);
        commuIconAct.setImageResource(R.drawable.ic_community_active_24_px);
        commuIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView coinIconAct = new ImageView(this);
        coinIconAct.setImageResource(R.drawable.ic_coin_active_24_px);
        coinIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView settingIconAct = new ImageView(this);
        settingIconAct.setImageResource(R.drawable.ic_setting_active_24_px);
        settingIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tabLayout.getTabAt(0).setCustomView(mainIconAct);
        tabLayout.getTabAt(1).setCustomView(commuIconAct);
        tabLayout.getTabAt(2).setCustomView(coinIconAct);
        tabLayout.getTabAt(3).setCustomView(settingIconAct);

        //탭선택시
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView iv = (ImageView) tab.getCustomView();
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.ic_home_active_24_px);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.ic_community_active_24_px);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.ic_coin_active_24_px);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.ic_setting_active_24_px);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab1) {
                ImageView iv = (ImageView) tab1.getCustomView();
                switch (tab1.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.ic_home_24_px);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.ic_community_24_px);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.ic_coin_24_px);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.ic_setting_24_px);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab2) {
                Log.d("reselected tap", String.valueOf(tab2.getPosition()));
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
