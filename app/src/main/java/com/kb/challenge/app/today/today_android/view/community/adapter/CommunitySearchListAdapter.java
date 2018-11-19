package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.SearchUserData;

import java.util.ArrayList;

public class CommunitySearchListAdapter extends RecyclerView.Adapter<CommunitySearchListAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchUserData> searchUserList;

    public CommunitySearchListAdapter(Context context, ArrayList<SearchUserData> searchUserList) {
        super();
        this.context = context;
        this.searchUserList = searchUserList;
    }

    @Override
    public CommunitySearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_search_list, viewGroup, false);
        CommunitySearchListAdapter.ViewHolder viewHolder = new CommunitySearchListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull CommunitySearchListAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(searchUserList.get(position).getProfile_url())
                .into(holder.community_search_following_img);

        holder.community_search_following_img.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            holder.community_search_following_img.setClipToOutline(true);
        }
        holder.community_search_following_name.setText(searchUserList.get(position).getName());
        holder.community_search_following_id.setText(searchUserList.get(position).getUser_ID());
    }


    @Override
    public int getItemCount() {
        return searchUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView community_search_following_id;
        public ImageView community_search_following_img;
        public TextView community_search_following_name;

        public ViewHolder(View itemView) {
            super(itemView);
            community_search_following_name = (TextView)itemView.findViewById(R.id.community_search_following_name);
            community_search_following_id = (TextView) itemView.findViewById(R.id.community_search_following_id);
            community_search_following_img = (ImageView)itemView.findViewById(R.id.community_search_following_img);
        }


    }

}
