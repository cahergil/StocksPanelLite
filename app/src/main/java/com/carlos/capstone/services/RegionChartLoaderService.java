package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;

import com.carlos.capstone.FragmentMain;
import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.HistoricalRApi;
import com.carlos.capstone.iretrofit.IndexOrShortInfoRApi;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.models.IndexDataUnit;
import com.carlos.capstone.models.IndexOrShortInfoDataResponse;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.TimeMeasure;
import com.carlos.capstone.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Carlos on 18/02/2016.
 */
public class RegionChartLoaderService extends IntentService {

    private static final String LOG_TAG =RegionChartLoaderService.class.getSimpleName();
    public static final String FETCH_EUROPE_DATA="com.carlos.capstone.services.action.FETCH_EUROPE_CHART_AND_INDEXES";
    public static final String FETCH_ASIA_DATA="com.carlos.capstone.services.action.FETCH_ASIA_CHART_AND_INDEXES";
    public static final String FETCH_AMERICA_DATA="com.carlos.capstone.services.action.FETCH_AMERICA_CHART";
    private ArrayList<IndexDataUnit> mIndexDataUnitList=new ArrayList<IndexDataUnit>();

    private  TimeMeasure tm;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegionChartLoaderService(String name) {
        super(name);
        tm=new TimeMeasure(LOG_TAG);
    }
    public RegionChartLoaderService(){
        super("RegionChartLoaderService");
        tm=new TimeMeasure(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if(intent!=null) {

            final String action=intent.getAction();
            if(action.equals(FETCH_EUROPE_DATA)) {
                tm.log("--BEGIN load1dDataChartEurope");
                load1dDataChartEurope();
               // tm.log("--BEING loadIndexEuropa");
               // loadIndexEuropa();
            } else if (action.equals(FETCH_ASIA_DATA)) {
                tm.log("--BEGIN Load1dDataChartAsia");
                load1dDataChartAsia();
              //  tm.log("--BEING loadIndexAsia");
             //   loadIndexAsia();
            } else if(action.equals(FETCH_AMERICA_DATA)) {
                tm.log("--BEGIN Load1dDataChartAmerica");
                load1dDataChartAmerica();
            }
        }


    }

    private void load1dDataChartAmerica(){

        HistoricalRApi.IStockChart service=HistoricalRApi.getMyApiService();

        Call<HistoricalDataResponseTimestamp> call1= service.get1DHistoricalDataByStock(getString(R.string.ticker_nasdaq));
        Call<HistoricalDataResponseTimestamp> call2= service.get1DHistoricalDataByStock(getString(R.string.ticker_dow_jones));
        Call<HistoricalDataResponseTimestamp> call3= service.get1DHistoricalDataByStock(getString(R.string.ticker_sp500));
        call1.enqueue(retrofitCallback(getString(R.string.region_america)));
        call2.enqueue(retrofitCallback(getString(R.string.region_america)));
        call3.enqueue(retrofitCallback(getString(R.string.region_america)));

    }
    private void load1dDataChartEurope(){
        HistoricalRApi.IStockChart service=HistoricalRApi.getMyApiService();

        Call<HistoricalDataResponseTimestamp> call4= service.get1DHistoricalDataByStock(getString(R.string.ticker_ibex35));
        Call<HistoricalDataResponseTimestamp> call5= service.get1DHistoricalDataByStock(getString(R.string.ticker_dax));
        Call<HistoricalDataResponseTimestamp> call6= service.get1DHistoricalDataByStock(getString(R.string.ticker_ftse));
        call4.enqueue(retrofitCallback(getString(R.string.region_europe)));
        call5.enqueue(retrofitCallback(getString(R.string.region_europe)));
        call6.enqueue(retrofitCallback(getString(R.string.region_europe)));

    }
    private void load1dDataChartAsia(){
        HistoricalRApi.IStockChart service=HistoricalRApi.getMyApiService();
        //^SSEC(china),^HSI (Hong Kong)
        Call<HistoricalDataResponseTimestamp> call7= service.get1DHistoricalDataByStock(getString(R.string.ticker_sse_composite));
        Call<HistoricalDataResponseTimestamp> call8= service.get1DHistoricalDataByStock(getString(R.string.ticker_hsi));
        Call<HistoricalDataResponseTimestamp> call9= service.get1DHistoricalDataByStock(getString(R.string.ticker_nikkei));
        call7.enqueue(retrofitCallback(getString(R.string.region_asia)));


    }

    private Callback<HistoricalDataResponseTimestamp> retrofitCallback(final String region) {

        return new Callback<HistoricalDataResponseTimestamp>() {
            @Override
            public void onResponse(Response<HistoricalDataResponseTimestamp> response, Retrofit retrofit) {

                if(response.isSuccess()) {
                    HistoricalDataResponseTimestamp resp= response.body();
                    List<HistoricalDataResponseTimestamp.SeriesEntity> lista=resp.getSeries();
                    IndexDataUnit indexDataUnit = Utilities.extractToIndexesData(lista,resp.getMeta().getPrevious_close());
                    indexDataUnit.setMarket(resp.getMeta().getExchange_name());
                    indexDataUnit.setName(resp.getMeta().getCompany_name());
                    indexDataUnit.setTicker(resp.getMeta().getTicker());
                    mIndexDataUnitList.add(indexDataUnit);
                    int sizeLimit;
                    sizeLimit=region.equals(getString(R.string.region_asia))?1:3;
                    if(mIndexDataUnitList.size() ==sizeLimit) {
                        //debugging purposes
                        for (int i = 0; i < mIndexDataUnitList.size(); i++) {
                            Log.d(LOG_TAG, ":" + mIndexDataUnitList.get(i).getTicker());
                        }
                        if(region.equals(getString(R.string.region_america))) {
                            sendFeedBackToSplash(region);
                        } else {
                            sendDataToFragmentMain(region);
                        }
                        mIndexDataUnitList.clear();
                        tm.log("========= END LOAD1dDataChart OK"+region);

                    }

                } else {
                    tm.log("========= END LOAD1dDataChart "+region + " unsuccess");
                    sendFailureFeedBack(region);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                tm.log("========= END LOAD1dDataChart "+region + " onFailure");
                sendFailureFeedBack(region);
            }
        };


    }

    private void sendFailureFeedBack(String region){
        if(region.equals(getString(R.string.region_america))) {
            Intent splashIntent = new Intent(getString(R.string.data_ready));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(splashIntent);
            return;
        }
        Intent intentFragmentMain = new Intent(getString(R.string.index_event));
        intentFragmentMain.putExtra(getString(R.string.region), region);
        intentFragmentMain.putExtra("failure",true);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentFragmentMain);

    }

    //only America data
    private void sendFeedBackToSplash(String region){
        ArrayList<IndexDataUnit> sendList;
        sendList=mIndexDataUnitList;
        if (region.equals(FragmentMain.REGION_AMERICA)) {
            Log.d(LOG_TAG, "PREnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }
        sendList=Utilities.normalizeDataUnits(sendList);
        if (region.equals(FragmentMain.REGION_AMERICA)) {
            Log.d(LOG_TAG, "POSTtnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }
        MyApplication.setmAmericaIndexes(sendList);
        Intent splashIntent = new Intent(getString(R.string.data_ready));
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(splashIntent);
        Utilities.setNeedEuropeSync(getApplicationContext(),true);
        Utilities.setNeedAsiaSync(getApplicationContext(),true);
        Log.d(LOG_TAG,"## broadcast splashIntent");
    }

    //only Europe and Asia data
    private void sendDataToFragmentMain(String region){

        ArrayList<IndexDataUnit> sendList;
        sendList=mIndexDataUnitList;
        if (region.equals(FragmentMain.REGION_EUROPE)) {
            Log.d(LOG_TAG, "PREnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }
        sendList=Utilities.normalizeDataUnits(sendList);
        if (region.equals(FragmentMain.REGION_EUROPE)) {
            Log.d(LOG_TAG, "POSTtnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }



        Intent intent = new Intent(getString(R.string.index_event));
        intent.putExtra(getString(R.string.region), region);
        intent.putExtra(getString(R.string.failure),false);
        intent.putParcelableArrayListExtra(getString(R.string.sent_list), sendList);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);



    }

    public void loadIndexEuropa(){

        IndexOrShortInfoRApi.IIndexOrShortInfoData service= IndexOrShortInfoRApi.getMyService();
        Call<IndexOrShortInfoDataResponse> call=service.getIndexesEurope();
        call.enqueue(retrofitCallbackIndexes(getString(R.string.region_europe)));
    }

    public void loadIndexAsia(){
        IndexOrShortInfoRApi.IIndexOrShortInfoData service= IndexOrShortInfoRApi.getMyService();
        Call<IndexOrShortInfoDataResponse> call=service.getIndexesAsia();
        call.enqueue(retrofitCallbackIndexes(getString(R.string.region_asia)));
    }
    private Callback<IndexOrShortInfoDataResponse> retrofitCallbackIndexes(final String region){

        return new Callback<IndexOrShortInfoDataResponse>() {
            @Override
            public void onResponse(Response<IndexOrShortInfoDataResponse> response, Retrofit retrofit) {
                if(response.isSuccess()) {
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
                    inserted_data=getContentResolver().bulkInsert(CapstoneContract.IndexesEntity.CONTENT_URI,insert_data);
                    Log.d(LOG_TAG,"insertIndexesIntoDatabase Succesfully Inserted : " + inserted_data);
                    tm.log("END Insert INDEXES " + region);


                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };

    }



}
