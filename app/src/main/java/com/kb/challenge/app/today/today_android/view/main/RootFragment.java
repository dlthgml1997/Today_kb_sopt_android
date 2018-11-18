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

public class RootFragment extends Fragment implements Init {
    private static final String TAG = "RootFragment";

    private NetworkService networkService;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

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
        transaction.replace(R.id.root_frame, MainFragment.newInstance());

        transaction.commit();
//        getTodayFeelingData();
        return view;
    }

    public void getTodayFeelingData() {
        Log.v("getTodayFeelingData", "getTodayFeelingData process!!!");
        //통신할 때 보낼 오늘의 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        Log.v("오늘의 날짜의 감정", getTime);
        Call<FeelingDataResponse> requestDetail = networkService.getTodayFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), getTime);
        requestDetail.enqueue(new Callback<FeelingDataResponse>() {
            @Override
            public void onResponse(Call<FeelingDataResponse> call, Response<FeelingDataResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getTodayFeelingData", "getProfileData process2!!!");
                    Log.v("feeling message", response.body().getMessage().toString());

                    ArrayList<FeelingData> feelingDataList = response.body().getData();
                    Log.v("feeling data list!!!", feelingDataList.toString());
                    if (feelingDataList.isEmpty()) {
                        FragmentTransaction transaction = getFragmentManager()
                                .beginTransaction();
  /*
   * When this container fragment is created, we fill it with our first
   * "real" fragment
   */
                        transaction.replace(R.id.root_frame, MainFragment.newInstance());

                        transaction.commit();

                    } else if (feelingDataList.get(0).getBad() != null) {
                        FragmentTransaction transaction = getFragmentManager()
                                .beginTransaction();
  /*
   * When this container fragment is created, we fill it with our first
   * "real" fragment
   */
                        transaction.replace(R.id.root_frame, MainBadFragment.newInstance());

                        transaction.commit();
                    } else if (feelingDataList.get(0).getGood() != null) {
                        FragmentTransaction transaction = getFragmentManager()
                                .beginTransaction();
  /*
   * When this container fragment is created, we fill it with our first
   * "real" fragment
   */
                        transaction.replace(R.id.root_frame, MainGoodFragment.newInstance());

                        transaction.commit();
                    }


                }
            }

            @Override
            public void onFailure(Call<FeelingDataResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
