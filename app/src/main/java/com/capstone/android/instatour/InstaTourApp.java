package com.capstone.android.instatour;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("Registered")
public class InstaTourApp extends Application {

    // 싱글톤 데이터 리스트

    public static String TAG = "InstaTour_APP";
    public static String X_ACCESS_TOKEN = "JWT_TOKEN";

    public static Retrofit retrofit;

    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    public static MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");

    public static String BASE_URL = "https://api.instatour.tech/";
    // 싱글톤 데이터 리스트
    @Override
    public void onCreate() {
        super.onCreate();

        //Tracking Start
        Stetho.initializeWithDefaults(this);

    }

    public static void awsInstanceMethod(final Context context) {
        AWSMobileClient.getInstance().initialize(context).execute();
    }


    public static RetrofitInterface getRetrofitMethod(final Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addNetworkInterceptor(new XAccessTokenInterceptor(context)) // JWT 자동 헤더 전송
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(RetrofitInterface.class);
    }

    public static String catchAllThrowable(final Context context, final Throwable throwable) {
        if (throwable instanceof NullPointerException) {
            return context.getString(R.string.error_null);
        } else {
            throwable.printStackTrace();
            //    Log.d("에러", throwable.toString());
            return context.getString(R.string.error_network);

        }

    }

    public static String httpChange(String http) {
        if(http != null) {
            if(http.length()>5) {
                if(http.charAt(0) == 'h' && http.charAt(1) == 't' && http.charAt(2) == 't' && http.charAt(3) == 'p' && http.charAt(4) != 's' ) {
                    return http.replaceFirst("http", "https");
                }
            }
        }
        return http;
    }

}
