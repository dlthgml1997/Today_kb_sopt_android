package com.kb.challenge.app.today.today_android.view.community;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kb.challenge.app.today.today_android.MainActivity;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.community.CommuProfileData;
import com.kb.challenge.app.today.today_android.model.community.CommuProfileResponse;

import com.kb.challenge.app.today.today_android.model.community.FollowingData;
import com.kb.challenge.app.today.today_android.model.community.FollowingListResponse;
import com.kb.challenge.app.today.today_android.model.community.FriendsProfileData;
import com.kb.challenge.app.today.today_android.model.community.FriendsProfileResponse;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.model.record.FeelingDataResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFollowingListAdapter;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFriendListAdapter;
import com.kb.challenge.app.today.today_android.view.dialog.RecordFeelingDialog;
import com.kb.challenge.app.today.today_android.view.login.LoginActivity;
import com.kb.challenge.app.today.today_android.view.main.RootFragment;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kb.challenge.app.today.today_android.view.community.adapter.CommunityFriendListAdapter.emotion_mark_resource;
import static com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment.feelingMsg;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class CommunityFragment extends Fragment implements Init {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG_COMM = "CommunityFragment";
    private CommunityFragment.OnFragmentInteractionListener mListener;
    private NetworkService networkService;
    private TextView community_follower_num_txt;
    private TextView community_following_num_txt;
    private ImageView community_my_profil_img;
    private TextView community_user_name;
    private TextView community_id;
    private ImageView community_btn_emotion_box;
    private RecyclerView mRecyclerView;
    private ImageView community_my_profile_emotion_mark;
    private String getTime;
    private TextView community_my_profil_status_txt;
    private ArrayList<FriendsProfileData> friendsProfileDataList;
    private ArrayList<FollowingData> followingList;
    private ArrayList<FriendsProfileData> friendsList = new ArrayList<>();
    private String user_name;
    private ArrayList<FeelingData> feelingDataList;
    private int feeling_bad = -1;
    private int feeling_good = -1;

    public final static int[] profile_emotion_mark_resource = {R.drawable.img_sns_profile_emotion_bad_1_40_px, R.drawable.img_sns_profile_emotion_bad_2_40_px, R.drawable.img_sns_profile_emotion_bad_3_40_px, R.drawable.img_sns_profile_emotion_soso_0_40_px, R.drawable.img_sns_profile_emotion_good_1_40_px, R.drawable.img_sns_profile_emotion_good_2_40_px, R.drawable.img_sns_profile_emotion_good_3_40_px};


    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
        getTodayFeelingData();
        getFollowingList();
    }

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
        init();

        //통신할 때 보낼 오늘의 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);


        //나의 프로필
        community_my_profil_img = (ImageView) view.findViewById(R.id.community_my_profil_img);

        //나의 상태 메시지
        community_my_profil_status_txt = (TextView) view.findViewById(R.id.community_my_profil_status_txt);

        //이미지 크롭
        community_my_profil_img.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= 21) {
            community_my_profil_img.setClipToOutline(true);
        }
        //팔로잉 보기
        community_following_num_txt = (TextView) view.findViewById(R.id.community_following_num_txt);
        //팔로워보기
        community_follower_num_txt = (TextView) view.findViewById(R.id.community_follower_num_txt);

        //프로필 유저 이름
        community_user_name = (TextView) view.findViewById(R.id.community_user_name);

        //프로필 id
        community_id = (TextView) view.findViewById(R.id.community_id);

        //사용자의 감정 이미지 아이콘
        community_my_profile_emotion_mark = (ImageView) view.findViewById(R.id.community_my_profile_emotion_mark);

        ImageView community_btn_emotion_box = (ImageView) view.findViewById(R.id.community_btn_emotion_box);

        //감정박스 보기 (프래그먼트 전환)
        community_btn_emotion_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                transaction.replace(R.id.root_frame2, new CommunityEmotionBox());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.community_friends_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), new LinearLayoutManager(getActivity()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //팔로워들의 감정 리스트
        getFriendsList();

        //프로필 정보 가져오는 통신 메소드 호출
        getProfileData();

        //팔로우 보기(나를 좋아하는 사람. 나도 좋아할 수도 있음(노란색 버튼)) -> 프래그먼트 전환
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

        //팔로워 보기(내가 좋아하는 사람 (흰색 버튼들)) -> 프래그먼트 전환
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

        //사용자 검색
        ImageView community_btn_search_id = (ImageView) view.findViewById(R.id.community_btn_search_id);

        community_btn_search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame2, new CommunitySearchListFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        //상태 메시지 수정
        ImageView img_status_edit = (ImageView) view.findViewById(R.id.img_status_edit);

        img_status_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CommunityEditStatusFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("hint", community_my_profil_status_txt.getText().toString());
                Log.v("status message", community_my_profil_status_txt.getText().toString());
                if (feeling_bad != -1)
                    bundle.putInt("feeling_data", feeling_bad);
                else if (feeling_good != -1)
                    bundle.putInt("feeling_data", feeling_good);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame2, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getTodayFeelingData();
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

    public void getProfileData() {
        Log.v("getProfileData process", "getProfileData process!!!");
        Call<CommuProfileResponse> requestDetail = networkService.getUserProfile(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<CommuProfileResponse>() {
            @Override
            public void onResponse(Call<CommuProfileResponse> call, Response<CommuProfileResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getProfileData", "getProfileData process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("success")) {
                        ArrayList<CommuProfileData> commuProfileDataList = response.body().getData();
                        Glide.with(getActivity())
                                .load(commuProfileDataList.get(0).getProfile_url())
                                .into(community_my_profil_img);

                        community_my_profil_img.setBackground(new ShapeDrawable(new OvalShape()));
                        if (Build.VERSION.SDK_INT >= 21) {
                            community_my_profil_img.setClipToOutline(true);
                        }
                        community_id.setText(commuProfileDataList.get(0).getId());

                        community_user_name.setText(commuProfileDataList.get(0).getName());

                        user_name = commuProfileDataList.get(0).getName();

                        community_follower_num_txt.setText(String.valueOf(commuProfileDataList.get(0).getCountFollower()));

                        community_following_num_txt.setText(String.valueOf(commuProfileDataList.get(0).getCountFollowing()));

                    }

                }
            }

            @Override
            public void onFailure(Call<CommuProfileResponse> call, Throwable t) {
                Log.v("profile err", "profile err");
                Log.i("err", t.getMessage());
            }
        });
    }

    public void getTodayFeelingData() {
        Log.v("getTodayFeelingData", "getTodayFeelingData process!!!");
        //통신할 때 보낼 오늘의 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

        Log.v("오늘의 날짜의 감정", getTime);
        Call<FeelingDataResponse> requestDetail = networkService.getTodayFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), getTime);
        requestDetail.enqueue(new Callback<FeelingDataResponse>() {
            @Override
            public void onResponse(Call<FeelingDataResponse> call, Response<FeelingDataResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getTodayFeelingData", "getProfileData process2!!!");
                    Log.v("feeling message", response.body().getMessage().toString());

                    feelingDataList = response.body().getData();
                    Log.v("feeling data list!!!", feelingDataList.toString());
                    if (feelingDataList.isEmpty()) {
                        //감정기록이 없으면 메인페이지로 이동!


                    } else if (feelingDataList.get(feelingDataList.size() - 1).getBad() != null) {
                        feeling_bad = profile_emotion_mark_resource.length - feelingDataList.get(feelingDataList.size() - 1).getBad() - 3;
                        Log.v("feeling data bad", profile_emotion_mark_resource.length - feelingDataList.get(feelingDataList.size() - 1).getBad() - 3 + "");
                        community_my_profile_emotion_mark.setBackgroundResource(profile_emotion_mark_resource[feeling_bad]);
                        community_my_profil_status_txt.setText(feelingDataList.get(feelingDataList.size() - 1).getComment());
                    } else if (feelingDataList.get(feelingDataList.size() - 1).getGood() != null) {
                        feeling_good = feelingDataList.get(feelingDataList.size() - 1).getGood() + 3;
                        Log.v("feeling data good", feelingDataList.get(feelingDataList.size() - 1).getGood() + 3 + "");
                        community_my_profile_emotion_mark.setBackgroundResource(profile_emotion_mark_resource[feelingDataList.get(feelingDataList.size() - 1).getGood() + 3]);
                        community_my_profil_status_txt.setText(feelingDataList.get(feelingDataList.size() - 1).getComment());
                    }
                }
            }

            @Override
            public void onFailure(Call<FeelingDataResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    //친구들의 감정
    public void getFriendsList() {
        Log.v("getFriendsList process", "getFriendsList process!!!");
        Log.v("getdate", getTime);

        Call<FriendsProfileResponse> requestDetail = networkService.getFollowingsFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), getTime);
        requestDetail.enqueue(new Callback<FriendsProfileResponse>() {
            @Override
            public void onResponse(Call<FriendsProfileResponse> call, Response<FriendsProfileResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getFriendsList", "getFriendsList process2!!!");
                    Log.v("friends list message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("success")) {
                        friendsProfileDataList = response.body().getData();

                        //profile 이미지, id, name, good, bad, comment

                        Log.v("friendsProfileDataList", friendsProfileDataList.toString());

                        if (friendsList != null) {
                            for (int i = 0; i < friendsList.size(); i++) {
                                for (int j = 0; j < friendsProfileDataList.size(); j++) {
                                    if (friendsList.get(i).getId().equals(friendsProfileDataList.get(j).getId())) {
                                        if (friendsProfileDataList.get(j).getGood() != null)
                                            friendsList.get(i).setGood(friendsProfileDataList.get(j).getGood());
                                        else if (friendsProfileDataList.get(j).getBad() != null)
                                            friendsList.get(i).setBad(friendsProfileDataList.get(j).getBad());
                                        if (friendsProfileDataList.get(j).getComment() != null)
                                            friendsList.get(i).setComment(friendsProfileDataList.get(j).getComment());
                                    }
                                }
                            }
                        }

                        Log.v("friends list", friendsList.toString());

                        for (int i = 0; i < friendsList.size(); i++) {
                            if (friendsList.get(i).getBad() == null && friendsList.get(i).getGood() == null) {
                                friendsList.remove(i);
                            }
                        }

                        CommunityFriendListAdapter communityFriendListAdapter = new CommunityFriendListAdapter(getActivity(), friendsList);

                        mRecyclerView.setAdapter(communityFriendListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendsProfileResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    public void getFollowingList() {
        Log.v("getFollowerList process", "getFollowerList process!!!");
        Call<FollowingListResponse> requestDetail = networkService.getFollowingList(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<FollowingListResponse>() {
            @Override
            public void onResponse(Call<FollowingListResponse> call, Response<FollowingListResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getFollowerList", "getFollowerList process2!!!");
                    Log.v("message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("success")) {
                        followingList = response.body().getData();
                        //프로필 이미지, id, name
                        Log.v("followerDataList", followingList.toString());

                        friendsList = new ArrayList<>();

                        for (int i = 0; i < followingList.size(); i++) {
                            friendsList.add(new FriendsProfileData(followingList.get(i).getProfile_img(), followingList.get(i).getId(), followingList.get(i).getName(), null, null, null));
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<FollowingListResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

}
