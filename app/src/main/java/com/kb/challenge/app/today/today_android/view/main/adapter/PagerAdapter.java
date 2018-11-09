package com.kb.challenge.app.today.today_android.view.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.kb.challenge.app.today.today_android.view.coin.CoinFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityFragment;
import com.kb.challenge.app.today.today_android.view.community.CommunityRootFragment;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;
import com.kb.challenge.app.today.today_android.view.main.RootFragment;
import com.kb.challenge.app.today.today_android.view.setting.SettingFragment;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RootFragment();
            case 1:
                return new CommunityRootFragment();
            case 2:
                return CoinFragment.newInstance();
            case 3:
                return SettingFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
