package com.example.app_doc_truyen.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.demo_app_doc_truyen.R;

import java.io.IOException;

public class ThongTin_Activity extends AppCompatActivity {
    public static String Tag=MainActivity.class.getName();
    private LinearLayout linearperson;
    private LinearLayout linearchangepass;
    private LinearLayout linearlogout;
    private ImageView UploadIMG;
    private int My_Request_Code=10;
    private TextView sellect;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;


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
                        Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        UploadIMG.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
    );
    private Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      check();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        aSwitch=findViewById(R.id.designbottom);
        sellect = findViewById(R.id.sellect);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            aSwitch.setChecked(true);
        }else {
            aSwitch.setChecked(false);
        }
        linearperson = findViewById(R.id.linearperson);
        linearchangepass = findViewById(R.id.linearchangepass);
        linearlogout = findViewById(R.id.linearlogout);
        UploadIMG = findViewById(R.id.UploadIMG);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        UploadIMG.setOnClickListener(v -> {
            onClickRequestPermisson();
        });
        //thogtincanhan
        linearperson.setOnClickListener(v -> {

        });
        //doi pass
        linearchangepass.setOnClickListener(v -> {
            Intent intent=new Intent(this, ChangepassActivity.class);
            startActivity(intent);
        });
        //dang xuat
        linearlogout.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Đăng Xuất.");
            builder.setMessage("Bạn Có Muốn Thoát Ứng Dụng Không ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(getApplication(),Login_Activity.class);
                    startActivity(intent);
//                    System.exit(0 );
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        });
    }

    private void onClickRequestPermisson() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
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
    public void check(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else {
            setTheme(R.style.Theme_Light);
        }
    }
}