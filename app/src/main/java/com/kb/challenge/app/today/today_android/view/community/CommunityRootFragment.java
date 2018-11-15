package com.kb.challenge.app.today.today_android.view.community;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;

/**
 * Created by shineeseo on 2018. 11. 9..
 */

public class CommunityRootFragment extends Fragment {
    private static final String TAG = "CommunityRootFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
  /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.community_root_fragment, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        transaction.replace(R.id.root_frame2, CommunityFragment.newInstance());

        transaction.commit();

        return view;
    }
}
