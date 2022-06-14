package com.example.app_doc_truyen.Model;

public interface CoreCallBack {

    void run();

    interface With<T> {
        void run(T t);
    }

    interface WithPair<T, V> {
        void run(T t, V v);
    }

    interface Request<T, V> {

        void onSuccessful(T data);

        void onFailure(V error);

    }

}