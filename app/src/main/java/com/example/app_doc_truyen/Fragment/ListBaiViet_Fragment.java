package com.example.app_doc_truyen.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Model.Comic;
//import com.example.demo_app_doc_truyen.GetDataInterface;
import com.example.demo_app_doc_truyen.R;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListBaiViet_Fragment extends Fragment {
    RecyclerView recy;
    Adapter_baiviet adapter;
    ArrayList<Comic> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_bai_viet_,container,false);
        recy = view.findViewById(R.id.recy);
        list = new ArrayList<>();
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recy.setLayoutManager(layoutManager);
        adapter = new Adapter_baiviet(list,getContext());
        recy.setAdapter(adapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.109:2000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        GetDataInterface apiInterface = retrofit.create(GetDataInterface.class);
//        Call<List<Comic>> call = apiInterface.getComics();
//        call.enqueue(new Callback<List<Comic>>() {
//            @Override
//            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
//                if (response.isSuccessful() && response.body()!=null){
//                    list.addAll(response.body());
//                    Log.e("TAG", "onResponse: " + list.size());
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Comic>> call, Throwable t) {
//
//            }
//        });
        return view;

    }
}