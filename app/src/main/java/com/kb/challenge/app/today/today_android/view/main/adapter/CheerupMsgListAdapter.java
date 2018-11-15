package com.kb.challenge.app.today.today_android.view.main.adapter;

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
import com.kb.challenge.app.today.today_android.model.cheerup.CheerupMsgData;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFollowingListAdapter;

import java.util.ArrayList;


/**
 * Created by shineeseo on 2018. 11. 15..
 */

public class CheerupMsgListAdapter extends RecyclerView.Adapter<CheerupMsgListAdapter.ViewHolder> {

    Context context;
    ArrayList<CheerupMsgData> cheerupMsgDataList;

    public CheerupMsgListAdapter(Context context, ArrayList<CheerupMsgData> cheerupMsgDataList) {
        super();
        this.context = context;
        this.cheerupMsgDataList = cheerupMsgDataList;
    }

    @Override
    public CheerupMsgListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cheerup_msg_list, viewGroup, false);
        CheerupMsgListAdapter.ViewHolder viewHolder = new CheerupMsgListAdapter.ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final CheerupMsgListAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        Log.v("communityFriendsList", cheerupMsgDataList.size() + " ");
        viewHolder.cheerup_msg_date.setText(cheerupMsgDataList.get(i).getFeeling_at());
        viewHolder.cheerup_msg_txt.setText(cheerupMsgDataList.get(i).getComment());

    }

    @Override
    public int getItemCount() {
        return cheerupMsgDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cheerup_msg_date;
        public ImageView cheerup_feeling_mark;
        public TextView cheerup_msg_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            cheerup_msg_date = (TextView) itemView.findViewById(R.id.cheerup_msg_date);
            cheerup_feeling_mark = (ImageView)itemView.findViewById(R.id.cheerup_feeling_mark);
            cheerup_msg_txt = (TextView) itemView.findViewById(R.id.cheerup_msg_txt);

        }


    }
}
