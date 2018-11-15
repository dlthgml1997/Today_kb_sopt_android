package com.kb.challenge.app.today.today_android.view.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kb.challenge.app.today.today_android.R;

public class AccountConnectFragment extends Fragment {

    private RelativeLayout rl_account_connect;
    @Override

        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
            rl_account_connect = v.findViewById(R.id.rl_account_connect);
            final ImageView account_connect_view = v.findViewById(R.id.account_connect_view);
            account_connect_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_account_connect.setBackgroundResource(R.drawable.img_linkaccount_active);
                    account_connect_view.setImageResource(0);
                    ((FirstSettingActivity)getActivity()).changeBtnAct();
                }
            });
            return v;
    }
}