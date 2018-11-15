package com.kb.challenge.app.today.today_android.view.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;

/**
 * Created by shineeseo on 2018. 11. 15..
 */

public class TabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "TabFragment";

    private TabFragment.OnFragmentInteractionListener mListener;

    public TabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragment newInstance(/*String param1, String param2*/) {
        TabFragment fragment = new TabFragment();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //감정 기록이 없을 경우 나타나는 main fragment
        Log.v("tablayout", "tablayout");
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(viewPager);

        final ImageView mainIcon = new ImageView(getActivity());
        mainIcon.setImageResource(R.drawable.ic_home_24_px);
        mainIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView commuIcon = new ImageView(getActivity());
        commuIcon.setImageResource(R.drawable.ic_community_24_px);
        commuIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView coinIcon = new ImageView(getActivity());
        coinIcon.setImageResource(R.drawable.ic_coin_24_px);
        coinIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView settingIcon = new ImageView(getActivity());
        settingIcon.setImageResource(R.drawable.ic_setting_24_px);
        settingIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //tablayout icon 선택시
        final ImageView mainIconAct = new ImageView(getActivity());
        mainIconAct.setImageResource(R.drawable.ic_home_active_24_px);
        mainIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView commuIconAct = new ImageView(getActivity());
        commuIconAct.setImageResource(R.drawable.ic_community_active_24_px);
        commuIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView coinIconAct = new ImageView(getActivity());
        coinIconAct.setImageResource(R.drawable.ic_coin_active_24_px);
        coinIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final ImageView settingIconAct = new ImageView(getActivity());
        settingIconAct.setImageResource(R.drawable.ic_setting_active_24_px);
        settingIconAct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tabLayout.getTabAt(0).setCustomView(mainIconAct);
        tabLayout.getTabAt(1).setCustomView(commuIconAct);
        tabLayout.getTabAt(2).setCustomView(coinIconAct);
        tabLayout.getTabAt(3).setCustomView(settingIconAct);

        //탭선택시
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView iv = (ImageView) tab.getCustomView();
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.ic_home_active_24_px);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.ic_community_active_24_px);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.ic_coin_active_24_px);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.ic_setting_active_24_px);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab1) {
                ImageView iv = (ImageView) tab1.getCustomView();
                switch (tab1.getPosition()) {
                    case 0:
                        iv.setImageResource(R.drawable.ic_home_24_px);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.ic_community_24_px);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.ic_coin_24_px);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.ic_setting_24_px);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab2) {
                Log.d("reselected tap", String.valueOf(tab2.getPosition()));
            }
        });

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
        if (context instanceof TabFragment.OnFragmentInteractionListener) {
            mListener = (TabFragment.OnFragmentInteractionListener) context;
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
