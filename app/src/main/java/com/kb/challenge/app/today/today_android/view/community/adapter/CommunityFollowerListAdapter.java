package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.FollowerData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

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
        viewHolder.community_following_id.setText(communityFollowingList.get(i).getId());

        viewHolder.community_following_img.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= 21) {
            viewHolder.community_following_img.setClipToOutline(true);
        }

        Glide.with(context)
                .load(communityFollowingList.get(i).getProfile_img())
                .into(viewHolder.community_following_img);

        //맞팔일 경우!
        if (communityFollowingList.get(pos).getFollowing()) {
            //팔로우 버튼 비활성화
            viewHolder.community_btn_follow.setVisibility(View.GONE);
            //팔로잉 버튼 활성화
            viewHolder.community_btn_follower.setVisibility(View.VISIBLE);

        }
        else {
            viewHolder.community_btn_follow.setVisibility(View.VISIBLE);
            viewHolder.community_btn_follower.setVisibility(View.GONE);
        }

        //팔로우 버튼 클릭 -> follow 호출
        viewHolder.community_btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow_id = communityFollowingList.get(pos).getId();
                viewHolder.community_btn_follow.setVisibility(View.GONE);
                viewHolder.community_btn_follower.setVisibility(View.VISIBLE);
                //팔로우하기
                follow();

            }
        });
        viewHolder.community_btn_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow_id = communityFollowingList.get(pos).getId();
                viewHolder.community_btn_follow.setVisibility(View.VISIBLE);
                viewHolder.community_btn_follower.setVisibility(View.GONE);
                cancelFollow();
            }
        });

        viewHolder.community_following_name.setText(communityFollowingList.get(i).getName());
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
        public TextView community_following_name;

        public ViewHolder(View itemView) {
            super(itemView);

            community_following_name = (TextView)itemView.findViewById(R.id.community_following_name);
            community_following_id = (TextView) itemView.findViewById(R.id.community_following_id);

            community_following_img = (ImageView)itemView.findViewById(R.id.community_following_img);
            community_btn_follow = (ImageView)itemView.findViewById(R.id.community_btn_follow);
            community_btn_follower = (ImageView)itemView.findViewById(R.id.community_btn_follower);

        }
    }
    public void follow() {
        Log.v("follow process", "follow process!!!");
        Log.i("맞팔할 id", follow_id);
        Call<BaseModel> requestDetail = networkService.followUser(SharedPreference.Companion.getInstance().getPrefStringData("data"),follow_id);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("follow", "follow process2!!!");
                    Log.i("follow message", response.body().getMessage().toString());

                }

                else {
                    Toast.makeText(context, "이미 팔로우하고 있습니다.", Toast.LENGTH_LONG).show();
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
        Log.v("follow id", follow_id);
        Call<BaseModel> requestDetail = networkService.cancelFollow(SharedPreference.Companion.getInstance().getPrefStringData("data"),follow_id);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("follow", "follow process2!!!");
                    Log.v("cancel message", response.body().getMessage().toString());

                }
                else if (response.body().getMessage().toString().equals("following does not exist")) {
                    Toast.makeText(context, "팔로우 하고 있지 않습니다. ", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, "사용자가 존재하지 않습니다. ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }



}
