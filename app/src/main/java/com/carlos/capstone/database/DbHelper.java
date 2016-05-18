package com.carlos.capstone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Carlos on 25/01/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "capstone.db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //there are cases when a stock hasn't data in stock detail query but has in favorite query,
        //so the constraints should be only ticker not null
        final String  SQL_CREATE_FAVORITES_TABLE="CREATE TABLE "+ CapstoneContract.FavoritesEntity.TABLE_NAME + " (" +
                CapstoneContract.FavoritesEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.FavoritesEntity.COMPANY_TICKER + " TEXT NOT NULL," +
                CapstoneContract.FavoritesEntity.COMPANY_NAME + " TEXT ," +
                CapstoneContract.FavoritesEntity.MARKET + " TEXT ," +
                CapstoneContract.FavoritesEntity.VALUE + " REAL ," +
                CapstoneContract.FavoritesEntity.CHANGE + " REAL ," +
                CapstoneContract.FavoritesEntity.CHANGE_PERCENT + " REAL ," +
                CapstoneContract.FavoritesEntity.MAX + " REAL," +
                CapstoneContract.FavoritesEntity.MIN + " REAL," +
                CapstoneContract.FavoritesEntity.TRADE_DATE + " TEXT," +
                "UNIQUE (" + CapstoneContract.FavoritesEntity.COMPANY_TICKER + ") ON CONFLICT IGNORE)";


        final String SQL_CREATE_NEWS_TABLE="CREATE TABLE " + CapstoneContract.NewsEntity.TABLE_NAME + " (" +
                CapstoneContract.NewsEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.NewsEntity.REGION + " TEXT NOT NULL," +
                CapstoneContract.NewsEntity.TITLE + " TEXT NOT NULL," +
                CapstoneContract.NewsEntity.DATE + " REAL NOT NULL," +
                CapstoneContract.NewsEntity.CONTENT + " TEXT NOT NULL," +
                CapstoneContract.NewsEntity.IMG_URL + " TEXT," +
                CapstoneContract.NewsEntity.LINK_URL + " TEXT NOT NULL," +
                "UNIQUE (" + CapstoneContract.NewsEntity.TITLE + ") ON CONFLICT IGNORE)";

        final String SQL_CREATE_INDEXES_TABLE="CREATE TABLE " + CapstoneContract.IndexesEntity.TABLE_NAME + " (" +
                CapstoneContract.IndexesEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.IndexesEntity.REGION + " TEXT NOT NULL," +
                CapstoneContract.IndexesEntity.INDEX_NAME + " TEXT," +
                CapstoneContract.IndexesEntity.INDEX_TICKER + " TEXT NOT NULL," +
                CapstoneContract.IndexesEntity.VALUE + " REAL," +
                CapstoneContract.IndexesEntity.VOLUMEN + " REAL," +
                CapstoneContract.IndexesEntity.CHANGE + " REAL," +
                CapstoneContract.IndexesEntity.CHANGE_PERCENT + " REAL," +
                CapstoneContract.IndexesEntity.DATE + " TEXT," +
                "UNIQUE (" + CapstoneContract.IndexesEntity.INDEX_TICKER + ") ON CONFLICT IGNORE)";



        final String SQL_CREATE_DEBUG_TABLE="CREATE TABLE " + CapstoneContract.DebugEntity.TABLE_NAME + " (" +
                CapstoneContract.DebugEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.DebugEntity.DEBUG_TYPE + " TEXT NOT NULL," +
                CapstoneContract.DebugEntity.OTHER + " TEXT," +
                CapstoneContract.DebugEntity.ELAPSED + " TEXT NOT NULL" +
                ")";

        final String SQL_CREATE_INDEX_DETAIL_TABLE="CREATE TABLE " + CapstoneContract.IndexDetailEntity.TABLE_NAME + " (" +
                CapstoneContract.IndexDetailEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.IndexDetailEntity.INDEX_TICKER + " TEXT NOT NULL," +
                CapstoneContract.IndexDetailEntity.INDEX_NAME + " TEXT," +
                CapstoneContract.IndexDetailEntity.PRICE + " REAL NOT NULL," +
                CapstoneContract.IndexDetailEntity.CHANGE + " REAL NOT NULL," +
                CapstoneContract.IndexDetailEntity.CHANGE_PERCENT + " REAL NOT NULL," +
                CapstoneContract.IndexDetailEntity.TIMESTAMP + " REAL," +
                CapstoneContract.IndexDetailEntity.UTCTIME + " TEXT," +
                CapstoneContract.IndexDetailEntity.DAY_HIGHT + " REAL," +
                CapstoneContract.IndexDetailEntity.DAY_LOW + " REAL," +
                CapstoneContract.IndexDetailEntity.YEAR_HIGHT + " REAL," +
                CapstoneContract.IndexDetailEntity.YEAR_LOW + " REAL," +
                CapstoneContract.IndexDetailEntity.VOLUME + " REAL," +
                "UNIQUE (" + CapstoneContract.IndexDetailEntity.INDEX_TICKER + ") ON CONFLICT IGNORE)";

        final String SQL_CREATE_INDEX_COMPONENT_TABLE="CREATE TABLE " + CapstoneContract.IndexComponentEntity.TABLE_NAME + " (" +
                CapstoneContract.IndexComponentEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.IndexComponentEntity.INDEX_NAME + " TEXT NOT NULL," +
                CapstoneContract.IndexComponentEntity.COMPANY_NAME + " TEXT NOT NULL," +
                CapstoneContract.IndexComponentEntity.LAST + " REAL NOT NULL," +
                CapstoneContract.IndexComponentEntity.VAR_DOLLAR + " REAL," +
                CapstoneContract.IndexComponentEntity.VAR_EURO + " REAL," +
                CapstoneContract.IndexComponentEntity.VAR + " REAL NOT NULL," +
                CapstoneContract.IndexComponentEntity.VOLUME + " REAL," +
                CapstoneContract.IndexComponentEntity.TIME + " TEXT," +
                CapstoneContract.IndexComponentEntity.FREE_FIELD + " TEXT,"+
                "UNIQUE (" + CapstoneContract.IndexComponentEntity.INDEX_NAME + "," +
                             CapstoneContract.IndexComponentEntity.COMPANY_NAME  + ") ON CONFLICT IGNORE)";

        final String SQL_CREATE_COMPANY_TABLE="CREATE TABLE " + CapstoneContract.CompanyEntity.TABLE_NAME + " (" +
                CapstoneContract.CompanyEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.CompanyEntity.SYMBOL + " TEXT NOT NULL," +
                CapstoneContract.CompanyEntity.NAME + " TEXT NOT NULL," +
                CapstoneContract.CompanyEntity.LAST_SALE + " TEXT," +
                CapstoneContract.CompanyEntity.MARKET_CAP + " TEXT," +
                CapstoneContract.CompanyEntity.IPO_YEAR + " TEXT,"+
                CapstoneContract.CompanyEntity.SECTOR + " TEXT," +
                CapstoneContract.CompanyEntity.STOCK_EXCHANGE + " TEXT," +
                "UNIQUE (" + CapstoneContract.CompanyEntity.SYMBOL + ") ON CONFLICT IGNORE)";


        final String SQL_CREATE_STOCK_DETAIL_TABLE="CREATE TABLE " + CapstoneContract.StockDetailEntity.TABLE_NAME + " (" +
                CapstoneContract.StockDetailEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.StockDetailEntity.TIMESTAMP + " INTEGER NOT NULL," +
                CapstoneContract.StockDetailEntity.SYMBOL + " TEXT NOT NULL," +
                CapstoneContract.StockDetailEntity.COMPANY_NAME + " TEXT NOT NULL," +
                CapstoneContract.StockDetailEntity.LAST_TRADE_PRICE + " REAL," +
                CapstoneContract.StockDetailEntity.CHANGE + " REAL," +
                CapstoneContract.StockDetailEntity.CHANGE_PERCENT + " REAL," +
                CapstoneContract.StockDetailEntity.LAST_TRADE_DATE + " TEXT," +
                CapstoneContract.StockDetailEntity.LAST_TRADE_TIME + " TEXT," +
                CapstoneContract.StockDetailEntity.MARKET_CAP + " TEXT," +
                CapstoneContract.StockDetailEntity.DAYS_RANGE + " TEXT," +
                CapstoneContract.StockDetailEntity.PREVIOUS_CLOSE + " REAL," +
                CapstoneContract.StockDetailEntity.OPEN + " REAL," +
                CapstoneContract.StockDetailEntity.ASK + " REAL," +
                CapstoneContract.StockDetailEntity.BID + " REAL," +
                CapstoneContract.StockDetailEntity.VOLUME + " INTEGER," +
                CapstoneContract.StockDetailEntity.AVG_DAILY_VOLUME + " REAL," +
                CapstoneContract.StockDetailEntity.EARNINGS_SHARE + " REAL," +
                CapstoneContract.StockDetailEntity.EPS_ESTIMATE_CURRENT_YEAR + " REAL,"+
                CapstoneContract.StockDetailEntity.EPS_ESTIMATE_NEXT_YEAR + " REAL," +
                CapstoneContract.StockDetailEntity.EPS_ESTIMATE_NEXT_QUARTER + " REAL," +
                CapstoneContract.StockDetailEntity.YEAR_LOW + " REAL," +
                CapstoneContract.StockDetailEntity.YEAR_HIGH + " REAL," +
                CapstoneContract.StockDetailEntity.CHANGE_FROM_YEAR_LOW + " REAL," +
                CapstoneContract.StockDetailEntity.PERCENT_CHANGE_FROM_YEAR_LOW + " REAL," +
                CapstoneContract.StockDetailEntity.CHANGE_FROM_YEAR_HIGH + " REAL," +
                CapstoneContract.StockDetailEntity.PERCENT_CHANGE_FROM_YEAR_HIGH + " REAL," +
                CapstoneContract.StockDetailEntity.PER_RATIO + " REAL," +
                CapstoneContract.StockDetailEntity.PEG_RATIO + " REAL," +
                CapstoneContract.StockDetailEntity.ONE_YEAR_TARGET_PRICE + " REAL," +
                CapstoneContract.StockDetailEntity.STOCK_EXCHANGE + " TEXT," +
                "UNIQUE (" + CapstoneContract.StockDetailEntity.SYMBOL + ") ON CONFLICT IGNORE)";

        final String SQL_CREATE_STOCK_STATS_TABLE="CREATE TABLE " + CapstoneContract.StockStatsEntity.TABLE_NAME + " (" +
                CapstoneContract.StockStatsEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.StockStatsEntity.SYMBOL + " TEXT NOT NULL," +
                CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE + " REAL," +
                CapstoneContract.StockStatsEntity.TRAILING_PE + " REAL," +
                CapstoneContract.StockStatsEntity.FORWARD_PE + " REAL," +
                CapstoneContract.StockStatsEntity.PEG_RATIO + " REAL," +
                CapstoneContract.StockStatsEntity.PRICE_SALES + " REAL," +
                CapstoneContract.StockStatsEntity.PRICE_BOOK + " REAL," +
                CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE_REVENUE + " REAL," +
                CapstoneContract.StockStatsEntity.ENTERPRISE_VALUE_EBITDA + " REAL," +
                CapstoneContract.StockStatsEntity.PROFIT_MARGIN + " REAL," +
                CapstoneContract.StockStatsEntity.OPERATING_MARGIN + " REAL," +
                CapstoneContract.StockStatsEntity.RETURN_ON_ASSETS + " REAL," +
                CapstoneContract.StockStatsEntity.RETURN_ON_EQUITY + " REAL," +
                CapstoneContract.StockStatsEntity.REVENUE + " REAL," +
                CapstoneContract.StockStatsEntity.REVENUE_PER_SHARE + " REAL," +
                CapstoneContract.StockStatsEntity.QTRLY_REVENUE_GROWTH + " REAL," +
                CapstoneContract.StockStatsEntity.GROSS_PROFIT + " REAL," +
                CapstoneContract.StockStatsEntity.EBITDA + " REAL," +
                CapstoneContract.StockStatsEntity.NET_INCOME_AVL_TO_COMMON + " REAL," +
                CapstoneContract.StockStatsEntity.DILUTED_EPS + " REAL," +
                CapstoneContract.StockStatsEntity.QTRLY_EARNINGS_GROWTH + " REAL," +
                CapstoneContract.StockStatsEntity.TOTAL_CASH + " REAL," +
                CapstoneContract.StockStatsEntity.TOTAL_CASH_PER_SHARE + " REAL," +
                CapstoneContract.StockStatsEntity.TOTAL_DEBT + " REAL," +
                CapstoneContract.StockStatsEntity.TOTAL_DEBT_EQUITY + " REAL," +
                CapstoneContract.StockStatsEntity.CURRENT_RATIO + " REAL," +
                CapstoneContract.StockStatsEntity.BOOK_VALUE_PER_SHARE + " REAL," +
                CapstoneContract.StockStatsEntity.OPERATING_CASH_FLOW + " REAL," +
                CapstoneContract.StockStatsEntity.LEVERED_FREE_CASH_FLOW + " REAL," +
                CapstoneContract.StockStatsEntity.BETA + " REAL," +
                CapstoneContract.StockStatsEntity.N_52_WEEK_CHANGE + " REAL," +
                CapstoneContract.StockStatsEntity.SP500_52_WEEK_CHANGE + " REAL," +
                CapstoneContract.StockStatsEntity.N_52_WEEK_HIGH + " REAL," +
                CapstoneContract.StockStatsEntity.N_52_WEEK_LOW + " REAL," +
                CapstoneContract.StockStatsEntity.N_50_DAY_MOVING_AVERAGE + " REAL," +
                CapstoneContract.StockStatsEntity.N_200_DAY_MOVING_AVERAGE  + " REAL," +
                CapstoneContract.StockStatsEntity.AVG_VOL_3_MONTH + " INTEGER," +
                CapstoneContract.StockStatsEntity.AVG_VOL_10_DAY + " INTEGER," +
                CapstoneContract.StockStatsEntity.SHARES_OUTSTANDING + " REAL," +
                CapstoneContract.StockStatsEntity.FLOAT + " REAL," +
                CapstoneContract.StockStatsEntity.PERCENT_HELD_BY_INSIDERS + " REAL," +
                CapstoneContract.StockStatsEntity.PERCENT_HELD_BY_INSTITUTIONS + " REAL," +
                CapstoneContract.StockStatsEntity.SHORT_RATIO + " REAL," +
                "UNIQUE (" + CapstoneContract.StockStatsEntity.SYMBOL + ") ON CONFLICT IGNORE)";

        final String SQL_CREATE_STOCK_NEWS_TABLE="CREATE TABLE " + CapstoneContract.StockNewsEntity.TABLE_NAME + " (" +
                CapstoneContract.StockNewsEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.StockNewsEntity.SYMBOL + " TEXT NOT NULL," +
                CapstoneContract.StockNewsEntity.TITLE + " TEXT," +
                CapstoneContract.StockNewsEntity.DATE + " TEXT," +
                CapstoneContract.StockNewsEntity.PUBLISHER + " TEXT," +
                CapstoneContract.StockNewsEntity.LINK_URL + " TEXT)";
        final String SQL_CREATE_SECURITY_EXCEL_TABLE="CREATE TABLE " + CapstoneContract.SecurityExcelEntity.TABLE_NAME + " (" +
                CapstoneContract.SecurityExcelEntity._ID + " INTEGER PRIMARY KEY," +
                CapstoneContract.SecurityExcelEntity.ROWNUM + " INTEGER," +
                CapstoneContract.SecurityExcelEntity.TICKER + " TEXT NOT NULL," +
                CapstoneContract.SecurityExcelEntity.NAME + " TEXT," +
                CapstoneContract.SecurityExcelEntity.EXCHANGE + " TEXT," +
                CapstoneContract.SecurityExcelEntity.COUNTRY + " TEXT," +
                CapstoneContract.SecurityExcelEntity.SECURITY_TYPE + " TEXT," +
                "UNIQUE (" + CapstoneContract.SecurityExcelEntity.TICKER + ") ON CONFLICT IGNORE)";


        Log.d("sql-statments",SQL_CREATE_FAVORITES_TABLE);
        Log.d("sql-statments",SQL_CREATE_NEWS_TABLE);
        Log.d("sql-statments",SQL_CREATE_INDEXES_TABLE);
        Log.d("sql-statments",SQL_CREATE_INDEX_DETAIL_TABLE);
        Log.d("sql-statments",SQL_CREATE_INDEX_COMPONENT_TABLE);
        Log.d("sql-statments",SQL_CREATE_COMPANY_TABLE);
        Log.d("sql-statments",SQL_CREATE_STOCK_DETAIL_TABLE);
        Log.d("sql-statments",SQL_CREATE_STOCK_STATS_TABLE);
        Log.d("sql-statments",SQL_CREATE_STOCK_NEWS_TABLE);
        Log.d("sql-statments",SQL_CREATE_SECURITY_EXCEL_TABLE);
        Log.d("sql-statments",SQL_CREATE_DEBUG_TABLE);

        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
        db.execSQL(SQL_CREATE_NEWS_TABLE);
        db.execSQL(SQL_CREATE_INDEXES_TABLE);
        db.execSQL(SQL_CREATE_INDEX_DETAIL_TABLE);
        db.execSQL(SQL_CREATE_INDEX_COMPONENT_TABLE);
        db.execSQL(SQL_CREATE_COMPANY_TABLE);
        db.execSQL(SQL_CREATE_STOCK_DETAIL_TABLE);
        db.execSQL(SQL_CREATE_STOCK_STATS_TABLE);
        db.execSQL(SQL_CREATE_STOCK_NEWS_TABLE);
        db.execSQL(SQL_CREATE_SECURITY_EXCEL_TABLE);
        db.execSQL(SQL_CREATE_DEBUG_TABLE);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.FavoritesEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.IndexesEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.NewsEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.IndexDetailEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.IndexComponentEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.CompanyEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.StockDetailEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.StockStatsEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.StockNewsEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.SecurityExcelEntity.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CapstoneContract.DebugEntity.TABLE_NAME);
        onCreate(db);
    }
}
