package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.app_doc_truyen.Model.ApiInterface;

import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity extends AppCompatActivity {
     TextInputEditText txtUsername ,txtpast  ;
    private CheckBox checkRemeber;
    private Button btnDangnhap;
    private Button btnDangky;
    String str_email,str_password;
    String token = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        setContentView(R.layout.activity_login);
        txtpast = findViewById(R.id.edtPass) ;
        txtUsername = findViewById(R.id.edtUser);
        btnDangnhap = (Button) findViewById(R.id.btnDangNhap);
        checkRemeber = (CheckBox) findViewById(R.id.checkBox);
        btnDangky=(Button) findViewById(R.id.btnDangKy);
        SharedPreferences preferences =getSharedPreferences("user_file", MODE_PRIVATE);
        txtUsername.setText(preferences.getString("gmail",""));
        txtpast.setText(preferences.getString("matkhau",""));
        checkRemeber.setChecked(preferences.getBoolean("remember",false));
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateemail() | !validatepass() ){
                    return;
                }
                else {
                    postLogin(txtUsername.getText().toString() , txtpast.getText().toString());
                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                }

            }
        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),DangKy_Activity.class);
                startActivity(intent);
            }
        });
    }
    void postLogin(String Email, String Password) {
        str_email = txtUsername.getText().toString().trim();
        str_password = txtpast.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.139:2000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        User users = new User(Email,Password);
        Call<User> call = apiInterface.posLogin(users);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    User userApi = response.body();
                    token = userApi.getToken();
                    if (token == null) {
                        remember(str_email,str_password, checkRemeber.isChecked());
                        Log.e("sss", String.valueOf(userApi));
                        Toast.makeText(Login_Activity.this , " login thành công " , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login_Activity.this, MainActivity.class));
                    }
                } catch (Exception e) {
                    Toast.makeText(Login_Activity.this, "Login failed , Please check email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(txtUsername.getText().toString().equals("")){
            txtUsername.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!txtUsername.getText().toString().matches(a)) {
            txtUsername.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            txtUsername.setError(null);
            return true;
        }
    }
    private void remember(String strname, String strpass, boolean checked) {
        SharedPreferences preferences=getSharedPreferences("user_file",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(!checked){
            editor.clear();
        }else {
            editor.putString("gmail",strname);
            editor.putString("matkhau",strpass);
            editor.putBoolean("remember",checked);
        }
        editor.commit();
    }
    public boolean validatepass(){
        if(txtpast.getText().toString().equals("")){
            txtpast.setError("Nhập mật khẩu của bạn");
            return false;
        } else{
            txtpast.setError(null);
            return true;
        }
    }
}