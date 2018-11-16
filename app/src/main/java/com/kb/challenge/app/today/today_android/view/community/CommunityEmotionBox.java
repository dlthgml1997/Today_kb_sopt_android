package com.kb.challenge.app.today.today_android.view.community;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.EmotionBoxData;
import com.kb.challenge.app.today.today_android.model.community.EmotionBoxResponse;
import com.kb.challenge.app.today.today_android.model.community.FollowListResponse;
import com.kb.challenge.app.today.today_android.model.community.FollowerData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityEmotionBoxListAdapter;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFollowerListAdapter;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 16..
 */

public class CommunityEmotionBox extends Fragment implements Init {
    private CommunityEmotionBox.OnFragmentInteractionListener mListener;

    private NetworkService networkService;

    private RecyclerView mRecyclerView;

    private ImageView community_emotion_profile_img;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    public CommunityEmotionBox() {
        // Required empty public constructor
    }

    public static CommunityEmotionBox newInstance(/*String param1, String param2*/) {
        CommunityEmotionBox fragment = new CommunityEmotionBox();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.community_emotion_box, container, false);

        //탭 레이아웃 감춤
        ((MainActivity) getActivity()).inVisibleTabLayout();

        init();

        ImageView community_back_btn = (ImageView) view.findViewById(R.id.community_back_btn);

        community_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("뒤로 가기 버튼", "뒤로 가기 버튼");
                ((MainActivity) getActivity()).visibleTabLayout();
                getFragmentManager().popBackStack();
            }
        });

        community_emotion_profile_img = (ImageView) view.findViewById(R.id.community_emotion_profile_img);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.community_emotion_box_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), new LinearLayoutManager(getActivity()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        getEmotionBoxList();
        return view;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunityEmotionBox.OnFragmentInteractionListener) {
            mListener = (CommunityEmotionBox.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).visibleTabLayout();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getEmotionBoxList() {
        //오늘 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        Log.v("getEmotionBoxList", "getEmotionBoxList process!!!");
        Call<EmotionBoxResponse> requestDetail = networkService.getEmotionBoxList(SharedPreference.Companion.getInstance().getPrefStringData("data"), "2018-11-16");
        requestDetail.enqueue(new Callback<EmotionBoxResponse>() {
            @Override
            public void onResponse(Call<EmotionBoxResponse> call, Response<EmotionBoxResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getEmotionBoxList", "getEmotionBoxList process2!!!");
                    Log.v("emotion message", response.body().getMessage().toString());

                    ArrayList<EmotionBoxData> emotionBoxList = response.body().getData();
                    if (emotionBoxList != null) {

                        CommunityEmotionBoxListAdapter communityEmotionBoxListAdapter = new CommunityEmotionBoxListAdapter(getActivity(), emotionBoxList);
                        mRecyclerView.setAdapter(communityEmotionBoxListAdapter);

                    }
                }

            }

            @Override
            public void onFailure(Call<EmotionBoxResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
