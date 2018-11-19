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

    @Override

        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

            final ImageView account_connect_view = v.findViewById(R.id.account_connect_view);
            account_connect_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    account_connect_view.setImageResource(R.drawable.img_linkaccount_active);

                    ((FirstSettingActivity)getActivity()).changeBtnAct();
                }
            });
            return v;
    }
}