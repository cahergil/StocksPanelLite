package com.carlos.capstone.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Carlos on 25/01/2016.
 */
public class CapstoneContract {
    public static final String CONTENT_AUTHORITY = "com.carlos.capstone";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES="favorites";
    public static final String PATH_NEWS="news";
    public static final String PATH_INDEXES="indexes";
    public static final String PATH_COMPANY="company";
    public static final String PATH_DEBUG="debug";
    public static final String PATH_INDEX_DETAIL="index_detail";
    public static final String PATH_INDEX_COMPONENT="index_component";
    public static final String PATH_STOCK_DETAIL="stock_detail";
    public static final String PATH_STOCK_STATISTICS="stock_statistics";
    public static final String PATH_STOCK_NEWS="stock_news";
    public static final String PATH_SECURITY_EXCEL="security_excel";

    public static final class FavoritesEntity implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        public static final String TABLE_NAME="favorites";
        public static final String COMPANY_TICKER="company_ticker";
        public static final String COMPANY_NAME="company_name";
        public static final String MARKET="market";
        public static final String VALUE="value";
        public static final String CHANGE="change";
        public static final String CHANGE_PERCENT="change_percent";
        public static final String MAX="max";
        public static final String MIN="min";
        public static final String TRADE_DATE="trade_date";

        public static Uri buildFavoritesUri(long id){

            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildFavoritesWithTicker(String ticker) {
            return CONTENT_URI.buildUpon().appendPath(ticker).build();
        }


    }
    public static final class NewsEntity implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String TABLE_NAME="news";
        public static final String REGION="region";
        public static final String TITLE="title";
        public static final String DATE="date";
        public static final String CONTENT="content";
        public static final String IMG_URL="img_url";
        public static final String LINK_URL="link_url";

        public static Uri buildNewsUri(long id){

            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static Uri buildNewsWithRegion() {

            return CONTENT_URI.buildUpon().appendPath("region").build();
        }
    }

    public static final class IndexesEntity implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INDEXES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_INDEXES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_INDEXES;

        public static final String TABLE_NAME="indexes";
        public static final String REGION="region";
        public static final String INDEX_NAME="index_name";
        public static final String INDEX_TICKER="index_ticker";
        public static final String VALUE="value";
        public static final String VOLUMEN="volumen";
        public static final String CHANGE="change";
        public static final String CHANGE_PERCENT="change_percent";
        public static final String DATE="date";

        public static Uri buildIndexesWithName(String name){
            return CONTENT_URI.buildUpon().appendPath(name).build();
        }
        public static Uri buildIndexesUri(long id){

            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

    }


