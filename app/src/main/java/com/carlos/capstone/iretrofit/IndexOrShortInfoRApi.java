package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.IndexOrShortInfoDataResponse;


import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Carlos on 29/01/2016.
 */
public class IndexOrShortInfoRApi {
    public static IIndexOrShortInfoData myService;
    public interface IIndexOrShortInfoData {

        @GET("/webservice/v1/symbols/TX60.TS,^GSPTSE,^IXIC,^NDX,^DJI,^GSPC,^BVSP,^MXX,^MERV,^IPSA/quote?format=json&view=detail")
        Call<IndexOrShortInfoDataResponse> getIndexesAmerica();
        @GET("/webservice/v1/symbols/^STOXX50E,^FTSE,^GDAXI,^FCHI,^IBEX,FTSEMIB.MI,PSI20.LS,BEL20.BR,^BFX,^SSMI,OBX.OL,RTS.RS,OMXC20.CO,^OMXSPI,^SSMI,FPXAA.PR,GD.AT,^ATX,^ISEQ/quote?format=json&view=detail")
        Call<IndexOrShortInfoDataResponse> getIndexesEurope();
        @GET("/webservice/v1/symbols/^N225,000001.SS,^AXJO,^AORD,^HSI,^BSESN,^NSEI,^NZ50,^TWII,^JKSE,^KLSE,^KS11,^STI,PSEI.PS/quote?format=json&view=detail")
        Call<IndexOrShortInfoDataResponse> getIndexesAsia();
        @GET("/webservice/v1/symbols/{ticker}/quote?format=json&view=detail")
        Call<IndexOrShortInfoDataResponse> getSecurityShortInfoByTicker(@Path("ticker") String ticker);
    }

    public static IIndexOrShortInfoData getMyService(){
        //OkHttpClient client=new OkHttpClient();

        HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        Dispatcher dispatcher=new Dispatcher();
        dispatcher.setMaxRequests(3);

        OkHttpClient client=new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .build();

        if (myService==null) {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://finance.yahoo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            myService=retrofit.create(IIndexOrShortInfoData.class);
            return myService;

        } else {
            return myService;
        }

    }
}
