package com.carlos.capstone.workerthreads;

import android.content.ContentValues;
import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.models.IndexOrShortInfoDataResponse;
import com.carlos.capstone.sync.CapstoneSyncAdapter;
import com.carlos.capstone.utils.TimeMeasure;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;


/**
 * Created by Carlos on 18/02/2016.
 */
public class WorkerThreadIndexes implements Runnable {
    private static final String LOG_TAG=WorkerThreadIndexes.class.getSimpleName();
    private String region;
    private Response<IndexOrShortInfoDataResponse> response;
    private Context context;
    private TimeMeasure tm;
    public WorkerThreadIndexes(Context context, TimeMeasure tm, Response<IndexOrShortInfoDataResponse> response, String region){
        this.region=region;
        this.response=response;
        this.context=context;
        this.tm=tm;
    }
    @Override
    public void run() {
        IndexOrShortInfoDataResponse resp=response.body();
        IndexOrShortInfoDataResponse.ListEntity list=resp.getList();
        List<IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity> resList=list.getResources();
        tm.log("BEGIN Insert INDEXES " + region);
        Vector<ContentValues> values=new Vector<ContentValues>();
        for(int i=0;i<resList.size();i++) {
            IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity resourcesEntity=resList.get(i);
            IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.
                    ResourceEntity resourceEntity=resourcesEntity.getResource();
            IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.ResourceEntity.
                    FieldsEntity fieldsEntity =resourceEntity.getFields();
            if(fieldsEntity!=null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CapstoneContract.IndexesEntity.REGION, region);
                contentValues.put(CapstoneContract.IndexesEntity.INDEX_NAME,
                        Html.fromHtml(fieldsEntity.getName()).toString());
                contentValues.put(CapstoneContract.IndexesEntity.INDEX_TICKER, fieldsEntity.getSymbol());
                contentValues.put(CapstoneContract.IndexesEntity.VALUE, fieldsEntity.getPrice());
                contentValues.put(CapstoneContract.IndexesEntity.VOLUMEN, fieldsEntity.getVolume());
                contentValues.put(CapstoneContract.IndexesEntity.CHANGE, fieldsEntity.getChange());
                contentValues.put(CapstoneContract.IndexesEntity.CHANGE_PERCENT, fieldsEntity.getChg_percent());
                contentValues.put(CapstoneContract.IndexesEntity.DATE, fieldsEntity.getUtctime());
                values.add(contentValues);
            }
        }
        int inserted_data = 0;
        ContentValues[] insert_data = new ContentValues[values.size()];
        values.toArray(insert_data);
        inserted_data=context.getContentResolver().bulkInsert(CapstoneContract.IndexesEntity.CONTENT_URI,insert_data);
        Log.d(LOG_TAG,"insertIndexesIntoDatabase Succesfully Inserted : " + inserted_data);
        tm.log("END Insert INDEXES " + region);
        CapstoneSyncAdapter.testEndLoads(context,tm);
    }
}
