package com.carlos.capstone.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.carlos.capstone.DetailIndexActivity;
import com.carlos.capstone.DetailStockActivity;
import com.carlos.capstone.MainActivity;
import com.carlos.capstone.R;
import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 07/05/2016.
 */
public class WidgetSwitchLauncherService extends IntentService {
    private static final String LOG_TAG=WidgetSwitchLauncherService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WidgetSwitchLauncherService(String name) {
        super(name);
    }
    public WidgetSwitchLauncherService(){
        super(LOG_TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle=intent.getExtras();
        if(bundle==null) {
            return;
        }
        Intent launchIntent=null;
        bundle.putBoolean(getString(R.string.stock_from_widget_key), true);
        if(!Utilities.hasTwoPane(getApplicationContext())) {
            String securityType = bundle.getString(getString(R.string.lpf_security_type_key));
            if (securityType != null) {
                if (securityType.equals(getString(R.string.equity).toUpperCase())) {
                    launchIntent = new Intent(getApplicationContext(), DetailStockActivity.class);
                } else {
                    launchIntent = new Intent(getApplicationContext(), DetailIndexActivity.class);
                }

                launchIntent.putExtras(bundle);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);
            }
        } else {

            launchIntent=new Intent(getApplicationContext(), MainActivity.class);
            launchIntent.putExtras(bundle);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchIntent);



        }

    }
}