    public static final class CompanyEntity implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMPANY).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_COMPANY;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_COMPANY;

        public static final String TABLE_NAME="company";
        public static final String SYMBOL="symbol";
        public static final String NAME="name";
        public static final String LAST_SALE="last_sale";
        public static final String MARKET_CAP="market_cap";
        public static final String IPO_YEAR="ipo_year";
        public static final String SECTOR="sector";
        public static final String STOCK_EXCHANGE="stock_exchange";
    }

    public static final class IndexDetailEntity implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INDEX_DETAIL).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_INDEX_DETAIL;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_INDEX_DETAIL;

        public static final String TABLE_NAME="index_detail";
        public static final String INDEX_TICKER="index_ticker";
        public static final String INDEX_NAME="index_name";
        public static final String PRICE="price";
        public static final String CHANGE="change";
        public static final String CHANGE_PERCENT="change_percent";
        public static final String TIMESTAMP="timestamp";
        public static final String UTCTIME="utctime";
        public static final String DAY_HIGHT="day_hight";
        public static final String DAY_LOW="day_low";
        public static final String YEAR_HIGHT="year_hight";
        public static final String YEAR_LOW="year_low";
        public static final String VOLUME="volume";

        public static Uri buildIndexDetailWithSymbol(String symbol) {

            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }

        public static Uri buildIndexDetailUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class IndexComponentEntity implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_INDEX_COMPONENT).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_INDEX_COMPONENT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_INDEX_COMPONENT;
        public static final String TABLE_NAME="index_component";
        public static final String INDEX_NAME="index_name";
        public static final String COMPANY_NAME="company_name";
        public static final String LAST="last";
        public static final String VAR_DOLLAR="var_dollar";
        public static final String VAR_EURO="var_euro";
        public static final String VAR="var";
        public static final String VOLUME="volume";
        public static final String TIME="time";
        public static final String FREE_FIELD="free_fiels";

        public static Uri buildIndexComponentWithIndex(String index) {
            return CONTENT_URI.buildUpon().appendPath(index).build();
        }

    }



    public static final class DebugEntity implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEBUG).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DEBUG;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DEBUG;

        public static final String TABLE_NAME="debug";
        public static final String DEBUG_TYPE="debug_type";
        public static final String OTHER="other";
        public static final String ELAPSED="elapsed";

        public static Uri buildDebugUri(long id){

            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class StockDetailEntity implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOCK_DETAIL).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_DETAIL;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_DETAIL;


        public static final String TABLE_NAME="stock_detail";
        public static final String TIMESTAMP="timestamp";
        public static final String SYMBOL="symbol";
        public static final String COMPANY_NAME="company_name";
        public static final String LAST_TRADE_PRICE="last_trade_price";
        public static final String CHANGE="change";
        public static final String CHANGE_PERCENT="change_percent";
        public static final String LAST_TRADE_DATE="last_trade_date";
        public static final String LAST_TRADE_TIME="last_trade_time";
        public static final String MARKET_CAP="market_cap";
        public static final String DAYS_RANGE="days_range";
        public static final String PREVIOUS_CLOSE="previous_close";
        public static final String OPEN="open";
        public static final String ASK="ask";
        public static final String BID="bid";
        public static final String VOLUME="volume";
        public static final String AVG_DAILY_VOLUME="avg_daily_volume";
        public static final String EARNINGS_SHARE="earnings_share";
        public static final String EPS_ESTIMATE_CURRENT_YEAR="eps_estimate_current_year";
        public static final String EPS_ESTIMATE_NEXT_YEAR="eps_estimate_next_year";
        public static final String EPS_ESTIMATE_NEXT_QUARTER="eps_estimate_next_quarter";
        public static final String YEAR_LOW="year_low";
        public static final String YEAR_HIGH="year_high";
        public static final String CHANGE_FROM_YEAR_LOW="change_from_year_low";
        public static final String PERCENT_CHANGE_FROM_YEAR_LOW="percent_change_from_year_low";
        public static final String CHANGE_FROM_YEAR_HIGH="change_from_year_high";
        public static final String PERCENT_CHANGE_FROM_YEAR_HIGH="percent_change_from_year_high";
        public static final String PER_RATIO="per_ratio";
        public static final String PEG_RATIO="peg_ratio";
        public static final String ONE_YEAR_TARGET_PRICE="one_year_target_price";
        public static final String STOCK_EXCHANGE="stock_exchange";

        public static Uri buildStockDetailUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildStockDetailWithSymbol(String symbol) {
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }
    }

    public static final class StockStatsEntity implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOCK_STATISTICS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_STATISTICS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_STATISTICS;

        public static final String TABLE_NAME="stock_statistics";
        public static final String SYMBOL="symbol";
        public static final String ENTERPRISE_VALUE="enterprise_value";
        public static final String TRAILING_PE="trailing_pe";
        public static final String FORWARD_PE="forward_pe";
        public static final String PEG_RATIO="peg_ratio";
        public static final String PRICE_SALES="price_sales";
        public static final String PRICE_BOOK="price_book";
        public static final String ENTERPRISE_VALUE_REVENUE="enterprise_value_revenue";
        public static final String ENTERPRISE_VALUE_EBITDA="enterprise_value_ebitda";
        public static final String PROFIT_MARGIN="profit_margin";
        public static final String OPERATING_MARGIN="operating_margin";
        public static final String RETURN_ON_ASSETS="return_on_assets";
        public static final String RETURN_ON_EQUITY="return_on_equity";
        public static final String REVENUE="revenue";
        public static final String REVENUE_PER_SHARE="revenue_per_share";
        public static final String QTRLY_REVENUE_GROWTH="qtrly_revenue_growth";
        public static final String GROSS_PROFIT="gross_profit";
        public static final String EBITDA="ebitda";
        public static final String NET_INCOME_AVL_TO_COMMON="net_income_avl_to_common";
        public static final String DILUTED_EPS="diluted_eps";
        public static final String QTRLY_EARNINGS_GROWTH="qtrly_earnings_growth";
        public static final String TOTAL_CASH="total_cash";
        public static final String TOTAL_CASH_PER_SHARE="total_cash_per_share";
        public static final String TOTAL_DEBT="total_debt";
        public static final String TOTAL_DEBT_EQUITY="total_debt_equity";
        public static final String CURRENT_RATIO="current_ratio";
        public static final String BOOK_VALUE_PER_SHARE="book_value_per_share";
        public static final String OPERATING_CASH_FLOW="operating_cash_flow";
        public static final String LEVERED_FREE_CASH_FLOW="levered_free_cash_flow";
        public static final String BETA="beta";
        public static final String N_52_WEEK_CHANGE="n_52_week_change";
        public static final String SP500_52_WEEK_CHANGE="sp500_52_week_change";
        public static final String N_52_WEEK_HIGH="n_52_week_high";
        public static final String N_52_WEEK_LOW="n_52_week_low";
        public static final String N_50_DAY_MOVING_AVERAGE="n_50_day_moving_average";
        public static final String N_200_DAY_MOVING_AVERAGE="n_200_day_moving_average";
        public static final String AVG_VOL_3_MONTH="avg_vol_3_month";
        public static final String AVG_VOL_10_DAY="avg_vol_10_day";
        public static final String SHARES_OUTSTANDING="shares_outstanding";
        public static final String FLOAT="float";
        public static final String PERCENT_HELD_BY_INSIDERS="percent_held_by_insiders";
        public static final String PERCENT_HELD_BY_INSTITUTIONS="percent_held_by_institutions";
        public static final String SHORT_RATIO="short_ratio";

        public static Uri buildStockStatsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildStockStatsWithSymbol(String symbol) {
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }

    }
    public static final class StockNewsEntity implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOCK_NEWS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_NEWS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_STOCK_NEWS;

        public final static String TABLE_NAME="stock_news";
        public static final String SYMBOL="symbol";
        public static final String TITLE="title";
        public static final String DATE="date";
        public static final String PUBLISHER="publisher";
        public static final String LINK_URL="link_url";

        public static Uri buildStockNewsWithSymbol(String symbol) {
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }

    }

    public static final class SecurityExcelEntity implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_SECURITY_EXCEL).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_SECURITY_EXCEL;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_SECURITY_EXCEL;

        public final static String TABLE_NAME="security_excel";
        public static final String ROWNUM="rownum";
        public static final String TICKER="ticker";
        public static final String NAME="name";
        public static final String EXCHANGE="exchange";
        public static final String COUNTRY="country";
        public static final String SECURITY_TYPE="security_type";



    }



}
