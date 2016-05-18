package com.carlos.capstone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlos.capstone.adapters.NewsAdapter;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.services.StockNewsService;
import com.carlos.capstone.utils.DividerItemDecoration;

/**
 * Created by Carlos on 25/12/2015.
 */
public class FragmentStockNews extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public String mSymbol;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    public static final int NEWS_LOADER=0;
    private View mEmptyView;
    private static final String LOG_TAG=FragmentStockNews.class.getSimpleName();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG,"onCreateView Fragment News");
        View view=inflater.inflate(R.layout.fragment_stock_news,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
//        recyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.i("ONFOCUSCHANGE- reclist", "focus has changed I repeat the focus has changed! current focus = " +hasFocus);
//                if(hasFocus){
//                  recyclerView.getChildAt(0).requestFocus();
//                }
//
//            }
//        });
        mEmptyView=view.findViewById(R.id.emptyView);
        mAdapter=new NewsAdapter(getActivity(),mEmptyView);
        recyclerView.setAdapter(mAdapter);

        if(savedInstanceState!=null) {
            mSymbol=savedInstanceState.getString(getString(R.string.symbol_key));


        } else {
           Bundle bundle=getArguments();
           if(bundle!=null) {
               mSymbol = bundle.getString(getString(R.string.symbol_key));
           }
           Intent intent=new Intent(getActivity(), StockNewsService.class);
           intent.putExtras(bundle);
           getActivity().startService(intent);

        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().restartLoader(NEWS_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(getString(R.string.symbol_key), mSymbol);


    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri= CapstoneContract.StockNewsEntity.buildStockNewsWithSymbol(mSymbol);
        return new CursorLoader(getActivity(),
                uri,
                null,
                CapstoneContract.StockNewsEntity.SYMBOL + "=?",
                new String[]{mSymbol},
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

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"FragmentStockNews on Destroy");
        super.onDestroy();
    }
}
