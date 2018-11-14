package com.kb.challenge.app.today.today_android.view.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.kb.challenge.app.today.today_android.BuildConfig;
import com.kb.challenge.app.today.today_android.R;
import com.kb.challenge.app.today.today_android.utils.PermissionUtils;
import com.kb.challenge.app.today.today_android.utils.SharedPreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class SetNameFragment extends Fragment {
    private ImageView iv_setname_user_image;
    private OnEditNameSetListener onEditNameSetListener;
    private OnProfileImageSetListener onProfileImageSetListener;
    private EditText et_setname_user_name;
    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int CROP_FROM_CAMERA = 1113;

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
                onEditNameSetListener.onNameSet(et_setname_user_name.getText().toString());
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

                final ArrayList<String> dialog_items = new ArrayList<>();
                dialog_items.add("사진촬영");
                dialog_items.add("앨범에서 사진 선택");
                dialog_items.add("기본 이미지로 변경");
                final CharSequence[] items = dialog_items.toArray(new String[dialog_items.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        switch (pos) {
                            case 0:
                                //사진촬영 선택
                                String state = Environment.getExternalStorageState();
                                if (Environment.MEDIA_MOUNTED.equals(state)) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                                        File photoFile = null;
//                                        try {
//                                            photoFile = createImageFile();
//                                        } catch (IOException ex) {
//
//                                        }
//                                        if (photoFile != null) {
//                                            photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName(), photoFile);
//                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                                            startActivityForResult(intent, CAMERA_CODE);
//                                        }
//                                    }
                                    String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";

                                    photoUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider",new File(Environment.getExternalStorageDirectory(), url));

                                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
                                    // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
                                    //intent.putExtra("return-data", true);
                                    startActivityForResult(intent, CAMERA_CODE);

                                }
                                break;
                            case 1:
                                //갤러리에서 사진 가져오기 선택
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.setType("image/*");
                                startActivityForResult(intent, GALLERY_CODE);
                                break;
                            case 2:
                                //기본 이미지 사용 선택
                                break;
                            default:
                                break;

                        }
                    }
                });

                builder.show();
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.v("request code(get image)", requestCode + "");
            switch (requestCode) {
                case CROP_FROM_CAMERA:
                    final Bundle extras = data.getExtras();

                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        iv_setname_user_image.setImageBitmap(photo);
                    }

                    // 임시 파일 삭제
                    File f = new File(photoUri.getPath());
                    if (f.exists()) {
                        f.delete();
                    }
                    break;
                case GALLERY_CODE:
//                    sendPicture(data.getData()); //갤러리에서 가져오기
                    photoUri = data.getData();
                    Bundle images = data.getExtras();

                    if (images != null) {
                        Bitmap photo = images.getParcelable("data");
                        iv_setname_user_image.setImageBitmap(photo);
                    }
                    break;
                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(photoUri, "image/*");

                    intent.putExtra("outputX", 90);
                    intent.putExtra("outputY", 90);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CROP_FROM_CAMERA);

                    break;

                default:
                    break;
            }
        }
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case CAMERA_PERMISSIONS_REQUEST:
//                if (PermissionUtils.isPermissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
//                    startCamera();
//                }
//                break;
//            case GALLERY_PERMISSIONS_REQUEST:
//                if (PermissionUtils.isPermissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
//                    startGalleryChooser();
//                }
//                break;
//        }
//    }
    public interface OnProfileImageSetListener {
        void onProfileImageSet(String image);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onEditNameSetListener = null;
        onProfileImageSetListener = null;
    }
}