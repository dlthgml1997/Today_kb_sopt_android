
package com.kb.challenge.app.today.today_android;


import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.model.push.PushTimeData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.coin.CoinFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityEmotionBox;
import com.kb.challenge.app.today.today_android.view.community.CommunityFollowerFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFollowingFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFragment;
import com.kb.challenge.app.today.today_android.view.login.SetNameFragment;
import com.kb.challenge.app.today.today_android.view.main.CustomViewPager;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;
import com.kb.challenge.app.today.today_android.view.main.MainDepositFragment;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;
import com.kb.challenge.app.today.today_android.view.main.adapter.PagerAdapter;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;
import com.kb.challenge.app.today.today_android.view.setting.PickTimeFragment_setting;
import com.kb.challenge.app.today.today_android.view.setting.SettingFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        RecordFeelingFragment.OnFragmentInteractionListener,
        CommunityEmotionBox.OnFragmentInteractionListener, Init,
        PickTimeFragment_setting.OnFragmentInteractionListener {


    private static final String TAG = MainActivity.class.getSimpleName();

    Long backKeyPressedTime = 0L;
    private PagerAdapter adapter;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private NetworkService networkService;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStack();
        else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getPushTime();

        //tablayout, viewPager 적용
        adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
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
        tabLayout.getTabAt(1).setCustomView(commuIcon);
        tabLayout.getTabAt(2).setCustomView(coinIcon);
        tabLayout.getTabAt(3).setCustomView(settingIcon);

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

    public void inVisibleTabLayout() {
        ((ViewGroup) tabLayout).setVisibility(View.GONE);

    }

    public void visibleTabLayout() {
        ((ViewGroup) tabLayout).setVisibility(View.VISIBLE);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void getPushTime() {
        Log.v("getPushTime process", "getPushTime process!!!");
        Call<PushTimeData> requestDetail = networkService.getPushTime(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<PushTimeData>() {
            @Override
            public void onResponse(Call<PushTimeData> call, Response<PushTimeData> response) {
                if (response.isSuccessful()) {
                    Log.v("getPushTime process2", "savingList process2!!!");
                    Log.v("getPushTime message", response.body().getMessage().toString());

                    PushTimeData pushTimeData = response.body();
                    new AlarmHATT(getApplicationContext()).Alarm(Integer.parseInt(pushTimeData.getHour()), Integer.parseInt(pushTimeData.getMinute()), Integer.parseInt(pushTimeData.getSecond()));


                }
            }

            @Override
            public void onFailure(Call<PushTimeData> call, Throwable t) {
                Log.i("err saving", t.getMessage());
            }
        });
    }
}
