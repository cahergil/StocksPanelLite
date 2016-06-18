package com.carlos.capstone;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.capstone.customcomponents.AutoResizeTextView;
import com.carlos.capstone.customcomponents.CustomSecurityMarkerView;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.models.HistoricalDataResponseDate;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.services.IndexEtfOrShortInfoSummaryService;
import com.carlos.capstone.services.StockSummaryService;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Carlos on 25/12/2015.
 */
public class FragmentStockSummary extends Fragment implements View.OnClickListener, OnChartGestureListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = FragmentStockSummary.class.getSimpleName();
    private CombinedChart mChart;
    private List<Long> yValuesBar = new ArrayList<Long>();
    private List<Float> yValuesLinear = new ArrayList<Float>();
    private List<String> xLabels = new ArrayList<String>();
    private TextView mTxtSymbol, mTxtLastTradePrice, mTxtLastTradeDate, txtLasTradeTime, mTxtChange;
    private AutoResizeTextView mTxtMarketCap, mTxtDaysRange, mTxtPreviousClose, mTxtOpen, mTxtAsk, mTxtBid, mTxtVolume,
            mTxtAverageDailyVolume, mTxtEarningsShare, mTxtEPSEstimateCurrentYear, mTxtEPSEstimateNextYear,
            mTxtEPSEstimateNextQuarter, mTxtYearLow, mTxtYearHigh, mTxtChangeFromYearLow, mTxtPercentChangeFromYearLow,
            mTxtChangeFromYearHigh, mTxtPercebtChangeFromYearHigh, mTxtPERatio, mTxtPEGRatio, mTxtOneYearTargetPrice,
            mTxtCompanyName, mTxtMarket;
    private ImageView ivLastTradePrice;
    private Button mButton1d, mButton1w, mButton1m, mButton6m, mButton2y, mButton5y, mButtonMax, mButtonFit;
    private int[] mArrayButtons;
    private View mView;

    private int mSelectedButtonId;


    private ShareActionProvider mShareActionProvider;
    private LinearLayout mLlScreenshotValueChange;
    private CardView mCardViewTradingInfo;
    private ProgressBar mProgressBar;
    private String mSymbol;
    private String mCompanyName;
    private static final int STOCK_LOADER = 0;
    private static final int SHORT_INFO_LOADER=1;

    public static final int COL_SHORT_INFO_STOCK_TICKER = 1;
    public static final int COL_SHORT_INFO_STOCK_NAME = 2;
    public static final int COL_SHORT_INFO_PRICE = 3;
    public static final int COL_SHORT_INFO_CHANGE = 4;
    public static final int COL_SHORT_INFO_CHANGE_PERCENT = 5;
    public static final int COL_SHORT_INFO_TIMESTAMP = 6;
    public static final int COL_SHORT_INFO_UTCTIME = 7;
    public static final int COL_SHORT_INFO_DAY_HIGH = 8;
    public static final int COL_SHORT_INFO_DAY_LOW = 9;
    public static final int COL_SHORT_INFO_YEAR_HIGH = 10;
    public static final int COL_SHORT_INFO_YEAR_LOW = 11;
    public static final int COL_SHORT_INFO_VOLUME = 12;


    public static final int COL_TIMESTAMP = 1;
    public static final int COL_SYMBOL = 2;
    public static final int COL_COMPANY_NAME = 3;
    public static final int COL_LAST_TRADE_PRICE = 4;
    public static final int COL_CHANGE = 5;
    public static final int COL_CHANGE_PERCENT = 6;
    public static final int COL_LAST_TRADE_DATE = 7;
    public static final int COL_LAST_TRADE_TIME = 8;
    public static final int COL_MARKET_CAP = 9;
    public static final int COL_DAYS_RANGE = 10;
    public static final int COL_PREVIOUS_CLOSE = 11;
    public static final int COL_OPEN = 12;
    public static final int COL_ASK = 13;
    public static final int COL_BID = 14;
    public static final int COL_VOLUME = 15;
    public static final int COL_AVG_DAILY_VOLUME = 16;
    public static final int COL_EARNINGS_SHARE = 17;
    public static final int COL_EPS_ESTIMATE_CURRENT_YEAR = 18;
    public static final int COL_EPS_ESTIMATE_NEXT_YEAR = 19;
    public static final int COL_EPS_ESTIMATE_NEXT_QUARTER = 20;
    public static final int COL_YEAR_LOW = 21;
    public static final int COL_YEAR_HIGH = 22;
    public static final int COL_CHANGE_FROM_YEAR_LOW = 23;
    public static final int COL_PERCENT_CHANGE_FROM_YEAR_LOW = 24;
    public static final int COL_CHANGE_FROM_YEAR_HIGH = 25;
    public static final int COL_PERCENT_CHANGE_FROM_YEAR_HIGH = 26;
    public static final int COL_PER_RATIO = 27;
    public static final int COL_PEG_RATIO = 28;
    public static final int COL_ONE_YEAR_TARGET_PRICE = 29;
    public static final int COL_STOCK_EXCHANGE = 30;

    private boolean mIsInFavorite = false;
    private MenuItem mFavoriteMenuItem;
    private ScrollView mScrollView;
    private
    @ChartMode
    int mChartMode;


    WrapperObserverDate wrapperObserverDate5 = new WrapperObserverDate(CHART_MODE_MAX);
    WrapperObserverDate wrapperObserverDate4 = new WrapperObserverDate(CHART_MODE_5Y);
    WrapperObserverDate wrapperObserverDate3 = new WrapperObserverDate(CHART_MODE_2Y);
    WrapperObserverDate wrapperObserverDate2 = new WrapperObserverDate(CHART_MODE_6M);
    WrapperObserverDate wrapperObserverDate1 = new WrapperObserverDate(CHART_MODE_1M);

    WrapperObserverTimestamp wrapperObserverTimestamp1 = new WrapperObserverTimestamp(CHART_MODE_1D);
    WrapperObserverTimestamp wrapperObserverTimestamp2 = new WrapperObserverTimestamp(CHART_MODE_7D);
    private Subscription subscription;

    //
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHART_MODE_1D, CHART_MODE_7D, CHART_MODE_1M, CHART_MODE_6M, CHART_MODE_2Y,
            CHART_MODE_5Y, CHART_MODE_MAX})
    public @interface ChartMode {
    }

    public static final int CHART_MODE_1D = 0;
    public static final int CHART_MODE_7D = 1;
    public static final int CHART_MODE_1M = 2;
    public static final int CHART_MODE_6M = 3;
    public static final int CHART_MODE_2Y = 4;
    public static final int CHART_MODE_5Y = 5;
    public static final int CHART_MODE_MAX = 6;

    private boolean mInvalidateMenu = false;
    private Bitmap mSourceBitmap;
    private String mSharedFileName = "";
    private static final String FILE_EXTENSION = ".jpg";
    private boolean mDisableShare;

    //implement interface of mpAndroidchart about gestures
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i(LOG_TAG, "onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i(LOG_TAG, "onChartGestureEndt");
        //if the gesture is distinct from single tap, hide the Markerview
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP) {
            mChart.highlightValues(null);
            int count = mChart.getLineData().getDataSetCount();
            if (mChartMode == CHART_MODE_1D) {
                if (count > 2) {
                    mChart.getLineData().removeDataSet(count - 1);
                }
            } else {
                if (count > 1) {
                    mChart.getLineData().removeDataSet(count - 1);
                }
            }
        }
        //  updateShareActionProvider();
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i(LOG_TAG, "onChartGestureLongPressedt");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i(LOG_TAG, "onChartGestureDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i(LOG_TAG, "onChartGestureSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i(LOG_TAG, "onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i(LOG_TAG, "onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i(LOG_TAG, "onChartTranslate");

    }


    public FragmentStockSummary() {
        //causes memory leak http://stackoverflow.com/questions/13421945/retained-fragments-with-ui-and-memory-leaks
        //to avoid leaks, store observers in Application class class
        //  setRetainInstance(true);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarRightPane);
            Menu menu;
            if (toolbar != null) {
                menu = toolbar.getMenu();
                if (menu != null) menu.clear();
                toolbar.inflateMenu(R.menu.summary_menu);
                if(mDisableShare) {
                    toolbar.getMenu().findItem(R.id.action_share).setVisible(false);
                }
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        handleNavigationPress(item.getItemId());
                        return true;
                    }
                });
                initMenuItems(toolbar.getMenu());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");
        mView = inflater.inflate(R.layout.fragment_stock_summary, container, false);


        if (Utilities.hasTwoPane(getActivity())) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                    new IntentFilter(getString(R.string.stock_tab_summary_intent_filter)));
        }
        mButton1d = (Button) mView.findViewById(R.id.btn1d);
        mButton1d.setContentDescription(getString(R.string.fss_button_1_day));
        mButton1d.setOnClickListener(this);
        mButton1w = (Button) mView.findViewById(R.id.btn1w);
        mButton1w.setContentDescription(getString(R.string.fss_button_1_week));
        mButton1w.setOnClickListener(this);
        mButton1m = (Button) mView.findViewById(R.id.btn1m);
        mButton1m.setContentDescription(getString(R.string.fss_button_1_month));
        mButton1m.setOnClickListener(this);
        mButton6m = (Button) mView.findViewById(R.id.btn6m);
        mButton6m.setContentDescription(getString(R.string.fss_button_6_months));
        mButton6m.setOnClickListener(this);
        mButton2y = (Button) mView.findViewById(R.id.btn2y);
        mButton2y.setContentDescription(getString(R.string.fss_button_2_year));
        mButton2y.setOnClickListener(this);
        mButton5y = (Button) mView.findViewById(R.id.btn5y);
        mButton5y.setContentDescription(getString(R.string.fss_button_5_year));
        mButton5y.setOnClickListener(this);
        mButtonMax = (Button) mView.findViewById(R.id.btnMax);
        mButtonMax.setContentDescription(getString(R.string.fss_button_max));
        mButtonMax.setOnClickListener(this);
        mButtonFit = (Button) mView.findViewById(R.id.btnFit);
        mButtonFit.setContentDescription(getString(R.string.fss_button_fit));
        mButtonFit.setOnClickListener(this);

        //HEADER MEASURES
        mTxtSymbol = (TextView) mView.findViewById(R.id.txtSymbol);
        mTxtLastTradePrice = (TextView) mView.findViewById(R.id.txtLastTradePrice);
        ivLastTradePrice = (ImageView) mView.findViewById(R.id.ivLastTradePrice);
        mTxtLastTradeDate = (TextView) mView.findViewById(R.id.txtlasTradeDate);
        // txtLasTradeTime= (TextView) view.findViewById(R.id.txtlasTradeTime);
        mTxtChange = (TextView) mView.findViewById(R.id.txtChange);
        // OTHER MEASURES
        mTxtMarketCap = (AutoResizeTextView) mView.findViewById(R.id.txtMarketCap);
        mTxtDaysRange = (AutoResizeTextView) mView.findViewById(R.id.txtDaysRange);
        mTxtPreviousClose = (AutoResizeTextView) mView.findViewById(R.id.txtPreviousClose);
        mTxtOpen = (AutoResizeTextView) mView.findViewById(R.id.txtOpen);
        mTxtAsk = (AutoResizeTextView) mView.findViewById(R.id.txtAsk);
        mTxtBid = (AutoResizeTextView) mView.findViewById(R.id.txtBid);
        mTxtVolume = (AutoResizeTextView) mView.findViewById(R.id.txtVolume);
        mTxtAverageDailyVolume = (AutoResizeTextView) mView.findViewById(R.id.txtAverageDailyVolume);
        mTxtEarningsShare = (AutoResizeTextView) mView.findViewById(R.id.txtEarningsShare);
        mTxtEPSEstimateCurrentYear = (AutoResizeTextView) mView.findViewById(R.id.txtEPSEstimateCurrentYear);
        mTxtEPSEstimateNextYear = (AutoResizeTextView) mView.findViewById(R.id.txtEPSEstimateNextYear);
        mTxtEPSEstimateNextQuarter = (AutoResizeTextView) mView.findViewById(R.id.txtEPSEstimateNextQuarter);
        mTxtYearLow = (AutoResizeTextView) mView.findViewById(R.id.txtYearLow);
        mTxtYearHigh = (AutoResizeTextView) mView.findViewById(R.id.txtYearHigh);
        mTxtChangeFromYearLow = (AutoResizeTextView) mView.findViewById(R.id.txtChangeFromYearLow);
        mTxtPercentChangeFromYearLow = (AutoResizeTextView) mView.findViewById(R.id.txtPercentChangeFromYearLow);
        mTxtChangeFromYearHigh = (AutoResizeTextView) mView.findViewById(R.id.txtChangeFromYearHigh);
        mTxtPercebtChangeFromYearHigh = (AutoResizeTextView) mView.findViewById(R.id.txtPercebtChangeFromYearHigh);
        mTxtPERatio = (AutoResizeTextView) mView.findViewById(R.id.txtPERatio);
        mTxtPEGRatio = (AutoResizeTextView) mView.findViewById(R.id.txtPEGRatio);
        mTxtOneYearTargetPrice = (AutoResizeTextView) mView.findViewById(R.id.txtOneYearTargetPrice);
        mTxtCompanyName = (AutoResizeTextView) mView.findViewById(R.id.txtCompanyName);
        mTxtMarket = (AutoResizeTextView) mView.findViewById(R.id.txtMarket);

        mChart = (CombinedChart) mView.findViewById(R.id.chart);
        mChart.setOnChartGestureListener(this);

        mArrayButtons = new int[]{R.id.btn1d, R.id.btn1w, R.id.btn1m, R.id.btn6m, R.id.btn2y, R.id.btn5y, R.id.btnMax, R.id.btnFit};
        mLlScreenshotValueChange = (LinearLayout) mView.findViewById(R.id.llScreenshotValueChange);
        mCardViewTradingInfo = (CardView) mView.findViewById(R.id.cardView);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        mScrollView = (ScrollView) mView.findViewById(R.id.verticalScrollview);
        configCombinatedChart();
        // device rotation
        if (savedInstanceState != null) {

            mSharedFileName = savedInstanceState.getString(getString(R.string.fss_shared_file_key));
            //Loader needs it when to make a CursorLoader object after rotating
            mSymbol = savedInstanceState.getString(getString(R.string.symbol_key));
            //  getLoaderManager().restartLoader(STOCK_LOADER,null,this);
            //restore the selected button and colour it
            mSelectedButtonId = savedInstanceState.getInt(getString(R.string.button_id_key));
            coordinateButtonsBackground(mSelectedButtonId);


            mChartMode = getmChartModeParcelable(savedInstanceState);
            if (mChartMode == CHART_MODE_1D) {
                wrapperObserverTimestamp1 = new WrapperObserverTimestamp(CHART_MODE_1D);
                subscription = MyApplication.mObservableDays.subscribe(wrapperObserverTimestamp1);

            } else if (mChartMode == CHART_MODE_7D) {
                wrapperObserverTimestamp2 = new WrapperObserverTimestamp(CHART_MODE_7D);
                subscription = MyApplication.mObservableDays.subscribe(wrapperObserverTimestamp2);

            } else if (mChartMode == CHART_MODE_1M) {
                wrapperObserverDate1 = new WrapperObserverDate(CHART_MODE_1M);
                subscription = MyApplication.mObservableMonths.subscribe(wrapperObserverDate1);

            } else if (mChartMode == CHART_MODE_6M) {
                wrapperObserverDate2 = new WrapperObserverDate(CHART_MODE_6M);
                subscription = MyApplication.mObservableMonths.subscribe(wrapperObserverDate2);

            } else if (mChartMode == CHART_MODE_2Y) {
                wrapperObserverDate3 = new WrapperObserverDate(CHART_MODE_2Y);
                subscription = MyApplication.mObservableMonths.subscribe(wrapperObserverDate3);

            } else if (mChartMode == CHART_MODE_5Y) {
                wrapperObserverDate4 = new WrapperObserverDate(CHART_MODE_5Y);
                subscription = MyApplication.mObservableMonths.subscribe(wrapperObserverDate4);

            } else if (mChartMode == CHART_MODE_MAX) {
                wrapperObserverDate5 = new WrapperObserverDate(CHART_MODE_MAX);
                subscription = MyApplication.mObservableMonths.subscribe(wrapperObserverDate5);

            }
            //after the chart is redrawn, set highlights
            ArrayList<Integer> highlights = savedInstanceState.getIntegerArrayList(getString(R.string.highlighs_key));
            if (highlights != null) {
                mChart.highlightValue(highlights.get(0), highlights.get(1));
            }

        } else {
            mSharedFileName = String.valueOf(System.currentTimeMillis()) + FILE_EXTENSION;
            Bundle bundle = getArguments();

            if (bundle != null) {
                mSymbol = bundle.getString(getString(R.string.symbol_key));
                mCompanyName = bundle.getString(getString(R.string.company_name_key));
                if (Utilities.isNetworkAvailable(getActivity())) {
                    Intent intentLongInfo = new Intent(getActivity(), StockSummaryService.class);
                    intentLongInfo.putExtras(bundle);
                    getActivity().startService(intentLongInfo);

                    Intent intentShortInfo = new Intent(getActivity(), IndexEtfOrShortInfoSummaryService.class);
                    Bundle bundle1=new Bundle();
                    bundle1.putString(getString(R.string.ticker_bundle_key),mSymbol);
                    intentShortInfo.putExtras(bundle1);
                    getActivity().startService(intentShortInfo);
                }

            }


            loadData();
        }

        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (getActivity() instanceof DetailStockActivity) {

            inflater.inflate(R.menu.summary_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.action_share);
            prepareShareActionProvider(menuItem);

            mFavoriteMenuItem = menu.findItem(R.id.action_favorite);


            final Drawable favoriteIcon;
            if (Utilities.isSecurityInFavorites(getActivity(), mSymbol)) {
                mIsInFavorite = true;
                favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full);
            } else {
                mIsInFavorite = false;
                favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border);

            }
            mFavoriteMenuItem.setIcon(favoriteIcon);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_favorite) {

            if (mIsInFavorite) {
                //remove from favorite
                deleteSecurityFromFavorites(mSymbol);
                Snackbar.make(mScrollView, getString(R.string.removed_security), Snackbar.LENGTH_SHORT).show();
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border));


            } else {
                //add to favorite
                addSecurityToFavorites(mSymbol);
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full));
                Snackbar.make(mScrollView, getString(R.string.added_security), Snackbar.LENGTH_SHORT).show();

            }
            mIsInFavorite = !mIsInFavorite;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        boolean visibility;
        if (mInvalidateMenu) {
            visibility = false;
            mInvalidateMenu = !mInvalidateMenu;
        } else {
            visibility = true;
        }
        if (mDisableShare) {
                menu.findItem(R.id.action_share).setVisible(false);
                menu.findItem(R.id.action_favorite).setVisible(true);
            return;
        }
        menu.findItem(R.id.action_share).setVisible(visibility);
        menu.findItem(R.id.action_favorite).setVisible(visibility);
    }

    private void loadData() {


        mProgressBar.setVisibility(View.VISIBLE);
        getHistoricalData(CHART_MODE_1D);
        coordinateButtonsBackground(R.id.btn1d);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getLoaderManager().restartLoader(STOCK_LOADER, null, this);
        getLoaderManager().restartLoader(SHORT_INFO_LOADER,null,this);
        if (getActivity() instanceof DetailStockActivity) {
            setHasOptionsMenu(true);
        }
        super.onActivityCreated(savedInstanceState);

    }

    @SuppressWarnings("ResourceType")
    private
    @ChartMode
    int getmChartModeParcelable(Bundle b) {
        return b.getInt(getString(R.string.chart_mode_key));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.symbol_key), mSymbol);
        outState.putInt(getString(R.string.chart_mode_key), mChartMode);
        outState.putInt(getString(R.string.button_id_key), mSelectedButtonId);
        outState.putString(getString(R.string.fss_shared_file_key), mSharedFileName);
        saveStateHighlight(outState);


    }

    public void saveStateHighlight(Bundle outState) {
        Highlight[] highlights = mChart.getHighlighted();
        if (highlights != null) {
            ArrayList<Integer> intHighlights = new ArrayList<Integer>();
            intHighlights.add(highlights[0].getXIndex());
            intHighlights.add(highlights[0].getDataSetIndex());
            outState.putIntegerArrayList(getString(R.string.highlighs_key), intHighlights);

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        mProgressBar.setVisibility(View.VISIBLE);
        //put this method here, so that in the Retrofit callback mSelectedButton has a valid value

        coordinateButtonsBackground(id);

        switch (id) {
            case R.id.btn1d:
                mChartMode = CHART_MODE_1D;
                getHistoricalData(CHART_MODE_1D);
                break;
            case R.id.btn1w:
                mChartMode = CHART_MODE_7D;
                getHistoricalData(CHART_MODE_7D);
                break;
            case R.id.btn1m:
                mChartMode = CHART_MODE_1M;
                getHistoricalData(CHART_MODE_1M);
                break;
            case R.id.btn6m:
                mChartMode = CHART_MODE_6M;
                getHistoricalData(CHART_MODE_6M);
                break;
            case R.id.btn2y:
                mChartMode = CHART_MODE_2Y;
                getHistoricalData(CHART_MODE_2Y);
                break;
            case R.id.btn5y:
                mChartMode = CHART_MODE_5Y;
                getHistoricalData(CHART_MODE_5Y);
                break;
            case R.id.btnMax:
                mChartMode = CHART_MODE_MAX;
                getHistoricalData(CHART_MODE_MAX);
                break;
            case R.id.btnFit:
                mProgressBar.setVisibility(View.GONE);
                mChart.fitScreen();
                mChart.invalidate();

        }

    }

    private void getHistoricalData(@ChartMode int chartMode) {
        if (!Utilities.isNetworkAvailable(getActivity())) {
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        MyApplication.IStockChart myService = MyApplication.getMyApiService();


        switch (chartMode) {
            case CHART_MODE_1D:
                MyApplication.mObservableDays = myService.get1DHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableDays.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverTimestamp1);


                break;
            case CHART_MODE_7D:
                MyApplication.mObservableDays = myService.get7DHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableDays.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverTimestamp2);


                break;
            case CHART_MODE_1M:
                MyApplication.mObservableMonths = myService.get1MHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate1);
                break;
            case CHART_MODE_6M:
                MyApplication.mObservableMonths = myService.get6MHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate2);
                break;
            case CHART_MODE_2Y:
                MyApplication.mObservableMonths = myService.get2YHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate3);
                break;
            case CHART_MODE_5Y:
                MyApplication.mObservableMonths = myService.get5YHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate4);
                break;
            case CHART_MODE_MAX:
                MyApplication.mObservableMonths = myService.getMAXHistoricalDataByStock(mSymbol).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate5);
        }


    }

    private void prepareShareActionProvider(MenuItem menuItem) {
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(Utilities.getSharedIntent(mSharedFileName));
        mShareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                updateShareActionProvider();

                Log.d(LOG_TAG, "onShareTargetSelected");
                return true;
            }
        });
    }

    private void updateShareActionProvider() {

        String shareMode = Utilities.getShareModeFromPreferences(getActivity());
        if (shareMode.equals(getString(R.string.pref_share_chart))) {
            mSourceBitmap = mChart.getChartBitmap();
        } else if (shareMode.equals(getString(R.string.pref_share_screen))) {
            View rootView = getActivity().getWindow().getDecorView().getRootView();
            mSourceBitmap = Utilities.screenShot(rootView, getActivity(), false);
        } else if (shareMode.equals(getString(R.string.pref_share_value_change))) {
            mSourceBitmap = Utilities.screenShot(mLlScreenshotValueChange, getActivity(), true);
        } else if (shareMode.equals(getString(R.string.pref_share_trading_info))) {
            mSourceBitmap = Utilities.screenShot(mCardViewTradingInfo, getActivity(), false);
        }

        Utilities.saveImageToSD(getActivity(), mSourceBitmap, mSharedFileName);

    }


    private void configCombinatedChart() {
        //  nhChart.setNoDataTextDescription("No Data available for Charts");
        mChart.setDrawMarkerViews(true);
        Paint p = mChart.getPaint(CombinedChart.PAINT_INFO);
        p.setTextSize(55);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });

        //to avoid the problem with setDrawFilled for lower versions
        //https://github.com/PhilJay/MPAndroidChart/issues/1100
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mChart.setHardwareAccelerationEnabled(false);
        }
        //mChart.setDoubleTapToZoomEnabled(true);

        // legend
        Legend legend = mChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        //Volume axis
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setStartAtZero(false);

        // Stock price axis
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        leftAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        // time axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(true);
        xAxis.enableGridDashedLine(10f, 10f, 0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


    }

    private void drawCombinatedChart(String description, @ChartMode int chartMode) {
        //remove marker view
        mChart.highlightValues(null);
        mChart.setDescription(description);
        mChart.setDescriptionTextSize(12f);
        mChart.setDescriptionColor(Color.BLACK);
        mChart.setHighlightPerTapEnabled(true);

        boolean rtl = getResources().getBoolean(R.bool.is_right_to_left);
        List<Float> yValuesLinearReversed = null;
        //if rtl =true reverse the labels
        if (rtl) {
            List<String> xLabelReversed = new ArrayList<String>();
            for (int i = 0; i < xLabels.size(); i++) {
                xLabelReversed.add(xLabels.get((xLabels.size() - 1) - i));
            }
            xLabels = xLabelReversed;

            yValuesLinearReversed = new ArrayList<Float>();
            for (int i = 0; i < yValuesLinear.size(); i++) {
                yValuesLinearReversed.add(yValuesLinear.get((yValuesLinear.size() - 1) - i));
            }
        }

        //in order to work every time it draws we set the MarkerView here
        CustomSecurityMarkerView customSecurityMarkerView = new CustomSecurityMarkerView(getActivity(),
                R.layout.custom_security_markerview,
                xLabels,
                rtl ? yValuesLinearReversed : yValuesLinear,
                mChart, chartMode);
        mChart.setMarkerView(customSecurityMarkerView);

        CombinedData combinedData = new CombinedData(xLabels);
        combinedData.setData(generateBardata());
        combinedData.setData(generateLineData(chartMode));
        mChart.setData(combinedData);
        Handler handler = new Handler();
        //animations
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sp.getBoolean(getString(R.string.pref_animation_key),false)){
//            handler.postDelayed(new Runnable() {
//                public void run() {
            mChart.animateX(getActivity().getResources().getInteger(R.integer.animation_duration),
                    Easing.EasingOption.EaseInOutQuad );
//                }
//            }, 100);

        }
        mChart.invalidate();

        if (!(getActivity() instanceof DetailStockActivity)) {
            mChart.setVisibility(View.VISIBLE);
        }



    }


    private BarData generateBardata() {
        BarData bd = new BarData();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        //rtl mode
        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            int cont = 0;
            for (int i = yValuesBar.size() - 1; i >= 0; i--) {
                entries.add(new BarEntry(yValuesBar.get(i), cont));
                cont++;
            }

            //ltr mode
        } else {
            for (int i = 0; i < yValuesBar.size(); i++) {
                entries.add(new BarEntry(yValuesBar.get(i), i));

            }
        }
        BarDataSet barDataSet = new BarDataSet(entries, getString(R.string.volume));
        barDataSet.setColor(ContextCompat.getColor(getActivity(), R.color.gray400));
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barDataSet.setHighlightEnabled(false);
        bd.addDataSet(barDataSet);


        return bd;
    }

    private LineData generateLineData(@ChartMode int chartMode) {

        ArrayList<Entry> valsTicker = new ArrayList<Entry>();
        ArrayList<Entry> valsOpen = new ArrayList<Entry>();
        Entry entryTicker;
        Entry entryOpen;
        //rtl mode
        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            int cont = 0;
            for (int i = yValuesLinear.size() - 1; i >= 0; i--) {
                entryTicker = new Entry(yValuesLinear.get(i), cont);
                valsTicker.add(entryTicker);
                if (chartMode == CHART_MODE_1D) {
                    entryOpen = new Entry(yValuesLinear.get(0), cont);
                    valsOpen.add(entryOpen);
                }
                cont++;
            }
            //ltr mode
        } else {
            for (int i = 0; i < yValuesLinear.size(); i++) {
                entryTicker = new Entry(yValuesLinear.get(i), i);
                valsTicker.add(entryTicker);
                if (chartMode == CHART_MODE_1D) {
                    entryOpen = new Entry(yValuesLinear.get(0), i);
                    valsOpen.add(entryOpen);
                }
            }
        }
        LineDataSet lineDataSetTicker = new LineDataSet(valsTicker, getString(R.string.ticker));
        lineDataSetTicker.setDrawCircleHole(false);
        lineDataSetTicker.setDrawFilled(true);//dibuja relleno hasta eje x
        lineDataSetTicker.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorAccent)); //color del relleno
        lineDataSetTicker.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent)); //color de la linea? si
        lineDataSetTicker.setCircleSize(0.f);
        lineDataSetTicker.setValueTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
        lineDataSetTicker.setLabel(mSymbol);
        //lineDataSetTicker.setDrawHighlightIndicators();
        lineDataSetTicker.setHighlightEnabled(true);
        lineDataSetTicker.setHighLightColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        lineDataSetTicker.setHighlightLineWidth(1f);
        LineData d = new LineData();
        d.addDataSet(lineDataSetTicker);
        if (chartMode == CHART_MODE_1D) {

            LineDataSet lineDataSetOpen = new LineDataSet(valsOpen, getString(R.string.open));
            lineDataSetOpen.setDrawCircleHole(false);
            lineDataSetOpen.setDrawFilled(false);//dibuja relleno hasta eje x
            lineDataSetOpen.setColor(ContextCompat.getColor(getActivity(), R.color.indigo_500));
            lineDataSetOpen.setCircleSize(0.f);
            lineDataSetOpen.setDrawValues(false);
            d.addDataSet(lineDataSetOpen);
        }


        return d;
    }


    private void bindView(Cursor quote) {

        if(quote==null) return;
        //The data come in English format. It has to be localized.
        //company name
        //bindViewShortInfo mTxtCompanyName.setText(quote.getString(COL_COMPANY_NAME));
        mChart.setContentDescription(getString(R.string.fss_talkback_chart));
        //stock exchange
        String stock_exchange = quote.getString(COL_STOCK_EXCHANGE);
        mTxtMarket.setText(Utilities.stockExchangeMapper(stock_exchange));

        //bindViewShortInfo
        //mTxtSymbol.setText(quote.getString(COL_SYMBOL));

        //bindViewShortInfo
        //last trade price
        //DecimalFormat: symbol '#'(digit), ','(group separator) and '.'(decimal separator) all are localized
        //float price = quote.getFloat(COL_LAST_TRADE_PRICE);
        //mTxtLastTradePrice.setText(quote.isNull(COL_LAST_TRADE_PRICE) ? getString(R.string.na) : new DecimalFormat(getString(R.string.decimal_format_price)).format(price));

        //bindViewShortInfo
        //icon
        // Utilities.setUpDownIcon(ivLastTradePrice, (double) quote.getFloat(COL_CHANGE), getActivity());

        //bindViewShortInfo
        //last trade date
        //mTxtLastTradeDate.setText(Utilities.
        //       formatLastTradeDateSummary(quote.getString(COL_LAST_TRADE_DATE), quote.getString(COL_LAST_TRADE_TIME)));

        //bindViewShortInfo
        //change and percent change
//        String change_percent = quote.getString(COL_CHANGE_PERCENT);
//        if (change_percent != null) {
//            change_percent = change_percent.replace(getString(R.string.percent_string), getString(R.string.empty_string));
//        }
//        float change = quote.getFloat(COL_CHANGE);
//
//        change_percent = Utilities.localizePercentWithPrefixValue(getActivity(), change_percent);
//        String change_localized;
//        change_localized = Utilities.localizeDecimalValue(change);
//        if (quote.isNull(COL_CHANGE)) {
//            mTxtChange.setText(getString(R.string.na));
//        } else {
//            Utilities.formatChangeSummary(getActivity(),
//                    mTxtChange,
//                    change_localized,
//                    change_percent,
//                    change);
//        }


        //market cap
        String market_cap = quote.getString(COL_MARKET_CAP);
        market_cap = Utilities.localizeMarket_Cap(getActivity(), market_cap);
        mTxtMarketCap.setText(market_cap);


        //days range
        String days_range = quote.getString(COL_DAYS_RANGE);
        mTxtDaysRange.setText(Utilities.localizeDays_Range(getActivity(), days_range));

        //previous close
        mTxtPreviousClose.setText(quote.isNull(COL_PREVIOUS_CLOSE) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_PREVIOUS_CLOSE)));
        mTxtOpen.setText(quote.isNull(COL_OPEN) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_OPEN)));
        mTxtAsk.setText(quote.isNull(COL_ASK) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_ASK)));
        mTxtBid.setText(quote.isNull(COL_BID) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_BID)));
        //daily volume
        long volume = quote.getInt(COL_VOLUME);
        mTxtVolume.setText(quote.isNull(COL_VOLUME) ? getString(R.string.na) : new DecimalFormat(getString(R.string.decimal_format_volume)).format(volume));
        //daily avg volume
        float avg_daily_volume = quote.getFloat(COL_AVG_DAILY_VOLUME);
        mTxtAverageDailyVolume.setText(quote.isNull(COL_AVG_DAILY_VOLUME) ? getString(R.string.na) : new DecimalFormat(getString(R.string.decimal_format_volume)).format(avg_daily_volume));

        mTxtEarningsShare.setText(quote.isNull(COL_EARNINGS_SHARE) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_EARNINGS_SHARE)));
        mTxtEPSEstimateCurrentYear.setText(quote.isNull(COL_EPS_ESTIMATE_CURRENT_YEAR) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_EPS_ESTIMATE_CURRENT_YEAR)));
        mTxtEPSEstimateNextYear.setText(quote.isNull(COL_EPS_ESTIMATE_NEXT_YEAR) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_EPS_ESTIMATE_NEXT_YEAR)));
        mTxtEPSEstimateNextQuarter.setText(quote.isNull(COL_EPS_ESTIMATE_NEXT_QUARTER) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_EPS_ESTIMATE_NEXT_QUARTER)));
        mTxtYearLow.setText(quote.isNull(COL_YEAR_LOW) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_YEAR_LOW)));
        mTxtYearHigh.setText(quote.isNull(COL_YEAR_HIGH) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_YEAR_HIGH)));

        //change from year low
        mTxtChangeFromYearLow.setText(quote.isNull(COL_CHANGE_FROM_YEAR_LOW) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_CHANGE_FROM_YEAR_LOW)));
        //percent change from year low
        float percent_change_from_year_low = quote.getFloat(COL_PERCENT_CHANGE_FROM_YEAR_LOW);
        mTxtPercentChangeFromYearLow.setText(quote.isNull(COL_PERCENT_CHANGE_FROM_YEAR_LOW) ? getString(R.string.na) : Utilities.localizePercentValue(getActivity(), percent_change_from_year_low));
        mTxtChangeFromYearHigh.setText(quote.isNull(COL_CHANGE_FROM_YEAR_HIGH) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_CHANGE_FROM_YEAR_HIGH)));
        //percet .... high
        float percent_change_from_year_high = quote.getFloat(COL_PERCENT_CHANGE_FROM_YEAR_HIGH);
        mTxtPercebtChangeFromYearHigh.setText(quote.isNull(COL_PERCENT_CHANGE_FROM_YEAR_HIGH) ? getString(R.string.na) : Utilities.localizePercentValue(getActivity(), percent_change_from_year_high));

        mTxtPERatio.setText(quote.isNull(COL_PER_RATIO) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_PER_RATIO)));
        mTxtPEGRatio.setText(quote.isNull(COL_PEG_RATIO) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_PEG_RATIO)));
        mTxtOneYearTargetPrice.setText(quote.isNull(COL_ONE_YEAR_TARGET_PRICE) ? getString(R.string.na) : Utilities.localizeDecimalValue(quote.getFloat(COL_ONE_YEAR_TARGET_PRICE)));


    }

    private void bindViewShortInfo(Cursor data){

        if(data==null) return;

        //company name
        mTxtCompanyName.setText(data.getString(COL_SHORT_INFO_STOCK_NAME));
        //symbol
        mTxtSymbol.setText(mSymbol);

        //price
        float price = data.isNull(COL_SHORT_INFO_PRICE) ? 0 : data.getFloat(COL_SHORT_INFO_PRICE);
        mTxtLastTradePrice.setText(new DecimalFormat(getString(R.string.format_decimal_value)).format(price));

        //change and percent change
        String change_percent = data.getString(COL_SHORT_INFO_CHANGE_PERCENT);

        float change = data.getFloat(COL_SHORT_INFO_CHANGE);

        change_percent = Utilities.localizeDecimalValue(Float.parseFloat(change_percent));
        String change_localized;
        change_localized = Utilities.localizeDecimalValue(change);
        if (data.isNull(COL_SHORT_INFO_CHANGE)) {
            mTxtChange.setText(getString(R.string.na));
        } else {
            Utilities.formatChangeSummary(getActivity(),
                    mTxtChange,
                    change_localized,
                    change_percent,
                    change);
        }

        //icon
        Utilities.setUpDownIcon(ivLastTradePrice, (double) change, getActivity());

        //date
        //date
        //getDateTimeInstance comes with the default locale of the device
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        Date date = new Date((long) data.getInt(COL_SHORT_INFO_TIMESTAMP) * 1000);
        mTxtLastTradeDate.setText(formatter.format(date));
    }

    private void resetCombinatedChartState() {
        yValuesBar.clear();
        yValuesLinear.clear();
        xLabels.clear();
        mChart.fitScreen();

    }

    private void fillCombinatedChartTimestampData(List<HistoricalDataResponseTimestamp.SeriesEntity> serie,
                                                  @ChartMode final int chartMode) {
        for (int i = 0; i < serie.size(); i++) {
            yValuesLinear.add((float) serie.get(i).getClose());
            xLabels.add(Utilities.formatXLabel(chartMode, String.valueOf(serie.get(i).getTimestamp())));
            yValuesBar.add(serie.get(i).getVolume());
        }


    }

    private void fillCombinatedChartDateData(List<HistoricalDataResponseDate.SeriesEntity> serie,
                                             @ChartMode final int chartMode) {
        for (int i = 0; i < serie.size(); i++) {
            yValuesLinear.add((float) serie.get(i).getClose());
            xLabels.add(Utilities.formatXLabel(chartMode, String.valueOf(serie.get(i).getDate())));
            yValuesBar.add(serie.get(i).getVolume());

        }


    }


    private Cursor getStockFromDb(String Symbol) {
        String[] projection = new String[]{
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_TICKER,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_NAME,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.PRICE,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE_PERCENT,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_HIGHT,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_LOW,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.TIMESTAMP
        };
        return getContext().getContentResolver().query(
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.buildIndexDetailWithSymbol(Symbol),
                projection,
                CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_TICKER + "=?",
                new String[]{Symbol},
                null);
    }

    private void addSecurityToFavorites(String symbolString) {
        Cursor c = getStockFromDb(symbolString);
        ContentValues contentValues = new ContentValues();
        if (!c.moveToNext()) return;
        String symbol = c.getString(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_TICKER));
        String companyName = c.getString(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_NAME));
        //String stockMarket = c.getString(c.getColumnIndex(CapstoneContract.StockDetailEntity.STOCK_EXCHANGE));
        float price = c.getFloat(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.PRICE));
        float change = c.getFloat(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE));
        float changePercent = c.getFloat(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.CHANGE_PERCENT));
        float hight=c.getFloat(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_HIGHT));
        float low=c.getFloat(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.DAY_LOW));
        //timestamp in IndexEtf.. is in real and in favorites is in string, a conversion is needed
        long ts=c.getLong(c.getColumnIndex(CapstoneContract.IndexEtfOrShortInfoDetailEntity.TIMESTAMP));
        String tsString=String.valueOf(ts);

        contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_TICKER, symbol);
        contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_NAME, companyName);
        contentValues.put(CapstoneContract.FavoritesEntity.MARKET, getString(R.string.equity).toUpperCase());
        contentValues.put(CapstoneContract.FavoritesEntity.VALUE, price);
        contentValues.put(CapstoneContract.FavoritesEntity.CHANGE, change);
        contentValues.put(CapstoneContract.FavoritesEntity.CHANGE_PERCENT, changePercent);
        //test this for null
