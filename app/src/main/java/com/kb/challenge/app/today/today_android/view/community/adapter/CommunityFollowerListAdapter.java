package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.FollowerData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.login.LoginActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 9..
 */

public class CommunityFollowerListAdapter extends RecyclerView.Adapter<CommunityFollowerListAdapter.ViewHolder>  implements Init {
    Context context;
    ArrayList<FollowerData> communityFollowingList;
    private NetworkService networkService;
    private String follow_id;
    private BaseModel msg;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(context);
    }

    public CommunityFollowerListAdapter(Context context, ArrayList<FollowerData> communityFollowingList) {
        super();
        this.context = context;
        this.communityFollowingList = communityFollowingList;
    }

    @Override
    public CommunityFollowerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_following_list, viewGroup, false);
        init();
        CommunityFollowerListAdapter.ViewHolder viewHolder = new CommunityFollowerListAdapter.ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final CommunityFollowerListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        Log.v("communityFriendsList", communityFollowingList.size() + " ");
        viewHolder.community_following_id.setText(communityFollowingList.get(i).getId());
        follow_id = viewHolder.community_following_id.getText().toString();

        viewHolder.community_btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.community_btn_follow.setVisibility(View.GONE);
                viewHolder.community_btn_follower.setVisibility(View.VISIBLE);

            }
        });
        viewHolder.community_btn_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.community_btn_follow.setVisibility(View.VISIBLE);
                viewHolder.community_btn_follower.setVisibility(View.GONE);
                //팔로우하기
                follow();
            }
        });

    }

    @Override
    public int getItemCount() {
        return communityFollowingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView community_following_id;
        public ImageView community_following_img;
        public ImageView community_btn_follower;
        public ImageView community_btn_follow;

        public ViewHolder(View itemView) {
            super(itemView);
            community_following_id = (TextView) itemView.findViewById(R.id.community_following_id);
            community_following_img = (ImageView)itemView.findViewById(R.id.community_following_img);
            community_btn_follow = (ImageView)itemView.findViewById(R.id.community_btn_follow);
            community_btn_follower = (ImageView)itemView.findViewById(R.id.community_btn_follower);

        }
    }
    public void follow() {
        Log.v("follow process", "follow process!!!");
        Call<BaseModel> requestDetail = networkService.followUser(SharedPreference.Companion.getInstance().getPrefStringData("data"),follow_id);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("follow", "follow process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("access denied")){
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

}
