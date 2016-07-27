package com.carlos.capstone.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.appwidget.AppWidgetManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.carlos.capstone.FragmentMain;
import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.HistoricalRApi;
import com.carlos.capstone.iretrofit.IndexOrShortInfoRApi;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.models.IndexDataUnit;
import com.carlos.capstone.models.IndexOrShortInfoDataResponse;
import com.carlos.capstone.services.DownloadSecurityFromTxt;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.TimeMeasure;
import com.carlos.capstone.utils.Utilities;
import com.carlos.capstone.widget.StocksWidgetProvider;
import com.carlos.capstone.workerthreads.WorkerThreadIndexes;
import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Carlos on 25/01/2016.
 */

public class CapstoneSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String LOG_TAG = CapstoneSyncAdapter.class.getSimpleName();
    // Interval at which to sync , in seconds.
    // 60 seconds (1 minute) * 7 = 7 minutes

    public static final int SYNC_INTERVAL=60*2;
    //  public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    public static final int SYNC_FLEXTIME = 0;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final long HOUR_IN_MILLIS = 1000 * 60 * 60;

    private ArrayList<IndexDataUnit> mIndexDataUnitArrayListAmerica =new ArrayList<IndexDataUnit>();



    private  TimeMeasure tm;
    private static final int HOT_START=1;
    private static final int NORMAL_START=2;
    private static final String SYNC_TYPE="sync_type";
    private static int mTotalCount=0;
    private static final int LOAD_PARTIAL =1;
    private static final int LOAD_FULL=8;
    private static int total_loads =0;
    private Context mContext;
    private ExecutorService mExecutorIndexes;
    private ExecutorService mExecutorChart;
    private boolean mInformSplash;
    private static boolean  preLoadWatchList=false;

    public static final String ACTION_DATA_UPDATED ="com.carlos.capstone.ACTION_DATA_UPDATED";


    public CapstoneSyncAdapter(Context mContext, boolean autoInitialize) {
        super(mContext, autoInitialize);
        this.mContext = mContext;
        tm=new TimeMeasure(LOG_TAG);

    }

    /**
     * Load Rss fedd for America
     */
    class RssLoaderUSA implements OnRssLoadListener {
        public RssLoaderUSA() {

        }

        private void loadFeeds() {
            //you can also pass multiple urls
            String[] urlArr = {mContext.getString(R.string.rss_america)};
            new RssReader(mContext)
                    .showDialog(false)
                    .urls(urlArr)
                    .parse(this);
        }

        @Override
        public void onSuccess(List<RssItem> rssItems) {

            insertNewsIntoDatabase(rssItems,mContext.getString(R.string.region_america));
        }

        @Override
        public void onFailure(String message) {
            tm.log("======== END LoadFeeds Usa with Failure");
            testEndLoads(mContext,tm);
        }
    }
    /**
     * Load Rss fedd for Europe
     */
    class RssLoaderEUROPE implements OnRssLoadListener {
        public RssLoaderEUROPE() {

        }

        private void loadFeeds() {
            //you can also pass multiple urls
            String[] urlArr = {mContext.getString(R.string.rss_europe)};
            new RssReader(mContext)
                    .showDialog(false)
                    .urls(urlArr)
                    .parse(this);
        }

        @Override
        public void onSuccess(List<RssItem> rssItems) {

            insertNewsIntoDatabase(rssItems,mContext.getString(R.string.region_europe));
        }

        @Override
        public void onFailure(String message) {
            tm.log("======== END LoadFeeds Europe with Failure");
            testEndLoads(mContext,tm);
        }
    }
    /**
     * Load Rss fedd for Asia
     */
    class RssLoaderASIA implements OnRssLoadListener {
        public RssLoaderASIA() {

        }

        private void loadFeeds() {
            //you can also pass multiple urls
            String[] urlArr = {mContext.getString(R.string.rss_asia)};
            new RssReader(mContext)
                    .showDialog(false)
                    .urls(urlArr)
                    .parse(this);
        }

        @Override
        public void onSuccess(List<RssItem> rssItems) {

            insertNewsIntoDatabase(rssItems,mContext.getString(R.string.region_asia));
        }

        @Override
        public void onFailure(String message) {
            tm.log("======== END LoadFeeds Asia with Failure");
            testEndLoads(mContext,tm);
        }
    }

    /**
     *
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {


        Log.d(LOG_TAG,"onPerformSync");
        //reset for the next load
        tm=new TimeMeasure(LOG_TAG);
        int numProc=Runtime.getRuntime().availableProcessors();
        mExecutorIndexes = Executors.newFixedThreadPool(1);
        mExecutorChart=Executors.newFixedThreadPool(3);
        //reset this variable used in testEndLoads
        mTotalCount=0;

        int syncType=extras.getInt(SYNC_TYPE);
        //if the user starts the app and the app is not in background then we only need to get
        // the data related to the charts
        if (syncType==HOT_START) {
            //i don't know the reason but after installation of the app the SyncAdapter executes twice
            //first execution is this, when HOT_START. To avoid this situation check first_launch sp variable if
            //it is the first launch then return, else go on with the "hot start"
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
            if(sharedPreferences.getBoolean(mContext.getString(R.string.first_launch),true)) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean(mContext.getString(R.string.first_launch),false);
                editor.commit();
                Intent intent=new Intent(mContext, DownloadSecurityFromTxt.class);
                getContext().startService(intent);
                mInformSplash=true;
                preLoadWatchList=true;

                return;
            }

           // Intent intent=new Intent(mContext, DownloadSymbolFromExcel.class);
           // getContext().startService(intent);
           // Intent intent=new Intent(mContext, DownloadSymbol.class);
           // getContext().startService(intent);


        } else {

            total_loads = LOAD_FULL;
            tm.log("======= BEGIN LoadIndexes ");
            loadIndexes();

            tm.log("======== BEGIN LOAD1dDataChartAmerica");
            load1dDataChartAmerica();

            deleteNewsFromDatabase();
            RssLoaderUSA rssLoaderUSA = new RssLoaderUSA();
            tm.log("======== BEGIN LoadFeedsUSA");
            rssLoaderUSA.loadFeeds();
            RssLoaderEUROPE rssLoaderEUROPE = new RssLoaderEUROPE();
            tm.log("======== BEGIN LoadFeedsEUROPE");
            rssLoaderEUROPE.loadFeeds();
            RssLoaderASIA rssLoaderASIA = new RssLoaderASIA();
            tm.log("======== BEGIN LoadFeedsASIA");
            rssLoaderASIA.loadFeeds();
            tm.log("======== BEGIN LoadFavorites");
            loadFavorites(true,null,getContext(),tm);

            return;
        }
    }

    public static void UpdateWidgetFavorites(Context context){

        setProgressBarWidgetVisibility(View.VISIBLE,context);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateWidget(context);
        setProgressBarWidgetVisibility(View.GONE,context);
        setUpdatedDateOnWidget(context);
    }

    private static void setProgressBarWidgetVisibility(final int viewType,Context context){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget_details);
        remoteViews.setViewVisibility(R.id.llprogressBar,viewType);
        ComponentName thisWidget = new ComponentName(context, StocksWidgetProvider.class);
        appWidgetManager.updateAppWidget(thisWidget,remoteViews);

    }
    private static void setUpdatedDateOnWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget_details);
        Date date=new Date(System.currentTimeMillis());
        DateFormat formatter=DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        remoteViews.setTextViewText(R.id.date,formatter.format(date));
        ComponentName thisWidget = new ComponentName(context, StocksWidgetProvider.class);
        appWidgetManager.updateAppWidget(thisWidget,remoteViews);

    }

     /***/
    public static void loadFavorites(final boolean belongsToSyncAdapter,
                                     final String helperVar,
                                     final Context context,
                                     final TimeMeasure tm) {
        IndexOrShortInfoRApi.IIndexOrShortInfoData service= IndexOrShortInfoRApi.getMyService();
        Call<IndexOrShortInfoDataResponse> call;
        if(belongsToSyncAdapter) {
            Cursor c=null;

            //pre load watchlist with 4 symbols
            if(preLoadWatchList) {
                String[] preLoadedQuotes=context.getResources().getStringArray(R.array.preLoadedQuotes);
                Uri uri=CapstoneContract.FavoritesEntity.CONTENT_URI;
                Vector<ContentValues> values=new Vector<ContentValues>();
                for(int i=0;i<4;i++) {
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_TICKER,preLoadedQuotes[i]);
                    values.add(contentValues);
                }
                ContentValues[] arrayValues=new ContentValues[values.size()];
                values.toArray(arrayValues);

                context.getContentResolver().bulkInsert(uri,arrayValues);
                preLoadWatchList=false;
            }
            //if the list of favorites is empty return
             c = context.getContentResolver().query(
                    CapstoneContract.FavoritesEntity.CONTENT_URI,
                    new String[]{CapstoneContract.FavoritesEntity.COMPANY_TICKER},
                    null,
                    null,
                    null);
            if (c != null && c.getCount() < 1) {
                if (helperVar != null && helperVar.equals(context.getString(R.string.sa_helper_var))) {
                    tm.log("END REFRESHING FAVORITES");
                } else {
                    tm.log("END LOAD FAVORITES");
                    testEndLoads(context, tm);
                }
                return;
            }

            StringBuilder tickerBuilder = new StringBuilder();
            String ticker;
            while (c.moveToNext()) {
                ticker = c.getString(c.getColumnIndex(CapstoneContract.FavoritesEntity.COMPANY_TICKER));
                tickerBuilder.append(ticker);
                if (c.isAfterLast()) {
                    continue;
                }
                tickerBuilder.append(context.getString(R.string.comma_string));

            }
            c.close();
             call = service.getSecurityShortInfoByTicker(tickerBuilder.toString());
        } else {
             call = service.getSecurityShortInfoByTicker(helperVar);
        }

        call.enqueue(new Callback<IndexOrShortInfoDataResponse>() {
            @Override
            public void onResponse(Call<IndexOrShortInfoDataResponse> call,  Response<IndexOrShortInfoDataResponse> response) {
                if(response.isSuccessful()) {

                    IndexOrShortInfoDataResponse resp=response.body();
                    IndexOrShortInfoDataResponse.ListEntity list=resp.getList();
                    List<IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity> resList=list.getResources();
                    Vector<ContentValues> values=new Vector<ContentValues>();
                    for(int i=0;i<resList.size();i++) {

                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity resourcesEntity=resList.get(i);
                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.
                                ResourceEntity resourceEntity=resourcesEntity.getResource();
                        IndexOrShortInfoDataResponse.ListEntity.ResourcesEntity.ResourceEntity.
                                FieldsEntity fieldsEntity =resourceEntity.getFields();

                        if(fieldsEntity!=null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_TICKER,fieldsEntity.getSymbol());
                            contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_NAME, Html.fromHtml(fieldsEntity.getName()).toString());
                            contentValues.put(CapstoneContract.FavoritesEntity.MARKET,fieldsEntity.getType().toUpperCase());
                            contentValues.put(CapstoneContract.FavoritesEntity.VALUE,fieldsEntity.getPrice());
                            contentValues.put(CapstoneContract.FavoritesEntity.CHANGE,fieldsEntity.getChange());
                            contentValues.put(CapstoneContract.FavoritesEntity.CHANGE_PERCENT,fieldsEntity.getChg_percent());
                            contentValues.put(CapstoneContract.FavoritesEntity.MAX,fieldsEntity.getDay_high());
                            contentValues.put(CapstoneContract.FavoritesEntity.MIN,fieldsEntity.getDay_low());
                            contentValues.put(CapstoneContract.FavoritesEntity.TRADE_DATE,fieldsEntity.getTs());
                            values.add(contentValues);

                        }


                    }//for
                    int inserted_data = 0;
                    ContentValues[] insert_data = new ContentValues[values.size()];
                    values.toArray(insert_data);
                    inserted_data=context.getContentResolver().bulkInsert(CapstoneContract.FavoritesEntity.CONTENT_URI,insert_data);
                    Log.d(LOG_TAG,"insert Security into Favorites Succesfully Inserted : " + inserted_data);
                    if(belongsToSyncAdapter) {
                        if(helperVar!=null && helperVar.equals(context.getString(R.string.sa_helper_var))) {
                            tm.log("END REFRESHING FAVORITES");
                        } else {
                            tm.log("END LOAD FAVORITES");
                            testEndLoads(context, tm);
                        }

                    }
                } else {
                    if(belongsToSyncAdapter) {
                        if(helperVar!=null && helperVar.equals(context.getString(R.string.sa_helper_var))) {
                            tm.log("END REFRESHING FAVORITES");
                        } else {
                            tm.log("END LOAD FAVORITES");
                            testEndLoads(context, tm);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<IndexOrShortInfoDataResponse> call, Throwable t) {
                if(belongsToSyncAdapter) {
                    tm.log("END LOAD FAVORITES WITH FAILURE");
                    testEndLoads(context, tm);

                }
            }

        });


    }
    private void loadIndexes(){

        IndexOrShortInfoRApi.IIndexOrShortInfoData service= IndexOrShortInfoRApi.getMyService();
        Call<IndexOrShortInfoDataResponse> call=service.getIndexesAmerica();
        Call<IndexOrShortInfoDataResponse> call2=service.getIndexesEurope();
        Call<IndexOrShortInfoDataResponse> call3=service.getIndexesAsia();
        call.enqueue(retrofitCallbackIndexes(mContext.getString(R.string.region_america)));
        call2.enqueue(retrofitCallbackIndexes(mContext.getString(R.string.region_europe)));
        call3.enqueue(retrofitCallbackIndexes(mContext.getString(R.string.region_asia)));




    }
    private void load1dDataChartAmerica(){

        HistoricalRApi.IStockChart service=HistoricalRApi.getMyApiService();

        Call<HistoricalDataResponseTimestamp> call1= service.get1DHistoricalDataByStock(mContext.getString(R.string.ticker_nasdaq));
        Call<HistoricalDataResponseTimestamp> call2= service.get1DHistoricalDataByStock(mContext.getString(R.string.ticker_dow_jones));
        Call<HistoricalDataResponseTimestamp> call3= service.get1DHistoricalDataByStock(mContext.getString(R.string.ticker_sp500));
        call1.enqueue(retrofitCallbackAmerica());
        call2.enqueue(retrofitCallbackAmerica());
        call3.enqueue(retrofitCallbackAmerica());
    }



    private Callback<IndexOrShortInfoDataResponse> retrofitCallbackIndexes(final String region){

        return new Callback<IndexOrShortInfoDataResponse>() {
            @Override
            public void onResponse(Call<IndexOrShortInfoDataResponse> call,  Response<IndexOrShortInfoDataResponse> response) {

                if(response.isSuccessful()) {
                    Runnable workerThreadIndexes = new WorkerThreadIndexes(mContext, tm, response, region);
                    mExecutorIndexes.execute(workerThreadIndexes);
                } else {
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                    tm.log("END Load INDEXES with Failure " + region);
                    testEndLoads(mContext,tm);
                }

            }

            @Override
            public void onFailure(Call<IndexOrShortInfoDataResponse> call, Throwable t) {
                tm.log("END Load INDEXES with Failure " + region);
                testEndLoads(mContext,tm);
            }
        };

    }

    private Callback<HistoricalDataResponseTimestamp> retrofitCallbackAmerica(){

        return new Callback<HistoricalDataResponseTimestamp>() {
            @Override
            public void onResponse(Call<HistoricalDataResponseTimestamp> call,  Response<HistoricalDataResponseTimestamp> response) {

                if(response.isSuccessful()) {
                    Runnable workerThreadAmerica = new WorkerThreadAmerica(response);
                    mExecutorChart.execute(workerThreadAmerica);
                } else {
                    tm.log("========= END LOAD1dDataChartAmerica with Failure");
                    testEndLoads(mContext,tm);
                }

            }

            @Override
            public void onFailure(Call<HistoricalDataResponseTimestamp> call, Throwable t) {
                tm.log("========= END LOAD1dDataChartAmerica with Failure");
                testEndLoads(mContext,tm);
            }
        };
    }




    public void sendDataUnitToFragmentMain(String region){

        ArrayList<IndexDataUnit> sendList=new ArrayList<IndexDataUnit>();

        switch (region) {
            case FragmentMain.REGION_AMERICA:
                sendList=mIndexDataUnitArrayListAmerica;

                break;
            case FragmentMain.REGION_EUROPE:

                break;
            case FragmentMain.REGION_ASIA:


        }
        if((sendList==null) || (sendList!=null && sendList.size()==0) ) {
            return;
        }
        if (region.equals(FragmentMain.REGION_AMERICA)|| region.equals(FragmentMain.REGION_EUROPE)) {
         //   Log.d(LOG_TAG, "PREnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }
        sendList=Utilities.normalizeDataUnits(sendList);
        if (region.equals(FragmentMain.REGION_AMERICA)|| region.equals(FragmentMain.REGION_EUROPE)) {
         //   Log.d(LOG_TAG, "POSTtnormalize: 1:" + sendList.get(0).getName() + ",2:" + sendList.get(1).getName() + ",3:" + sendList.get(2).getName());
        }

        //this will be true only after the installation of the app
        //save the data in app class and inform SplashActivity
        if(mInformSplash) {
            MyApplication.setmAmericaIndexes(sendList);
            Intent splashIntent = new Intent(mContext.getString(R.string.data_ready));
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(splashIntent);
            mInformSplash=false;
            Log.d(LOG_TAG,"## broadcast send");
        }

        //(subsequent load) send data
        Intent intent =new Intent(mContext.getString(R.string.index_event));
        intent.putExtra(mContext.getString(R.string.region),region);
        intent.putExtra(mContext.getString(R.string.failure),false);
        intent.putParcelableArrayListExtra(mContext.getString(R.string.sent_list),sendList);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);




    }

    private void deleteIndexesFromDatabase() {
        int rowsDeleted;
        rowsDeleted=mContext.getContentResolver().delete(CapstoneContract.IndexesEntity.CONTENT_URI,null,null);
        Log.d(LOG_TAG, "rows deleted from INDEXES:" + rowsDeleted);
    }
    private void deleteNewsFromDatabase(){
        int rowsDeleted;
        String selection=CapstoneContract.NewsEntity.REGION + "=?";
        Uri uri=CapstoneContract.NewsEntity.CONTENT_URI;
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(mContext);
        long ts=System.currentTimeMillis();

        long lastDeleteAmerica=sp.getLong(mContext.getString(R.string.last_deleted_america),0);
        if (System.currentTimeMillis()- lastDeleteAmerica >=HOUR_IN_MILLIS*3){
            rowsDeleted=mContext.getContentResolver()
                    .delete(uri,selection,new String[]{mContext.getString(R.string.region_america)});
            sp.edit().putLong(mContext.getString(R.string.last_deleted_america), ts).commit();
        }

        long lastDeleteEurope=sp.getLong(mContext.getString(R.string.last_deleted_europe),0);
        if (System.currentTimeMillis()-lastDeleteEurope>=DAY_IN_MILLIS*3) {
            mContext.getContentResolver().delete(uri,selection,new String[]{mContext.getString(R.string.region_europe)});
            sp.edit().putLong(mContext.getString(R.string.last_deleted_europe), ts).commit();

        }

        long lastDeleteAsia=sp.getLong(mContext.getString(R.string.last_deleted_asia),0);
        if (System.currentTimeMillis()-lastDeleteAsia>=DAY_IN_MILLIS*4) {
            mContext.getContentResolver().delete(uri,selection,new String[]{mContext.getString(R.string.region_asia)} );
            sp.edit().putLong(mContext.getString(R.string.last_deleted_asia),ts);
        }

    }


    private void insertNewsIntoDatabase(List<RssItem> rssItems, String region) {
        tm.log("BEGIN INSERT NEWS into " + region);
        Vector<ContentValues> values=new Vector<ContentValues>();
        for (int i=0;i<rssItems.size();i++) {
            RssItem rssObject=rssItems.get(i);
            ContentValues contentValues=new ContentValues();
            contentValues.put(CapstoneContract.NewsEntity.REGION,region);
            contentValues.put(CapstoneContract.NewsEntity.TITLE,rssObject.getTitle());
            //transform date to long
            SimpleDateFormat parser=new SimpleDateFormat(mContext.getString(R.string.mask_date_with_time_zone),
                    Locale.ENGLISH);
            Date date=null;
            try {
                date=parser.parse(rssObject.getPubDate());
            } catch (ParseException e) {
                e.printStackTrace();
                date=new Date(System.currentTimeMillis());
            }
            contentValues.put(CapstoneContract.NewsEntity.DATE,date.getTime());
            contentValues.put(CapstoneContract.NewsEntity.CONTENT,rssObject.getDescription());
            contentValues.put(CapstoneContract.NewsEntity.IMG_URL,rssObject.getImageUrl());
            contentValues.put(CapstoneContract.NewsEntity.LINK_URL,rssObject.getLink());

            values.add(contentValues);
        }
        int inserted_data = 0;
        ContentValues[] insert_data = new ContentValues[values.size()];
        values.toArray(insert_data);

        inserted_data=mContext.getContentResolver().bulkInsert(CapstoneContract.NewsEntity.CONTENT_URI,insert_data);
        Log.d(LOG_TAG," insertNewsIntoDatabase: Succesfully Inserted : " + String.valueOf(inserted_data));
        tm.log("END INSERT NEWS into "+region);
        tm.log("======== END LoadFeeds "+region);
        testEndLoads(mContext,tm);
    }

    private static void insertIntoDebugTable(Context context,String end,String debugType) {

        ContentValues contentValues=new ContentValues();

        contentValues.put(CapstoneContract.DebugEntity.DEBUG_TYPE,debugType);
        contentValues.put(CapstoneContract.DebugEntity.OTHER,"");
        contentValues.put(CapstoneContract.DebugEntity.ELAPSED,end);
        context.getContentResolver().insert(CapstoneContract.DebugEntity.CONTENT_URI,contentValues);



    }
    public static void testEndLoads(Context context,TimeMeasure tm) {
        String debugType;
        ++mTotalCount;
        Log.d(LOG_TAG,"testEndLoads count:"+mTotalCount);
        if(mTotalCount== total_loads) {
            String end=tm.log("END SYNC_ADAPTER");
            if(total_loads==LOAD_PARTIAL) {
                debugType="PARTIAL";
            } else {
                debugType="TOTAL";
                SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor=sp.edit();
                editor.putLong(context.getString(R.string.last_sync_key),System.currentTimeMillis());
                editor.putBoolean(context.getString(R.string.needs_europe_sync_key),true);
                editor.putBoolean(context.getString(R.string.needs_asia_sync_key),true);
                editor.commit();
                setUpdatedDateOnWidget(context);
                updateWidget(context.getApplicationContext());

            }
            insertIntoDebugTable(context,end,debugType);
            mTotalCount=0;


        }
    }
    public static  void updateWidget(Context context){

        Intent dataUpdatedIntent=new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);

    }
    public  class WorkerThreadAmerica implements Runnable {

        private Response<HistoricalDataResponseTimestamp> response;

        public WorkerThreadAmerica(Response<HistoricalDataResponseTimestamp> response){

            this.response=response;
        }
        @Override
        public void run() {
            HistoricalDataResponseTimestamp resp= response.body();
            List<HistoricalDataResponseTimestamp.SeriesEntity> lista=resp.getSeries();

            if(resp.getMeta().getCompany_name()==null)
                return;
            IndexDataUnit indexDataUnit = Utilities.extractToIndexesData(lista, resp.getMeta().getPrevious_close());
            indexDataUnit.setMarket(resp.getMeta().getExchange_name());
            indexDataUnit.setName(resp.getMeta().getCompany_name());
            indexDataUnit.setTicker(resp.getMeta().getTicker());
            mIndexDataUnitArrayListAmerica.add(indexDataUnit);
            tm.log(""+mIndexDataUnitArrayListAmerica.size());
            if(mIndexDataUnitArrayListAmerica.size() ==3) {

                for (int i = 0; i < mIndexDataUnitArrayListAmerica.size(); i++) {
                    Log.d("VILLANUEVA", ":" + mIndexDataUnitArrayListAmerica.get(i).getTicker());
                }
                tm.log("========= END LOAD1dDataChartAmerica");

                sendDataUnitToFragmentMain(mContext.getString(R.string.region_america));
                mIndexDataUnitArrayListAmerica.clear();
                testEndLoads(mContext,tm);

            }
        }
    }


    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        Bundle bundle=new Bundle();
        bundle.putInt(SYNC_TYPE,NORMAL_START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(bundle).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, bundle, syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The mContext used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putInt(SYNC_TYPE,HOT_START);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The mContext used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        CapstoneSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }


}