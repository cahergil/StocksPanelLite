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

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Carlos on 02/03/2016.
 */
public class IndexSummaryService extends IntentService {
    private String mTicker;
    public static final String TAG=IndexSummaryService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IndexSummaryService(String name) {
        super(name);
    }
    public IndexSummaryService() {
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
            public void onResponse(Response<IndexOrShortInfoDataResponse> response, Retrofit retrofit) {
                Log.i(TAG,"getSecurityShortInfoByTicker:"+response.raw());
                if(response.isSuccess()) {
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
                        contentValues.put(CapstoneContract.IndexDetailEntity.INDEX_TICKER, fieldsEntity.getSymbol());
                        String indexName = Html.fromHtml(fieldsEntity.getName()).toString();
                        contentValues.put(CapstoneContract.IndexDetailEntity.INDEX_NAME, indexName);
                        contentValues.put(CapstoneContract.IndexDetailEntity.PRICE, fieldsEntity.getPrice());
                        contentValues.put(CapstoneContract.IndexDetailEntity.CHANGE, fieldsEntity.getChange());
                        contentValues.put(CapstoneContract.IndexDetailEntity.CHANGE_PERCENT, fieldsEntity.getChg_percent());
                        contentValues.put(CapstoneContract.IndexDetailEntity.TIMESTAMP, fieldsEntity.getTs());
                        contentValues.put(CapstoneContract.IndexDetailEntity.UTCTIME, fieldsEntity.getUtctime());
                        contentValues.put(CapstoneContract.IndexDetailEntity.DAY_HIGHT, fieldsEntity.getDay_high());
                        contentValues.put(CapstoneContract.IndexDetailEntity.DAY_LOW, fieldsEntity.getDay_low());
                        contentValues.put(CapstoneContract.IndexDetailEntity.YEAR_HIGHT, fieldsEntity.getYear_high());
                        contentValues.put(CapstoneContract.IndexDetailEntity.YEAR_LOW, fieldsEntity.getYear_low());
                        contentValues.put(CapstoneContract.IndexDetailEntity.VOLUME, fieldsEntity.getVolume());
                        Uri uri = CapstoneContract.IndexDetailEntity.buildIndexDetailWithSymbol(fieldsEntity.getSymbol());
                        getContentResolver().insert(uri, contentValues);
                    }
                } else {
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