//        String dayRange = c.getString(c.getColumnIndex(CapstoneContract.StockDetailEntity.DAYS_RANGE));
//        if (dayRange != null) {
//            String[] maxminPrice = dayRange.split(getString(R.string.day_range_split_string));
//            contentValues.put(CapstoneContract.FavoritesEntity.MIN, maxminPrice[0]);
//            contentValues.put(CapstoneContract.FavoritesEntity.MAX, maxminPrice[1]);
//        } else {
//            contentValues.put(CapstoneContract.FavoritesEntity.MIN, (String) null);
//            contentValues.put(CapstoneContract.FavoritesEntity.MAX, (String) null);
//        }
        contentValues.put(CapstoneContract.FavoritesEntity.MAX,hight);
        contentValues.put(CapstoneContract.FavoritesEntity.MIN,low);
        contentValues.put(CapstoneContract.FavoritesEntity.TRADE_DATE,tsString);
        c.close();
        getContext().getContentResolver().
                insert(CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(symbol), contentValues);

    }

    public boolean deleteSecurityFromFavorites(String mSymbol) {
        Uri uri = CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(mSymbol);
        int row_deleted = getContext().getContentResolver().delete(uri, CapstoneContract.FavoritesEntity.COMPANY_TICKER + "=?",
                new String[]{mSymbol});
        return (row_deleted == 1);
    }

    /**
     * @param id
     */

    private void coordinateButtonsBackground(int id) {
        Button other;
        for (int i = 0; i < mArrayButtons.length; i++) {

            if (mArrayButtons[i] == id) {
                Button selected = (Button) mView.findViewById(mArrayButtons[i]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    selected.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.selector_focusable_button_selected));
                } else {
                    selected.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_focusable_button_selected));
                }
                mSelectedButtonId = mArrayButtons[i];

            } else {
                other = (Button) mView.findViewById(mArrayButtons[i]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    other.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.selector_focusable_button_others));
                } else {
                    other.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selector_focusable_button_others));
                }

            }

        }


    }

    /**
     * there is a case where the result code of the request is 200 but the app doesn't handle
     * that situation. Check run transition must be executed in else of
     * if(resp!=null && resp.getSeries()!=null && resp.getMeta()!=null) {
     * finance_charts_json_callback(
     * {
     * meta: {
     * uri: "/instrument/1.0/orn.mx/chartdata;type=quote;range=1d/json",
     * ticker: "orn.mx"
     * },
     * errorid: 1,
     * message: "No data available for given Time Range - symbol: orn.mx representation: quoterange: 1d start: -36780 end: 597233245",
     * alternate_ranges: [
     * "",
     * "1m",
     * "3m",
     * "6m",
     * "1y",
     * "2y",
     * "5y"
     * ]
     * }
     * )
     */

    private class WrapperObserverTimestamp implements Observer<Response<HistoricalDataResponseTimestamp>> {
        @ChartMode
        int chartMode;

        public WrapperObserverTimestamp(@ChartMode int chartMode) {
            super();
            this.chartMode = chartMode;
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i(LOG_TAG, "On error  WrapperObserbable days:" + e.getMessage());
            mProgressBar.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);

        }

        @Override
        public void onNext(Response<HistoricalDataResponseTimestamp> response) {
            //there is a case(e.g:NA7.BE) trying to load graphic data, the response here is ok code=200 but in web there is an error
            Log.i(LOG_TAG, "On next WrapperObserbable Days:" + response.raw());
            mProgressBar.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);
            if (response.isSuccess()) {
                HistoricalDataResponseTimestamp resp = response.body();
                if (resp != null && resp.getSeries() != null && resp.getMeta() != null) {
                    resetCombinatedChartState();
                    fillCombinatedChartTimestampData(resp.getSeries(), chartMode);
                    String chartDescription = Utilities.formatChartDescription(chartMode, resp.getMeta().getTicker());
                    drawCombinatedChart(chartDescription, chartMode);

                }

            }
        }
    }

    private class WrapperObserverDate implements Observer<Response<HistoricalDataResponseDate>> {
        @ChartMode
        int chartMode;

        public WrapperObserverDate(@ChartMode int chartMode) {
            super();
            this.chartMode = chartMode;

        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i(LOG_TAG, "On error WrapperObserbable months:" + e.getMessage());
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onNext(Response<HistoricalDataResponseDate> response) {
            //there is a case(e.g:NA7.BE) trying to load graphic data, the response here is ok code=200 but in web there is an error
            Log.i(LOG_TAG, "On next WrapperObserbable months:" + response.raw());
            mProgressBar.setVisibility(View.GONE);
            if (response.isSuccess()) {
                HistoricalDataResponseDate resp = response.body();
                if (resp != null && resp.getSeries() != null && resp.getMeta() != null) {
                    resetCombinatedChartState();
                    fillCombinatedChartDateData(resp.getSeries(), chartMode);
                    String chartDescription = Utilities.formatChartDescription(chartMode, resp.getMeta().getTicker());
                    drawCombinatedChart(chartDescription, chartMode);


                }
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "OnDestroyView FragmentStockSummary");
        if (subscription != null) subscription.unsubscribe();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==STOCK_LOADER) {
            Uri uri = CapstoneContract.StockDetailEntity.buildStockDetailWithSymbol(mSymbol);
            String selection = CapstoneContract.StockDetailEntity.SYMBOL + "=?";
            return new CursorLoader(getActivity(),
                    uri,
                    null, //proyection
                    selection, //selection
                    new String[]{mSymbol}, //selection args
                    null //sortorder
            );
        } else if(id==SHORT_INFO_LOADER) {
            Uri uri=CapstoneContract.IndexEtfOrShortInfoDetailEntity.buildIndexDetailWithSymbol(mSymbol);
            String selection=CapstoneContract.IndexEtfOrShortInfoDetailEntity.INDEX_TICKER + "=?";
            return new CursorLoader(getActivity(),
                    uri,
                    null,
                    selection,
                    new String[]{mSymbol},
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && !data.moveToFirst()) {
            //disable menu since there is no data
            mInvalidateMenu = true;
            getActivity().invalidateOptionsMenu();
            if (!Utilities.isNetworkAvailable(getActivity())) {
                Snackbar.make(mScrollView, getString(R.string.no_internet_conextion), Snackbar.LENGTH_LONG)
                        .setDuration(Snackbar.LENGTH_INDEFINITE)
                        .show();

                mTxtCompanyName.setText(getString(R.string.na_company_name));
                mTxtMarket.setText(getString(R.string.na_market));
                mTxtSymbol.setText(mSymbol);
                mTxtLastTradePrice.setText(getString(R.string.na_price));
                mTxtLastTradeDate.setText(getString(R.string.na_date));
                mTxtChange.setText(getString(R.string.na_change));

            }
            return;
        }
        mInvalidateMenu = false;
        //this will call onPrepareOptionsMenu
        getActivity().invalidateOptionsMenu();
        int id=loader.getId();
        if (id == STOCK_LOADER) {
            bindView(data);
        } else if(id==SHORT_INFO_LOADER) {
            bindViewShortInfo(data);
        }


        if (!(getActivity() instanceof DetailStockActivity)) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarRightPane);
            Menu menu;
            if (toolbar != null) {
                toolbar.setTitle(mSymbol + " details");
                menu = toolbar.getMenu();
                if (menu != null) menu.clear();
                toolbar.inflateMenu(R.menu.summary_menu);
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        handleNavigationPress(item.getItemId());
                        return true;
                    }
                });
                menu = toolbar.getMenu();
                initMenuItems(menu);
                MenuItem menuItem = menu.findItem(R.id.action_share);
                prepareShareActionProvider(menuItem);

            }
        }
        askForPermission();

    }



    private void initMenuItems(Menu menu) {

        mFavoriteMenuItem = menu.findItem(R.id.action_favorite);

        final Drawable favoriteIcon;
        if (Utilities.isSecurityInFavorites(getActivity(), mSymbol)) {
            mIsInFavorite = true;
            favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full);
        } else {
            mIsInFavorite = false;
            favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border);

        }
        mFavoriteMenuItem.setIcon(favoriteIcon);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void handleNavigationPress(int id) {
        if (id == R.id.action_favorite) {

            if (mIsInFavorite) {
                //remove from favorite
                deleteSecurityFromFavorites(mSymbol);
                Snackbar.make(mScrollView, getString(R.string.removed_security), Snackbar.LENGTH_SHORT).show();
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border));


            } else {
                //add to favorite
                addSecurityToFavorites(mSymbol);
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full));
                Snackbar.make(mScrollView, getString(R.string.added_security), Snackbar.LENGTH_SHORT).show();

            }
            mIsInFavorite = !mIsInFavorite;
        }


    }



    private void askForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionsCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionsCheck == PackageManager.PERMISSION_DENIED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), "This permission is needed to share data with other applications",
                            Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        8);


            }
            // api<23
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 8:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_DENIED)) {

                    if(!Utilities.hasTwoPane(getActivity())) {
                        mDisableShare = true;
                        getActivity().invalidateOptionsMenu();
                    } else {
                        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarRightPane);
                        if(toolbar!=null){
                            mDisableShare=true;
                            Menu menu=toolbar.getMenu();
                            menu.findItem(R.id.action_share).setVisible(false);
                        }
                    }


                }


        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "FragmentStockSummary on Destroy");
        Utilities.deleteFileFromSD(mSharedFileName);
        super.onDestroy();
    }
}
