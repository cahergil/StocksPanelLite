package com.carlos.capstone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.carlos.capstone.adapters.Stats;
import com.carlos.capstone.adapters.StatsChild;
import com.carlos.capstone.adapters.StatsExpandableAdapter;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.models.StockStatsResponse;
import com.carlos.capstone.services.StockStatsService;
import com.carlos.capstone.utils.Utilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 25/12/2015.
 */
public class FragmentStockStats extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView recyclerView;
    //private List<StatsPOJO> mList;
    private static final String LOG_TAG=FragmentStockStats.class.getSimpleName();
    private static final int STATS_LOADER=0;
    private String mSymbol;
    private StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity mMeasures;
    private StatsExpandableAdapter mStatsExpandableAdapter;
    private StockStatsResponse.QueryEntity.ResultsEntity.StatsEntity mStats ;

    public static final int COL_SYMBOL=1;
    public static final int COL_ENTERPRISE_VALUE=2;
    public static final int COL_TRAILING_PE=3;
    public static final int COL_FORWARD_PE=4;
    public static final int COL_PEG_RATIO=5;
    public static final int COL_PRICE_SALES=6;
    public static final int COL_PRICE_BOOK=7;
    public static final int COL_ENTERPRISE_VALUE_REVENUE=8;
    public static final int COL_ENTERPRISE_VALUE_EBITDA=9;
    public static final int COL_PROFIT_MARGIN=10;
    public static final int COL_OPERATING_MARGIN=11;
    public static final int COL_RETURN_ON_ASSETS=12;
    public static final int COL_RETURN_ON_EQUITY=13;
    public static final int COL_REVENUE=14;
    public static final int COL_REVENUE_PER_SHARE=15;
    public static final int COL_QTRLY_REVENUE_GROWTH=16;
    public static final int COL_GROSS_PROFIT=17;
    public static final int COL_EBITDA=18;
    public static final int COL_NET_INCOME_AVL_TO_COMMON=19;
    public static final int COL_DILUTED_EPS=20;
    public static final int COL_QTRLY_EARNINGS_GROWTH=21;
    public static final int COL_TOTAL_CASH=22;
    public static final int COL_TOTAL_CASH_PER_SHARE=23;
    public static final int COL_TOTAL_DEBT=24;
    public static final int COL_TOTAL_DEBT_EQUITY=25;
    public static final int COL_CURRENT_RATIO=26;
    public static final int COL_BOOK_VALUE_PER_SHARE=27;
    public static final int COL_OPERATING_CASH_FLOW=28;
    public static final int COL_LEVERED_FREE_CASH_FLOW=29;
    public static final int COL_BETA=30;
    public static final int COL_N_52_WEEK_CHANGE=31;
    public static final int COL_SP500_52_WEEK_CHANGE=32;
    public static final int COL_N_52_WEEK_HIGH=33;
    public static final int COL_N_52_WEEK_LOW=34;
    public static final int COL_N_50_DAY_MOVING_AVERAGE=35;
    public static final int COL_N_200_DAY_MOVING_AVERAGE=36;
    public static final int COL_AVG_VOL_3_MONTH=37;
    public static final int COL_AVG_VOL_10_DAY=38;
    public static final int COL_SHARES_OUTSTANDING=39;
    public static final int COL_FLOAT=40;
    public static final int COL_PERCENT_HELD_BY_INSIDERS=41;
    public static final int COL_PERCENT_HELD_BY_INSTITUTIONS=42;
    public static final int COL_SHORT_RATIO=43;
    private Bundle mBundle;
    private View mEmptyView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG,"onCreateView Fragment Stats");
        View view=inflater.inflate(R.layout.fragment_stock_stats,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEmptyView=view.findViewById(R.id.emptyView);

        if(savedInstanceState!=null) {

            mSymbol=savedInstanceState.getString(getString(R.string.symbol_key));
            getLoaderManager().restartLoader(STATS_LOADER,null,this);
            mBundle=savedInstanceState;


        } else {

            Bundle bundle=getArguments();
            mSymbol=bundle.getString(getString(R.string.symbol_key));
            Intent intent=new Intent(getActivity(), StockStatsService.class);
            intent.putExtras(bundle);
            getActivity().startService(intent);
            getLoaderManager().initLoader(STATS_LOADER,null,this);

        }

        return view;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.symbol_key),mSymbol);
        StatsExpandableAdapter statsExpandableAdapter=(StatsExpandableAdapter) recyclerView.getAdapter();
        if(statsExpandableAdapter!=null) {
            statsExpandableAdapter.onSaveInstanceState(outState);
        }
     //   ((StatsExpandableAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri= CapstoneContract.StockStatsEntity.buildStockStatsWithSymbol(mSymbol);
        String selection=CapstoneContract.StockStatsEntity.SYMBOL + "=?";
        String[] selectionArgs=new String[]{mSymbol};
        return new CursorLoader(getActivity(),
                uri,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null && !data.moveToFirst() ) {
            ArrayList<ParentListItem> parentObject=new ArrayList<>();
            mStatsExpandableAdapter = new StatsExpandableAdapter(getActivity(),parentObject,mEmptyView );
            mStatsExpandableAdapter.onRestoreInstanceState(mBundle);//creo que esta linea no hace falta
            recyclerView.setAdapter(mStatsExpandableAdapter);
            return;
        }
        bindView(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void bindView(Cursor cursor) {

        List<Stats> stats=new ArrayList<Stats>();
        String[] statsHeaders= getResources().getStringArray(R.array.stats_header);
        for(String header:statsHeaders) {
            Stats stat=new Stats();
            List<StatsChild> list=new ArrayList<>();
            stat.setChildObjectList(list);
            stat.setNombreGrupo(header);
            stats.add(stat);
        }


        ArrayList<ParentListItem> parentObject=new ArrayList<>();

        //VALUATION MEASURES
        ArrayList<StatsChild> childlist = new ArrayList<StatsChild>();

        String ev=cursor.isNull(COL_ENTERPRISE_VALUE)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_ENTERPRISE_VALUE));
        childlist.add(new StatsChild(getString(R.string.enterprise_value),ev));
        String tpe=cursor.isNull(COL_TRAILING_PE)? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_TRAILING_PE));
        childlist.add(new StatsChild(getString(R.string.trailing_pe),tpe));
        String fpe=cursor.isNull(COL_FORWARD_PE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_FORWARD_PE));
        childlist.add(new StatsChild(getString(R.string.forward_pe),fpe));
        String peg=cursor.isNull(COL_PEG_RATIO) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_PEG_RATIO));
        childlist.add(new StatsChild(getString(R.string.peg_ratio),peg));
        String pts=cursor.isNull(COL_PRICE_SALES) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_PRICE_SALES));
        childlist.add(new StatsChild(getString(R.string.price_sales),pts));
        String ptb=cursor.isNull(COL_PRICE_BOOK) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_PRICE_BOOK));
        childlist.add(new StatsChild(getString(R.string.price_book),ptb));
        String evr=cursor.isNull(COL_ENTERPRISE_VALUE_REVENUE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_ENTERPRISE_VALUE_REVENUE));
        childlist.add(new StatsChild(getString(R.string.enterprise_value_revenue),evr));
        String eve=cursor.isNull(COL_ENTERPRISE_VALUE_EBITDA) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_ENTERPRISE_VALUE_EBITDA));
        childlist.add(new StatsChild(getString(R.string.enterprise_value_ebitda),eve));
        stats.get(0).setChildObjectList(childlist);
        parentObject.add(stats.get(0));

        //PROFITABILITY
        ArrayList<StatsChild> childlist1 = new ArrayList<StatsChild>();
        String pm=cursor.isNull(COL_PROFIT_MARGIN)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_PROFIT_MARGIN));
        childlist1.add(new StatsChild(getString(R.string.profit_margin),pm));
        String om=cursor.isNull(COL_OPERATING_MARGIN)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_OPERATING_MARGIN));
        childlist1.add(new StatsChild(getString(R.string.operating_margin),om));
        stats.get(1).setChildObjectList(childlist1);
        parentObject.add(stats.get(1));

        //MANAGEMENT EFFECTIVENESS
        ArrayList<StatsChild> childlist2 = new ArrayList<StatsChild>();
        String roa=cursor.isNull(COL_RETURN_ON_ASSETS)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_RETURN_ON_ASSETS));
        childlist2.add(new StatsChild(getString(R.string.return_on_assets),roa));
        String roe=cursor.isNull(COL_RETURN_ON_EQUITY)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_RETURN_ON_EQUITY));
        childlist2.add(new StatsChild(getString(R.string.return_on_equity),roe));
        stats.get(2).setChildObjectList(childlist2);
        parentObject.add(stats.get(2));

        //INCOME STATEMENTS
        ArrayList<StatsChild> childlist3 = new ArrayList<StatsChild>();
        String rev=cursor.isNull(COL_REVENUE)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_REVENUE));
        childlist3.add(new StatsChild(getString(R.string.revenue),rev));
        String rps=cursor.isNull(COL_REVENUE_PER_SHARE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_REVENUE_PER_SHARE));
        childlist3.add(new StatsChild(getString(R.string.revenue_per_share),rps)) ;
        String qrg=cursor.isNull(COL_QTRLY_REVENUE_GROWTH)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_QTRLY_REVENUE_GROWTH));
        childlist3.add(new StatsChild(getString(R.string.qtrly_revenue_growth),qrg));
        String gp=cursor.isNull(COL_GROSS_PROFIT)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_GROSS_PROFIT));
        childlist3.add(new StatsChild(getString(R.string.gross_profit),gp));
        String ebitda=cursor.isNull(COL_EBITDA)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_EBITDA));
        childlist3.add(new StatsChild(getString(R.string.ebitda),ebitda)) ;
        String niatc=cursor.isNull(COL_NET_INCOME_AVL_TO_COMMON)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_NET_INCOME_AVL_TO_COMMON));
        childlist3.add(new StatsChild(getString(R.string.net_income_avl_to_common),niatc)) ;
        String deps=cursor.isNull(COL_DILUTED_EPS) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_DILUTED_EPS));
        childlist3.add(new StatsChild(getString(R.string.diluted_eps),deps)) ;
        String qeg=cursor.isNull(COL_QTRLY_EARNINGS_GROWTH)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_QTRLY_EARNINGS_GROWTH));
        childlist3.add(new StatsChild(getString(R.string.qtrly_earnings_growth),qeg)) ;
        stats.get(3).setChildObjectList(childlist3);
        parentObject.add(stats.get(3));

        //BALANCE SHEET
        ArrayList<StatsChild> childlist4= new ArrayList<StatsChild>();
        String tc=cursor.isNull(COL_TOTAL_CASH)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_TOTAL_CASH));
        childlist4.add(new StatsChild(getString(R.string.total_cash),tc));
        String tcps=cursor.isNull(COL_TOTAL_CASH_PER_SHARE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_TOTAL_CASH_PER_SHARE));
        childlist4.add(new StatsChild(getString(R.string.total_cash_per_share),tcps));
        String td=cursor.isNull(COL_TOTAL_DEBT)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_TOTAL_DEBT));
        childlist4.add(new StatsChild(getString(R.string.total_debt),td));
        String tdte=cursor.isNull(COL_TOTAL_DEBT_EQUITY) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_TOTAL_DEBT_EQUITY));
        childlist4.add(new StatsChild(getString(R.string.total_debt_equity),tdte));
        String cr=cursor.isNull(COL_CURRENT_RATIO) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_CURRENT_RATIO));
        childlist4.add(new StatsChild(getString(R.string.current_ratio),cr));
        String bvps=cursor.isNull(COL_BOOK_VALUE_PER_SHARE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_BOOK_VALUE_PER_SHARE));
        childlist4.add(new StatsChild(getString(R.string.book_value_per_share),bvps));
        stats.get(4).setChildObjectList(childlist4);
        parentObject.add(stats.get(4));

        //CASH FLOW STATEMENT
        ArrayList<StatsChild> childlist5= new ArrayList<StatsChild>();
        String ocf=cursor.isNull(COL_OPERATING_CASH_FLOW)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_OPERATING_CASH_FLOW));
        childlist5.add(new StatsChild(getString(R.string.operating_cash_flow),ocf));
        String lcf=cursor.isNull(COL_LEVERED_FREE_CASH_FLOW)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_LEVERED_FREE_CASH_FLOW));
        childlist5.add(new StatsChild(getString(R.string.levered_free_cash_flow),lcf));
        stats.get(5).setChildObjectList(childlist5);
        parentObject.add(stats.get(5));
        //STOCK PRICE HISTORY
        ArrayList<StatsChild> childlist6= new ArrayList<StatsChild>();
        String beta=cursor.isNull(COL_BETA) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_BETA));
        childlist6.add(new StatsChild(getString(R.string.beta),beta));
        String p_52WeekChange=cursor.isNull(COL_N_52_WEEK_CHANGE)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_N_52_WEEK_CHANGE));
        childlist6.add(new StatsChild(getString(R.string.n_52_week_change),p_52WeekChange));
        String sp500WeekChange=cursor.isNull(COL_SP500_52_WEEK_CHANGE)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_SP500_52_WEEK_CHANGE));
        childlist6.add(new StatsChild(getString(R.string.sp500_52_week_change),sp500WeekChange));
        String p52wh=cursor.isNull(COL_N_52_WEEK_HIGH) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_N_52_WEEK_HIGH));
        childlist6.add(new StatsChild(getString(R.string.n_52_week_high),p52wh));
        String p52wl=cursor.isNull(COL_N_52_WEEK_LOW) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_N_52_WEEK_LOW));
        childlist6.add(new StatsChild(getString(R.string.n_52_week_low),p52wl));
        String p_50DayMovingAverage=cursor.isNull(COL_N_50_DAY_MOVING_AVERAGE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_N_50_DAY_MOVING_AVERAGE));
        childlist6.add(new StatsChild(getString(R.string.n_50_day_moving_average),p_50DayMovingAverage));
        String p_200DayMovingAverage=cursor.isNull(COL_N_200_DAY_MOVING_AVERAGE) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_N_200_DAY_MOVING_AVERAGE));
        childlist6.add(new StatsChild(getString(R.string.n_200_day_moving_average),p_200DayMovingAverage));
        stats.get(6).setChildObjectList(childlist6);
        parentObject.add(stats.get(6));

        //SHARE STATISTICS
        ArrayList<StatsChild> childlist7= new ArrayList<StatsChild>();
        String v_3m_Avg_Vol=cursor.isNull(COL_AVG_VOL_3_MONTH) ? getString(R.string.na): new DecimalFormat("###,###,###,###,###").format(cursor.getInt(COL_AVG_VOL_3_MONTH));
        childlist7.add(new StatsChild(getString(R.string.avg_vol_3_month),v_3m_Avg_Vol));
        String v_10d_Avg_Vol=cursor.isNull(COL_AVG_VOL_10_DAY) ? getString(R.string.na): new DecimalFormat("###,###,###,###,###").format(cursor.getInt(COL_AVG_VOL_10_DAY));
        childlist7.add(new StatsChild(getString(R.string.avg_vol_10_day),v_10d_Avg_Vol));

        String so=cursor.isNull(COL_SHARES_OUTSTANDING)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_SHARES_OUTSTANDING));
        childlist7.add(new StatsChild(getString(R.string.share_outstanding),so));
        String fl=cursor.isNull(COL_FLOAT)? getString(R.string.na):Utilities.formatValueMonetaryUnits(getContext(),cursor.getFloat(COL_FLOAT));
        childlist7.add(new StatsChild(getString(R.string.floatt),fl));
        String pctInsider=cursor.isNull(COL_PERCENT_HELD_BY_INSIDERS)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_PERCENT_HELD_BY_INSIDERS));
        childlist7.add(new StatsChild(getString(R.string.percentage_held_by_insiders),pctInsider));
        String pctInstitutions=cursor.isNull(COL_PERCENT_HELD_BY_INSTITUTIONS)? getString(R.string.na):Utilities.localizePercentValue(getContext(),cursor.getFloat(COL_PERCENT_HELD_BY_INSTITUTIONS));
        childlist7.add(new StatsChild(getString(R.string.percentage_held_by_institutions),pctInstitutions));
        String sr=cursor.isNull(COL_SHORT_RATIO) ? getString(R.string.na):Utilities.localizeDecimalValue(cursor.getFloat(COL_SHORT_RATIO));
        childlist7.add(new StatsChild(getString(R.string.short_ratio),sr));
        stats.get(7).setChildObjectList(childlist7);
        parentObject.add(stats.get(7));


        mStatsExpandableAdapter = new StatsExpandableAdapter(getActivity(),parentObject,mEmptyView );
        //mBundle is always null except after rotation
        mStatsExpandableAdapter.onRestoreInstanceState(mBundle);
        recyclerView.setAdapter(mStatsExpandableAdapter);
    }

    @Override
     public void onDestroy() {
        Log.d(LOG_TAG,"FragmentStockStats on Destroy");
        super.onDestroy();
    }
}
