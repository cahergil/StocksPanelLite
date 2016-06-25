package com.carlos.capstone.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.carlos.capstone.R;
import com.carlos.capstone.jsonpconverter.JsonpGsonConverterFactory;
import com.carlos.capstone.models.HistoricalDataResponseDate;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.models.IndexDataUnit;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Carlos on 04/02/2016.
 */
public class MyApplication extends Application {

    public static ArrayList<IndexDataUnit> mAmericaIndexes=new ArrayList<IndexDataUnit>();

    public static ArrayList<IndexDataUnit> getmAmericaIndexes() {
        return mAmericaIndexes;
    }

    public static void setmAmericaIndexes(ArrayList<IndexDataUnit> list) {
        mAmericaIndexes = list;
    }

    public static IStockChart myService;
    //initialize to empty in case there is no network(so that on rotation don't crasch)
    public static Observable<Response<HistoricalDataResponseTimestamp>> mObservableDays=Observable.empty();
    public static Observable<Response<HistoricalDataResponseDate>> mObservableMonths=Observable.empty();
    public static Context myContext;
    @Override public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
      //  LeakCanary.install(this);
        myContext=getApplicationContext();
    }

    public static Context getMyContext() {
        return MyApplication.myContext;
    }
    //http://stackoverflow.com/questions/33035867/android-app-crashes-on-pre-lollipop-devices/33041346
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public interface IStockChart {
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=1d/json")
        Observable<Response<HistoricalDataResponseTimestamp>> get1DHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=7d/json")
        Observable<Response<HistoricalDataResponseTimestamp>> get7DHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=1m/json")
        Observable<Response<HistoricalDataResponseDate>> get1MHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=6m/json")
        Observable<Response<HistoricalDataResponseDate>> get6MHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=2y/json")
        Observable<Response<HistoricalDataResponseDate>> get2YHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=5y/json")
        Observable<Response<HistoricalDataResponseDate>> get5YHistoricalDataByStock(@Path("ticker") String ticker);
        @GET("/instrument/1.0/{ticker}/chartdata;type=quote;range=my/json")
        Observable<Response<HistoricalDataResponseDate>> getMAXHistoricalDataByStock(@Path("ticker") String ticker);

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
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            myService=retrofit.create(IStockChart.class);
            return myService;
        } else {
            return myService;
        }



    }

    //GOOGLE ANALYTICS

    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    // Get the tracker associated with this app
    public void startTracking() {

        // Initialize an Analytics tracker using a Google Analytics property ID.

        // Does the Tracker already exist?
        // If not, create it

        if (mTracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);

            // Get the config data for the tracker
            mTracker = ga.newTracker(R.xml.app_tracker);

            // Enable tracking of activities
            ga.enableAutoActivityReports(this);

            //to enable advertising features(demographics and interest reports)
            //requires authorization from the client, so set to false for the moment
            ga.enableAdvertisingIdCollection(true);

            // Set the log level to verbose.
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);

            //Disables reporting when app runs on debug ,setDryRun(BuildConfig.DEBUG);
            ga.setDryRun(false);
        }
    }


    public Tracker getTracker() {
        // Make sure the tracker exists
        startTracking();

        // Then return the tracker
        return mTracker;
    }
}
