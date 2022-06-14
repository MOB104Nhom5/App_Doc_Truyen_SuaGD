package com.example.app_doc_truyen.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_doc_truyen.Activity.DangKy_Activity;
import com.example.app_doc_truyen.Activity.Login_Activity;
import com.example.app_doc_truyen.Model.ApiInterface;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThemBaiViet_Fragment extends Fragment {
    FloatingActionButton btnfloat  ;
    TextInputEditText name , author , category , description ;
    Button btn_addtruyen  ;
    ImageView avatar ;
    List<User> userList  = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_bai_viet_,container,false);
        //
        btnfloat =view.findViewById(R.id.fab_themnhanvien) ;

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        //
        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext()) ;
                View view1 =LayoutInflater.from(getContext()) .inflate(R.layout.dialog_addtruyen , null) ;
                builder.setView(view1) ;
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                alertDialog.show();
                // ánh xạ
                avatar = view1.findViewById(R.id.imageView) ;
                name = view1.findViewById(R.id.tentruyen_addtruyen);
                author = view1.findViewById(R.id.tacgia_addtruyen);
                category = view1.findViewById(R.id.theloai_addtruyen);
                description = view1.findViewById(R.id.mota_addtruyen);

                btn_addtruyen = view1.findViewById(R.id.bnt_add_truyen) ;
                //
                String Name  = name.getText().toString() ;
                String Author  = author.getText().toString() ;
                String Category  = category.getText().toString() ;
                String Description  = description.getText().toString() ;
                String ngayup = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                String logo = " thêm sau " ;
                String like = "0" ;
                String trangthai = "chưa duyệt " ;
                User user = new User() ;
                String iduser =  user.getIDuser();
//                Log.e("id: " ,iduser) ;
                btn_addtruyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Name, Logo , Author , Category ,Description  , TrangThai , Like , DateUp , IDUserUp ;
                        postData(Name , logo, Author  ,Category, Description , trangthai , like , ngayup , iduser);
                        alertDialog.dismiss();
                    }
                });

            }
        });



        return view;

    }
    //Name, Logo , Author , Category ,Description  , TrangThai , Like , DateUp , IDUserUp ;
    private void postData (String Name , String Logo , String Author ,String Category , String  Description , String TrangThai , String Like, String DateUp , String IDUserUp)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.139:2000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Comic comic = new Comic( ) ;

        Call<User> call = apiInterface.posComic(comic);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getContext(), "thêm thành công " , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "thêm không thành công " , Toast.LENGTH_SHORT).show();
            }
        });
    }


}