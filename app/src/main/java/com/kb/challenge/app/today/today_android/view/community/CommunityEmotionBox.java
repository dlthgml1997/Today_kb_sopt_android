package com.kb.challenge.app.today.today_android.view.community;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.community.EmotionBoxData;
import com.kb.challenge.app.today.today_android.model.community.EmotionBoxResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.community.adapter.CommunityEmotionBoxListAdapter;
import com.kb.challenge.app.today.today_android.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 16..
 */

public class CommunityEmotionBox extends Fragment implements Init {
    private CommunityEmotionBox.OnFragmentInteractionListener mListener;

    private NetworkService networkService;

    private RecyclerView mRecyclerView;

    private ImageView community_emotion_profile_img;

    private RecyclerView.OnItemTouchListener onItemTouchListener;

    private ArrayList<EmotionBoxData> emotionBoxList;

    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
    }

    public CommunityEmotionBox() {
        // Required empty public constructor
    }

    public static CommunityEmotionBox newInstance(/*String param1, String param2*/) {
        CommunityEmotionBox fragment = new CommunityEmotionBox();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.community_emotion_box, container, false);

        //탭 레이아웃 감춤
        ((MainActivity) getActivity()).inVisibleTabLayout();

        init();
        community_emotion_profile_img = (ImageView) view.findViewById(R.id.community_emotion_profile_img);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.community_emotion_box_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), new LinearLayoutManager(getActivity()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        getEmotionBoxList();

        ImageView community_back_btn = (ImageView) view.findViewById(R.id.community_back_btn);

        community_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("뒤로 가기 버튼", "뒤로 가기 버튼");
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
                //손으로 터치한 곳의 좌표를 토대로 해당 Item의 View를 가져옴
                View childView = rv.findChildViewUnder(e.getX(), e.getY());

                //터치한 곳의 View가 RecyclerView 안의 아이템이고 그 아이템의 View가 null이 아니라
                //정확한 Item의 View를 가져왔고, gestureDetector에서 한번만 누르면 true를 넘기게 구현했으니
                //한번만 눌려서 그 값이 true가 넘어왔다면
                if (childView != null && gestureDetector.onTouchEvent(e)) {

                    //현재 터치된 곳의 position을 가져오고
                    int currentPosition = rv.getChildAdapterPosition(childView);
                    Log.v("current position", currentPosition + "");

                    //위로의 메시지를 담은 다이얼로그 띄우기
                    final Dialog send_Cheerup_Msg_Dialog = new Dialog(getActivity());
                    send_Cheerup_Msg_Dialog.setContentView(R.layout.dialog_cheerup_msg);
                    send_Cheerup_Msg_Dialog.setTitle("cheerup Dialog");

                    final EditText edit_cheerup_msg = (EditText)send_Cheerup_Msg_Dialog.findViewById(R.id.edit_cheerup_msg);

                    TextView txt_dialog_cheerup_name = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.txt_dialog_cheerup_name);

                    String from_id = emotionBoxList.get(currentPosition).getFrom_id();

                    txt_dialog_cheerup_name.setText(emotionBoxList.get(currentPosition).getName());

                    TextView txt_dialog_cheerup_hello = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.txt_dialog_cheerup_hello);

                    txt_dialog_cheerup_hello.setText("님이 보낸");

                    TextView txt_dialog_cheerup_feeling = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.txt_dialog_cheerup_feeling);

                    txt_dialog_cheerup_feeling.setText("위로의 메시지");

                    TextView btn_cancel_dialog_cheerup = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.btn_cancel_dialog_cheerup);


                    btn_cancel_dialog_cheerup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            send_Cheerup_Msg_Dialog.dismiss();
                        }
                    });

                    TextView btn_send_dialog_cheerup = (TextView)send_Cheerup_Msg_Dialog.findViewById(R.id.btn_send_dialog_cheerup);

                    btn_send_dialog_cheerup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            send_Cheerup_Msg_Dialog.dismiss();
                        }
                    });

                    send_Cheerup_Msg_Dialog.show();
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunityEmotionBox.OnFragmentInteractionListener) {
            mListener = (CommunityEmotionBox.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).visibleTabLayout();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getEmotionBoxList() {
        //오늘 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        Log.v("getEmotionBoxList", "getEmotionBoxList process!!!");
        Call<EmotionBoxResponse> requestDetail = networkService.getEmotionBoxList(SharedPreference.Companion.getInstance().getPrefStringData("data"), "2018-11-17");
        requestDetail.enqueue(new Callback<EmotionBoxResponse>() {
            @Override
            public void onResponse(Call<EmotionBoxResponse> call, Response<EmotionBoxResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("getEmotionBoxList", "getEmotionBoxList process2!!!");
                    Log.v("emotion message", response.body().getMessage().toString());

                    final ArrayList<EmotionBoxData> emotionBoxList = response.body().getData();
                    if (emotionBoxList != null) {

                        CommunityEmotionBoxListAdapter communityEmotionBoxListAdapter = new CommunityEmotionBoxListAdapter(getActivity(), emotionBoxList);
                        mRecyclerView.setAdapter(communityEmotionBoxListAdapter);

                        mRecyclerView.addOnItemTouchListener(onItemTouchListener);



                    }
                }

            }

            @Override
            public void onFailure(Call<EmotionBoxResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
}
