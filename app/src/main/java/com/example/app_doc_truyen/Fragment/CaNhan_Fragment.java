package com.example.app_doc_truyen.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Activity.MainActivity;
import com.example.app_doc_truyen.Activity.ThongTin_Activity;
import com.example.app_doc_truyen.Adapter.TapAdapter;
import com.example.app_doc_truyen.Model.ApiInterface;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CaNhan_Fragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView iconThongTin;
    TextView edtTen_Nd,edtmail_Nd;
    EditText editTENSPT;
    ImageView editHASPT;
    EditText edittheloai;
    EditText editMOTASPT;
    private int My_Request_Code=10;
    public static String Tag= MainActivity.class.getName();

    Button btnHuyThem;
    Button btnLuuThem;
    private ActivityResultLauncher<Intent> mActivityResuiltlauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK);
                    Intent data=result.getData();
                    if(data==null){
                        return;
                    }
                    Uri uri=data.getData();
                    try {
                        Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        editHASPT.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
    );
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan_,container,false);
        tabLayout = view.findViewById(R.id.tap_layout);
        viewPager = view.findViewById(R.id.viewPage);
        edtTen_Nd=view.findViewById(R.id.edtTen_Nd);
        edtmail_Nd=view.findViewById(R.id.edtmail_Nd);
        iconThongTin = view.findViewById(R.id.iconThongTin);
        TapAdapter tapAdapter = new TapAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tapAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
//            ThongTin_Activity.check(true);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
//            ThongTin_Activity.check(false);
        }
        iconThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ThongTin_Activity.class);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        edtmail_Nd.setText(user);
        edtTen_Nd.setOnClickListener(v -> {
            themSP();
        });
        return view;
    }
    private void themSP(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View viewT = LayoutInflater.from(builder.getContext()).inflate(R.layout.dialog_them_sp, null);
        builder.setView(viewT);
        Dialog dialog = builder.create();
        dialog.show();


        editTENSPT = (EditText) viewT.findViewById(R.id.editTENSPT);
        editHASPT = (ImageView) viewT.findViewById(R.id.editHASPT);
        edittheloai = (EditText) viewT.findViewById(R.id.edittheloai);
        editMOTASPT = (EditText) viewT.findViewById(R.id.editMOTASPT);
        btnHuyThem = (Button) viewT.findViewById(R.id.btnHuyThem);
        btnLuuThem = (Button) viewT.findViewById(R.id.btnLuuThem);


        editHASPT.setOnClickListener(v -> {
            onClickRequestPermisson();
        });
        btnLuuThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_tensp = editTENSPT.getText().toString().trim();
                String str_theloai = edittheloai.getText().toString().trim();
                String str_motasp = editMOTASPT.getText().toString().trim();
                if (str_tensp.equalsIgnoreCase("") || str_theloai.equalsIgnoreCase("")
                        || str_motasp.equalsIgnoreCase("")) {
                    Toast.makeText(builder.getContext(), "Hãy nhập hết tất cả các trường trên form!", Toast.LENGTH_SHORT).show();
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.28:2000/api/comics")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                    Comic comic = new Comic();
                    Call<Comic> call = apiInterface.View(comic);
                    call.enqueue(new Callback<Comic>() {

                        @Override
                        public void onResponse(Call<Comic> call, retrofit2.Response<Comic> response) {
                            Log.e("aaaaa", String.valueOf(response.body()));
                        }

                        @Override
                        public void onFailure(Call<Comic> call, Throwable t) {

                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void onClickRequestPermisson() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            String [] pemisson={Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(pemisson,My_Request_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==My_Request_Code){
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }

    }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResuiltlauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
}