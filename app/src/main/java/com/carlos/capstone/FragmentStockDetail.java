package com.carlos.capstone;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carlos.capstone.utils.AdvertisementListener;
import com.carlos.capstone.utils.Utilities;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class FragmentStockDetail extends Fragment implements TabLayout.OnTabSelectedListener{
    private static final String LOG_TAG=FragmentStockDetail.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;
    private static String mSymbol;
    private static String mCompanyName;
    private static boolean mFromWidget;
    private static final int PAGE_SUMMARY=0;
    private static final int PAGE_STATS=1;
    private static final int PAGE_NEWS=2;

    private AdView mAdView;
    private LinearLayout mAdContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_stock_detail,container,false);

        Bundle bundle=getArguments();
        if(bundle!=null) {
            mSymbol=bundle.getString(getString(R.string.symbol_key));
            mCompanyName=bundle.getString(getString(R.string.company_name_key));
            mFromWidget=bundle.getBoolean(getString(R.string.stock_from_widget_key));
        }

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        final PagerAdapter pagerAdapter=new Adapter(getActivity(),getChildFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(this);
        setTabIcons();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarRightPane);
        if(Utilities.hasOnePane(getActivity())) {

            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mSymbol + " details");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            //in 2 pane mode track this fragment as if it were an activity
            Utilities.analyticsTrackScreen(getActivity().getApplication(),LOG_TAG);
            //if we want to change colors in tablet mode
            toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
            mTabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        }

        //advert
        // see http://stackoverflow.com/questions/34231810/the-ad-size-and-ad-unit-id-must-be-set-before-loadad-when-set-programmatically
        //Change the name to advBottomBarRight in case it is 2-pane-mode(we already have one of left side panel)
        mAdContainer= (LinearLayout) view.findViewById(R.id.advBottomBarRight);
        //http://stackoverflow.com/questions/33509371/adview-causes-memory-leak
        mAdView = new AdView(getActivity().getApplicationContext());
        mAdView.setAdUnitId(getString(R.string.banner2_ad_unit_id));
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setFocusable(true);
        mAdView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(LOG_TAG,"pulsando...");
                if(keyCode==event.KEYCODE_DPAD_CENTER) {
                    Log.d(LOG_TAG,"pulsando enter");
                    return true;
                }
                return false;
            }
        });
        mAdView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(LOG_TAG,"cambiando foco... "+hasFocus);
            }
        });
        mAdContainer.addView(mAdView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("065886d30aca98cd")
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdvertisementListener(getActivity(),"banner 2 click"));



        //seems not to work, except if I change the color
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_focused}, // focused
                new int[] {}
        };
        int[] colors = new int[] {
                Color.BLUE,
                Color.parseColor("#FF5722")
        };
        ColorStateList colorStateList=new ColorStateList(states,colors);
        mFab.setBackgroundTintList(colorStateList);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showhelpKeyStats();

            }
        });
        mFab.hide();
        if(savedInstanceState==null) {
            Utilities.analyticsTrackScreen(getActivity().getApplication(), getString(R.string.stock_ga_summary_screen_name));
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

    private void setTabIcons(){

        mTabLayout.getTabAt(PAGE_SUMMARY).setIcon(R.drawable.chart_line);
        mTabLayout.getTabAt(PAGE_STATS).setIcon(R.drawable.beta);
        mTabLayout.getTabAt(PAGE_NEWS).setIcon(R.drawable.rss);

        mTabLayout.getTabAt(PAGE_SUMMARY).getIcon().setColorFilter(ContextCompat.getColor(getActivity(),R.color.white),PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(PAGE_STATS).getIcon().setColorFilter(ContextCompat.getColor(getActivity(),R.color.blue_gray_700),PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(PAGE_NEWS).getIcon().setColorFilter(ContextCompat.getColor(getActivity(),R.color.blue_gray_700),PorterDuff.Mode.SRC_IN);

    }
    public void showhelpKeyStats(){


        View messageView = getActivity().getLayoutInflater().inflate(R.layout.info_key_stats, null, false);
        LinearLayout linearLayout= (LinearLayout) messageView.findViewById(R.id.llMain);

        TextView textView=new TextView(getActivity());
        String link = getString(R.string.stock_dialog_link);
        String message=getString(R.string.stock_indicator_definitions)+ link;

        Spanned spannedString=Html.fromHtml(message);
        textView.setText(spannedString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if(getResources().getBoolean(R.bool.is_right_to_left)) {
            textView.setGravity(Gravity.RIGHT);
        }

        linearLayout.addView(textView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // builder.setIcon(R.drawable.app_icon);
        builder.setTitle(getString(R.string.stock_dialog_title));
        builder.setView(linearLayout);
        builder.create();
        builder.show();

    }




    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int tabColorIcon=ContextCompat.getColor(getActivity(),R.color.white);

        tab.getIcon().setColorFilter(tabColorIcon, PorterDuff.Mode.SRC_IN);
        int position=tab.getPosition();

        switch(position){
            case PAGE_SUMMARY:
                mViewPager.setCurrentItem(position);
                //only send Broadcast in two pane mode
                if(Utilities.hasTwoPane(getActivity())) {
                    final Intent intentSend=new Intent(getString(R.string.stock_tab_summary_intent_filter));
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentSend);
                }
                mFab.hide();
                Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.stock_ga_summary_screen_name));
                break;
            case PAGE_STATS:
                mViewPager.setCurrentItem(position);
                clearMenuRightPane();
                mFab.show();
                Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.stock_ga_stats_screen_name));
                break;
            case PAGE_NEWS:
                mViewPager.setCurrentItem(position);
                clearMenuRightPane();
                mFab.hide();
                Utilities.analyticsTrackScreen(getActivity().getApplication(),getString(R.string.stock_ga_news_screen_name));
                break;


        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor=ContextCompat.getColor(getContext(),R.color.blue_gray_700);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private static class Adapter extends FragmentPagerAdapter {

        private Context context;
        public Adapter(Context context,FragmentManager fm) {

            super(fm);
            this.context=context;

        }



        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            bundle.putString(context.getString(R.string.symbol_key),mSymbol);
            bundle.putString(context.getString(R.string.company_name_key),mCompanyName);
            bundle.putBoolean(context.getString(R.string.stock_from_widget_key),mFromWidget);
          switch (position) {
                case PAGE_SUMMARY:
                    FragmentStockSummary fragmentStockSummary =new FragmentStockSummary();
                    fragmentStockSummary.setArguments(bundle);
                    return fragmentStockSummary;
                case PAGE_STATS:
                    FragmentStockStats fragmentStockStats =new FragmentStockStats();
                    fragmentStockStats.setArguments(bundle);
                    return fragmentStockStats;
                case PAGE_NEWS:
                    FragmentStockNews fragmentStockNews =new FragmentStockNews();
                    fragmentStockNews.setArguments(bundle);
                    return fragmentStockNews;
                default: return null;

            }
       }
       @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PAGE_SUMMARY:
                    return context.getString(R.string.stock_summary);
                case PAGE_STATS:
                    return context.getString(R.string.stock_statistics);
                case PAGE_NEWS:
                    return context.getString(R.string.stock_news);
                default: return null;

            }
        }
    }

    private void clearMenuRightPane(){
        Toolbar toolbar= (Toolbar) getView().findViewById(R.id.toolbarRightPane);
        if(toolbar!=null) {
            Menu menu=toolbar.getMenu();
            if(menu!=null) menu.clear();
        }
    }
    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentStockDetail on Destroy");
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
