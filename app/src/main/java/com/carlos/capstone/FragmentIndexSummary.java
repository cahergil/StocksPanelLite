package com.carlos.capstone;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.capstone.customcomponents.AutoResizeTextView;
import com.carlos.capstone.customcomponents.CustomSecurityMarkerView;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.models.HistoricalDataResponseDate;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.services.IndexSummaryService;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
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
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Carlos on 09/02/2016.
 */
public class FragmentIndexSummary extends Fragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, OnChartGestureListener {
    private static final String LOG_TAG = FragmentIndexSummary.class.getSimpleName();
    private Button mButton1d, mButton1w, mButton1m, mButton6m, mButton2y, mButton5y, mButtonMax, mButtonFit;
    private TextView tvTickerName, tvTime, tvDayHigh, tvDayLow, tvYearHigh, tvYearLow, tvVolume;
    private AutoResizeTextView tvPrice, tvChange, tvPercentChange;
    private ImageView ivIcon;
    private int[] mArrayButtons;
    private int mSelectedButtonId;
    private List<Long> yValuesBar = new ArrayList<Long>();
    private List<Float> yValuesLinear = new ArrayList<Float>();
    private List<String> xLabels = new ArrayList<String>();

    private CombinedChart mChart;
    private ProgressBar mProgressBar;

