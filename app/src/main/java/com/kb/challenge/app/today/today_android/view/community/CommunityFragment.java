package com.kb.challenge.app.today.today_android.view.community;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FriendsInfoItem;
import com.kb.challenge.app.today.today_android.view.coin.adapter.CoinSavingListAdapter;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFriendListAdapter;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class CommunityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG_COMM = "CommunityFragment";
    private CommunityFragment.OnFragmentInteractionListener mListener;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(/*String param1, String param2*/) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        //팔로잉 보기
        TextView community_following_num_txt = (TextView)view.findViewById(R.id.community_following_num_txt);

        community_following_num_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                transaction.replace(R.id.root_frame2, new CommunityFollowingFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();
            }
        });

        //팔로워보기
        TextView community_follower_num_txt = (TextView)view.findViewById(R.id.community_follower_num_txt);
        community_follower_num_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                transaction.replace(R.id.root_frame2, new CommunityFollowerFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();
            }
        });

        ImageView community_btn_search_id = (ImageView)view.findViewById(R.id.community_btn_search_id);

        community_btn_search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.community_friends_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<FriendsInfoItem> friendsList = new ArrayList<>();

        friendsList.add (new FriendsInfoItem(R.drawable.person, "오늘은", "기분최고!!", 3));
        friendsList.add (new FriendsInfoItem(R.drawable.person, "오늘은", "기분최고!!", 3));
        friendsList.add (new FriendsInfoItem(R.drawable.person, "오늘은", "기분최고!!", 3));
        friendsList.add (new FriendsInfoItem(R.drawable.person, "오늘은", "기분최고!!", 3));
        friendsList.add (new FriendsInfoItem(R.drawable.person, "오늘은", "기분최고!!", 3));

        CommunityFriendListAdapter communityFriendListAdapter = new CommunityFriendListAdapter(getActivity(),friendsList);
        Log.v("friendListSIze", friendsList.size() + " ");
        mRecyclerView.setAdapter(communityFriendListAdapter);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunityFragment.OnFragmentInteractionListener) {
            mListener = (CommunityFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
