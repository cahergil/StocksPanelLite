package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.IndexOrShortInfoDataResponse;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

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
        OkHttpClient client=new OkHttpClient();
        Dispatcher dispatcher=new Dispatcher();
        dispatcher.setMaxRequests(3);
        client.setDispatcher(dispatcher);
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
