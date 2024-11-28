package com.credoapp.parent.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.credoapp.parent.CredoApp;
import com.credoapp.parent.common.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.credoapp.parent.utils.IConstants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL NO 1 on 01-10-2017.
 */
public class RetrofitImpl {
    public Retrofit retrofit;
    private OkHttpClient.Builder httpClient;
    public static RetrofitImpl retrofitImpl;

    private RetrofitImpl() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            Request newRequest;
            SharedPreferences sp = CredoApp.getAppContext().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
            String string = sp.getString(Constants.DEFAULTACADEMIC_ID, "-1");
            newRequest = request.newBuilder()
                    .addHeader("academic_year", string)
                    .build();
            Log.e("TAG", "init: "+newRequest.headers().toString());
            return chain.proceed(newRequest);
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(IConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();


    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static RetrofitImpl getRetrofitImpl() {
        if (retrofitImpl == null) {
            retrofitImpl = new RetrofitImpl();
        }
        return retrofitImpl;
    }
}
