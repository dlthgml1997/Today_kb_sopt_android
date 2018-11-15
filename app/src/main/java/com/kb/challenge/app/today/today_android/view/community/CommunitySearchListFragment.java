package com.kb.challenge.app.today.today_android.view.community;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FollowingItem;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunitySearchListAdapter;

import java.util.ArrayList;

public class CommunitySearchListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static CommunitySearchListFragment newInstance(/*String param1, String param2*/) {
        CommunitySearchListFragment fragment = new CommunitySearchListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_user_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<FollowingItem> followingList = new ArrayList<>();

        followingList.add(new FollowingItem("오늘은"));
        followingList.add(new FollowingItem("오늘은"));

        CommunitySearchListAdapter CommunitySearchListAdapter = new CommunitySearchListAdapter(getActivity(),followingList);
        mRecyclerView.setAdapter(CommunitySearchListAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("로그","눌렸다");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame2, new CommunityUserFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();
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