package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.app_doc_truyen.Model.ApiInterface;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKy_Activity extends AppCompatActivity {
    TextInputEditText DateFormat;
    TextInputEditText edtSDT, edtEmail, edtHoTen, edtMk, edtMkck,edtDate;
    Button btnDK, btnHuy;
    RadioButton rbNam , rbNu;
    RadioGroup radioGroup;
    Intent intent;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        setTitle("Dang Ky");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DateFormat=findViewById(R.id.DateFormat);
//        edtDate= findViewById(R.id.edtDate);
        edtEmail = findViewById(R.id.edtEmail);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtMk = findViewById(R.id.edtPassDk);
        edtMkck = findViewById(R.id.edtPassCk);
        edtSDT = findViewById(R.id.edtSDT);
        btnDK = findViewById(R.id.btnDangKyT);
        btnHuy = findViewById(R.id.btnHuy);
        radioGroup = findViewById(R.id.radioG);
        rbNam =findViewById(R.id.rbNam);
        rbNu=findViewById(R.id.rbNu);


        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatename() | !validatesdt() | !validateemail() | !validatepass()|!validatrepass() ){
                    return;
                }
                else{
                    String FullName = edtHoTen.getText().toString() ;

                    String Password = edtMk.getText().toString() ;
                    String mkcheck = edtMkck.getText().toString() ;
                    String PhoneNumber = edtSDT.getText().toString() ;
                    String DateOfBirth = DateFormat.getText().toString() ;
                    String Email  = edtEmail.getText().toString() ;
                    String Gt ;
                    String Role = "User" ;
                    if (rbNam.isChecked()== true)
                    {
                        Gt =  "nam " ;
                    }
                    else
                    {
                        Gt = "nữ" ;
                    }

                    postData(FullName , Email , Password  , DateOfBirth , PhoneNumber , Gt , Role);

                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        DateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DangKy_Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                DateFormat.setText(date);
            }
        };
    }




    private void postData (String FullName , String Email , String Password ,String DateOfBirth , String  PhoneNumber , String GT , String Role )
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.139:2000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        User users = new User(FullName , Email ,Password , DateOfBirth , GT ,PhoneNumber , Role ) ;

        Call<User> call = apiInterface.posReg(users);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                startActivity(new Intent(DangKy_Activity.this,Login_Activity.class));
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(DangKy_Activity.this, "Register Failed!" + t, Toast.LENGTH_SHORT).show();
                Log.e( "nó bị " ,DateOfBirth );
                Log.e("loi o day ne", "onFailure: "+t );
            }
        });
    }
    public boolean validatename(){
        if(edtHoTen.getText().toString().equals("")){
            edtHoTen.setError("Hãy nhập tên của bạn.");
            return false;
        } else{
            edtHoTen.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edtEmail.getText().toString().equals("")){
            edtEmail.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!edtEmail.getText().toString().matches(a)) {
            edtEmail.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            edtEmail.setError(null);
            return true;
        }
    }
    public boolean validatrepass(){
        if(edtMkck.getText().toString().equals("")){
            edtMkck.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(!edtMkck.getText().toString().matches(edtMk.getText().toString())) {
            Log.e("aa",edtMkck.getText().toString()+edtMk.getText().toString() );
            edtMkck.setError("Mật khẩu không trùng khớp.");
            return false;
        }
        else{
            edtMkck.setError(null);
            return true;
        }
    }
    public boolean validatepass(){
        if(edtMk.getText().toString().equals("")){
            edtMk.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(edtMk.length()<8) {
            edtMk.setError("Nhập mật khẩu trên 8 kí tự.");
            return false;
        }
        else{
            edtMk.setError(null);
            return true;
        }
    }
    public boolean validatesdt(){
        String a = "^0[0-9]{9}$";
        if(edtSDT.getText().toString().equals("")){
            edtSDT.setError("Nhập số điện thoại của bạn");
            return false;
        }else if(!edtSDT.getText().toString().matches(a)){
            edtSDT.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        }
        else{
            edtSDT.setError(null);
            return true;
        }
    }

}
