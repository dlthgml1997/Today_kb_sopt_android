package com.kb.challenge.app.today.today_android.view.main;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse;
import com.kb.challenge.app.today.today_android.model.login.UserNameData;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.model.record.FeelingDataResponse;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.Init;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.login.DotFirstFragment;
import com.kb.challenge.app.today.today_android.view.login.FirstSettingActivity;
import com.kb.challenge.app.today.today_android.view.login.SetNameFragment;
import com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.kb.challenge.app.today.today_android.view.record.RecordFeelingFragment.feelingMsg;

/**
 * Created by shineeseo on 2018. 11. 6..
 */

public class MainFragment extends Fragment implements Init {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "MainFragment";

    private OnFragmentInteractionListener mListener;

    private NetworkService networkService;

    private String user_name;
    private int total_money;

    private TextView main_name_txt;


    @Override
    public void init() {
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance();
        getSavingList();

    }

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(/*String param1, String param2*/) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("onActivityResult",  requestCode + " : " + requestCode + " : " + data);

        //감정기록이 있을 경우 판별해서 각각의 fragment로 대입하는 코드 삽입
        if(requestCode == 200){
            if (resultCode == RESULT_OK) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                int feeling_record = data.getIntExtra("feeling_record", 0);

                if (feeling_record < 3) {
                    transaction.replace(R.id.root_frame, new MainBadFragment());

                } else {
                    transaction.replace(R.id.root_frame, new MainGoodFragment());
                }
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
//
///** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        show_record_feeling_dialog();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //감정 기록이 없을 경우 나타나는 main fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        init();

        main_name_txt = (TextView)view.findViewById(R.id.main_name_txt);

        getUserNameProperty();

        Button btn_go_to_main = (Button) view.findViewById(R.id.btn_go_to_main);

        btn_go_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragment 교체
                Fragment fragment = new RecordFeelingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("user_name", user_name);
                fragment.setArguments(bundle);
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                transaction.replace(R.id.root_frame, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

/** * Fragment의 변경사항을 반영시킨다. */
                transaction.commit();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void show_record_feeling_dialog() {
        final Dialog go_to_record_feeling_dialog = new Dialog(getActivity());
        go_to_record_feeling_dialog.setContentView(R.layout.dialog_record_feeling_main);

        TextView btn_ok_dialog = (TextView) go_to_record_feeling_dialog.findViewById(R.id.btn_ok_dialog);

        btn_ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new RecordFeelingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("user_name", user_name);
                Log.v("feeling record로 이동", user_name);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();
                go_to_record_feeling_dialog.dismiss();

            }
        });
        go_to_record_feeling_dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        getTodayFeelingData();
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
    public void getUserNameProperty() {
        Log.v("getUserNameProperty", "getUserNameProperty process!!!");

        Call<UserNameData> requestDetail = networkService.getUserName(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<UserNameData>() {
            @Override
            public void onResponse(Call<UserNameData> call, Response<UserNameData> response) {
                if (response.isSuccessful()) {
                    Log.v("getUserNameProperty", "saving process2!!!");
                    Log.v("name message", response.body().getMessage().toString());

                    user_name = response.body().getName();
                    Log.v("name", user_name);
                    if (user_name != null)
                        main_name_txt.setText(user_name);


                }
                else {
                    //사용자 이름 설정이 없으면 설정 페이지로 이동
                    Intent intent = new Intent(getActivity(), FirstSettingActivity.class);
                    getActivity().startActivity(intent);

                }


            }

            @Override
            public void onFailure(Call<UserNameData> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }


    //오늘의 감정이 존재하면 -> 감정에 따라 다른 프레그먼트로 전환됨.
    public void getTodayFeelingData() {
        Log.v("main feeling", "main feeling process!!!");
        //통신할 때 보낼 오늘의 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        Call<FeelingDataResponse> requestDetail = networkService.getTodayFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), getTime);
        requestDetail.enqueue(new Callback<FeelingDataResponse>() {
            @Override
            public void onResponse(Call<FeelingDataResponse> call, Response<FeelingDataResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("main feeling", "main feeling process2!!!");
                    Log.v("main feeling message", response.body().getMessage().toString());

                    ArrayList<FeelingData> feelingDataList = response.body().getData();
                    Log.v("main feeling data!!!", feelingDataList.toString());

                    if (!feelingDataList.isEmpty()) {
                        if (feelingDataList.get(feelingDataList.size() - 1).getBad() != null) {
                            Fragment fragment = new MainBadFragment();
                            Bundle bundle = new Bundle(1);
                            bundle.putString("user_name", user_name);
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.root_frame, fragment);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);

                            transaction.commit();

                        } else if (feelingDataList.get(feelingDataList.size() - 1).getGood() != null) {
                            //total money, username 필요
                            Fragment fragment = new MainDepositFragment();
                            Bundle bundle = new Bundle(2);
                            bundle.putString("user_name", user_name);
                            bundle.putInt("total_money", total_money);
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.root_frame, fragment);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);

                            transaction.commit();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FeelingDataResponse> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }
    public void getSavingList() {
        Log.v("savingList process", "savingList process!!!");
        Call<CoinSavingResponse> requestDetail = networkService.getSavingList(SharedPreference.Companion.getInstance().getPrefStringData("data"));
        requestDetail.enqueue(new Callback<CoinSavingResponse>() {
            @Override
            public void onResponse(Call<CoinSavingResponse> call, Response<CoinSavingResponse> response) {
                if (response.isSuccessful()) {
                    Log.v("savingList process2", "savingList process2!!!");
                    Log.v("saving list get message", response.body().getMessage().toString());
                    Log.v("coin saving list res", response.body().toString());

                    total_money = response.body().getTotalMoney();
                    Log.v("total toatl", total_money + "");

                }
            }

            @Override
            public void onFailure(Call<CoinSavingResponse> call, Throwable t) {
                Log.i("err saving", t.getMessage());
            }
        });
    }
}
