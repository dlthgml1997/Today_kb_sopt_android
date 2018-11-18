package com.kb.challenge.app.today.today_android.view.community;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.MainActivity;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.SearchUserData;
import com.kb.challenge.app.today.today_android.model.community.SearchUserResponse;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunitySearchListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityEditStatusFragment extends Fragment implements Init {
    private NetworkService networkService;
    private EditText edit_status_change;
    private TextView txt_ok_status;
    private ImageView community_back_btn_status;
    private int feeling_data;

    public static CommunityEditStatusFragment newInstance(/*String param1, String param2*/) {
        CommunityEditStatusFragment fragment = new CommunityEditStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_status, container, false);

        init();

        Bundle bundle = getArguments();

        feeling_data = bundle.getInt("feeling_data");
        edit_status_change = (EditText) view.findViewById(R.id.edit_status_change);

        edit_status_change.setHint(bundle.getString("hint"));


        //확인 버튼 눌렀을 때
        txt_ok_status = (TextView) view.findViewById(R.id.txt_ok_status);
        txt_ok_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordFeeling();
                //서버로 보낸 후 뒤로가기
                ((MainActivity) getActivity()).visibleTabLayout();
                getFragmentManager().popBackStack();
            }
        });

        //뒤로돌아가기
        ImageView community_back_btn_status = (ImageView) view.findViewById(R.id.community_back_btn_status);
        community_back_btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).visibleTabLayout();
                getFragmentManager().popBackStack();

            }
        });

        return view;

    }
//
//    public static void hideKeyboardFrom(Context context, View view) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).inVisibleTabLayout();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).visibleTabLayout();

    }

    public void recordFeeling() {
        //감정기록하기 (token 값, feelingdata)
        FeelingData feelingData;
        if (feeling_data < 3)
            feelingData = new FeelingData(null, 6-feeling_data-3, edit_status_change.getText().toString());
        else
            feelingData = new FeelingData(feeling_data-3, null, edit_status_change.getText().toString());
        final Call<BaseModel> requestDetail = networkService.recordFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), feelingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("edit status messge", "edit status message process2!!!");
                    Log.v("edit message", response.body().getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}