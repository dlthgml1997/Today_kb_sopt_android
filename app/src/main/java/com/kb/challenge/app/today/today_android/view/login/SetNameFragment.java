package com.kb.challenge.app.today.today_android.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;

public class SetNameFragment extends Fragment {

    private ImageView iv_setname_user_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setname, container, false);
      //  View view = inflater.inflate(R.layout.fragment_setname, container, false);
        /*
        iv_setname_user_image = view.findViewById(R.id.iv_setname_user_image);
        iv_setname_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), UserImageSettingActivity.class);
                //startActivity(intent);

            }
        });
*/
       // return view;
    }
}