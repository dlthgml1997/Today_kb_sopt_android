package com.kb.challenge.app.today.today_android.view.community;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.FollowingItem;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFollowingListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 11. 9..
 */

public class CommunityFollowerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "CommunityFollowerFragment";

    private CommunityFollowerFragment.OnFragmentInteractionListener mListener;

    public CommunityFollowerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFollowerFragment newInstance(/*String param1, String param2*/) {
        CommunityFollowerFragment fragment = new CommunityFollowerFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 200){
            Log.v("yong",data.getStringExtra("result"));
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_community_following, container, false);
        TextView community_toolbar_title = (TextView)view.findViewById(R.id.community_toolbar_title);

        community_toolbar_title.setText("팔로워");
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.community_following_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<FollowingItem> followingList = new ArrayList<>();

        for (int i = 0; i < 10; i ++)
            followingList.add(new FollowingItem("오늘은"));

        CommunityFollowingListAdapter communityFollowingListAdapter = new CommunityFollowingListAdapter(getActivity(),followingList);
        mRecyclerView.setAdapter(communityFollowingListAdapter);

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
        if (context instanceof CommunityFollowerFragment.OnFragmentInteractionListener) {
            mListener = (CommunityFollowerFragment.OnFragmentInteractionListener) context;
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
