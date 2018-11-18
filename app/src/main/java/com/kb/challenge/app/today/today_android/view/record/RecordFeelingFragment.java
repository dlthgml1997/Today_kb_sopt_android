package com.kb.challenge.app.today.today_android.view.record;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kb.challenge.app.today.today_android.base.BaseModel;
import com.kb.challenge.app.today.today_android.model.record.FeelingData;
import com.kb.challenge.app.today.today_android.network.ApplicationController;
import com.kb.challenge.app.today.today_android.network.NetworkService;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.MainActivity;
import com.kb.challenge.app.today.today_android.view.main.MainBadFragment;
import com.kb.challenge.app.today.today_android.view.main.MainGoodFragment;
import com.kb.challenge.app.today.today_android.R;

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

    public final static String[] feelingMsg = {"건드리면 물어요", "날 내버려둬요", "그저 그래요", "별 생각 없어요", "나쁘지 않아요", "기분이 좋아요", "오늘 기분 최고!"};
    int[] emotionImg = {R.drawable.img_emotion_bad_3, R.drawable.img_emotion_bad_2, R.drawable.img_emotion_bad_1, R.drawable.img_emotion_soso_0, R.drawable.img_emotion_good_1, R.drawable.img_emotion_good_2, R.drawable.img_emotion_good_3};
    int[] emotionTextImg = {R.drawable.img_text_bad_3, R.drawable.img_text_bad_2, R.drawable.img_text_bad_1, R.drawable.img_text_soso_0, R.drawable.img_text_good_1, R.drawable.img_text_good_2, R.drawable.img_text_good_3};
    private ImageView img_emotion;
    private ImageView img_text;
    private SeekBar seekBar;
    private Button record_save_btn;
    private static int progress_status = 3;
    private NetworkService networkService;
    private ImageView hint_bad_3, hint_bad_2, hint_bad_1, hint_soso_1, hint_good_1, hint_good_2, hint_good_3;
    private FeelingData feelingData;

    private String user_name;

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

        //탭 레이아웃 안 보이게.
        ((MainActivity) getActivity()).inVisibleTabLayout();

        Bundle bundle = getArguments();

        user_name = bundle.getString("user_name");

        networkService = ApplicationController.Companion.getInstance().getNetworkService();
        SharedPreference.Companion.getInstance().load(getActivity());

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        record_save_btn = (Button) view.findViewById(R.id.record_save_btn);
        img_emotion = (ImageView) view.findViewById(R.id.img_emotion);
        img_text = (ImageView) view.findViewById(R.id.img_text);
        //힌트 바꾸기위해 힌트아이디 찾음
        hint_bad_3 = (ImageView) view.findViewById(R.id.hint_bad_3);
        hint_bad_2 = (ImageView) view.findViewById(R.id.hint_bad_2);
        hint_bad_1 = (ImageView) view.findViewById(R.id.hint_bad_1);
        hint_soso_1 = (ImageView) view.findViewById(R.id.hint_soso_1);
        hint_good_1 = (ImageView) view.findViewById(R.id.hint_good_1);
        hint_good_2 = (ImageView) view.findViewById(R.id.hint_good_2);
        hint_good_3 = (ImageView) view.findViewById(R.id.hint_good_3);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        record_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog save_feeling_record_dialog = new Dialog(getActivity());
                save_feeling_record_dialog.setContentView(R.layout.dialog_record_emotion);

                TextView txt_name = (TextView) save_feeling_record_dialog.findViewById(R.id.txt_name);

                txt_name.setText(user_name + "님,");

                TextView btn_save_dialog = (TextView) save_feeling_record_dialog.findViewById(R.id.btn_save_dialog);

                btn_save_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordFeeling();
                        save_feeling_record_dialog.dismiss();
                    }
                });

                TextView btn_cancel_dialog = (TextView) save_feeling_record_dialog.findViewById(R.id.btn_cancel_dialog);

                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save_feeling_record_dialog.dismiss();
                    }
                });

                save_feeling_record_dialog.show();
            }
        });

        return view;
    }

    public void setEmotionImage(int progress_status) {
        switch (progress_status) {
            case 0:
                img_emotion.setImageResource(emotionImg[0]);
                img_text.setImageResource(emotionTextImg[0]);

                hint_bad_3.setVisibility(View.VISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                img_emotion.setImageResource(emotionImg[1]);
                img_text.setImageResource(emotionTextImg[1]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.VISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img_emotion.setImageResource(emotionImg[2]);
                img_text.setImageResource(emotionTextImg[2]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.VISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img_emotion.setImageResource(emotionImg[3]);
                img_text.setImageResource(emotionTextImg[3]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.VISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                img_emotion.setImageResource(emotionImg[4]);
                img_text.setImageResource(emotionTextImg[4]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.VISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 5:
                img_emotion.setImageResource(emotionImg[5]);
                img_text.setImageResource(emotionTextImg[5]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.VISIBLE);
                hint_good_3.setVisibility(View.INVISIBLE);
                break;
            case 6:
                img_emotion.setImageResource(emotionImg[6]);
                img_text.setImageResource(emotionTextImg[6]);

                hint_bad_3.setVisibility(View.INVISIBLE);
                hint_bad_2.setVisibility(View.INVISIBLE);
                hint_bad_1.setVisibility(View.INVISIBLE);
                hint_soso_1.setVisibility(View.INVISIBLE);
                hint_good_1.setVisibility(View.INVISIBLE);
                hint_good_2.setVisibility(View.INVISIBLE);
                hint_good_3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void recordFeeling() {
        Log.v("feeling save process", "feeling save process!!!");
        feelingData = new FeelingData(0, null, feelingMsg[3]);

        switch (progress_status) {
            case 0:
            case 1:
            case 2:
                feelingData = new FeelingData(null, seekBar.getMax() - progress_status - 3, feelingMsg[seekBar.getMax() - progress_status - 3]);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                feelingData = new FeelingData(progress_status - 3, null, feelingMsg[progress_status - 3]);
                break;

        }

        Log.v("feeling data!!!!!!!!", feelingData.toString());
        //감정기록하기 (token 값, feelingdata)
        final Call<BaseModel> requestDetail = networkService.recordFeeling(SharedPreference.Companion.getInstance().getPrefStringData("data"), feelingData);
        requestDetail.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                if (response.isSuccessful()) {
                    Log.v("feeling save process", "feeling save process!!!");
                    Log.v("feeling save message", response.body().getMessage().toString());
                    if (response.body().getMessage().toString().equals("success")) {
                        Log.v("feeling record", progress_status + " ");
                        if (progress_status < 3) {
                            Fragment fragment = new MainBadFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("user_name", user_name);
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                            transaction.replace(R.id.root_frame, fragment);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
                            ((MainActivity) getActivity()).visibleTabLayout();
/** * Fragment의 변경사항을 반영시킨다. */
                            transaction.commit();

                        } else {
                            Fragment fragment = new MainGoodFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
                            bundle.putInt("feeling_data", progress_status - 3); // key , value
                            bundle.putString("user_name", user_name);
                            fragment.setArguments(bundle);
/** * R.id.container(activity_main.xml)에 띄우겠다. * 파라미터로 오는 fragmentId에 따라 다음에 보여질 Fragment를 설정한다. */
                            transaction.replace(R.id.root_frame, fragment);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
/** * Fragment의 변경사항을 반영시킨다. */
                            ((MainActivity) getActivity()).visibleTabLayout();
                            transaction.commit();
                        }
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

                setEmotionImage(progress);
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
        ((MainActivity) getActivity()).visibleTabLayout();
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
