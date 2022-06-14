package com.example.app_doc_truyen.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Model.ApiInterface;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.CoreCallBack;
import com.example.demo_app_doc_truyen.R;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class YeuThich_Fragment extends Fragment {
    TextView tieude , noidung ;
    RecyclerView rclDStop;
    ImageView avatar;
    List<Comic> postList;
    Adapter_baiviet adapterbaiviet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeu_thich_,container,false);
//        iddd=view.findViewById(R.id.iddd);
        avatar = view.findViewById(R.id.imgBaiViet);
        tieude = view.findViewById(R.id.itemTenTieuDe) ;
        noidung = view.findViewById(R.id.itemTenNoiDung) ;
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        YeuThich_Fragment.self().getTodo("62a384e58bfcab300bef520f", new CoreCallBack.With<Comic>() {
            @Override
            public void run(Comic comic) {
                if (comic != null)
//                    ComicArrayList.add(new Comic());
                    Picasso.get().load(comic.getLogo())
                            .into(avatar);
//                    Log.e("aaaaaaaaaa",comic.getDescription());
               noidung.setText("Tên tác giả : " + comic.getAuthor());
                tieude.setText("Tên Truyện : " + comic.getName());
            }
        });
//        rclDStop.setHasFixedSize(true);
//        rclDStop.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapterbaiviet = new Adapter_baiviet((ArrayList<Comic>) postList,getContext());
//        rclDStop.setAdapter(adapterbaiviet);
        return view;
    }
    private static final String TAG = YeuThich_Fragment.class.getSimpleName();

    private static volatile YeuThich_Fragment mInstance = null;

    private Retrofit retrofit;

    public YeuThich_Fragment() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.139:2000/api/")
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }

    public static YeuThich_Fragment self() {
        if (mInstance == null)
            mInstance = new YeuThich_Fragment();
        return mInstance;
    }

    private <T> T getService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public void getTodo(String id, final CoreCallBack.With<Comic> coreCallBack) {
        getService(ApiInterface.class).getComic(id).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                if (coreCallBack != null) {
                    coreCallBack.run(response.body());
//                    postList = (List<Comic>) response.body();
                    Log.e("aaaaa", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}