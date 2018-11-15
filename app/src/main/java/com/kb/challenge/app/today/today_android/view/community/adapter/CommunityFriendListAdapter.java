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

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FriendsProfileData;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 8..
 */

public class CommunityFriendListAdapter extends RecyclerView.Adapter<CommunityFriendListAdapter.ViewHolder> {

    Context context;
    ArrayList<FriendsProfileData> friendsProfileDataList;
    public final static int[] emotion_mark_resource = {R.drawable.img_sns_emotion_bad_3_20_px,R.drawable.img_sns_emotion_bad_2_20_px,R.drawable.img_sns_emotion_bad_1_20_px,R.drawable.img_sns_emotion_soso_0_20_px,R.drawable.img_sns_emotion_good_1_20_px,R.drawable.img_sns_emotion_good_2_20_px,R.drawable.img_sns_emotion_good_3_20_px };
    public CommunityFriendListAdapter(Context context, ArrayList<FriendsProfileData> friendsProfileDataList) {
        super();
        this.context = context;
        this.friendsProfileDataList = friendsProfileDataList;
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
        Log.v("communityFriendsList", friendsProfileDataList.size() + " ");
        Glide.with(context)
                .load(friendsProfileDataList.get(i).getProfile_img())
                .into(viewHolder.community_user_img);
        viewHolder.community_user_id_txt.setText(friendsProfileDataList.get(i).getName());
        viewHolder.community_status_txt.setText(friendsProfileDataList.get(i).getComment());
        if (friendsProfileDataList.get(i).getBad()!= null)
            viewHolder.community_emotion_mark.setBackgroundResource(emotion_mark_resource[emotion_mark_resource.length-friendsProfileDataList.get(i).getBad()-3]);
        else if (friendsProfileDataList.get(i).getGood()!= null)
            viewHolder.community_emotion_mark.setBackgroundResource(emotion_mark_resource[friendsProfileDataList.get(i).getGood()+3]);

    }

    @Override
    public int getItemCount() {
        return friendsProfileDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView community_user_img;
        public TextView community_user_id_txt;
        public TextView community_status_txt;
        public ImageView community_profile_btn_msg;
        public ImageView community_emotion_mark;

        public ViewHolder(View itemView) {
            super(itemView);
            community_emotion_mark = (ImageView)itemView.findViewById(R.id.community_emotion_mark);
            community_user_img = (ImageView)itemView.findViewById(R.id.community_user_img);
            community_user_id_txt = (TextView) itemView.findViewById(R.id.community_user_id_txt);
            community_status_txt = (TextView) itemView.findViewById(R.id.community_status_txt);
            community_profile_btn_msg = (ImageView) itemView.findViewById(R.id.community_profile_btn_msg);
        }
    }
}
