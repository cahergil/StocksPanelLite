package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.YahooNewsResponse;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;


import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Carlos on 21/12/2015.
 */
public class NewsRApi {

    private static IStockNews myNewsService;

    public interface IStockNews {
        @GET("/v1/public/yql?format=json&diagnostics=true")
        Call<YahooNewsResponse> getNewsByStock(@Query(value = "q", encoded = true) String stock);
    }

    public static IStockNews getMyApiClient() {
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();


        if (myNewsService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://query.yahooapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            myNewsService = retrofit.create(IStockNews.class);
            return myNewsService;
        } else {
            return myNewsService;
        }
    }

    //    public static Interceptor provideOfflineCacheInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//
//                Request request = chain.request();
//
//                if (!Utilities.isNetworkAvailable()) {
//                    CacheControl cacheControl = new CacheControl.Builder()
//                            .maxStale(7, TimeUnit.DAYS)
//                            .build();
//                    request = request.newBuilder()
//                            .cacheControl(cacheControl)
//                            .build();
//                }
//                return chain.proceed(request);
//
//            }
//        };
//
//
//    }
    public static final Interceptor provideOfflineCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse=chain.proceed(chain.request());
            if(Utilities.isNetworkAvailable()) {
                int maxAge = 60*1; // read from cache for 1 minutes
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    };


}
