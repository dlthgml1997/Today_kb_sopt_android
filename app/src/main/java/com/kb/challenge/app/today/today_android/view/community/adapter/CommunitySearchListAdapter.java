package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FollowingItem;

import java.util.ArrayList;

public class CommunitySearchListAdapter extends RecyclerView.Adapter<CommunityFollowingListAdapter.ViewHolder> {
    Context context;
    ArrayList<FollowingItem> communityFollowingList;

    public CommunitySearchListAdapter(Context context, ArrayList<FollowingItem> communityFollowingList) {
        super();
        this.context = context;
        this.communityFollowingList = communityFollowingList;
    }
    @Override
    public CommunityFollowingListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_search_list, viewGroup, false);
        CommunityFollowingListAdapter.ViewHolder viewHolder = new CommunityFollowingListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityFollowingListAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return communityFollowingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView community_following_id;
        public ImageView community_following_img;


        public ViewHolder(View itemView) {
            super(itemView);
            community_following_id = (TextView) itemView.findViewById(R.id.community_search_following_id);
            community_following_img = (ImageView)itemView.findViewById(R.id.community_search_following_img);
        }


    }

}
