package com.kb.challenge.app.today.today_android.view.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kb.challenge.app.today.today_android.BuildConfig;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.utils.PermissionUtils;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;
import com.kb.challenge.app.today.today_android.view.main.MainFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class SetNameFragment extends Fragment {
    private ImageView iv_setname_user_image;
    private OnEditNameSetListener onEditNameSetListener;
    private static OnProfileImageSetListener onProfileImageSetListener;
    private SetNameFragment.OnFragmentInteractionListener mListener;
    private EditText et_setname_user_name;
    public final int CAMERA_CODE = 1111;
    public final int GALLERY_CODE = 1112;
    MultipartBody.Part image;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setname, container, false);
        et_setname_user_name = (EditText) view.findViewById(R.id.et_setname_user_name);
        et_setname_user_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (!et_setname_user_name.getText().toString().equals("")) {
                    onEditNameSetListener.onNameSet(et_setname_user_name.getText().toString());
                    ((FirstSettingActivity) getActivity()).changeBtnAct();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        iv_setname_user_image = (ImageView) view.findViewById(R.id.iv_setname_user_image);

        iv_setname_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View v = inflater.inflate(R.layout.select_image_dialog_layout, null);
                builder.setView(v);

                builder.setTitle("프로필");
                final ArrayList<String> dialog_items = new ArrayList<>();
                dialog_items.add("사진촬영");
                dialog_items.add("앨범에서 사진 선택");
                dialog_items.add("기본 이미지로 변경");
                final CharSequence[] items = dialog_items.toArray(new String[dialog_items.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        switch (pos) {
                            case 0:
                                //사진촬영하기
                                sendTakePhotoIntent();
                                break;
                            case 1:
                                //갤러리에서 사진 가져오기 선택
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(intent, GALLERY_CODE);
                                break;
                            case 2:
                                //기본 이미지 사용 선택
                                break;
                            default:
                                break;

                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return view;
    }


    // 사진촬영 인텐트 실행
    private void sendTakePhotoIntent() {
        int permissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            // 권한 없음
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
            //Toast.makeText(getApplicationContext(),"권한없음",Toast.LENGTH_SHORT).show();
        } else {
            //권한 있음
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            getActivity().startActivityForResult(intent, CAMERA_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(getActivity(), "카메라 권한이 승인됨", Toast.LENGTH_SHORT).show();
            } else {
                //권한 거절된 경우
                Toast.makeText(getActivity(), "카메라 권한이 거절 되었습니다.카메라를 이용하려면 권한을 승낙하여야 합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    try {
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = this.getActivity().getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        Bitmap cropBitmap = cropCircle(img);
                        // 이미지 표시
                        iv_setname_user_image.setImageBitmap(cropBitmap);
                        iv_setname_user_image.setBackground(new ShapeDrawable(new OvalShape()));
                        if (Build.VERSION.SDK_INT >= 21) {
                            iv_setname_user_image.setClipToOutline(true);
                        }

                        //create a file to write bitmap data
                        File f = new File(this.getActivity().getCacheDir(), "profile");
                        f.createNewFile();

//Convert bitmap to byte array
                        Bitmap bitmap = img;
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

//                        ByteArrayOutputStream out = new ByteArrayOutputStream();
//                        //비트맵 이미지를 jpeg로 손실 압축
//                        img.compress(Bitmap.CompressFormat.JPEG, 100, out);

                        File photo = new File(data.toString());
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_url", photo.getName(), reqFile);

//                        RequestBody temp = RequestBody.create(MediaType.parse("image/jpeg"), out.toByteArray());
//
//
//                        image = MultipartBody.Part.createFormData("profile_url",photo.getName(),temp);
                        onProfileImageSetListener.onProfileImageSet(body);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case CAMERA_CODE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    Bitmap cropBitmap = cropCircle(imageBitmap);
                    iv_setname_user_image.setImageBitmap(cropBitmap);
                    iv_setname_user_image.setBackground(new ShapeDrawable(new OvalShape()));
                    if (Build.VERSION.SDK_INT >= 21) {
                        iv_setname_user_image.setClipToOutline(true);
                    }

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    RequestBody temp = RequestBody.create(MediaType.parse("image/png"), out.toByteArray());
                    Log.v("profile_img ", imageBitmap.toString());

                    File photo = new File(data.toString());
                    image = MultipartBody.Part.createFormData("profile_url", photo.getName(), temp);
                    onProfileImageSetListener.onProfileImageSet(image);
                    break;

                default:
                    break;
            }
        }
    }

    public Bitmap cropCircle(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        int size = (bitmap.getWidth() / 4);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    public interface OnProfileImageSetListener {
        void onProfileImageSet(MultipartBody.Part image);
    }

    public interface OnEditNameSetListener {
        void onNameSet(String name);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SetNameFragment.OnEditNameSetListener) {
            onEditNameSetListener = (SetNameFragment.OnEditNameSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onEditNameSetListener");
        }
        if (context instanceof SetNameFragment.OnProfileImageSetListener) {
            onProfileImageSetListener = (SetNameFragment.OnProfileImageSetListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onEditNameSetListener");
        }
        if (context instanceof SetNameFragment.OnFragmentInteractionListener) {
            mListener = (SetNameFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onEditNameSetListener = null;
        onProfileImageSetListener = null;
        mListener = null;
    }

}