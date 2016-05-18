package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.utils.TimeMeasure;
import com.carlos.capstone.utils.Utilities;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by Carlos on 19/02/2016.
 */
public class DownloadSymbol extends IntentService {
    private TimeMeasure tm;
    //private  static final String TAG=
    Vector<ContentValues> symbolList=new Vector<ContentValues>();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadSymbol(String name) {
        super(name);
    }

    public DownloadSymbol() {
        super("DownloadSymbols");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        tm=new TimeMeasure("DOWNLOADSYMBOL");
        String[] urls=new String[]{
                getString(R.string.downloadsymbol_url_nyse),
                getString(R.string.downloadsymbol_url_nasdaq),
                getString(R.string.downloadsymbol_url_amex)
        };

        for(int i=0;i<urls.length;i++) {
            tm.log("=== BEFORE GRAB PAGE");
            String data = Utilities.grabPage(tm,urls[i]);
            tm.log("=== AFTER GRAB PAGE");


            int insertedRows = 0;
            tm.log("##### BEFORE EXTRACT LIST " + i);
            switch (i) {
                case 0:symbolList = extractFieldsFromString(data,"NYSE");
                    break;
                case 1:symbolList = extractFieldsFromString(data,"NASDAQ");
                    break;
                case 2:symbolList = extractFieldsFromString(data,"AMEX");

            }

            tm.log("##### AFTER EXTRACT LIST " + i);
            ContentValues[] contentValues = new ContentValues[symbolList.size()];
            symbolList.toArray(contentValues);
            Uri uri = CapstoneContract.CompanyEntity.CONTENT_URI;
            tm.log("##### BEFORE BulkInsert "+ i);
            insertedRows = getContentResolver().bulkInsert(uri, contentValues);
            tm.log("##### AFTER BulkInsert "+ i);
            Log.d("VILLANUEVA", "Rows inserted in Company table:" + insertedRows);
        }

    }

    public Vector<ContentValues> extractFieldsFromString(String data,String stockMarket) {
        Vector<ContentValues> symbolList = new Vector<ContentValues>();
        StringTokenizer companies = new StringTokenizer(data, "\n");
        //discard header
        if (companies.hasMoreTokens()) {
            companies.nextToken();
        }
        ContentValues contentValues;
        String c;
        String[] splittedString;
        while (companies.hasMoreTokens()) {

            c=companies.nextToken();
            splittedString=c.split(",\"");
            contentValues=new ContentValues();

            for (int i = 0; i <splittedString.length ; i++) {

                if (i == 0) {
                    contentValues.put(CapstoneContract.CompanyEntity.SYMBOL, splittedString[i].replaceAll("\"", ""));
                } else if (i == 1) {
                    contentValues.put(CapstoneContract.CompanyEntity.NAME,splittedString[i].replaceAll("\"", ""));
                } else if (i == 2) {
                    contentValues.put(CapstoneContract.CompanyEntity.LAST_SALE, splittedString[i].replaceAll("\"", ""));
                } else if (i == 3) {
                    contentValues.put(CapstoneContract.CompanyEntity.MARKET_CAP, splittedString[i].replaceAll("\"", ""));
                } else if (i == 4) {
                    contentValues.put(CapstoneContract.CompanyEntity.IPO_YEAR, splittedString[i].replaceAll("\"", ""));
                } else if (i == 5) {
                    contentValues.put(CapstoneContract.CompanyEntity.SECTOR, splittedString[i].replaceAll("\"", ""));
                } else if (i == 6) {
                    contentValues.put(CapstoneContract.CompanyEntity.STOCK_EXCHANGE,stockMarket);
                } else if (i>6) {
                    break;
                }
            }

            symbolList.add(contentValues);
        }
        Log.d("VILLANUEVA", "grabbed companies" + symbolList.size());

        return symbolList;
    }


}
