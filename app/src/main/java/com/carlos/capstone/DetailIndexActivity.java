package com.carlos.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 09/02/2016.
 */
public class DetailIndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_detail);
        String ticker=null;
        boolean fromWidget=false;



        if(savedInstanceState==null) {//si no pongo esto se bloquea la pagina summary al rotar
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    ticker = bundle.getString(getString(R.string.ticker_bundle_key));
                    fromWidget=bundle.getBoolean(getString(R.string.stock_from_widget_key),false);

                }
            }

            FragmentIndexDetail fragmentIndexDetail = new FragmentIndexDetail();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.ticker_bundle_key), ticker);
            bundle.putBoolean(getString(R.string.stock_from_widget_key),fromWidget);
            fragmentIndexDetail.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragmentIndexDetail)
                    .commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),"DetailIndexActivity");

    }


}
