package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.YahooNewsResponse;

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
        Call<YahooNewsResponse> getNewsByStock(@Query(value = "q",encoded =true ) String stock);


    }


    public static IStockNews getMyApiClient() {
        if(myNewsService ==null){
            Retrofit client=new Retrofit.Builder()
                    .baseUrl("https://query.yahooapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myNewsService =client.create(IStockNews.class);
            return myNewsService;
        } else {
            return myNewsService;
        }



    }
}
