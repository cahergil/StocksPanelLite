package com.carlos.capstone;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import com.carlos.capstone.adapters.IndexesAdapter;
import com.carlos.capstone.adapters.RssNewsAdapter;
import com.carlos.capstone.adapters.SuggestionsAdapter;
import com.carlos.capstone.customtabs.CustomTabActivityHelper;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.interfaces.Callback;
import com.carlos.capstone.utils.MyApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.linearlistview.LinearListView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Carlos on 19/01/2016.
 */
public class FragmentRegion extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private LinearListView mNewsListView;
    private LinearListView mIndexesListView;
    private NestedScrollView mScrollView;
    private static final String KEY_POSX="x_pos";
    private static final String KEY_POSY="y_pos";
    private static final int NEWS_LOADER=0;
    private static final int INDEXES_LOADER=1;
    private static final int SUGGESTIONS_LOADER=2;
    private static final String LOG_TAG =FragmentRegion.class.getSimpleName();
    private IndexesAdapter mAdapterIndexes;
    private RssNewsAdapter mAdapterNews;

    private static final String selectionNews =CapstoneContract.NewsEntity.REGION + " =?";
    private static final String selectionIndexes=CapstoneContract.IndexesEntity.REGION + " =?";
    private static final String sortByDateDesc = CapstoneContract.NewsEntity.DATE + " DESC";
    private int x;
    private int y;
    private int old_y;
    private String mRegion;
    private SuggestionsAdapter mSuggestionsAdapter;
    private String mSearchFilter;



    LinearListView.OnItemClickListener mListenerIndex=new LinearListView.OnItemClickListener() {
        @Override
        public void onItemClick(LinearListView parent, View view, int position, long id) {
            TextView tvTicker,tvIndexName;
            tvTicker = (TextView) view.findViewById(R.id.ticker);
            tvIndexName=(TextView)view.findViewById(R.id.txtindexName);
            ((Callback)getActivity()).onItemInIndexesSelected(tvTicker.getText().toString(),tvIndexName.getText().toString(),mRegion);

        }
    };


    LinearListView.OnItemClickListener mListenerNews = new LinearListView.OnItemClickListener() {


        @Override
        public void onItemClick(LinearListView parent, View view, int position,long id) {

            final TextView tvLink,tvTitle;
            tvLink= (TextView) view.findViewById(R.id.txtUrlLink);
            tvTitle= (TextView) view.findViewById(R.id.txtTitulo);

            Tracker tracker=((MyApplication)getActivity().getApplication()).getTracker();
            SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_mask_ga));
            String currentDateandTime = sdf.format(new Date());

            //send the news clicked to GA. Mainly important to know in which region there are more clicks
           tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.news_ga_category))
                    .setAction(mRegion)
                    .setLabel(currentDateandTime)
                    .build());


            Uri uri = Uri.parse(tvLink.getText().toString());

            Bitmap backIcon = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.arrow_left);
            //customization possibilities
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                    .setShowTitle(true)
                    .setCloseButtonIcon(backIcon)
                    .build();

            CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent, uri,
                    //in case the user doesn't have chromium v 45 installed, offer an alternative
                    //browser experience with WebView
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                            intent.putExtra("url", tvLink.getText());
                            intent.putExtra("title", tvTitle.getText());
                            startActivity(intent);
                        }
                    });

        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //getActivity().getSupportLoaderManager().initLoader(INDEXES_LOADER, null, this);
        getLoaderManager().initLoader(NEWS_LOADER,null,this);
        //getActivity().getSupportLoaderManager().initLoader(NEWS_LOADER, null, this);
        getLoaderManager().initLoader(INDEXES_LOADER,null,this);

        super.onActivityCreated(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView FragmentRegion");
        View view=inflater.inflate(R.layout.fragment_region,container,false);
        setHasOptionsMenu(true);
        mNewsListView = (LinearListView) view.findViewById(R.id.newsList);
        mIndexesListView=(LinearListView) view.findViewById(R.id.indexesList);

        mScrollView= (NestedScrollView) view.findViewById(R.id.scrollView);




        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                x=mScrollView.getScrollX();//only y is relevant
                y=mScrollView.getScrollY();

            }
        });

        //disable Ads if the user scroll down, enalbe it again when scrolling up. Since I am using the
        //custom components NonScrollListView for indexes(and it doesn't scrooll there is no point in atta
        //ching to it an ScrollListener. The unique possible way is to use setOnScrollChangeListener in api 23
