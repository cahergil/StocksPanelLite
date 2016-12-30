package com.carlos.capstone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carlos.capstone.customcomponents.CustomIndexMarkerView;
import com.carlos.capstone.models.IndexDataUnit;
import com.carlos.capstone.services.RegionChartLoaderService;
import com.carlos.capstone.utils.AdvertisementListener;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 19/01/2016.
 */
public class FragmentMain extends Fragment  implements OnChartGestureListener,
        TabLayout.OnTabSelectedListener{
    private static final String LOG_TAG=FragmentMain.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LineChart mChart;
    private ProgressBar mProgressBar;
    private BroadcastReceiver mMessageReceiver;
    private CustomIndexMarkerView mCustomIndexMarkerView;
    private ArrayList<IndexDataUnit> listAmerica;
    private ArrayList<IndexDataUnit> listEurope;
    private ArrayList<IndexDataUnit> listAsia;
    private static final String FILTER_RECEIVER="index_event";
    private static final String KEY_LIST_AMERICA="list_america";
    private static final String KEY_LIST_EUROPE="list_europe";
    private static final String KEY_LIST_ASIA="list_asia";
    private static final String KEY_CURRENT_ITEM="current_item";
    private static final String KEY_REGION="region";
    public static final String REGION_AMERICA ="AMERICA";
    public static final String REGION_EUROPE ="EUROPE";
    public static final String REGION_ASIA ="ASIA";
    public static final int PAGE_AMERICA=1;
    private static final int PAGE_FAVORITES=0;
    public static final int PAGE_EUROPE=2;
    public static final int PAGE_ASIA=3;
    private String TICKER_NASDAQ="^ixic";
    private String TICKER_DAX="^gdaxi";
    private String TICKER_SP500="^gspc";
    private String TICKER_DOW_JONES="^dji";
    private String TICKER_IBEX="^ibex";
    private String TICKER_FTSE="^ftse";
    private String TICKER_SSE="000001.ss";
    // private FragmentRegion fragmentRegion;
    private FloatingActionButton mFab;
    private Tracker mTracker;
    private AdView mAdView;
    private LinearLayout mAdContainer;
    private CoordinatorLayout mCoordinatorContainer;
    private TextView tvTitle;
    private AppBarLayout mBarLayout;


    public FragmentMain(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.d(LOG_TAG,"onCreateView FragmentMain");
        View view=inflater.inflate(R.layout.fragment_main,container);
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        mCoordinatorContainer= (CoordinatorLayout) view.findViewById(R.id.containerView);
        collapsingToolbarLayout= (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setTitle("Stock ");
        collapsingToolbarLayout.setTitleEnabled(false);
        mBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        mBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isVisible = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.d("log",""+verticalOffset);
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (verticalOffset == 0) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mToolbar.setTitle("Stock Panel Lite");
                        }
                    });

                    isVisible = true;
                } else if(isVisible) {
                    mToolbar.setTitle(" ");
                    isVisible = false;
                }
            }
        });


        mProgressBar= (ProgressBar) view.findViewById(R.id.progressBar);

        mViewPager= (ViewPager) view.findViewById(R.id.viewpagerMain);
        final PagerAdapter pagerAdapter=new Adapter(getChildFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(PAGE_AMERICA);
        mTabLayout= (TabLayout) view.findViewById(R.id.tabsMain);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(this);
        //http://stackoverflow.com/questions/11082341/android-requestfocus-ineffective

        mFab = (FloatingActionButton) view.findViewById(R.id.fabMain);
        mFab.setContentDescription(getString(R.string.talkback_fm_fab));
        //mFab.setAccessibilityTraversalAfter(R.id.toolbar);
        mFab.hide();

        //chart
        mChart= (LineChart) view.findViewById(R.id.chart);
        mChart.setContentDescription(getString(R.string.talback_fm_image));
        mChart.setOnChartGestureListener(this);

        //advert see http://stackoverflow.com/questions/34231810/the-ad-size-and-ad-unit-id-must-be-set-before-loadad-when-set-programmatically
        mAdContainer= (LinearLayout) view.findViewById(R.id.advBottomBar);
        //http://stackoverflow.com/questions/33509371/adview-causes-memory-leak
        mAdView = new AdView(getActivity().getApplicationContext());
        mAdView.setAdUnitId(getString(R.string.banner_1_main_ad_unit_id));
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setFocusable(true);
        mAdView.setContentDescription(getString(R.string.talkback_fm_admob));
        mAdContainer.addView(mAdView);


        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("065886d30aca98cd")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdvertisementListener(getActivity(),"banner 1 click"));


        mMessageReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(LOG_TAG,"##### receiver broadcast in FragmentMain");
                String region=intent.getStringExtra(getString(R.string.region));
                boolean failure=intent.getBooleanExtra(getString(R.string.failure),false);
                if(!failure) {
                    ArrayList<IndexDataUnit> indexDataUnitList = intent.getParcelableArrayListExtra(getString(R.string.sent_list));
                    if (region.equals(REGION_AMERICA)) {
                        listAmerica = indexDataUnitList;
                        if (mViewPager.getCurrentItem() == PAGE_FAVORITES || mViewPager.getCurrentItem() == PAGE_AMERICA) {
                            drawChart(listAmerica);
                        }
                    } else if (region.equals(REGION_EUROPE)) {
                        listEurope = indexDataUnitList;
                        if (mViewPager.getCurrentItem() == PAGE_EUROPE) {
                            drawChart(listEurope);
                        }
                    } else if (region.equals(REGION_ASIA)) {
                        listAsia = indexDataUnitList;
                        if (mViewPager.getCurrentItem() == PAGE_ASIA) {
                            drawChart(listAsia);
                        }
                    }
                } else {
                    //no data, only disable Progressbar
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };

        //register receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,new IntentFilter(FILTER_RECEIVER));

        if(savedInstanceState!=null) {
            //get the saved list
            listAmerica=savedInstanceState.getParcelableArrayList(KEY_LIST_AMERICA);
            listEurope=savedInstanceState.getParcelableArrayList(KEY_LIST_EUROPE);
            listAsia=savedInstanceState.getParcelableArrayList(KEY_LIST_ASIA);
            //get the saved page
            int current_item=savedInstanceState.getInt(KEY_CURRENT_ITEM);
            //set saved paget to current in view pager
            mViewPager.setCurrentItem(current_item);
            //draw chart accordingly
            if(current_item==PAGE_FAVORITES || current_item==PAGE_AMERICA) {
                drawChart(listAmerica);
            } else if(current_item==PAGE_EUROPE) {
                drawChart(listEurope);
            } else {
                drawChart(listAsia);
            }
        } else  {
            //to track the default page, america
            Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.ga_fm_page_favorites));

            listAmerica= MyApplication.getmAmericaIndexes();
            if (listAmerica.size()!=0) {
                drawChart(listAmerica);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

        }



        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    public void onPause() {
        if(mAdView!=null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST_AMERICA,listAmerica);
        outState.putParcelableArrayList(KEY_LIST_EUROPE,listEurope);
        outState.putParcelableArrayList(KEY_LIST_ASIA,listAsia);
        outState.putInt(KEY_CURRENT_ITEM,mViewPager.getCurrentItem());
    }

    private void drawChart(List<IndexDataUnit> list) {

        if(list==null) return;

        mChart.setDescription("");
        Legend legend=mChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);


        // value axis
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.enableGridDashedLine(10f,10f,0);
        rightAxis.setStartAtZero(false);
        rightAxis.setValueFormatter(new PercentFormatter());

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setStartAtZero(false);
        leftAxis.setEnabled(false);

        // time axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(true);
        xAxis.enableGridDashedLine(10f,10f,0);


        int counter=0;
        ArrayList<Entry> valsTicker;
        ArrayList<LineDataSet> dataSets=new ArrayList<LineDataSet>(); // v. 2.1.6
        List<String> xLabelVal=new ArrayList<String>();

        // for logging purposes
        StringBuilder string =new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
             if(i==0) {
                 string.append("draw chart:");
             }
             string.append(i+1+":"+list.get(i).getName()+",");
        }

        boolean rtl=getResources().getBoolean(R.bool.is_right_to_left);
        List<IndexDataUnit> reversedList=new ArrayList<IndexDataUnit>();
        if(rtl) {
            for(int i=0;i<list.size();i++) {
                IndexDataUnit reversedIndexDataUnit=new IndexDataUnit();
                IndexDataUnit indexDataUnit=list.get(i);
                //get the ticker, name  and market(not used)
                reversedIndexDataUnit.setTicker(indexDataUnit.getTicker());
                reversedIndexDataUnit.setName(indexDataUnit.getName());
                reversedIndexDataUnit.setMarket(indexDataUnit.getMarket());
                //reverse 3 list
                int size=indexDataUnit.getyValues().size();
                List<Double> yValues=new ArrayList<Double>();
                List<String> xVal=new ArrayList<String>();
                List<Double> yValuesMarkView=new ArrayList<Double>();
                for(int j=0;j<size;j++) {
                        yValues.add(indexDataUnit.getyValues().get((size-1)-j));
                        xVal.add(indexDataUnit.getxLabels().get((size-1)-j));
                        yValuesMarkView.add(indexDataUnit.getyValuesMarkerView().get((size-1)-j));
                }
                reversedIndexDataUnit.setxLabels(xVal);
                reversedIndexDataUnit.setyValues(yValues);
                reversedIndexDataUnit.setyValuesMarkerView(yValuesMarkView);
                reversedList.add(reversedIndexDataUnit);
            }

            list=reversedList;

        }
        Log.d(LOG_TAG, string.toString());
        for (IndexDataUnit indexDataUnit:list) {

            ++counter;
            valsTicker = new ArrayList<Entry>();
            Entry entryTicker;
            for (int i = 0; i < indexDataUnit.getyValues().size(); i++) {
                entryTicker = new Entry(indexDataUnit.getyValues().get(i).floatValue(), i);
                valsTicker.add(entryTicker);
                //get the xval , should be equals for all, we take the firs serie xval
                if (i == 0) {
                    xLabelVal = indexDataUnit.getxLabels();
                }
            }
            LineDataSet lineDataSetTicker=new LineDataSet(valsTicker,indexDataUnit.getName());
            lineDataSetTicker.setDrawCircleHole(false);
            lineDataSetTicker.setDrawValues(true);
            lineDataSetTicker.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            lineDataSetTicker.setLineWidth(1.5f);
            int color=0;
            String ticker=indexDataUnit.getTicker();
            if( ticker.equals(TICKER_NASDAQ) || ticker.equals(TICKER_DAX) ||
                ticker.equals(TICKER_SSE) ) {
                color=ContextCompat.getColor(getActivity(),R.color.company_1);
            } else if ( ticker.equals(TICKER_DOW_JONES) || ticker.equals(TICKER_IBEX) ) {
                color=ContextCompat.getColor(getActivity(),R.color.company_2);
            } else if ( ticker.equals(TICKER_SP500) || ticker.equals(TICKER_FTSE)) {
                color=ContextCompat.getColor(getActivity(),R.color.company_3);
            }

            lineDataSetTicker.setColor(color);
            lineDataSetTicker.setCircleSize(0.f);
            lineDataSetTicker.setValueTextColor(ContextCompat.getColor(getActivity(),android.R.color.black));
            lineDataSetTicker.setLabel(indexDataUnit.getName());
            lineDataSetTicker.setHighlightEnabled(true);
            lineDataSetTicker.setHighLightColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
            lineDataSetTicker.setHighlightLineWidth(1f);
            lineDataSetTicker.setAxisDependency(YAxis.AxisDependency.RIGHT);
            dataSets.add(lineDataSetTicker);

        }
        LineData data=new LineData(xLabelVal,dataSets);


        mCustomIndexMarkerView = new CustomIndexMarkerView(getActivity(),R.layout.custom_index_markerview, xLabelVal, list, mChart);
        mChart.setMarkerView(mCustomIndexMarkerView);

        mChart.setData(data);
        mChart.invalidate();
        mProgressBar.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


       inflater.inflate(R.menu.menu_fragment_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        //if the gesture is distinct from single tap, hide the Markerview
        if(lastPerformedGesture!= ChartTouchListener.ChartGesture.SINGLE_TAP) {
            mChart.highlightValue(null);
            int count=mChart.getLineData().getDataSetCount();
            if(count>3) {
                mChart.getLineData().removeDataSet(count-1);
            }
        }
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

        mChart.fitScreen();
        mChart.invalidate();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int pos=tab.getPosition();
       //remove previous highlights
        mChart.highlightValue(null);
        switch (pos) {
            case PAGE_FAVORITES:
                 mFab.show();
                //so that fab always has the focus when selecting PAGE_FAVORITES
                //http://stackoverflow.com/questions/11082341/android-requestfocus-ineffective
                 mFab.post(new Runnable() {
                     @Override
                     public void run() {
                         mFab.requestFocus();
                     }
                 });
                 drawChart(listAmerica);
                 Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.ga_fm_page_favorites));

                break;
            case PAGE_AMERICA:
                 mFab.hide();
                 drawChart(listAmerica);
                 Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.ga_fm_page_america));
                break;
            case PAGE_EUROPE:
                mFab.hide();
                mProgressBar.setVisibility(View.VISIBLE);
                if(Utilities.getNeedEuropeSync(getActivity())) {
                    Utilities.setNeedEuropeSync(getActivity(),false);
                    Intent intentAmerica = new Intent(getActivity(), RegionChartLoaderService.class);
                    intentAmerica.setAction(RegionChartLoaderService.FETCH_EUROPE_DATA);
                    getActivity().startService(intentAmerica);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    drawChart(listEurope);
                }
                Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.ga_fm_page_europe));
                break;
            case PAGE_ASIA:
                mFab.hide();
                mProgressBar.setVisibility(View.VISIBLE);
                if(Utilities.getNeedAsiaSync(getActivity())) {
                    Utilities.setNeedAsiaSync(getActivity(),false);
                    Intent intentAsia = new Intent(getActivity(), RegionChartLoaderService.class);
                    intentAsia.setAction(RegionChartLoaderService.FETCH_ASIA_DATA);
                    getActivity().startService(intentAsia);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    drawChart(listAsia);
                }
                Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.ga_fm_page_asia));
                break;
        }
        mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



    private  class Adapter extends FragmentPagerAdapter {


        public Adapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            FragmentRegion fragmentRegion;
            switch (position) {
                case 0:
                    return new FragmentFavorites();
                case 1:
                    fragmentRegion=new FragmentRegion();
                    bundle.putString(getString(R.string.region), getString(R.string.region_america));
                    fragmentRegion.setArguments(bundle);

                    return fragmentRegion;

                case 2:
                    fragmentRegion=new FragmentRegion();
                    bundle.putString(getString(R.string.region),getString(R.string.region_europe));
                    fragmentRegion.setArguments(bundle);
                    return fragmentRegion;

                case 3:
                    fragmentRegion =new FragmentRegion();
                    bundle.putString(getString(R.string.region),getString(R.string.region_asia));
                    fragmentRegion.setArguments(bundle);
                    return fragmentRegion;

                default: return null;

            }


        }
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fm_favorites);
                case 1:
                    return getString(R.string.fm_america);
                case 2:
                    return getString(R.string.fm_europe);
                case 3:
                    return getString(R.string.fm_asia);
                default: return null;

            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentMain on Destroy");
        if (mAdView != null) {
            mAdView.destroy();
        }

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


}
