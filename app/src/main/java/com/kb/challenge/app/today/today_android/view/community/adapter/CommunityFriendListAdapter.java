package com.kb.challenge.app.today.today_android.view.community.adapter;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.FriendsProfileData;
import com.kb.challenge.app.today.today_android.model.community.SendEmotionData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.dialog.SendCheerupMsgDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 8..
 */

public class CommunityFriendListAdapter extends RecyclerView.Adapter<CommunityFriendListAdapter.ViewHolder> implements Init {

    Context context;
    ArrayList<FriendsProfileData> friendsProfileDataList;
    private NetworkService networkService;

    private String id;
    private String comment;

    public final static int[] emotion_mark_resource = {R.drawable.img_sns_emotion_bad_3_20_px,R.drawable.img_sns_emotion_bad_2_20_px,R.drawable.img_sns_emotion_bad_1_20_px,R.drawable.img_sns_emotion_soso_0_20_px,R.drawable.img_sns_emotion_good_1_20_px,R.drawable.img_sns_emotion_good_2_20_px,R.drawable.img_sns_emotion_good_3_20_px };

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(context);
    }

    public CommunityFriendListAdapter(Context context, ArrayList<FriendsProfileData> friendsProfileDataList) {
        super();
        this.context = context;
        this.friendsProfileDataList = friendsProfileDataList;
    }

    @Override
    public CommunityFriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.community_friends_list, viewGroup, false);

        init();

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

        viewHolder.community_user_img.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            viewHolder.community_user_img.setClipToOutline(true);
        }

        viewHolder.community_user_id_txt.setText(friendsProfileDataList.get(i).getName());
        viewHolder.community_status_txt.setText(friendsProfileDataList.get(i).getComment());
        if (friendsProfileDataList.get(i).getBad()!= null)
            viewHolder.community_emotion_mark.setBackgroundResource(emotion_mark_resource[emotion_mark_resource.length-friendsProfileDataList.get(i).getBad()-3]);
        else if (friendsProfileDataList.get(i).getGood()!= null)
            viewHolder.community_emotion_mark.setBackgroundResource(emotion_mark_resource[friendsProfileDataList.get(i).getGood()+3]);

        viewHolder.community_profile_btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog send_Cheerup_Msg_Dialog = new Dialog(context);
                send_Cheerup_Msg_Dialog.setContentView(R.layout.dialog_cheerup_msg);
                send_Cheerup_Msg_Dialog.setTitle("cheerup Dialog");

                final EditText edit_cheerup_msg = (EditText)send_Cheerup_Msg_Dialog.findViewById(R.id.edit_cheerup_msg);

                TextView txt_dialog_cheerup_name = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.txt_dialog_cheerup_name);

                id = friendsProfileDataList.get(pos).getId();

                txt_dialog_cheerup_name.setText(friendsProfileDataList.get(pos).getName());

                TextView txt_dialog_cheerup_feeling = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.txt_dialog_cheerup_feeling);

                if (friendsProfileDataList.get(pos).getBad()!= null) {
                    txt_dialog_cheerup_feeling.setText("기분이 안 좋아요");
                }
                else if (friendsProfileDataList.get(pos).getGood()!= null) {
                    txt_dialog_cheerup_feeling.setText("기분이 좋아요!");
                }

                TextView btn_cancel_dialog_cheerup = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.btn_cancel_dialog_cheerup);

                btn_cancel_dialog_cheerup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        send_Cheerup_Msg_Dialog.dismiss();
                    }
                });

                TextView btn_send_dialog_cheerup = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.btn_send_dialog_cheerup);

                btn_send_dialog_cheerup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        comment = edit_cheerup_msg.getText().toString();
                        sendEmotionMsg();
                        send_Cheerup_Msg_Dialog.dismiss();
                    }
                });

                send_Cheerup_Msg_Dialog.show();
            }
        });
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
    public void sendEmotionMsg() {
        Log.v("sendEmotionMsg process", "sendEmotionMsg process!!!");
        SendEmotionData sendEmotionData = new SendEmotionData(id,comment);
        Call<BaseModel> requestDetail = networkService.sendEmotionBox(SharedPreference.Companion.getInstance().getPrefStringData("data"),sendEmotionData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("sendEmotionMsg", "sendEmotionMsg process2!!!");
                    Log.i("sendEmotionMsg message", response.body().getMessage().toString());

                }

            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