//        if(Build.VERSION.SDK_INT>=23) {
//            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY >= oldScrollY) { //scroll down
//                        getActivity().findViewById(R.id.advBottomBar).setVisibility(View.GONE);
//                        Log.d(LOG_TAG, "scrolling down");
//                    } else { //scroll up
//                        getActivity().findViewById(R.id.advBottomBar).setVisibility(View.VISIBLE);
//                        Log.d(LOG_TAG, "scrolling up");
//                    }
//                }
//            });
//        }
        //adapters
        mAdapterNews=new RssNewsAdapter(getActivity(),null,NEWS_LOADER);
        mAdapterIndexes=new IndexesAdapter(getActivity(),null,INDEXES_LOADER,FragmentMain.PAGE_AMERICA);

        //set both adapters
        mIndexesListView.setAdapter(mAdapterIndexes);
        mNewsListView.setAdapter(mAdapterNews);

        // listener
        mNewsListView.setOnItemClickListener(mListenerNews);
        mIndexesListView.setOnItemClickListener(mListenerIndex);




        if(savedInstanceState==null) {
            Bundle bundle=getArguments();
            mRegion=bundle.getString(getString(R.string.region));

        } else {
            mRegion=savedInstanceState.getString(getString(R.string.region_key));
            mSearchFilter=savedInstanceState.getString(getString(R.string.search_filter_key));
            x=savedInstanceState.getInt(KEY_POSX);
            y=savedInstanceState.getInt(KEY_POSY);
            Log.d(LOG_TAG,"xRotation:"+savedInstanceState.getInt(KEY_POSX)+",yRotation:"+savedInstanceState.getInt(KEY_POSY));
            getLoaderManager().restartLoader(NEWS_LOADER, null, this);
            getLoaderManager().restartLoader(INDEXES_LOADER, null, this);
            getLoaderManager().restartLoader(SUGGESTIONS_LOADER,null,this);


        }


        return view;
    }

    @Override
    public void onResume() {
       super.onResume();

       //set tab as default focus
       //http://stackoverflow.com/questions/11082341/android-requestfocus-ineffective
       final TabLayout tabs= (TabLayout) getActivity().findViewById(R.id.tabsMain);
        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.requestFocus();
            }
        });

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.region_key),mRegion);
        outState.putInt(KEY_POSX,x);
        outState.putInt(KEY_POSY,y);
        outState.putString(getString(R.string.search_filter_key),mSearchFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_search,menu);
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Stock/Index/Etf");

        //flag android:imeOptions="flagNoExtractUi" set in menu_search
        //antes de la version 16 vista grande seach view 1 segundo
        //despues va bien
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
            int options = searchView.getImeOptions();
            searchView.setImeOptions(options | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        }
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        mSuggestionsAdapter =new SuggestionsAdapter(getActivity(),null,SUGGESTIONS_LOADER);
        
        searchView.setSuggestionsAdapter(mSuggestionsAdapter);

        if(mSearchFilter!=null) {
            searchView.setIconified(false);
            searchView.setQuery(mSearchFilter, true);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d(LOG_TAG,"OnQueryTextChange");
                mSearchFilter= TextUtils.isEmpty(newText) ? null: newText;
                getLoaderManager().restartLoader(SUGGESTIONS_LOADER,null,FragmentRegion.this);
                return true;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
//                Cursor c= (Cursor) mSuggestionsAdapter.getItem(position);
//                c.close();
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
                String symbol = c.getString(SuggestionsAdapter.COL_TICKER);
                String companyName = c.getString(SuggestionsAdapter.COL_NAME);
                String securityType = c.getString(SuggestionsAdapter.COL_SECURITY_TYPE);
                ((Callback)getActivity()).onItemSelected(symbol,companyName,securityType.toUpperCase());

                //clear focus for when returning from the previous activity
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id==INDEXES_LOADER) {


            return new CursorLoader(getActivity(),
                    CapstoneContract.IndexesEntity.CONTENT_URI,null,selectionIndexes,new String[]{mRegion},null);


        } else if(id==NEWS_LOADER) {
            return new CursorLoader(getActivity(),
                    CapstoneContract.NewsEntity.CONTENT_URI,null,selectionNews,new String[]{mRegion},sortByDateDesc);

        } else if(id==SUGGESTIONS_LOADER){
            Uri uri = CapstoneContract.SecurityExcelEntity.CONTENT_URI;
            //_ID column is necessary, else the app crashes
            String[] projection = new String[]{CapstoneContract.SecurityExcelEntity._ID,
                    CapstoneContract.SecurityExcelEntity.TICKER,
                    CapstoneContract.SecurityExcelEntity.NAME,
                    CapstoneContract.SecurityExcelEntity.EXCHANGE,
                    CapstoneContract.SecurityExcelEntity.SECURITY_TYPE};
            // String selection = CapstoneContract.SecurityExcelEntity.NAME + " LIKE '%" + mSearchFilter + "%'";
            String selection=CapstoneContract.SecurityExcelEntity.NAME + " LIKE '%" + mSearchFilter + "%'" +
                    " OR " + CapstoneContract.SecurityExcelEntity.TICKER + " LIKE '%" + mSearchFilter + "%'";
            String sortOrder = CapstoneContract.SecurityExcelEntity.NAME + " DESC";
            return new CursorLoader(getActivity(),
                    uri,
                    projection,
                    selection,
                    null,
                    sortOrder);

        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        switch(loader.getId()) {
            case INDEXES_LOADER:
                mAdapterIndexes.swapCursor(data);
                break;
            case NEWS_LOADER:
                mAdapterNews.swapCursor(data);
                break;
            case SUGGESTIONS_LOADER:

               if (mSuggestionsAdapter != null) {
                   Log.d(LOG_TAG,"onLoadFinish SUGG") ;
                    mSuggestionsAdapter.swapCursor(data);

                }
        }

        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(x, y);
            }
        }, 100);



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       switch(loader.getId()) {
            case INDEXES_LOADER:
               mAdapterIndexes.swapCursor(null);
                break;
            case NEWS_LOADER:
                mAdapterNews.swapCursor(null);
                break;
            case SUGGESTIONS_LOADER:
                if(mSuggestionsAdapter!=null) {
                    mSuggestionsAdapter.swapCursor(null);
                }
        }

    }


    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentRegion on Destroy");
        super.onDestroy();
    }




}
