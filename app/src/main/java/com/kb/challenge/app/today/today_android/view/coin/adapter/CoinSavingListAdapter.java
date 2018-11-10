package com.kb.challenge.app.today.today_android.view.coin.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        viewHolder.coin_saving_date.setText(coinSavingItems.get(i).getSaving_at());
        viewHolder.coin_saving_amount.setText(String.valueOf(coinSavingItems.get(i).getSaving_money()));
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

        public ViewHolder(View itemView) {
            super(itemView);
            coin_saving_msg = (TextView)itemView.findViewById(R.id.coin_saving_msg);
            coin_saving_date = (TextView) itemView.findViewById(R.id.coin_saving_date);
            coin_saving_amount = (TextView) itemView.findViewById(R.id.coin_saving_amount);
        }


    }
}
