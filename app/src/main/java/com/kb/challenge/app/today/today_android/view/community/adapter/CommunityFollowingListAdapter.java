package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.FollowingData;
import com.kb.challenge.app.today.today_android.model.community.FollowingItem;
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

public class CommunityFollowingListAdapter extends RecyclerView.Adapter<CommunityFollowingListAdapter.ViewHolder>  implements Init {
    Context context;
    ArrayList<FollowingData> communityFollowingList;
    private NetworkService networkService;
    private String follow_id;
    private BaseModel msg;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(context);
    }

    public CommunityFollowingListAdapter(Context context, ArrayList<FollowingData> communityFollowingList) {
        super();
        this.context = context;
        this.communityFollowingList = communityFollowingList;
    }

    @Override
    public CommunityFollowingListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_following_list, viewGroup, false);
        init();
        CommunityFollowingListAdapter.ViewHolder viewHolder = new CommunityFollowingListAdapter.ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final CommunityFollowingListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        Log.v("communityFriendsList", communityFollowingList.size() + " ");
        viewHolder.community_following_id.setText(communityFollowingList.get(i).getId());
        follow_id = viewHolder.community_following_id.getText().toString();
        Log.i("id2323", follow_id);

        Glide.with(context)
                .load(communityFollowingList.get(i).getProfile_img())
                .into(viewHolder.community_following_img);

        viewHolder.community_following_img.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            viewHolder.community_following_img.setClipToOutline(true);
        }
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
                cancelFollow();
            }
        });

    }

    @Override
    public int getItemCount() {
        return communityFollowingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView community_following_id;
        public ImageView community_btn_follow;
        public ImageView community_btn_follower;
        public ImageView community_following_img;


        public ViewHolder(View itemView) {
            super(itemView);
            community_following_id = (TextView) itemView.findViewById(R.id.community_following_id);
            community_following_img = (ImageView)itemView.findViewById(R.id.community_following_img);
            community_btn_follow = (ImageView)itemView.findViewById(R.id.community_btn_follow);
            community_btn_follower = (ImageView)itemView.findViewById(R.id.community_btn_follower);
        }
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
