package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.NewsRApi;
import com.carlos.capstone.models.YahooNewsResponse;
import com.carlos.capstone.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Carlos on 02/03/2016.
 */
public class StockNewsService extends IntentService {
    private String mSymbol;
    private static String LOG_TAG= StockNewsService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public StockNewsService(String name) {
        super(name);
    }
    public StockNewsService() {
        super(LOG_TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
           mSymbol= bundle.getString(getString(R.string.symbol_key));
        }
        getYahooNews();
    }
    public void getYahooNews(){
        NewsRApi.IStockNews myService = NewsRApi.getMyApiClient();
        String qParam = "select%20*%20from%20html%20where%20url%3D'http%3A%2F%2Ffinance.yahoo.com%2Fq%3Fs%3D" + mSymbol + "'%20%20%0Aand%20xpath%3D'%2F%2Fdiv%5B%40id%3D%22yfi_headlines%22%5D%2Fdiv%5B2%5D%2Ful%2Fli'";
        Call<YahooNewsResponse> call=myService.getNewsByStock(qParam);
        call.enqueue(new Callback<YahooNewsResponse>() {
            @Override
            public void onResponse(Call<YahooNewsResponse> call, Response<YahooNewsResponse> response) {
                Log.i(LOG_TAG, "retrofit getNewsByStock" + response.raw());
                if(response.isSuccessful()) {
                    YahooNewsResponse resp = response.body();
                    YahooNewsResponse.QueryEntity queryEntity = resp.getQuery();
                    YahooNewsResponse.QueryEntity.ResultsEntity resultsEntity = queryEntity.getResults();
                    //revistar esto me ha dado un npe
                    if(resultsEntity!=null) {
                        int deletedRows=0;
                        //first delete to avoid duplicate entries
                        deletedRows=getContentResolver().delete(CapstoneContract.StockNewsEntity.buildStockNewsWithSymbol(mSymbol),
                                CapstoneContract.StockNewsEntity.SYMBOL+"=?",
                                new String[]{mSymbol});
                        if(deletedRows>0) {
                            Log.i(LOG_TAG, "deleted rows from StockNews " + deletedRows);
                        }
                        ArrayList<YahooNewsResponse.QueryEntity.ResultsEntity.LiEntity> lista = (ArrayList<YahooNewsResponse.QueryEntity.ResultsEntity.LiEntity>) resultsEntity.getLi();
                        Vector<ContentValues> values=new Vector<ContentValues>();
                        for (int i=0;i<lista.size();i++) {
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(CapstoneContract.StockNewsEntity.SYMBOL,mSymbol);
                            YahooNewsResponse.QueryEntity.ResultsEntity.LiEntity.AEntity aEntity=lista.get(i).getA();
                            YahooNewsResponse.QueryEntity.ResultsEntity.LiEntity.CiteEntity citeEntity=lista.get(i).getCite();
                            if(aEntity!=null) {
                                contentValues.put(CapstoneContract.StockNewsEntity.TITLE,aEntity.getContent());
                                contentValues.put(CapstoneContract.StockNewsEntity.LINK_URL,aEntity.getHref());
                            }
                            if(citeEntity!=null) {
                                contentValues.put(CapstoneContract.StockNewsEntity.DATE,citeEntity.getSpan());
                                contentValues.put(CapstoneContract.StockNewsEntity.PUBLISHER,citeEntity.getContent());
                            }
                            values.add(contentValues);
                        }
                        int inserted_data=0;
                        Uri uri=CapstoneContract.StockNewsEntity.buildStockNewsWithSymbol(mSymbol);
                        ContentValues[] values_array=new ContentValues[values.size()];
                        values.toArray(values_array);
                        inserted_data=getContentResolver().bulkInsert(uri,values_array);
                        Log.d(LOG_TAG,"insert YahooNews into Database Succesfully Inserted : " + inserted_data);
                    }

                } else {

                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }
            }

            @Override
            public void onFailure(Call<YahooNewsResponse> call,Throwable t) {

                if (t instanceof IOException) {
                    Log.i(LOG_TAG, "retrofit getNewsByStock" + t.getMessage());
                   // Toast.makeText(getActivity(), "There was a network problem:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    if(!Utilities.isNetworkAvailable(getApplicationContext())){

                    }
                } else {
                //    Toast.makeText(getActivity(), "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
