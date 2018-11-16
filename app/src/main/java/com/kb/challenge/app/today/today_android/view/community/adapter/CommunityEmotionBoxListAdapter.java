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

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.EmotionBoxData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 17..
 */

public class CommunityEmotionBoxListAdapter extends RecyclerView.Adapter<CommunityEmotionBoxListAdapter.ViewHolder>  implements Init {
    Context context;
    private NetworkService networkService;
    private ArrayList<EmotionBoxData> emotionBoxData;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(context);
    }

    public CommunityEmotionBoxListAdapter(Context context, ArrayList<EmotionBoxData> emotionBoxData) {
        super();
        this.context = context;
        this.emotionBoxData = emotionBoxData;
    }

    @Override
    public CommunityEmotionBoxListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_emotion_box_list, viewGroup, false);
        init();
        CommunityEmotionBoxListAdapter.ViewHolder viewHolder = new CommunityEmotionBoxListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final CommunityEmotionBoxListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        viewHolder.community_user_id_txt.setText(emotionBoxData.get(i).getName());
        viewHolder.community_emotion_date_txt.setText(emotionBoxData.get(i).getToday_at());
        viewHolder.community_status_txt.setText(emotionBoxData.get(i).getComment());

        Glide.with(context)
                .load(emotionBoxData.get(i).getProfile_url())
                .into(viewHolder.community_user_img);

        viewHolder.community_user_img.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            viewHolder.community_user_img.setClipToOutline(true);
        }

    }
    @Override
    public int getItemCount() {
        return emotionBoxData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView community_user_img;
       private TextView community_user_id_txt;
       private TextView community_status_txt;
       private TextView community_emotion_date_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            community_user_img = (ImageView)itemView.findViewById(R.id.community_user_img);
            community_user_id_txt = (TextView)itemView.findViewById(R.id.community_user_id_txt);
            community_status_txt = (TextView)itemView.findViewById(R.id.community_status_txt);
            community_emotion_date_txt = (TextView)itemView.findViewById(R.id.community_emotion_date_txt);

        }
    }
}