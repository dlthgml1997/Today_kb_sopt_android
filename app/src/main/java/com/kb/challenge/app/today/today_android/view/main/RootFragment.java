package com.kb.challenge.app.today.today_android.view.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.model.record.FeelingDataResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment.feelingMsg;

/**
 * Created by shineeseo on 2018. 11. 7..
 */

public class RootFragment extends Fragment {
    private static final String TAG = "RootFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
  /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.root_fragment, container, false);
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
  /*
   * When this container fragment is created, we fill it with our first
   * "real" fragment
   */
        transaction.replace(R.id.root_frame, MainFragment.newInstance(), TAG);
        transaction.addToBackStack(TAG);
        transaction.commit();

        return view;
    }

}