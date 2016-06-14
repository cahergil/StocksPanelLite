package com.carlos.capstone;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.carlos.capstone.adapters.SecurityAdapter;
import com.carlos.capstone.adapters.SuggestionsAdapter;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.interfaces.Callback;
import com.carlos.capstone.sync.CapstoneSyncAdapter;
import com.carlos.capstone.utils.DividerItemDecoration;
import com.carlos.capstone.utils.TimeMeasure;

/**
 * Created by Carlos on 19/01/2016.
 */
public class FragmentFavorites extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SwipeRefreshLayout.OnRefreshListener{
    public static final String LOG_TAG=FragmentFavorites.class.getSimpleName();
    public static final int SUGGESTIONS_LOADER=0;
    private static final int SECURITIES_LOADER=1;
    private SuggestionsAdapter mSuggestionsAdapter;
   // private SecurityAdapter mSecurityAdapter;
    private SecurityAdapter mSecurityAdapter;
    //private LinearListView securityList;
    private RecyclerView securityList;
    private String mSearchFilter;
    private FloatingActionButton mFab;
    private boolean mAddToFavoritIntent;
    private SwipeRefreshLayout mSwipeContainer;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView FragmentFavorites");
        View view=inflater.inflate(R.layout.fragment_favorites,container,false);
        setHasOptionsMenu(true);
        mFab= (FloatingActionButton) getActivity().findViewById(R.id.fabMain);
        View emptyView=view.findViewById(R.id.emptyView);
        securityList= (RecyclerView) view.findViewById(R.id.securitiesList);
        mSecurityAdapter=new SecurityAdapter(getActivity(),emptyView);
        securityList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeColors(R.color.colorPrimary);
        //for api>=21 set elevation in layout security_item.xml and disable DividerItemDecoration
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
            securityList.addItemDecoration(new DividerItemDecoration(getActivity()));
        }
        securityList.setHasFixedSize(true);
        securityList.setAdapter(mSecurityAdapter);
        securityList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(LOG_TAG,"focusChange"+hasFocus);
            }
        });


        mFab= (FloatingActionButton) getActivity().findViewById(R.id.fabMain);
        securityList.setNextFocusDownId(mFab.getId());

        if(savedInstanceState!=null) {
            mSearchFilter=savedInstanceState.getString(getString(R.string.search_filter_fav_key));
            mAddToFavoritIntent =savedInstanceState.getBoolean("add_to_favorite_intent_key");
            //restart the loader so that the searchview suggestions gets populated again
            getLoaderManager().restartLoader(SECURITIES_LOADER,null,this);
            getLoaderManager().restartLoader(SUGGESTIONS_LOADER,null,this);
        }
        
        //change the color windowbackground only for this fragment
//        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.ltgray));
//        getActivity().getWindow().setBackgroundDrawable(cd);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SECURITIES_LOADER,null,this);
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        Log.d(LOG_TAG,"onCreateOptionsMenu Favorites");
        inflater.inflate(R.menu.menu_search,menu);
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,"onClickFav Favorites");
                searchView.setQuery("",true);
                searchView.setIconified(false);
                mAddToFavoritIntent =true;
            }
        });


        searchView.setQueryHint("Stock/Index/Etf");
        //flag android:imeOptions="flagNoExtractUi" set in menu_search
        //antes de la version 16 vista grande seach view 1 segundo
        //despues la cosa va perfecta
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


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(mAddToFavoritIntent) {
                    mAddToFavoritIntent =false;
                  //  mFab.requestFocus();
                }

                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d(LOG_TAG,"OnQueryTextChange");
                mSearchFilter= TextUtils.isEmpty(newText) ? null: newText;
                getLoaderManager().restartLoader(SUGGESTIONS_LOADER,null,FragmentFavorites.this);
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
                if(!mAddToFavoritIntent) {
                    String companyName = c.getString(SuggestionsAdapter.COL_NAME);
                    String securityType = c.getString(SuggestionsAdapter.COL_SECURITY_TYPE);
                    ((Callback) getActivity()).onItemSelected(symbol, companyName, securityType.toUpperCase());
                } else {
                    CapstoneSyncAdapter.loadFavorites(false,symbol,getActivity(),null);
                }

                searchView.setQuery("", false);
                searchView.setIconified(true);

                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.search_filter_fav_key),mSearchFilter);
        outState.putBoolean("add_to_favorite_intent_key", mAddToFavoritIntent);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        TimeMeasure tm=new TimeMeasure(LOG_TAG);
        tm.log("START REFRESHING FAVORITES");
        CapstoneSyncAdapter.loadFavorites(true,getString(R.string.sa_helper_var),getActivity(),tm);
        mSwipeContainer.setRefreshing(false);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==SUGGESTIONS_LOADER) {
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
        } else if(id==SECURITIES_LOADER) {
            return new CursorLoader(getActivity(),
                    CapstoneContract.FavoritesEntity.CONTENT_URI,
                    null,
                    null,
                    null,
                    CapstoneContract.FavoritesEntity.COMPANY_NAME + " COLLATE NOCASE ASC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        switch (loader.getId()) {
            case SUGGESTIONS_LOADER:
            if (mSuggestionsAdapter != null) {
                mSuggestionsAdapter.swapCursor(data);
            }
            break;
            case SECURITIES_LOADER:
                mSecurityAdapter.swapCursor(data);
            break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case SUGGESTIONS_LOADER:
                if(mSuggestionsAdapter!=null) {
                    mSuggestionsAdapter.swapCursor(null);
                }
                break;
            case SECURITIES_LOADER:
                mSecurityAdapter.swapCursor(null);
                break;
        }

    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentFavorite on Destroy");
        super.onDestroy();

    }


}
