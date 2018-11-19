package com.kb.challenge.app.today.today_android.view.community;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.FollowListResponse;
import com.kb.challenge.app.today.today_android.model.community.FollowerData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.MainActivity;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFollowerListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityUserFragment extends Fragment implements Init {
    private Button community_follow_btn;
    private Button community_following_btn;
    private ImageView community_ic_cancel;
    private ImageView img_search_user_follow;
    private NetworkService networkService;
    private String user_id;
    private ArrayList<FollowerData> followerDataList;
    private int flag = 0;
    //내가 팔로우하는 사람들의 리스트

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();

    }
    public CommunityUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_user_follow, container, false);

        ((MainActivity) getActivity()).inVisibleTabLayout();

        init();

        img_search_user_follow = (ImageView)view.findViewById(R.id.img_search_user_follow);

        TextView community_user_name_txt = (TextView)view.findViewById(R.id.community_user_name_txt);

        TextView community_user_id_txt = (TextView)view.findViewById(R.id.community_user_id_txt);

        community_follow_btn= view.findViewById(R.id.community_follow_btn);
        community_following_btn= view.findViewById(R.id.community_following_btn);

        getFollowerList();

        Bundle bundle = getArguments();

        community_user_name_txt.setText(bundle.getString("userName"));
        community_user_id_txt.setText(bundle.getString("userId"));

        user_id = community_user_id_txt.getText().toString();

        Glide.with(getActivity())
                .load(bundle.getString("profile_url"))
                .into(img_search_user_follow);

        img_search_user_follow.setBackground(new ShapeDrawable(new OvalShape()));

        if(Build.VERSION.SDK_INT >= 21) {
            img_search_user_follow.setClipToOutline(true);
        }


        //노란색 -> 나를 팔로우하는 사람 (나는 관심 없음 )
        community_follow_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                community_follow_btn.setVisibility(View.INVISIBLE);
                community_following_btn.setVisibility(View.VISIBLE);
                follow();
            }
        });

        //흰색 -> 내가 팔로우하는 사람 (내가 좋아하는 사람)
        community_following_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                community_follow_btn.setVisibility(View.VISIBLE);
                community_following_btn.setVisibility(View.INVISIBLE);
                cancelFollow();
            }
        });

        community_ic_cancel=(ImageView)view.findViewById(R.id.community_ic_cancel);
        community_ic_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).visibleTabLayout();
                getFragmentManager().popBackStack();

            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).visibleTabLayout();

    }
    public void follow() {
        Log.v("follow process", "follow process!!!");
        Log.i("맞팔할 id", user_id);
        Call<BaseModel> requestDetail = networkService.followUser(SharedPreference.Companion.getInstance().getPrefStringData("data"),user_id);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("follow", "follow process2!!!");
                    Log.i("follow message", response.body().getMessage().toString());

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void cancelFollow() {
        Log.v("cancelFollow process", "cancelFollow process!!!");
        Log.v("follow id", user_id);
        Call<BaseModel> requestDetail = networkService.cancelFollow(SharedPreference.Companion.getInstance().getPrefStringData("data"),user_id);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("follow", "follow process2!!!");
                    Log.v("cancel message", response.body().getMessage().toString());


                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void getFollowerList() {
        Log.v("getFollowerList process", "getFollowerList process!!!");
        Call<FollowListResponse> requestDetail = networkService.getFollowerList(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<FollowListResponse>() {
            @Override
            public void onResponse(Call<FollowListResponse> call, Response<FollowListResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getFollowerList", "getFollowerList process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("success")) {
                        followerDataList = response.body().getData();
                        Log.v("followerDataList", followerDataList.toString());
                        //profile_img, id, name

                        for (int i= 0; i < followerDataList.size(); i++) {
                            if (followerDataList.get(i).getId().equals(user_id))
                                flag = 1;
                        }
                        Log.v("flagggg", flag + "");
                        if (flag == 1) {
                            community_follow_btn.setVisibility(View.INVISIBLE);
                            community_following_btn.setVisibility(View.VISIBLE);

                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<FollowListResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}