package com.carlos.capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.carlos.capstone.utils.AdvertisementListener;
import com.carlos.capstone.utils.Utilities;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by Carlos on 09/02/2016.
 */
public class FragmentIndexDetail extends Fragment implements TabLayout.OnTabSelectedListener {
    private static final String LOG_TAG=FragmentIndexDetail.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private String mTicker;
    private boolean mFromWidget;


    private static final int PAGE_SUMMARY =0;
    private static final int PAGE_COMPONENTS =1;

    private AdView mAdView;
    private LinearLayout mAdContainer;

    public FragmentIndexDetail(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_index_detail, container, false);
        Bundle bundle=getArguments();
        mTicker=bundle.getString(getString(R.string.ticker_bundle_key));
        mFromWidget=bundle.getBoolean(getString(R.string.stock_from_widget_key));

        mViewPager= (ViewPager) view.findViewById(R.id.viewpager);
        final Adapter adapter=new Adapter(getChildFragmentManager(),mTicker,getActivity(),mFromWidget);
        mViewPager.setAdapter(adapter);
        mTabLayout= (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(this);
        if(!Utilities.hasTwoPane(getActivity())) {
            mToolbar = (Toolbar) view.findViewById(R.id.toolbarRightPane);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mTicker + " details");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            //in 2 pane mode track this fragment as if it was an activity
            Utilities.analyticsTrackScreen(getActivity().getApplication(),LOG_TAG);

        }

        //advert
        // see http://stackoverflow.com/questions/34231810/the-ad-size-and-ad-unit-id-must-be-set-before-loadad-when-set-programmatically
        //Change the name to advBottomBarRight in case it is 2-pane-mode(we already have one of left side panel)
        mAdContainer= (LinearLayout) view.findViewById(R.id.advBottomBarRight);
        //http://stackoverflow.com/questions/33509371/adview-causes-memory-leak
        mAdView = new AdView(getActivity().getApplicationContext());
        mAdView.setAdUnitId(getString(R.string.banner3_ad_unit_id));
        mAdView.setAdSize(AdSize.BANNER);
        mAdContainer.addView(mAdView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("065886d30aca98cd")
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdvertisementListener(getActivity(),"banner 3 click"));


        //the first screens that loads
        if(savedInstanceState==null) {
            Utilities.analyticsTrackScreen(getActivity().getApplication(), getString(R.string.index_ga_summary_screen_name));
        }

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        Intent intent = null;
        int position = tab.getPosition();
        if (position == PAGE_SUMMARY) {
            Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.index_ga_summary_screen_name));
            mViewPager.setCurrentItem(position);
            intent = new Intent(getString(R.string.index_tab_summary_intent_filter));
        } else if (position == PAGE_COMPONENTS) {
            Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.index_ga_components_screen_name));
            mViewPager.setCurrentItem(position);
            intent = new Intent(getString(R.string.index_tab_components_intent_filter));

        }

        //if two pane mode, send the broadcast
        if(!(getActivity() instanceof DetailIndexActivity)) {
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }

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
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public static class Adapter extends FragmentPagerAdapter {
        private String ticker;
        private Context context;
        private boolean fromWidget;

        public Adapter(FragmentManager fm,String ticker,Context context,boolean fromWidget) {
            super(fm);
            this.ticker=ticker;
            this.context=context;
            this.fromWidget=fromWidget;

        }

        @Override
        public Fragment getItem(int position) {
            if(position== PAGE_SUMMARY) {
                FragmentIndexSummary fragmentIndexSummary=new FragmentIndexSummary();
                Bundle bundle=new Bundle();
                bundle.putString(context.getString(R.string.ticker_bundle_key),ticker);
                bundle.putBoolean(context.getString(R.string.stock_from_widget_key),fromWidget);
                fragmentIndexSummary.setArguments(bundle);
                return fragmentIndexSummary;
            }
            if(position== PAGE_COMPONENTS) {
                FragmentIndexComponents fragmentIndexComponents=new FragmentIndexComponents();
                Bundle bundle=new Bundle();
                bundle.putString(context.getString(R.string.ticker_bundle_key),ticker);
                fragmentIndexComponents.setArguments(bundle);
                return fragmentIndexComponents;
            }

            return null;
        }

        @Override
        public int getCount() {

            return Utilities.indexHasTickerComponents(ticker)? 2:1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PAGE_SUMMARY: return context.getString(R.string.tab_summary_string);

                case PAGE_COMPONENTS: return context.getString(R.string.tab_componets_string);
                default: return null;

            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentIndexDetail on Destroy");
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
