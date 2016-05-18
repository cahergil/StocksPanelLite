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
import com.carlos.capstone.models.StockStatsResponse;
import com.carlos.capstone.utils.Utilities;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Carlos on 01/03/2016.
 */
public class StockStatsService extends IntentService {
    private static final String LOG_TAG=StockStatsService.class.getSimpleName();
    private String mSymbol;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public StockStatsService(String name) {
        super(name);
    }
    public StockStatsService() {
        super("StockStatsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent!=null) {
           Bundle bundle=intent.getExtras();
           mSymbol=bundle.getString(getString(R.string.symbol_key));
        }
        getStockStatistics();
    }


    public void getStockStatistics() {
        QueryRApi.IQuery myService= QueryRApi.getMyQueryService();
        String qParam=getString(R.string.stats_qParam_left)+mSymbol+getString(R.string.stats_qParam_right);
        Call<StockStatsResponse> call=myService.getStatsByStock(qParam);
        call.enqueue(retrofitCallbackStockStats());

    }
    public Callback<StockStatsResponse> retrofitCallbackStockStats(){
        return new Callback<StockStatsResponse>() {
            @Override
            public void onResponse(Response<StockStatsResponse> response, Retrofit retrofit) {

                Log.i(LOG_TAG,"retrofitCallbackStockStats"+response.raw());
                if(response.isSuccess()) {
                    StockStatsResponse resp=response.body();
                    StockStatsResponse.QueryEntity query=resp.getQuery();
                    StockStatsResponse.QueryEntity.ResultsEntity results=query.getResults();
                    insertStatisticsIntoTable(results.getStats());

                } else {
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }

            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof IOException) {
                    Log.d(LOG_TAG,"There was a network problem"+ t.getMessage());
                    if(!Utilities.isNetworkAvailable(getApplicationContext())){
                    }
                } else {
                    Log.d(LOG_TAG,"error"+t.getMessage());
                }


            }
        };

    }

    public void insertStatisticsIntoTable(StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity st){
        //   StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.MarketCapEntity mc=st.getMarketCap();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.EnterpriseValueEntity ev=st.getEnterpriseValue();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.TrailingPEEntity tpe=st.getTrailingPE();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.ForwardPEEntity fpe=st.getForwardPE();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.PEGRatioEntity peg=st.getPEGRatio();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.PriceToSalesEntity pts=st.getPriceToSales();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.PriceToBookEntity ptb=st.getPriceToBook();
        StockStatsResponse.QueryEntity.ResultsEntity.
                StatsEntity.EnterpriseValueToRevenueEntity evr=st.getEnterpriseValueToRevenue();
        StockStatsResponse.QueryEntity.ResultsEntity.
                StatsEntity.EnterpriseValueToEBITDAEntity eve=st.getEnterpriseValueToEBITDA();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.ProfitMarginEntity pm=st.getProfitMargin();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.OperatingMarginEntity om=st.getOperatingMargin();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.ReturnOnAssetsEntity roa=st.getReturnOnAssets();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.ReturnOnEquityEntity roe=st.getReturnOnEquity();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.RevenueEntity rev=st.getRevenue();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.RevenuePerShareEntity rps=st.getRevenuePerShare();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.QtrlyRevenueGrowthEntity qrg=st.getQtrlyRevenueGrowth();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.GrossProfitEntity gp=st.getGrossProfit();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.EBITDAEntity ebitda=st.getEBITDA();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.NetIncomeAvlToCommonEntity niatc=st.getNetIncomeAvlToCommon();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.DilutedEPSEntity deps=st.getDilutedEPS();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.QtrlyEarningsGrowthEntity qeg=st.getQtrlyEarningsGrowth();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.TotalCashEntity tc=st.getTotalCash();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.TotalCashPerShareEntity tcps=st.getTotalCashPerShare();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.TotalDebtEntity td=st.getTotalDebt();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.TotalDebtToEquityEntity tdte=st.getTotalDebtToEquity();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.CurrentRatioEntity cr=st.getCurrentRatio();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.BookValuePerShareEntity bvps=st.getBookValuePerShare();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.OperatingCashFlowEntity ocf=st.getOperatingCashFlow();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.LeveredFreeCashFlowEntity lcf=st.getLeveredFreeCashFlow();
        String beta= st.getBeta();
        String P_52WeekChange= st.getP_52WeekChange();
        String SP500WeekChange=st.getSAndP50052WeekChange();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.P52WeekHighEntity p52wh= st.getP_52WeekHigh();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.P52WeekLowEntity p52wl=st.getP_52WeekLow();
        String P_50DayMovingAverage=st.getP_50DayMovingAverage();
        String P_200DayMovingAverage=st.getP_200DayMovingAverage();
        List<StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.AvgVolEntity> avgVolEntityList=st.getAvgVol();
        String v_3m_Avg_Vol;
        String v_10d_Avg_Vol;
        try {
            v_3m_Avg_Vol=avgVolEntityList.get(0)==null? null:avgVolEntityList.get(0).getContent();
            v_10d_Avg_Vol=avgVolEntityList.get(1)==null? null:avgVolEntityList.get(1).getContent();
        }catch (Exception e) {
            Log.i(LOG_TAG,"error lista no informada avgvolEntity"+e.getMessage());
            v_3m_Avg_Vol=null;
            v_10d_Avg_Vol=null;
        }
        String so=st.getSharesOutstanding();
        String fl=st.getFloat();
        String pctInsider=st.getPctHeldByInsiders();
        String pctInstitutions=st.getPctHeldByInstitutions();
        StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity.ShortRatioEntity sre=st.getShortRatio();

        ContentValues contentValues=new ContentValues();

        //VALUATION MEASURES
        contentValues.put(CapstoneContract.StockStatsEntity.SYMBOL,mSymbol);
        contentValues.put(CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE,ev==null? null:ev.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.TRAILING_PE,tpe==null? null:tpe.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.FORWARD_PE,fpe==null? null:fpe.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.PEG_RATIO,peg==null? null:peg.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.PRICE_SALES,pts==null? null:pts.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.PRICE_BOOK,ptb==null? null:ptb.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE_REVENUE,evr==null? null:evr.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE_EBITDA,eve==null? null:eve.getContent());


        //PROFITABILITY
        contentValues.put(CapstoneContract.StockStatsEntity.PROFIT_MARGIN,pm==null? null:pm.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.OPERATING_MARGIN,om==null? null:om.getContent());


        //MANAGEMENT EFFECTIVENESS
        contentValues.put(CapstoneContract.StockStatsEntity.RETURN_ON_ASSETS,roa==null? null:roa.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.RETURN_ON_EQUITY,roe==null? null:roe.getContent());


        //INCOME STATEMENTS
        contentValues.put(CapstoneContract.StockStatsEntity.REVENUE,rev==null? null:rev.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.REVENUE_PER_SHARE,rps==null? null:rps.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.QTRLY_REVENUE_GROWTH,qrg==null? null:qrg.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.GROSS_PROFIT,gp==null? null:gp.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.EBITDA,ebitda==null? null:ebitda.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.NET_INCOME_AVL_TO_COMMON,niatc==null? null:niatc.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.DILUTED_EPS,deps==null? null:deps.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.QTRLY_EARNINGS_GROWTH,qeg==null?null:qeg.getContent());


        //BALANCE SHEET
        contentValues.put(CapstoneContract.StockStatsEntity.TOTAL_CASH,tc==null? null:tc.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.TOTAL_CASH_PER_SHARE,tcps==null? null:tcps.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.TOTAL_DEBT,td==null? null:td.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.TOTAL_DEBT_EQUITY,tdte==null? null:tdte.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.CURRENT_RATIO,cr==null? null:cr.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.BOOK_VALUE_PER_SHARE,bvps==null? null:bvps.getContent());


        //CASH FLOW STATEMENT
        contentValues.put(CapstoneContract.StockStatsEntity.OPERATING_CASH_FLOW,ocf==null? null:ocf.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.LEVERED_FREE_CASH_FLOW,lcf==null? null:lcf.getContent());


        //STOCK PRICE HISTORY
        contentValues.put(CapstoneContract.StockStatsEntity.BETA,beta);
        contentValues.put(CapstoneContract.StockStatsEntity.N_52_WEEK_CHANGE,P_52WeekChange);
        contentValues.put(CapstoneContract.StockStatsEntity.SP500_52_WEEK_CHANGE,SP500WeekChange);
        contentValues.put(CapstoneContract.StockStatsEntity.N_52_WEEK_HIGH,p52wh==null? null:p52wh.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.N_52_WEEK_LOW,p52wl==null? null:p52wl.getContent());
        contentValues.put(CapstoneContract.StockStatsEntity.N_50_DAY_MOVING_AVERAGE,P_50DayMovingAverage);
        contentValues.put(CapstoneContract.StockStatsEntity.N_200_DAY_MOVING_AVERAGE,P_200DayMovingAverage);



        //SHARE STATISTICS
        contentValues.put(CapstoneContract.StockStatsEntity.AVG_VOL_3_MONTH,v_3m_Avg_Vol);
        contentValues.put(CapstoneContract.StockStatsEntity.AVG_VOL_10_DAY,v_10d_Avg_Vol);
        contentValues.put(CapstoneContract.StockStatsEntity.SHARES_OUTSTANDING,so);
        contentValues.put(CapstoneContract.StockStatsEntity.FLOAT,fl);
        contentValues.put(CapstoneContract.StockStatsEntity.PERCENT_HELD_BY_INSIDERS,pctInsider);
        contentValues.put(CapstoneContract.StockStatsEntity.PERCENT_HELD_BY_INSTITUTIONS,pctInstitutions);
        contentValues.put(CapstoneContract.StockStatsEntity.SHORT_RATIO,sre==null? null:sre.getContent());
        Uri uri=CapstoneContract.StockStatsEntity.buildStockStatsWithSymbol(mSymbol);
        getContentResolver().insert(uri,contentValues);


    }

}
