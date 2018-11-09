package com.kb.challenge.app.today.today_android.view.login.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kb.challenge.app.today.today_android.view.login.PickTimeFragment;
import com.kb.challenge.app.today.today_android.view.login.SetChangeFuncFragment;
import com.kb.challenge.app.today.today_android.view.login.SetNameFragment;

public class PagerAdapter2 extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter2(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PickTimeFragment tab1 = new PickTimeFragment();
                return tab1;
            case 1:
                SetChangeFuncFragment tab2 = new SetChangeFuncFragment();
                return tab2;
            case 2:
                SetNameFragment tab3 = new SetNameFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
