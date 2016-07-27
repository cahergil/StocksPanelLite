package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.QueryRApi;
import com.carlos.capstone.models.StockDataResponse;
import com.carlos.capstone.utils.Utilities;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Carlos on 01/03/2016.
 */
public class StockSummaryService extends IntentService {
    private String mSymbol;
    private String mCompanyName;
    int count=0;
    private static final String LOG_TAG=StockSummaryService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public StockSummaryService(String name) {
        super(name);
    }
    public StockSummaryService() {
        super("StockSummaryService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            mSymbol = bundle.getString(getString(R.string.symbol_key));
            mCompanyName=bundle.getString(getString(R.string.company_name_key));
        }
        getStockData();
    }
    public void getStockData() {

        QueryRApi.IQuery myService=QueryRApi.getMyQueryService();
        Call<StockDataResponse> call=null;
        String alternativQParam="use%20%22http%3A%2F%2Fgithub.com%2Fspullara%2Fyql-tables%2Fraw%2Fd60732fd4fbe72e5d5bd2994ff27cf58ba4d3f84%2Fyahoo%2Ffinance%2Fyahoo.finance.quotes.xml%22%20as%20quotes%0A%09%09select%20*%20from%20quotes%20where%20symbol%20in%20(%22"+mSymbol+"%22)";

//        String qParam=getString(R.string.stock_qParam_left)+ mSymbol + getString(R.string.stock_qParam_right);
        call=myService.getDataByStock(alternativQParam);
        call.enqueue(retrofitCallbackStockData());
    }
    public Callback<StockDataResponse> retrofitCallbackStockData() {

        return new Callback<StockDataResponse>() {
            @Override
            public void onResponse(Call<StockDataResponse> call,Response<StockDataResponse> response) {
                count++;
                Log.i(LOG_TAG,"retrofitCallbackStockData"+response.raw());
                if (response.isSuccessful()) {

                    StockDataResponse stockDataResponse = response.body();
                    StockDataResponse.QueryEntity queryEntity = stockDataResponse.getQuery();
                    StockDataResponse.QueryEntity.ResultsEntity resultsEntity = queryEntity.getResults();
                    //5131@OM.KL
//                    {
//                        query: {
//                            count: 0,
//                                    created: "2016-03-30T08:57:31Z",
//                                    lang: "es",
//                                    results: null
//                        }
//                    }
                    if(resultsEntity==null) return;
                    StockDataResponse.QueryEntity.ResultsEntity.QuoteEntity quoteEntity = resultsEntity.getQuote();


                    ContentValues contentValues=new ContentValues();
                    contentValues.put(CapstoneContract.StockDetailEntity.TIMESTAMP,System.currentTimeMillis());
                    contentValues.put(CapstoneContract.StockDetailEntity.SYMBOL,quoteEntity.getSymbol());
                    contentValues.put(CapstoneContract.StockDetailEntity.COMPANY_NAME,mCompanyName);
                    contentValues.put(CapstoneContract.StockDetailEntity.LAST_TRADE_PRICE,quoteEntity.getLastTradePriceOnly());
                    contentValues.put(CapstoneContract.StockDetailEntity.CHANGE,quoteEntity.getChange());
                    contentValues.put(CapstoneContract.StockDetailEntity.CHANGE_PERCENT,quoteEntity.getChangeinPercent());
                    contentValues.put(CapstoneContract.StockDetailEntity.LAST_TRADE_DATE,quoteEntity.getLastTradeDate());
                    contentValues.put(CapstoneContract.StockDetailEntity.LAST_TRADE_TIME,quoteEntity.getLastTradeTime());
                    contentValues.put(CapstoneContract.StockDetailEntity.MARKET_CAP,quoteEntity.getMarketCapitalization());
                    contentValues.put(CapstoneContract.StockDetailEntity.DAYS_RANGE,quoteEntity.getDaysRange());
                    contentValues.put(CapstoneContract.StockDetailEntity.PREVIOUS_CLOSE,quoteEntity.getPreviousClose());
                    contentValues.put(CapstoneContract.StockDetailEntity.OPEN,quoteEntity.getOpen());
                    contentValues.put(CapstoneContract.StockDetailEntity.ASK,quoteEntity.getAsk());
                    contentValues.put(CapstoneContract.StockDetailEntity.BID,quoteEntity.getBid());
                    contentValues.put(CapstoneContract.StockDetailEntity.VOLUME,quoteEntity.getVolume());
                    contentValues.put(CapstoneContract.StockDetailEntity.AVG_DAILY_VOLUME,quoteEntity.getAverageDailyVolume());
                    contentValues.put(CapstoneContract.StockDetailEntity.EARNINGS_SHARE,quoteEntity.getEarningsShare());
                    contentValues.put(CapstoneContract.StockDetailEntity.EPS_ESTIMATE_CURRENT_YEAR,quoteEntity.getEPSEstimateCurrentYear());
                    contentValues.put(CapstoneContract.StockDetailEntity.EPS_ESTIMATE_NEXT_YEAR,quoteEntity.getEPSEstimateNextYear());
                    contentValues.put(CapstoneContract.StockDetailEntity.EPS_ESTIMATE_NEXT_QUARTER,quoteEntity.getEPSEstimateNextQuarter());
                    contentValues.put(CapstoneContract.StockDetailEntity.YEAR_LOW,quoteEntity.getYearLow());
                    contentValues.put(CapstoneContract.StockDetailEntity.YEAR_HIGH,quoteEntity.getYearHigh());
                    contentValues.put(CapstoneContract.StockDetailEntity.CHANGE_FROM_YEAR_LOW,quoteEntity.getChangeFromYearLow());
                    contentValues.put(CapstoneContract.StockDetailEntity.PERCENT_CHANGE_FROM_YEAR_LOW,quoteEntity.getPercentChangeFromYearLow());
                    contentValues.put(CapstoneContract.StockDetailEntity.CHANGE_FROM_YEAR_HIGH,quoteEntity.getChangeFromYearHigh());
                    contentValues.put(CapstoneContract.StockDetailEntity.PERCENT_CHANGE_FROM_YEAR_HIGH,quoteEntity.getPercebtChangeFromYearHigh());
                    contentValues.put(CapstoneContract.StockDetailEntity.PEG_RATIO, quoteEntity.getPEGRatio());
                    contentValues.put(CapstoneContract.StockDetailEntity.PER_RATIO, quoteEntity.getPERatio());
                    contentValues.put(CapstoneContract.StockDetailEntity.ONE_YEAR_TARGET_PRICE, quoteEntity.getOneyrTargetPrice());
                    contentValues.put(CapstoneContract.StockDetailEntity.STOCK_EXCHANGE,quoteEntity.getStockExchange());
                    Uri uri=CapstoneContract.StockDetailEntity.buildStockDetailWithSymbol(quoteEntity.getSymbol());
                    getContentResolver().insert(uri,contentValues);





                } else {
                    if (response.code()==400) {

                        if(count>1) return;
                        String alternativQParam="use%20%22http%3A%2F%2Fgithub.com%2Fspullara%2Fyql-tables%2Fraw%2Fd60732fd4fbe72e5d5bd2994ff27cf58ba4d3f84%2Fyahoo%2Ffinance%2Fyahoo.finance.quotes.xml%22%20as%20quotes%0A%09%09select%20*%20from%20quotes%20where%20symbol%20in%20(%22"+mSymbol+"%22)";
                        QueryRApi.IQuery myService=QueryRApi.getMyQueryService();
//                        Call<StockDataResponse> call=null;
//                        call=myService.getDataByStockAlternative(alternativQParam);
//                        call.enqueue(retrofitCallbackStockData());
                    }
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }
            }

            @Override
            public void onFailure(Call<StockDataResponse> call,Throwable t) { //socket time out, unknown host

                if (t instanceof IOException) {
                 //   Toast.makeText(getActivity(), "There was a network problem:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    if(!Utilities.isNetworkAvailable(getApplicationContext())){

                    }
                } else {
                 //   Toast.makeText(getActivity(), "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        };
    }
}
