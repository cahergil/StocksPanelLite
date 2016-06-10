package com.carlos.capstone.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Carlos on 25/01/2016.
 */
public class CapstoneProvider extends ContentProvider {
    private  DbHelper mDbHelper;
    private static UriMatcher mUriMatcher=buildUriMatcher();
    private static final int FAVORITE_ID=100;
    private static final int FAVORITE=101;
    private static final int NEWS_ID=200;
    private static final int NEWS=201;
    private static final int INDEX_ID=300;
    private static final int INDEX=301;
    private static final int DEBUG=401;
    private static final int INDEX_DETAIL_ID=500;
    private static final int INDEX_DETAIL=501;
    private static final int INDEX_COMPONENT_ID=600;
    private static final int INDEX_COMPONENT=601;
    private static final int COMPANY=701;
    private static final int STOCK_DETAIL_ID=800;
    private static final int STOCK_STATS_ID=900;
    private static final int STOCK_NEWS_ID=1000;
    private static final int SECURITY_EXCEL=1101;


    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }


     static UriMatcher buildUriMatcher(){
        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);

        final String authority=CapstoneContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,CapstoneContract.PATH_FAVORITES + "/*",FAVORITE_ID);
        matcher.addURI(authority,CapstoneContract.PATH_NEWS + "/#",NEWS_ID);
        matcher.addURI(authority,CapstoneContract.PATH_INDEXES + "/*",INDEX_ID);
        matcher.addURI(authority,CapstoneContract.PATH_INDEX_DETAIL + "/*",INDEX_DETAIL_ID);

        matcher.addURI(authority,CapstoneContract.PATH_FAVORITES,FAVORITE);
        matcher.addURI(authority,CapstoneContract.PATH_NEWS,NEWS);
        matcher.addURI(authority,CapstoneContract.PATH_INDEXES,INDEX);
        matcher.addURI(authority,CapstoneContract.PATH_INDEX_DETAIL,INDEX_DETAIL);
        matcher.addURI(authority,CapstoneContract.PATH_INDEX_COMPONENT + "/*",INDEX_COMPONENT);
        matcher.addURI(authority,CapstoneContract.PATH_COMPANY,COMPANY);
        matcher.addURI(authority,CapstoneContract.PATH_STOCK_DETAIL + "/*",STOCK_DETAIL_ID);
        matcher.addURI(authority,CapstoneContract.PATH_STOCK_STATISTICS + "/*",STOCK_STATS_ID);
        matcher.addURI(authority,CapstoneContract.PATH_STOCK_NEWS + "/*",STOCK_NEWS_ID);
        matcher.addURI(authority,CapstoneContract.PATH_SECURITY_EXCEL,SECURITY_EXCEL);
        matcher.addURI(authority,CapstoneContract.PATH_DEBUG,DEBUG);


        return matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match=mUriMatcher.match(uri);

        switch(match) {

            case FAVORITE_ID:
                return CapstoneContract.FavoritesEntity.CONTENT_ITEM_TYPE;
            case FAVORITE:
                return CapstoneContract.FavoritesEntity.CONTENT_TYPE;
            case NEWS:
                return CapstoneContract.NewsEntity.CONTENT_TYPE;
            case INDEX:
                return CapstoneContract.IndexesEntity.CONTENT_TYPE;
            case INDEX_ID:
                return CapstoneContract.IndexesEntity.CONTENT_ITEM_TYPE;
            case INDEX_COMPONENT:
                return CapstoneContract.IndexComponentEntity.CONTENT_TYPE;
            case COMPANY:
                return CapstoneContract.CompanyEntity.CONTENT_TYPE;
            case STOCK_DETAIL_ID:
                return CapstoneContract.StockDetailEntity.CONTENT_ITEM_TYPE;
            case STOCK_STATS_ID:
                return CapstoneContract.StockStatsEntity.CONTENT_ITEM_TYPE;
            case STOCK_NEWS_ID:
                return CapstoneContract.StockNewsEntity.CONTENT_TYPE;
            case SECURITY_EXCEL:
                return CapstoneContract.SecurityExcelEntity.CONTENT_TYPE;
            case DEBUG:
                return CapstoneContract.DebugEntity.CONTENT_TYPE;


            default:throw new UnsupportedOperationException("Unknown uri :" + uri );

        }


    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor=null;
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case FAVORITE:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.FavoritesEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITE_ID:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.FavoritesEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case NEWS:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.NewsEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group by
                        null, //having
                        sortOrder);
                break;
            case INDEX:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.IndexesEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            case INDEX_DETAIL_ID:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.IndexEtfOrShortInfoDetailEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            case INDEX_COMPONENT:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.IndexComponentEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case COMPANY:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.CompanyEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STOCK_DETAIL_ID:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.StockDetailEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STOCK_STATS_ID:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.StockStatsEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STOCK_NEWS_ID:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.StockNewsEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case SECURITY_EXCEL:
                retCursor=mDbHelper.getReadableDatabase().query(
                        CapstoneContract.SecurityExcelEntity.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder + " LIMIT 100");
                break;
            default: throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }



    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri returnUri=null;
        long _id;
        switch (match) {

            case FAVORITE_ID:
                _id=db.insertWithOnConflict(CapstoneContract.FavoritesEntity.TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
                if (_id>0) {
                    returnUri=CapstoneContract.FavoritesEntity.buildFavoritesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case DEBUG:
                _id=db.insert(CapstoneContract.DebugEntity.TABLE_NAME,null,values);
                if (_id>0) {
                    returnUri=CapstoneContract.DebugEntity.buildDebugUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case INDEX_DETAIL_ID:
                _id=db.insertWithOnConflict(CapstoneContract.IndexEtfOrShortInfoDetailEntity.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id>0) {
                    returnUri= CapstoneContract.IndexEtfOrShortInfoDetailEntity.buildIndexDetailUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case STOCK_DETAIL_ID:
                _id=db.insertWithOnConflict(CapstoneContract.StockDetailEntity.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id>0) {
                    returnUri=CapstoneContract.StockDetailEntity.buildStockDetailUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case STOCK_STATS_ID:
                _id=db.insertWithOnConflict(CapstoneContract.StockStatsEntity.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if(_id>0) {
                    returnUri=CapstoneContract.StockStatsEntity.buildStockStatsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int returnCount=0;
        switch (match)
        {
            case FAVORITE:
                db.beginTransaction();
                returnCount = 0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insertWithOnConflict(CapstoneContract.FavoritesEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case NEWS:
                db.beginTransaction();
                returnCount = 0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insert(CapstoneContract.NewsEntity.TABLE_NAME,null,value);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case INDEX:
                db.beginTransaction();
                returnCount = 0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insertWithOnConflict(CapstoneContract.IndexesEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case INDEX_COMPONENT:
                db.beginTransaction();
                returnCount=0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insertWithOnConflict(CapstoneContract.IndexComponentEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case COMPANY:
                db.beginTransaction();
                returnCount=0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insertWithOnConflict(CapstoneContract.CompanyEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case STOCK_NEWS_ID:
                db.beginTransaction();
                returnCount=0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insertWithOnConflict(CapstoneContract.StockNewsEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            case SECURITY_EXCEL:
                db.beginTransaction();
                returnCount=0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id=db.insert(CapstoneContract.SecurityExcelEntity.TABLE_NAME, CapstoneContract.SecurityExcelEntity.TICKER, value);
                        //long _id=db.insertWithOnConflict(CapstoneContract.SecurityExcelEntity.TABLE_NAME,null,value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returnCount;
            default:
                return super.bulkInsert(uri,values);
        }
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int rowsDeleted=0;
        final int match = mUriMatcher.match(uri);
        switch (match){
            case FAVORITE_ID:
                rowsDeleted=db.delete(CapstoneContract.FavoritesEntity.TABLE_NAME,selection,selectionArgs);
                break;
            case NEWS:
                rowsDeleted=db.delete(CapstoneContract.NewsEntity.TABLE_NAME,selection,selectionArgs);
                break;
            case INDEX:
                rowsDeleted=db.delete(CapstoneContract.IndexesEntity.TABLE_NAME,selection,selectionArgs);
                break;
            case INDEX_COMPONENT:
                rowsDeleted=db.delete(CapstoneContract.IndexComponentEntity.TABLE_NAME,selection,selectionArgs);
                break;
            case STOCK_NEWS_ID:
                rowsDeleted=db.delete(CapstoneContract.StockNewsEntity.TABLE_NAME,selection,selectionArgs);
                break;
            case SECURITY_EXCEL:
                rowsDeleted=db.delete(CapstoneContract.SecurityExcelEntity.TABLE_NAME,selection,selectionArgs);
                break;
            default:throw new UnsupportedOperationException("Unknown ur:"+uri);
        }
        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int rowsUpdated=0;
        final int match=mUriMatcher.match(uri);
        switch (match) {
            case INDEX_ID:
                rowsUpdated=db.update(CapstoneContract.IndexesEntity.TABLE_NAME,values,selection,selectionArgs);
                break;
        }
        if (rowsUpdated>0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }
}
