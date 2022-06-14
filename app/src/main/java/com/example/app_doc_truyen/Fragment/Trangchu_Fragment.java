package com.example.app_doc_truyen.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.demo_app_doc_truyen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Trangchu_Fragment extends Fragment {

    ViewFlipper viewFlipper;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_,container,false);
        viewFlipper = (ViewFlipper)view.findViewById(R.id.viewlipper);
        ViewFlip();
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }

        return view;
    }
    void ViewFlip()
    {
        ArrayList<String> mang = new ArrayList<>();
        mang.add("https://truyentranhtronbo.com/wp-content/uploads/2022/05/banner-truyen-tranh-tron-bo-bayvienngocrong.jpg");
        mang.add("https://truyenbanquyen.com/wp-content/uploads/2018/01/BANNER-WEB-180102.jpg");
        mang.add("https://congdongshop.com/wp-content/uploads/2022/05/banner-cong-dong-shop-truyen-tranh-full-bo.jpg");
        for(int i=0;i<mang.size();i++)
        {
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(mang.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_in);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_out);

        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    }
