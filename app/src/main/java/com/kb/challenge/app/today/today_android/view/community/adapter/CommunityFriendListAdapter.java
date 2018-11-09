package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingItem;
import com.kb.challenge.app.today.today_android.model.community.FriendsInfoItem;
import com.kb.challenge.app.today.today_android.view.coin.adapter.CoinSavingListAdapter;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 8..
 */

public class CommunityFriendListAdapter extends RecyclerView.Adapter<CommunityFriendListAdapter.ViewHolder> {

    Context context;
    ArrayList<FriendsInfoItem> communityFriendsList;

    public CommunityFriendListAdapter(Context context, ArrayList<FriendsInfoItem> communityFriendsList) {
        super();
        this.context = context;
        this.communityFriendsList = communityFriendsList;
    }

    @Override
    public CommunityFriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_friends_list, viewGroup, false);
        CommunityFriendListAdapter.ViewHolder viewHolder = new CommunityFriendListAdapter.ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(CommunityFriendListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        Log.v("communityFriendsList", communityFriendsList.size() + " ");
        viewHolder.community_user_img.setBackgroundResource(communityFriendsList.get(i).getProfile_img());
        viewHolder.community_user_id_txt.setText(communityFriendsList.get(i).getUser_id());
        viewHolder.community_status_txt.setText(communityFriendsList.get(i).getStatus_msg());
        viewHolder.community_status_mark_txt.setText(String.valueOf(communityFriendsList.get(i).getStatus_num()));

    }

    @Override
    public int getItemCount() {
        return communityFriendsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView community_user_img;
        public TextView community_user_id_txt;
        public TextView community_status_txt;
        public TextView community_status_mark_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            community_user_img = (ImageView)itemView.findViewById(R.id.community_user_img);
            community_user_id_txt = (TextView) itemView.findViewById(R.id.community_user_id_txt);
            community_status_txt = (TextView) itemView.findViewById(R.id.community_status_txt);
            community_status_mark_txt = (TextView) itemView.findViewById(R.id.community_status_mark_txt);
        }


    }
}
