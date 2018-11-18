package com.kb.challenge.app.today.today_android.view.coin.adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingItem;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 7..
 */

public class CoinSavingListAdapter extends RecyclerView.Adapter<CoinSavingListAdapter.ViewHolder> {

    Context context;
    ArrayList<CoinSavingItem> coinSavingItems;

    public CoinSavingListAdapter(Context context, ArrayList<CoinSavingItem> coinSavingItems) {
        super();
        this.context = context;
        this.coinSavingItems = coinSavingItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.coin_saving_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int pos = i;
        String result = coinSavingItems.get(i).getSaving_at().substring(coinSavingItems.get(i).getSaving_at().length()-5, coinSavingItems.get(i).getSaving_at().length());
        viewHolder.coin_saving_date.setText(result);

        viewHolder.coin_saving_amount.setText("+ "+coinSavingItems.get(i).getSaving_money());
        viewHolder.coin_saving_msg.setText(coinSavingItems.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return coinSavingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView coin_saving_date;
        public TextView coin_saving_amount;
        public TextView coin_saving_msg;
        public ImageView img_coin_pig;
        public RelativeLayout rl_coin_goal_box;
        public Button coin_btn_withdrawal;

        public ViewHolder(View itemView) {
            super(itemView);
            coin_saving_msg = (TextView)itemView.findViewById(R.id.coin_saving_msg);
            coin_saving_date = (TextView) itemView.findViewById(R.id.coin_saving_date);
            coin_saving_amount = (TextView) itemView.findViewById(R.id.coin_saving_amount);
            coin_btn_withdrawal = (Button) itemView.findViewById(R.id.coin_btn_withdrawal);
            rl_coin_goal_box = (RelativeLayout) itemView.findViewById(R.id.rl_coin_goal_box);
            img_coin_pig = (ImageView) itemView.findViewById(R.id.img_coin_pig);
        }


    }
}
