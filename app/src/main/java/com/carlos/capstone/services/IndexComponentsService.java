package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.carlos.capstone.R;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.iretrofit.IndexComponentsRApi;
import com.carlos.capstone.models.Components;

import java.util.List;
import java.util.Vector;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Carlos on 02/03/2016.
 */
public class IndexComponentsService extends IntentService {
    private String mTickerQuery;
    private String mTickerIndex;
    private static final String LOG_TAG = IndexComponentsService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IndexComponentsService(String name) {
        super(name);
    }

    public IndexComponentsService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        mTickerIndex = bundle.getString(getString(R.string.ticker_bundle_key));
        mTickerQuery = bundle.getString(getString(R.string.ticker_query_key));
        loadComponents();
    }

    private void loadComponents() {
        IndexComponentsRApi.IIndexComponents service = IndexComponentsRApi.getMyService();
        //String qParam="use%20%22https%3A%2F%2Fraw.githubusercontent.com%2Fdavidayalas%2Fyql-stocks%2Fmaster%2Fstocks.xml%22%20as%20bolsa%3B%20select%20*%20from%20bolsa%20where%20url%3D%22http%3A%2F%2Fwww.eleconomista.es/indice/EUROSTOXX-50%22";
        String qParam = getString(R.string.qParam) + mTickerQuery + getString(R.string.index_components_qParam_right);
        Log.d(LOG_TAG, "qParam:" + qParam);
        Call<Components> call = service.getComponentsByIndex(qParam);

        call.enqueue(new Callback<Components>() {
            @Override
            public void onResponse(Response<Components> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    Components components = response.body();
                    if (components == null) return;
                    Components.QueryEntity queryEntity = components.getQuery();
                    if (queryEntity == null) return;
                    Components.QueryEntity.ResultsEntity resultsEntity = queryEntity.getResults();
                    if (resultsEntity == null) return;
                    Components.QueryEntity.ResultsEntity.ItemsEntity itemsEntity = resultsEntity.getItems();
                    if (itemsEntity == null) return;
                    List<Components.QueryEntity.ResultsEntity.ItemsEntity.ItemEntity> item = itemsEntity.getItem();
                    if (item==null) return;
                    //now it is fine to delete old records(assuming this response ALWAYS has records)
                    //we always perform a delete-insert operation, thus preventing the situation where
                    //the components in the index change, specially when there are components that no
                    //longer form part of the index.
                    Uri uri_delete = CapstoneContract.IndexComponentEntity.buildIndexComponentWithIndex(mTickerIndex);
                    String where = CapstoneContract.IndexComponentEntity.INDEX_NAME + "=?";
                    String[] selectionArgs = new String[]{mTickerIndex};
                    int rowsDeleted = getContentResolver().delete(uri_delete, where, selectionArgs);
                    Log.d(LOG_TAG, "Index Components rows deleted" + rowsDeleted);

                    Vector<ContentValues> values = new Vector<ContentValues>();
                    for (int i = 0; i < item.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(CapstoneContract.IndexComponentEntity.INDEX_NAME, mTickerIndex);
                        contentValues.put(CapstoneContract.IndexComponentEntity.COMPANY_NAME, item.get(i).getName());
                        contentValues.put(CapstoneContract.IndexComponentEntity.LAST, item.get(i).getLast());
                        contentValues.put(CapstoneContract.IndexComponentEntity.VAR, item.get(i).getVar());
                        if (item.get(i).getVar_dollar() != null) {
                            contentValues.put(CapstoneContract.IndexComponentEntity.VAR_DOLLAR, item.get(i).getVar_dollar());
                            contentValues.put(CapstoneContract.IndexComponentEntity.VAR_EURO, "null");
                        } else {
                            contentValues.put(CapstoneContract.IndexComponentEntity.VAR_EURO, item.get(i).getVar_euro());
                            contentValues.put(CapstoneContract.IndexComponentEntity.VAR_DOLLAR, "null");
                        }
                        contentValues.put(CapstoneContract.IndexComponentEntity.VOLUME, item.get(i).getVolume());
                        contentValues.put(CapstoneContract.IndexComponentEntity.TIME, item.get(i).getTime());
                        contentValues.put(CapstoneContract.IndexComponentEntity.FREE_FIELD, "");
                        values.add(contentValues);

                    }
                    int inserted_data = 0;
                    Uri uri = CapstoneContract.IndexComponentEntity.buildIndexComponentWithIndex(mTickerIndex);
                    ContentValues[] insert_data = new ContentValues[values.size()];
                    values.toArray(insert_data);

                    inserted_data = getContentResolver().bulkInsert(uri, insert_data);
                    Log.d(LOG_TAG, "IndexComponents. Succesfully Inserted : " + inserted_data);
                } else {
                    // bad networks request(400) or 404
                    // bnr cuando las tablas estan caidas
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(LOG_TAG, "onFailure" + t.getMessage());

            }
        });
    }
}
