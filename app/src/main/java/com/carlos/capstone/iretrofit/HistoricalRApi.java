package com.carlos.capstone.iretrofit;

import com.carlos.capstone.jsonpconverter.JsonpGsonConverterFactory;
import com.carlos.capstone.models.HistoricalDataResponseDate;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Carlos on 22/12/2015.
 */
public class HistoricalRApi {
    public static IStockChart myService;

    public interface IStockChart {
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=1d/json")
        Call<HistoricalDataResponseTimestamp> get1DHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=7d/json")
        Call<HistoricalDataResponseTimestamp> get7DHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=1m/json")
        Call<HistoricalDataResponseDate> get1MHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=6m/json")
        Call<HistoricalDataResponseDate> get6MHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=2y/json")
        Call<HistoricalDataResponseDate> get2YHistoricalDataByStock(@Path("ticker") String ticker);

    }
    public static IStockChart getMyApiService() {
        OkHttpClient client=new OkHttpClient();
        Dispatcher dispatcher=new Dispatcher();
        dispatcher.setMaxRequests(3);
        client.setDispatcher(dispatcher);
       // OkHttpClient client = new OkHttpClient();
      //  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      //  interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      //  client.interceptors().add(interceptor);
        if(myService ==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://chartapi.finance.yahoo.com/")
                    .addConverterFactory(JsonpGsonConverterFactory.create())
              //      .client(client)
                    .build();
            myService=retrofit.create(IStockChart.class);
            return myService;
        } else {
            return myService;
        }



    }
}
