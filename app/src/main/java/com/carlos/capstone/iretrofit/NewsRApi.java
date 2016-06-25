package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.YahooNewsResponse;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(provideOfflineCacheInterceptor);//setup cache
        File httpCacheDirectory = new File(MyApplication.getMyContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //add cache to the client
        okHttpClient.setCache(cache);

        if (myNewsService == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl("https://query.yahooapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(okHttpClient)
                    .build();
            myNewsService = client.create(IStockNews.class);
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
