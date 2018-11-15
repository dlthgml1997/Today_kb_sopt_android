package com.kb.challenge.app.today.today_android.view.record;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.login.LoginActivity;
import com.kb.challenge.app.today.today_android.view.main.MainActivity;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shineeseo on 2018. 11. 11..
 */

public class RecordFeelingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecordFeelingFragment.OnFragmentInteractionListener mListener;

    String[] feelingMsg = {"건드리면 물어요", "날 내버려둬요", "그저 그래요", "별 생각 없어요", "기분이 좋아요!", "기분이 좋아요!", "오늘 기분 최고!"};
    private TextView record_feeling_variation_txt;
    private SeekBar seekBar;
    private Button record_save_btn;
    private static int progress_status = 3;
    private NetworkService networkService;

    public RecordFeelingFragment() {
    }

    public static RecordFeelingFragment newInstance(/*String param1, String param2*/) {
        RecordFeelingFragment fragment = new RecordFeelingFragment();
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
        View view = inflater.inflate(R.layout.activity_record_feeling, container, false);
        ((MainActivity)getActivity()).inVisibleTabLayout();
        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(getActivity());

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        record_save_btn = (Button) view.findViewById(R.id.record_save_btn);
        record_feeling_variation_txt = (TextView) view.findViewById(R.id.record_feeling_variation_txt);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        record_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFeeling();
            }
        });

        return view;
    }

    public void recordFeeling() {
        Log.v("feeling save process", "feeling save process!!!");
        FeelingData feelingData = new FeelingData(0, null, record_feeling_variation_txt.getText().toString());
        if (!SharedPreference.Companion.getInstance().getPrefStringData("user_id").isEmpty()) {
            switch (progress_status) {
                case 0:
                case 1:
                case 2:
                    SharedPreference.Companion.getInstance().setPrefData(SharedPreference.Companion.getInstance().getPrefStringData("user_id") + "" + "feeling_score", (seekBar.getMax() - progress_status - 3));
                    new FeelingData(null, seekBar.getMax() - progress_status - 3, record_feeling_variation_txt.getText().toString());
                case 3:
                case 4:
                case 5:
                case 6:
                    SharedPreference.Companion.getInstance().setPrefData(SharedPreference.Companion.getInstance().getPrefStringData("user_id") + "" + "feeling_score", (seekBar.getMax() - 3));
                    new FeelingData(seekBar.getMax() - 3, null, record_feeling_variation_txt.getText().toString());
            }
        }

        //감정기록하기 (token 값, feelingdata)
        final Call<BaseModel> requestDetail = networkService.recordFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), feelingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("feeling save process", "feeling save process!!!");
                    Log.v("message", response.body().getMessage().toString());
                    if (response.body().getMessage().toString().equals("success")) {
                        Log.v("feeling record", progress_status + " ");
                        if (progress_status < 3) {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                            transaction.replace(R.id.root_frame, new MainBadFragment());
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
                            ((MainActivity) getActivity()).visibleTabLayout();
/** * Fragment의 변경사항을 반영시킨다. */
                            transaction.commit();

                        } else {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                            transaction.replace(R.id.root_frame, new MainGoodFragment());
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
/** * Fragment의 변경사항을 반영시킨다. */
                            ((MainActivity) getActivity()).visibleTabLayout();
                            transaction.commit();
                        }
                    }
                    else if (response.body().getMessage().toString().equals("access denied")){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("err", t.getMessage());
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            if (seekBar.getId() == R.id.seekBar) {
                progress_status = progress;
                record_feeling_variation_txt.setText(feelingMsg[progress]);
            }

        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecordFeelingFragment.OnFragmentInteractionListener) {
            mListener = (RecordFeelingFragment.OnFragmentInteractionListener) context;
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
