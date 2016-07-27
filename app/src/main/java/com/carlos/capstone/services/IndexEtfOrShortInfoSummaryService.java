package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.IndexOrShortInfoRApi;
import com.carlos.capstone.models.IndexOrShortInfoDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Carlos on 02/03/2016.
 */
public class IndexEtfOrShortInfoSummaryService extends IntentService {
    private String mTicker;
    public static final String TAG=IndexEtfOrShortInfoSummaryService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IndexEtfOrShortInfoSummaryService(String name) {
        super(name);
    }
    public IndexEtfOrShortInfoSummaryService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle=intent.getExtras();
        mTicker=bundle.getString(getString(R.string.ticker_bundle_key));
        getIndexData();
    }
    public void getIndexData() {
        IndexOrShortInfoRApi.IIndexOrShortInfoData myService= IndexOrShortInfoRApi.getMyService();
        Call<IndexOrShortInfoDataResponse> call=myService.getSecurityShortInfoByTicker(mTicker);
        call.enqueue(new Callback<IndexOrShortInfoDataResponse>() {
            @Override
            public void onResponse(Call<IndexOrShortInfoDataResponse> call, Response<IndexOrShortInfoDataResponse> response) {
                Log.i(TAG,"getSecurityShortInfoByTicker:"+response.raw());
                if(response.isSuccessful()) {
                    IndexOrShortInfoDataResponse resp = response.body();
                    IndexOrShortInfoDataResponse.ListEntity list = resp.getList();
                    List<IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity> resList = list.getResources();
                    for (int i = 0; i < resList.size(); i++) {
                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity resourcesEntity = resList.get(i);
                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.
                                ResourceEntity resourceEntity = resourcesEntity.getResource();
                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.ResourceEntity.
                                FieldsEntity fieldsEntity = resourceEntity.getFields();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_TICKER, fieldsEntity.getSymbol());
                        String indexName = Html.fromHtml(fieldsEntity.getName()).toString();
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_NAME, indexName);
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.PRICE, fieldsEntity.getPrice());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE, fieldsEntity.getChange());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE_PERCENT, fieldsEntity.getChg_percent());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.TIMESTAMP, fieldsEntity.getTs());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.UTCTIME, fieldsEntity.getUtctime());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_HIGHT, fieldsEntity.getDay_high());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_LOW, fieldsEntity.getDay_low());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.YEAR_HIGHT, fieldsEntity.getYear_high());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.YEAR_LOW, fieldsEntity.getYear_low());
                        contentValues.put(CapstoneContract.IndexEtfOrShortInfoDetailEntity.VOLUME, fieldsEntity.getVolume());
                        Uri uri = CapstoneContract.IndexEtfOrShortInfoDetailEntity.buildIndexDetailWithSymbol(fieldsEntity.getSymbol());
                        getContentResolver().insert(uri, contentValues);
                    }
                } else {
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }
            }

            @Override
            public void onFailure(Call<IndexOrShortInfoDataResponse> call,Throwable t) {

            }
        });
    }
}