    private View mView;
    private String mTicker, mIndexName;
    private static final int INDEX_LOADER = 1;
    private ScrollView mScrollView;
    private boolean mDisableShare;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHART_MODE_1D, CHART_MODE_7D, CHART_MODE_1M, CHART_MODE_6M, CHART_MODE_2Y, CHART_MODE_5Y,
            CHART_MODE_MAX})
    public @interface ChartMode {
    }

    public static final int CHART_MODE_1D = 0;
    public static final int CHART_MODE_7D = 1;
    public static final int CHART_MODE_1M = 2;
    public static final int CHART_MODE_6M = 3;
    public static final int CHART_MODE_2Y = 4;
    public static final int CHART_MODE_5Y = 5;
    public static final int CHART_MODE_MAX = 6;

    public static final int COL_INDEX_TICKER = 1;
    public static final int COL_INDEX_NAME = 2;
    public static final int COL_PRICE = 3;
    public static final int COL_CHANGE = 4;
    public static final int COL_CHANGE_PERCENT = 5;
    public static final int COL_TIMESTAMP = 6;
    public static final int COL_UTCTIME = 7;
    public static final int COL_DAY_HIGH = 8;
    public static final int COL_DAY_LOW = 9;
    public static final int COL_YEAR_HIGH = 10;
    public static final int COL_YEAR_LOW = 11;
    public static final int COL_VOLUME = 12;


    private
    @ChartMode
    int mChartMode;
    private Observable<Response<HistoricalDataResponseTimestamp>> mObservableDays;
    private Observable<Response<HistoricalDataResponseDate>> mObservableMonths;
    WrapperObserverDate wrapperObserverDate5 = new WrapperObserverDate(CHART_MODE_MAX);
    WrapperObserverDate wrapperObserverDate4 = new WrapperObserverDate(CHART_MODE_5Y);
    WrapperObserverDate wrapperObserverDate3 = new WrapperObserverDate(CHART_MODE_2Y);
    WrapperObserverDate wrapperObserverDate2 = new WrapperObserverDate(CHART_MODE_6M);
    WrapperObserverDate wrapperObserverDate1 = new WrapperObserverDate(CHART_MODE_1M);
    WrapperObserverTimestamp wrapperObserverTimestamp1 = new WrapperObserverTimestamp(CHART_MODE_1D);
    WrapperObserverTimestamp wrapperObserverTimestamp2 = new WrapperObserverTimestamp(CHART_MODE_7D);
    private Subscription subscription;
    private MenuItem mFavoriteMenuItem;
    private boolean mIsInFavorite = false;
    private boolean mInvalidateMenu = false;
    private boolean mFromWidget = false;

    private ShareActionProvider mShareActionProvider;
    private String mSharedFileName = "";
    private static final String FILE_EXTENSION = ".jpg";
    private Bitmap mSourceBitmap;
    private CardView mCardViewScreenshotValueChange;
    private CardView mCardViewTradingInfo;


    //implement interface of mpAndroidchart about gestures
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

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

    public FragmentIndexSummary() {
        //setRetainInstance(true) can causes memory leak http://stackoverflow.com/questions/13421945/retained-fragments-with-ui-and-memory-leaks
        //to avoid leaks, store observers in Application class class
        //  setRetainInstance(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG,"OnCreateOptionsMenu");

        //one-pane mode
        if (getActivity() instanceof DetailIndexActivity) {

            inflater.inflate(R.menu.summary_menu, menu);
            mFavoriteMenuItem = menu.findItem(R.id.action_favorite);
            MenuItem menuItem = menu.findItem(R.id.action_share);
            prepareShareActionProvider(menuItem);



            final Drawable favoriteIcon;
            if (Utilities.isSecurityInFavorites(getActivity(), mTicker)) {
                mIsInFavorite = true;
                favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full);
            } else {
                mIsInFavorite = false;
                favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border);

            }

            mFavoriteMenuItem.setIcon(favoriteIcon);

            super.onCreateOptionsMenu(menu, inflater);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        handleNavigationPress(item.getItemId());

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

    @SuppressWarnings("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_index_summary, container, false);
        Log.d(LOG_TAG, "OnCreateView-FragmentIndexSummary");
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

        tvTickerName = (TextView) mView.findViewById(R.id.tickerName);
        tvTime = (TextView) mView.findViewById(R.id.tvtime);
        tvPrice = (AutoResizeTextView) mView.findViewById(R.id.price);
        tvChange = (AutoResizeTextView) mView.findViewById(R.id.change);
        tvPercentChange = (AutoResizeTextView) mView.findViewById(R.id.percentChange);
        ivIcon = (ImageView) mView.findViewById(R.id.ivIcon);
        tvDayHigh = (TextView) mView.findViewById(R.id.day_high);
        tvDayLow = (TextView) mView.findViewById(R.id.day_low);
        tvYearHigh = (TextView) mView.findViewById(R.id.year_high);
        tvYearLow = (TextView) mView.findViewById(R.id.year_low);
        tvVolume = (TextView) mView.findViewById(R.id.volume);

        mChart = (CombinedChart) mView.findViewById(R.id.chart);
        mChart.setOnChartGestureListener(this);

        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        mCardViewScreenshotValueChange = (CardView) mView.findViewById(R.id.cardViewTop);
        mCardViewTradingInfo = (CardView) mView.findViewById(R.id.cardRanges);

        //stackoverflow.com/questions/2020882/how-to-change-progress-bars-progress-color-in-android
        //mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(
        //        getActivity().getApplicationContext(),R.color.red_700), PorterDuff.Mode.MULTIPLY);

        mArrayButtons = new int[]{R.id.btn1d, R.id.btn1w, R.id.btn1m, R.id.btn6m, R.id.btn2y, R.id.btn5y, R.id.btnMax, R.id.btnFit};
        mScrollView = (ScrollView) mView.findViewById(R.id.verticalScrollview);

        if (Utilities.hasTwoPane(getActivity())) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                    new IntentFilter(getString(R.string.index_tab_summary_intent_filter)));

        }


        configCombinatedChart();
        //HANDLING ROTATION
        if (savedInstanceState != null) {

            mSharedFileName = savedInstanceState.getString(getString(R.string.fss_shared_file_key));



            //Retrofit needs the ticker when rotating again
            mTicker = savedInstanceState.getString(getString(R.string.ticker_key));

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
            Intent intent = new Intent(getActivity(), IndexSummaryService.class);

            if (bundle != null) {
                mTicker = bundle.getString(getString(R.string.ticker_bundle_key));
                mFromWidget = bundle.getBoolean(getString(R.string.stock_from_widget_key));
                if (Utilities.isNetworkAvailable(getActivity())) {
                    intent.putExtras(bundle);
                    getActivity().startService(intent);
//                } else {
//                    checkRunTransition();
//                }
                }
            }

            loadData();
            //if a put the following code here there is a flicker in the Transition
//            if(!Utilities.isDeviceTablet(getActivity())) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                  //  setTransitionListenerV21();
//                  //  scheduleStartPostponedTransition(mScrollView);
//
//                }
//            }
        }


        return mView;
    }


    @SuppressWarnings("ResourceType")
    private void loadData() {

        mProgressBar.setVisibility(View.VISIBLE);
        getHistoricalData(CHART_MODE_1D);
        coordinateButtonsBackground(R.id.btn1d);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getLoaderManager().restartLoader(INDEX_LOADER, null, this);
        if (getActivity() instanceof DetailIndexActivity) {
            setHasOptionsMenu(true);
        }
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.ticker_key), mTicker);
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
            mSourceBitmap = Utilities.screenShot(mCardViewScreenshotValueChange, getActivity(), true);
        } else if (shareMode.equals(getString(R.string.pref_share_trading_info))) {
            mSourceBitmap = Utilities.screenShot(mCardViewTradingInfo, getActivity(), false);
        }
        Utilities.saveImageToSD(getActivity(), mSourceBitmap, mSharedFileName);


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
                MyApplication.mObservableDays = myService.get1DHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableDays.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverTimestamp1);


                break;
            case CHART_MODE_7D:
                MyApplication.mObservableDays = myService.get7DHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableDays.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverTimestamp2);


                break;
            case CHART_MODE_1M:
                MyApplication.mObservableMonths = myService.get1MHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate1);
                break;
            case CHART_MODE_6M:
                MyApplication.mObservableMonths = myService.get6MHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate2);
                break;
            case CHART_MODE_2Y:
                MyApplication.mObservableMonths = myService.get2YHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate3);
                break;
            case CHART_MODE_5Y:
                MyApplication.mObservableMonths = myService.get5YHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate4);
                break;
            case CHART_MODE_MAX:
                MyApplication.mObservableMonths = myService.getMAXHistoricalDataByStock(mTicker).cache();
                subscription = MyApplication.mObservableMonths.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wrapperObserverDate5);

        }


    }

    private void configCombinatedChart() {

        mChart.setDrawMarkerViews(true);
        Paint p = mChart.getPaint(CombinedChart.PAINT_INFO);
        p.setTextSize(55);
        //no hace falta
        //  mChart.setNoDataTextDescription("Chart data unavailable.");
        //mChart.setDescriptionPosition(mViewPortHandler.contentTop(),Utils.calcTextHeight(mDescPaint, description));
        // mChart.setDescriptionPosition(650.0f,80.0f);

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });

        //to avoid the problem with setDrawFilled for lower versions
        //https://github.com/PhilJay/MPAndroidChart/issues/1100
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mChart.setHardwareAccelerationEnabled(false);
        }

        //animations
        // mChart.animateX(200);
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
        rightAxis.setValueFormatter(new LargeValueFormatter());

        // Stock price axis
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        leftAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

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
        // mChart.highlightValue(100,2); test
        mChart.invalidate();
        if (!(getActivity() instanceof DetailIndexActivity)) {
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
        lineDataSetTicker.setLabel(mTicker);
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


    private void fillCombinatedChartTimestampData(List<HistoricalDataResponseTimestamp.SeriesEntity> serie,
                                                  @ChartMode final int chartMode) {
        for (int i = 0; i < serie.size(); i++) {
            yValuesLinear.add((float) serie.get(i).getClose());
            xLabels.add(Utilities.formatXLabel(chartMode, String.valueOf(serie.get(i).getTimestamp())));
            yValuesBar.add((long) serie.get(i).getVolume());
        }


    }

    private void fillCombinatedChartDateData(List<HistoricalDataResponseDate.SeriesEntity> serie,
                                             @ChartMode final int chartMode) {
        for (int i = 0; i < serie.size(); i++) {
            yValuesLinear.add((float) serie.get(i).getClose());
            xLabels.add(Utilities.formatXLabel(chartMode, String.valueOf(serie.get(i).getDate())));
            yValuesBar.add((long) serie.get(i).getVolume());

        }


    }

    @SuppressWarnings("ResourceType")
    private
    @ChartMode
    int getmChartModeParcelable(Bundle b) {
        return b.getInt(getString(R.string.chart_mode_key));

    }


    private void resetCombinatedChartState() {
        yValuesBar.clear();
        yValuesLinear.clear();
        xLabels.clear();
        mChart.fitScreen();

    }


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
            mProgressBar.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);

        }

        @Override
        public void onNext(Response<HistoricalDataResponseTimestamp> response) {
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
            mProgressBar.setVisibility(View.GONE);

        }

        @Override
        public void onNext(Response<HistoricalDataResponseDate> response) {
            Log.i(LOG_TAG, "On next WrapperObserbable Month:" + response.raw());
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

    private void deleteSecurityFromFavorites(String ticker) {
        Uri uri = CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(ticker);
        int row_deleted = getContext().getContentResolver().delete(uri, CapstoneContract.FavoritesEntity.COMPANY_TICKER + "=?",
                new String[]{ticker});

    }

    private void addSecurityToFavorites() {
        Cursor c = getIndexFromDb(mTicker);
        ContentValues contentValues = new ContentValues();
        if (!c.moveToNext()) return;
        String ticker = c.getString(c.getColumnIndex(CapstoneContract.IndexDetailEntity.INDEX_TICKER));
        String indexName = c.getString(c.getColumnIndex(CapstoneContract.IndexDetailEntity.INDEX_NAME));
        String stockMarket = getString(R.string.index).toUpperCase();
        float price = c.getFloat(c.getColumnIndex(CapstoneContract.IndexDetailEntity.PRICE));
        float change = c.getFloat(c.getColumnIndex(CapstoneContract.IndexDetailEntity.CHANGE));
        float changePercent = c.getFloat(c.getColumnIndex(CapstoneContract.IndexDetailEntity.CHANGE_PERCENT));
        float max = c.getFloat(c.getColumnIndex(CapstoneContract.IndexDetailEntity.DAY_HIGHT));
        float min = c.getFloat(c.getColumnIndex(CapstoneContract.IndexDetailEntity.DAY_LOW));
        contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_TICKER, ticker);
        contentValues.put(CapstoneContract.FavoritesEntity.COMPANY_NAME, indexName);
        contentValues.put(CapstoneContract.FavoritesEntity.MARKET, stockMarket);
        contentValues.put(CapstoneContract.FavoritesEntity.VALUE, price);
        contentValues.put(CapstoneContract.FavoritesEntity.CHANGE, change);
        contentValues.put(CapstoneContract.FavoritesEntity.CHANGE_PERCENT, changePercent);
        contentValues.put(CapstoneContract.FavoritesEntity.MAX, max);
        contentValues.put(CapstoneContract.FavoritesEntity.MIN, min);
        c.close();
        getContext().getContentResolver()
                .insert(CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(ticker), contentValues);
    }

    private Cursor getIndexFromDb(String ticker) {
        String[] projection = new String[]{
                CapstoneContract.IndexDetailEntity.INDEX_TICKER,
                CapstoneContract.IndexDetailEntity.INDEX_NAME,
                CapstoneContract.IndexDetailEntity.PRICE,
                CapstoneContract.IndexDetailEntity.CHANGE,
                CapstoneContract.IndexDetailEntity.CHANGE_PERCENT,
                CapstoneContract.IndexDetailEntity.DAY_HIGHT,
                CapstoneContract.IndexDetailEntity.DAY_LOW
        };
        return getContext().getContentResolver().query(
                CapstoneContract.IndexDetailEntity.buildIndexDetailWithSymbol(ticker),
                projection,
                CapstoneContract.IndexDetailEntity.INDEX_TICKER + "=?",
                new String[]{ticker},
                null
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        if (subscription != null) subscription.unsubscribe();
        if (Utilities.hasTwoPane(getActivity())) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), CapstoneContract.IndexDetailEntity.buildIndexDetailWithSymbol(mTicker),
                null,
                CapstoneContract.IndexDetailEntity.INDEX_TICKER + "=?",
                new String[]{mTicker},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && !data.moveToFirst()) {
            //disable menu since there is no data
            mInvalidateMenu = true;
            //this will call onPrepareOptionsMenu
            getActivity().invalidateOptionsMenu();
            if (!Utilities.isNetworkAvailable(getActivity())) {
                Snackbar.make(mScrollView, getString(R.string.no_internet_conextion), Snackbar.LENGTH_LONG)
                        .setDuration(Snackbar.LENGTH_INDEFINITE)
                        .show();
                tvTickerName.setText(getString(R.string.na_index_fund_name));
                tvPrice.setText(getString(R.string.na_price));
                tvChange.setText(getString(R.string.na_change));
                tvTime.setText(getString(R.string.na_date));
            }

            return;
        }
        mInvalidateMenu = false;
        getActivity().invalidateOptionsMenu();
        bindView(data);
        if (!(getActivity() instanceof DetailIndexActivity)) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarRightPane);
            Menu menu;
            if (toolbar != null) {
                toolbar.setTitle(mTicker + " details");
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
                menu=toolbar.getMenu();
                //initMenuItems(toolbar.getMenu());
                initMenuItems(menu);
                //prepare share
                MenuItem menuItem = menu.findItem(R.id.action_share);
                //creo que no hace falta poner esta linea en el broadcastreceiver
                prepareShareActionProvider(menuItem);
            }
        }
        askForPermission();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
    public void bindView(Cursor data) {
        //ticker name
        tvTickerName.setText(data.getString(COL_INDEX_NAME).toUpperCase());
        mChart.setContentDescription(getString(R.string.fss_talkback_chart));


        //price
        float price = data.isNull(COL_PRICE) ? 0 : data.getFloat(COL_PRICE);
        tvPrice.setText(new DecimalFormat(getString(R.string.format_decimal_value)).format(price));
        //change
        float change = data.isNull(COL_CHANGE) ? 0 : data.getFloat(COL_CHANGE);
        tvChange.setText(getString(R.string.format_change, change));
        Utilities.setUpDownColors(tvChange, (double) change, getActivity());
        //change percent
        float change_percent = data.isNull(COL_CHANGE_PERCENT) ? 0 : data.getFloat(COL_CHANGE_PERCENT);
        tvPercentChange.setText(getString(R.string.format_change_percent, change_percent));
        Utilities.setUpDownColors(tvPercentChange, (double) change_percent, getActivity());

        //icon
        Utilities.setUpDownIcon(ivIcon, (double) change, getActivity());
        //date
        //getDateTimeInstance comes with the default locale of the device
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
        Date date = new Date((long) data.getInt(COL_TIMESTAMP) * 1000);
        tvTime.setText(formatter.format(date));


        DecimalFormat decimalFormat = new DecimalFormat(getString(R.string.format_decimal_value));

        tvDayHigh.setText(decimalFormat.format(data.isNull(COL_DAY_HIGH) ? 0 : data.getFloat(COL_DAY_HIGH)));
        tvDayLow.setText(decimalFormat.format(data.isNull(COL_DAY_LOW) ? 0 : data.getFloat(COL_DAY_LOW)));
        tvYearHigh.setText(decimalFormat.format(data.isNull(COL_YEAR_HIGH) ? 0 : data.getFloat(COL_YEAR_HIGH)));
        tvYearLow.setText(decimalFormat.format(data.isNull(COL_YEAR_LOW) ? 0 : data.getFloat(COL_YEAR_LOW)));
        tvVolume.setText(new DecimalFormat(getString(R.string.format_integer_value)).
                format(data.isNull(COL_VOLUME) ? 0 : data.getFloat(COL_VOLUME)));


    }

    private void initMenuItems(Menu menu) {

        mFavoriteMenuItem = menu.findItem(R.id.action_favorite);

        final Drawable favoriteIcon;
        if (Utilities.isSecurityInFavorites(getActivity(), mTicker)) {
            mIsInFavorite = true;
            favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full);
        } else {
            mIsInFavorite = false;
            favoriteIcon = Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border);

        }
        mFavoriteMenuItem.setIcon(favoriteIcon);
    }

    private void handleNavigationPress(int id) {
        if (id == R.id.action_favorite) {

            if (mIsInFavorite) {
                //remove from favorite
                deleteSecurityFromFavorites(mTicker);
                Snackbar.make(mScrollView, getString(R.string.removed_security), Snackbar.LENGTH_SHORT).show();
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_border));


            } else {
                //add to favorite
                addSecurityToFavorites();
                mFavoriteMenuItem.setIcon(Utilities.getFavoriteDrawable(getActivity(), R.drawable.ic_favorite_full));
                Snackbar.make(mScrollView, getString(R.string.added_security), Snackbar.LENGTH_SHORT).show();

            }
            mIsInFavorite = !mIsInFavorite;
        } else if (id == R.id.action_share) {


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
                            mDisableShare = true;
                            Menu menu=toolbar.getMenu();
                            menu.findItem(R.id.action_share).setVisible(false);
                        }
                    }

                }


        }
    }


    @Override
    public void onDestroy() {
        //  if(Utilities.hasTwoPane(getActivity())) {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        //   }
        Utilities.deleteFileFromSD(mSharedFileName);
        super.onDestroy();
    }
}
