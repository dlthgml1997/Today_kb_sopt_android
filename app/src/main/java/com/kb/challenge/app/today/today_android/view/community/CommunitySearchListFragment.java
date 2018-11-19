package com.kb.challenge.app.today.today_android.view.community;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.SearchUserData;
import com.kb.challenge.app.today.today_android.model.community.SearchUserResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunitySearchListAdapter;
import com.kb.challenge.app.today.today_android.MainActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunitySearchListFragment extends Fragment implements Init {
    private RecyclerView mRecyclerView;
    private NetworkService networkService;
    private EditText search_user_id_edit_txt;
    private  ArrayList<SearchUserData> searchUserList;
    private RecyclerView.OnItemTouchListener onItemTouchListener;
    public static CommunitySearchListFragment newInstance(/*String param1, String param2*/) {
        CommunitySearchListFragment fragment = new CommunitySearchListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).inVisibleTabLayout();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        init();

        search_user_id_edit_txt = (EditText) view.findViewById(R.id.search_user_id_edit_txt);

        search_user_id_edit_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search_word = search_user_id_edit_txt.getText().toString();
                searchUser(search_word);
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_user_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ImageView community_back_btn = (ImageView) view.findViewById(R.id.community_back_btn);
        community_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).visibleTabLayout();
                getFragmentManager().popBackStack();

            }
        });

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        onItemTouchListener = new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                hideKeyboardFrom(getActivity(),getView());
                //손으로 터치한 곳의 좌표를 토대로 해당 Item의 View를 가져옴
                View childView = rv.findChildViewUnder(e.getX(), e.getY());

                //터치한 곳의 View가 RecyclerView 안의 아이템이고 그 아이템의 View가 null이 아니라
                //정확한 Item의 View를 가져왔고, gestureDetector에서 한번만 누르면 true를 넘기게 구현했으니
                //한번만 눌려서 그 값이 true가 넘어왔다면
                if (childView != null && gestureDetector.onTouchEvent(e)) {

                    //현재 터치된 곳의 position을 가져오고
                    int currentPosition = rv.getChildAdapterPosition(childView);

                    Log.v("current position", currentPosition + "");
                    if (searchUserList != null) {
                        Log.v("fragment 전환!", "fragment 전환!!");
                        //해당 위치의 Data를 가져옴
                        Fragment fragment = new CommunityUserFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle(3); // 파라미터는 전달할 데이터 개수
                        bundle.putString("userId", searchUserList.get(currentPosition).getUser_ID()); // key , value
                        bundle.putString("userName", searchUserList.get(currentPosition).getName()); // key , value
                        bundle.putString("profile_url", searchUserList.get(currentPosition).getProfile_url()); // key , value
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.root_frame2, fragment);
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    else {
                        Log.v("fragment 전달x", "fragment 전환X");
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };



        return view;

    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).visibleTabLayout();

    }

    public void searchUser(String word) {
        Log.v("serchUser process", "serchUser process!!!");
        Log.v("search_word", word);
        Call<SearchUserResponse> requestDetail = networkService.searchUser(word);
        requestDetail.enqueue(new Callback<SearchUserResponse>() {
            @Override
            public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("serchUser", "getFriendsList process2!!!");
                    Log.v("serchUserlist message", response.body().getMessage().toString());

                    if (response.body().getMessage().toString().equals("success")) {
                        if (response.body().getResult()!=null) {
                            Log.v("searchuserlist", response.body().getResult().toString());
                            searchUserList = response.body().getResult();

                            CommunitySearchListAdapter communitySearchListAdapter = new CommunitySearchListAdapter(getActivity(), searchUserList);
                            mRecyclerView.setAdapter(communitySearchListAdapter);
                            mRecyclerView.addOnItemTouchListener(onItemTouchListener);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}