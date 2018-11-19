package com.kb.challenge.app.today.today_android.view.main;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.cheerup.CheerupMsgData;
import com.kb.challenge.app.today.today_android.model.cheerup.CheerupMsgResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.view.main.adapter.CheerupMsgListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 7..
 */

public class MainBadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "MainBadFragment";

    private MainBadFragment.OnFragmentInteractionListener mListener;
    private NetworkService networkService;
    private RecyclerView mRecyclerView;
    private ImageView main_bad_image;
    private TextView txt_main_bad_user_name; //유저네임 님 힘네세요
    private TextView txt_main_bad_user_name2; //유저네임 님이 행복했던 3일

    public MainBadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainBadFragment newInstance(/*String param1, String param2*/) {
        MainBadFragment fragment = new MainBadFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        View view1 = getActivity().getWindow().getDecorView();
        view1.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#f2f2f2"));

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
        View view = inflater.inflate(R.layout.main_record_bad, container, false);

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_bad_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        txt_main_bad_user_name = (TextView)view.findViewById(R.id.txt_main_bad_user_name);
        txt_main_bad_user_name2 = (TextView)view.findViewById(R.id.txt_main_bad_user_name2);
        main_bad_image = (ImageView) view.findViewById(R.id.main_bad_image);
        getCheerupMsg();

        return view;

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainBadFragment.OnFragmentInteractionListener) {
            mListener = (MainBadFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getCheerupMsg() {
        Log.v("getCheerupMsg", "getCheerupMsg process!!!");
        Call<CheerupMsgResponse> requestDetail = networkService.getCheerupMsg(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<CheerupMsgResponse>() {
            @Override
            public void onResponse(Call<CheerupMsgResponse> call, Response<CheerupMsgResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getCheerupMsg", "getCheerupMsg process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    ArrayList<CheerupMsgData> cheerupMsgDataList = response.body().getData();
                    Log.v("cheerupmes",cheerupMsgDataList.size() + "");
                    CheerupMsgListAdapter cheerupMsgListAdapter = new CheerupMsgListAdapter(getActivity(),cheerupMsgDataList);
                    mRecyclerView.setAdapter(cheerupMsgListAdapter);
                    if (!response.body().getComfortImg().get(0).getComfort_img().isEmpty()) {
                        Glide.with(getActivity())
                                .load(response.body().getComfortImg().get(0).getComfort_img())
                                .into(main_bad_image);
                    }

                }

            }

            @Override
            public void onFailure(Call<CheerupMsgResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
