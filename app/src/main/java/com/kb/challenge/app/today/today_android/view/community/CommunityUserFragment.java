package com.kb.challenge.app.today.today_android.view.community;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;

public class CommunityUserFragment extends Fragment {
    private Button community_follow_btn;
    private Button community_following_btn;
    private  ImageView community_ic_cancel;
    public CommunityUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_user_follow, container, false);

        community_follow_btn= view.findViewById(R.id.community_follow_btn);
        community_following_btn= view.findViewById(R.id.community_following_btn);
        community_follow_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                community_follow_btn.setVisibility(View.INVISIBLE);
                community_following_btn.setVisibility(View.VISIBLE);
            }
        });

        community_following_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                community_follow_btn.setVisibility(View.VISIBLE);
                community_following_btn.setVisibility(View.INVISIBLE);
            }
        });

        community_ic_cancel=view.findViewById(R.id.community_ic_cancel);
        community_ic_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}