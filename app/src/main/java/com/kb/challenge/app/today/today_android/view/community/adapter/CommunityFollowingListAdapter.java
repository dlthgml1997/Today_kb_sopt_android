package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
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

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FollowingItem;
import com.kb.challenge.app.today.today_android.model.community.FriendsInfoItem;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 9..
 */

public class CommunityFollowingListAdapter extends RecyclerView.Adapter<CommunityFollowingListAdapter.ViewHolder> {
    Context context;
    ArrayList<FollowingItem> communityFollowingList;

    public CommunityFollowingListAdapter(Context context, ArrayList<FollowingItem> communityFollowingList) {
        super();
        this.context = context;
        this.communityFollowingList = communityFollowingList;
    }

    @Override
    public CommunityFollowingListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_following_list, viewGroup, false);
        CommunityFollowingListAdapter.ViewHolder viewHolder = new CommunityFollowingListAdapter.ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final CommunityFollowingListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        Log.v("communityFriendsList", communityFollowingList.size() + " ");
        viewHolder.community_following_id.setText(communityFollowingList.get(i).getId());
        viewHolder.community_btn_check_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.community_btn_check_following.setVisibility(View.GONE);
                viewHolder.community_btn_check_follower.setVisibility(View.VISIBLE);

            }
        });
        viewHolder.community_btn_check_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.community_btn_check_following.setVisibility(View.VISIBLE);
                viewHolder.community_btn_check_follower.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return communityFollowingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView community_following_id;
        public ImageView community_btn_check_following;
        public ImageView community_btn_check_follower;


        public ViewHolder(View itemView) {
            super(itemView);
            community_following_id = (TextView) itemView.findViewById(R.id.community_follower_id);
            community_btn_check_following = (ImageView)itemView.findViewById(R.id.community_btn_check_following);
            community_btn_check_follower = (ImageView)itemView.findViewById(R.id.community_btn_check_follower);
        }


    }

}
