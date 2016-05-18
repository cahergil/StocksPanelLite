package com.carlos.capstone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.carlos.capstone.adapters.ComponentsAdapter;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.services.IndexComponentsService;
import com.carlos.capstone.utils.DividerItemDecoration;
import com.carlos.capstone.utils.TimeMeasure;
import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 09/02/2016.
 */
public class FragmentIndexComponents extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG=FragmentIndexComponents.class.getSimpleName();
    private TimeMeasure tm;

    private String mTickerIndex;
    private String mTickerQuery;
    private RecyclerView mRecyclerView;
    private ComponentsAdapter mAdapter;
    private String mSearchFilter;
    private static final int COMPONENT_LOADER = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private Toolbar mToolbar;

    private BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //  String message=intent.getStringExtra("tab_summary");

            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbarRightPane);
            Menu menu=mToolbar.getMenu();
            if(menu!=null) menu.clear();
            mToolbar.inflateMenu(R.menu.menu_search);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    handleNavigationPress(item.getItemId(),mToolbar.getMenu());
                    return true;
                }
            });
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_components, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //without this line the recyclerview doesn't scroll smooth
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ComponentsAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        if(Utilities.hasTwoPane(getActivity())) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                    new IntentFilter(getString(R.string.index_tab_components_intent_filter)));

        }

        tm = new TimeMeasure(LOG_TAG);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            mTickerIndex = bundle.getString(getString(R.string.ticker_bundle_key));
            mTickerQuery = Utilities.indexTickerMapperComponents(mTickerIndex);
            Intent intent=new Intent(getActivity(), IndexComponentsService.class);
            bundle.putString(getString(R.string.ticker_query_key),mTickerQuery);
            intent.putExtras(bundle);
            getActivity().startService(intent);

        } else {

            mTickerIndex=savedInstanceState.getString(getString(R.string.ticker_name_key));
            //restore the position of recyclerview
            Parcelable state=savedInstanceState.getParcelable(getString(R.string.manager_key));
            mLinearLayoutManager.onRestoreInstanceState(state);
            mSearchFilter=savedInstanceState.getString(getString(R.string.search_filter_key_components));
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(COMPONENT_LOADER, null, this);
        if(getActivity() instanceof DetailIndexActivity ) {
            setHasOptionsMenu(true);
        }

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {


        //save the index ticker so that on rotation loader don't crash
        outState.putString(getString(R.string.ticker_name_key),mTickerIndex);
        //save the position in the recyclerview
        outState.putParcelable(getString(R.string.manager_key),mLinearLayoutManager.onSaveInstanceState());

        //search filter
        outState.putString(getString(R.string.search_filter_key_components),mSearchFilter);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(getActivity() instanceof DetailIndexActivity) {
            inflater.inflate(R.menu.menu_search, menu);
            final SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
            if (mSearchFilter != null) {
                searchView.setIconified(false);
                searchView.setQuery(mSearchFilter, true);
            }
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
                int options = searchView.getImeOptions();
                searchView.setImeOptions(options | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            }
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mSearchFilter = TextUtils.isEmpty(newText) ? null : newText;
                    getLoaderManager().restartLoader(COMPONENT_LOADER, null, FragmentIndexComponents.this);
                    return true;
                }
            });
            super.onCreateOptionsMenu(menu, inflater);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = CapstoneContract.IndexComponentEntity.buildIndexComponentWithIndex(mTickerIndex);
        String selection=CapstoneContract.IndexComponentEntity.INDEX_NAME + "=?";
        String selectionLike=CapstoneContract.IndexComponentEntity.INDEX_NAME + "=? AND "+
                CapstoneContract.IndexComponentEntity.COMPANY_NAME + " LIKE '%"+ mSearchFilter + "%'";
        return new CursorLoader(getActivity(),
                uri,
                null,
                mSearchFilter==null? selection:selectionLike,
                new String[]{mTickerIndex},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void handleNavigationPress(int id,Menu menu){
        final SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (mSearchFilter != null) {
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
                mSearchFilter = TextUtils.isEmpty(newText) ? null : newText;
                getLoaderManager().restartLoader(COMPONENT_LOADER, null, FragmentIndexComponents.this);
                return true;
            }
        });
    }


    @Override
    public void onDestroy() {
       // if(Utilities.hasTwoPane(getActivity())) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
       // }
        super.onDestroy();
    }
}