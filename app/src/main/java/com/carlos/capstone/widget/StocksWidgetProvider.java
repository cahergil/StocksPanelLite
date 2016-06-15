package com.carlos.capstone.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.carlos.capstone.MainActivity;
import com.carlos.capstone.R;
import com.carlos.capstone.services.UpdateWidgetService;
import com.carlos.capstone.services.WidgetSwitchLauncherService;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Carlos on 01/05/2016.
 */
public class StocksWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG=StocksWidgetProvider.class.getSimpleName();
    public static final String ACTION_DATA_UPDATED ="com.carlos.capstone.ACTION_DATA_UPDATED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(LOG_TAG,"onUpdate");
        for (int appWidgetId : appWidgetIds) {

            RemoteViews view=new RemoteViews(context.getPackageName(), R.layout.widget_details);

            //create an intent to lauch MainActivity when the user clicks
            // the orange upper bar of the widget. Only for views not belonging to the Listview(collection)
            Intent intentMainActivity = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentMainActivity, 0);
            view.setOnClickPendingIntent(R.id.widget,pendingIntent);


            //intent for launching UpdateWidgetService
            Intent launchUpdateService = new Intent(context, UpdateWidgetService.class);
            PendingIntent pendingIntentUpdate=PendingIntent.getService(context,0,launchUpdateService,0);
            view.setOnClickPendingIntent(R.id.imgUpdate,pendingIntentUpdate);

            //set vector drawable update inside the orange bar
           //view.setImageViewResource(R.id.imgUpdate,R.drawable.widget_update);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                view.setContentDescription(R.id.imgUpdate, context.getString(R.string.swp_update_widget_img));
            }

            //update Widget date
            Date date=new Date(System.currentTimeMillis());
            DateFormat formatter=DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
            view.setTextViewText(R.id.date,formatter.format(date));

            //bind the collection(Listview)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context, view);
            } else {
                setRemoteAdapterV11(context,view);
            }

            Intent clickIntent = new Intent(context, WidgetSwitchLauncherService.class);
            PendingIntent clickPI = PendingIntent.getService(context, 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            view.setPendingIntentTemplate(R.id.widgetList, clickPI);

            view.setEmptyView(R.id.widgetList,R.id.widgetEmpty);
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, view);

            Log.d(LOG_TAG,"UpdatedWidget");

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(LOG_TAG, "inside onReceive widget");
        if(ACTION_DATA_UPDATED.equals(intent.getAction())) {
            try {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
                //testing
                if(appWidgetIds!=null && appWidgetIds.length==0) {
                    Log.d(LOG_TAG,"zero appWidgetId array");
                } else if(appWidgetIds!=null && appWidgetIds.length>0) {
                    Log.d(LOG_TAG,"non zero appWidgetId array");
                } else if(appWidgetIds==null) {
                    Log.d(LOG_TAG,"null appWidgetId array");
                }

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetList);
                Log.d(LOG_TAG, "after notifyAppWidgetViewDataChanged");

            }catch (Exception e) {
                Log.d(LOG_TAG,"error in onReceive widget:"+e.getMessage());
            }
        }
        super.onReceive(context, intent); //moved here instead of the first line
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widgetList, new Intent(context, WidgetRemoteViewService.class));
    }
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widgetList,new Intent(context, WidgetRemoteViewService.class));
    }

}
