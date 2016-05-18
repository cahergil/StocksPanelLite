package com.carlos.capstone.iretrofit;


import com.carlos.capstone.models.StockDataResponse;
import com.carlos.capstone.models.StockStatsResponse;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Carlos on 26/12/2015.
 */
public class QueryRApi {
    public static IQuery myQueryService;
    public interface IQuery {

        @GET("v1/public/yql?format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env")
        Call<StockDataResponse> getDataByStock(@Query(value = "q",encoded =true ) String stock);
        @GET("v1/public/yql?diagnostics=true&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
        Call<StockDataResponse> getDataByStockAlternative(@Query(value = "q",encoded =true )String stock);
        @GET("v1/public/yql?format=json&env=https%3A%2F%2Fraw.githubusercontent.com%2Fcynwoody%2Fyql-tables%2Ffinance-1%2F/tables.env")
        Call<StockStatsResponse> getStatsByStock(@Query(value="q",encoded = true) String stock);

    }
    public static IQuery getMyQueryService() {

        if(myQueryService ==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://query.yahooapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            myQueryService=retrofit.create(IQuery.class);
            return myQueryService;
        } else {
            return myQueryService;
        }


    }

}


