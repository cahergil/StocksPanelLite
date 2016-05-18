package com.carlos.capstone.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.utils.Utilities;

import java.text.DecimalFormat;

/**
 * Created by Carlos on 01/05/2016.
 */
public class ListProviderFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int appWidgetId;
    private Cursor mCursor;

    private static int COL_SECURITY_TICKER=0;
    private static int COL_SECURITY_PRICE=1;
    private static int COL_SECURITY_DATE=2;
    private static int COL_SECURITY_CHANGE=3;
    private static int COL_SECURITY_TYPE=4;
    private static int COL_SECURITY_NAME=5;

    public ListProviderFactory(Context context, Intent intent) {
        this.context=context;
        this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {

    }

    // Called when the underlying data collection being displayed is
    // modified. AppWidgetManager’s
    // notifyAppWidgetViewDataChanged method to trigger this handler
    @Override
    public void onDataSetChanged() {
        if(mCursor!=null) {
            mCursor.close();
        }
        String[] projection=new String[] {
                CapstoneContract.FavoritesEntity.COMPANY_TICKER,
                CapstoneContract.FavoritesEntity.VALUE,
                CapstoneContract.FavoritesEntity.TRADE_DATE,
                CapstoneContract.FavoritesEntity.CHANGE_PERCENT,
                CapstoneContract.FavoritesEntity.MARKET,
                CapstoneContract.FavoritesEntity.COMPANY_NAME
        };
        final long identityToken = Binder.clearCallingIdentity();
        Uri uri= CapstoneContract.FavoritesEntity.CONTENT_URI;
        mCursor=context.getContentResolver().query(
                uri,
                projection, //projection
                null, //selection
                null, //selectionArgs
                null); //sortOrder

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    @Override
    public int getCount() {
        return mCursor==null? 0:mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews row=new RemoteViews(context.getPackageName(), R.layout.widget_item);

        //Ticker
        row.setTextViewText(R.id.securityTicker,mCursor.getString(COL_SECURITY_TICKER));


        //securityType
        String type=mCursor.getString(COL_SECURITY_TYPE);
        row.setTextViewText(R.id.securityType,type);
        int color=0;
        if (type.equals(context.getString(R.string.index).toUpperCase())) {
            color= ContextCompat.getColor(context,R.color.index_color);
        } else if (type.equals(context.getString(R.string.equity).toUpperCase())) {
            color=ContextCompat.getColor(context,R.color.equity_color);
        } else  {
            color=ContextCompat.getColor(context,R.color.etf_color);
        }//if
        row.setTextColor(R.id.securityType,color);


        //price
        float price=mCursor.getFloat(COL_SECURITY_PRICE);
        String formattedPrice=new DecimalFormat(context.getString(R.string.format_decimal_value)).format(price);
        row.setTextViewText(R.id.securityPrice,formattedPrice);

//        //date
//        row.setTextViewText(R.id.securityDate,mCursor.getString(COL_SECURITY_DATE));


        //change
        float change=mCursor.getFloat(COL_SECURITY_CHANGE);
        row.setTextViewText(R.id.securityChange,context.getString(R.string.format_change_percent_without_parenthesis,change));

        if (change < 0) {
            color = Color.RED;
        } else if (change > 0) {
            color = Color.GREEN;
        } else  {
            color =  ContextCompat.getColor(context, R.color.equal_lime_500);
        }//if
        row.setTextColor(R.id.securityChange,color);

        //last trade date
        String date=Utilities.getDateFromStringDate(mCursor.getString(COL_SECURITY_DATE));
        row.setTextViewText(R.id.securityDate,date);


        Intent intent;
        Bundle bundle=new Bundle();
        bundle.putString(context.getString(R.string.lpf_security_type_key),mCursor.getString(COL_SECURITY_TYPE));

        if(mCursor.getString(COL_SECURITY_TYPE).equals(context.getString(R.string.equity).toUpperCase())) {

            Intent intentEquity=new Intent();
            bundle.putString(context.getString(R.string.symbol_key),mCursor.getString(COL_SECURITY_TICKER));
            bundle.putString(context.getString(R.string.company_name_key),mCursor.getString(COL_SECURITY_NAME));
            intentEquity.putExtras(bundle);
            intent=intentEquity;

        } else {
            Intent intentIndexEtf=new Intent();
            bundle.putString(context.getString(R.string.ticker_bundle_key),mCursor.getString(COL_SECURITY_TICKER));
            intentIndexEtf.putExtras(bundle);
            intent=intentIndexEtf;

        }
        row.setOnClickFillInIntent(R.id.widgetItem,intent);


        //set contents descriptions
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            row.setContentDescription(R.id.securityTicker,context.getString(R.string.lpf_ticker) + mCursor.getString(COL_SECURITY_TICKER));
            row.setContentDescription(R.id.securityType,context.getString(R.string.lpf_type) + mCursor.getString(COL_SECURITY_TYPE));
            row.setContentDescription(R.id.securityPrice,context.getString(R.string.lpf_price) + formattedPrice);
            row.setContentDescription(R.id.securityDate,context.getString(R.string.lpf_date) + date);
            row.setContentDescription(R.id.securityChange,context.getString(R.string.lpf_change)+ change);
        }


        return row;
    }

    // Optionally specify a “loading” view to display. Return null to
    // use the default.
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Return true if the unique IDs provided by each item are stable --
    // that is, they don’t change at run time.
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
