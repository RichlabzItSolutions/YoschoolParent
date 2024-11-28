package com.credoapp.parent.retrofit;

import com.credoapp.parent.BuildConfig;
import com.credoapp.parent.common.Urls;
import com.credoapp.parent.network.ParentAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ITutorSource {
    private static ParentAPI restAPI;
    private static void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL_PARENT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        restAPI = retrofit.create(ParentAPI.class);
    }
    public static ParentAPI getRestAPI() {
        if (restAPI == null) init();
        return restAPI;
    }
}
