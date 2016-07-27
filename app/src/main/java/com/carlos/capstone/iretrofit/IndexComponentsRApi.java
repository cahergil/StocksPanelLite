package com.carlos.capstone.iretrofit;

import com.carlos.capstone.models.Components;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Carlos on 13/02/2016.
 */
public class IndexComponentsRApi {
    public static IIndexComponents myService;
    public interface IIndexComponents {
        @GET("/v1/public/yql?format=json&callback=&_maxage=60")
        Call<Components> getComponentsByIndex(@Query(value="q",encoded = true) String qParam);

    }

    public static IIndexComponents getMyService(){

        if (myService==null) {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://query.yahooapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myService=retrofit.create(IIndexComponents.class);
            return myService;
        } else {
            return myService;
        }
    }
}
