package com.carlos.capstone.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.capstone.FragmentStockSummary;
import com.carlos.capstone.R;
import com.carlos.capstone.customcomponents.AutoResizeTextView;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.models.HistoricalDataResponseTimestamp;
import com.carlos.capstone.models.IndexDataUnit;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Carlos on 23/12/2015.
 */
public class Utilities {
    public static final String LOG_TAG = Utilities.class.getSimpleName();
    public static final int ONE_BILLION = 1000000000;
    public static final int ONE_MILLION = 1000000;
    public static final int ONE_THOUSAND = 1000;


    public static String getDateFromStringDate(String dateToFormat) {
        Date date;

        if(dateToFormat==null) {
            return null;
        }
        DateFormat formatter=DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        date = new Date(Long.parseLong(dateToFormat)*1000);
        return formatter.format(date);

    }

    public static String formatXLabel(String dateToFormat) {
        return formatXLabel(FragmentStockSummary.CHART_MODE_1D, dateToFormat);
    }


    public static String formatXLabel(@FragmentStockSummary.ChartMode int chartmode, String dataToFormat) {
        SimpleDateFormat formatter6m2y = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter5yTo = new SimpleDateFormat("MMM yy");
        SimpleDateFormat formatter6mTo = new SimpleDateFormat("dd MMM");
        SimpleDateFormat formatter2yTo = new SimpleDateFormat("MMM yy");
        SimpleDateFormat formatter1dTo = new SimpleDateFormat("kk:mm");
        SimpleDateFormat formatter7dTo = new SimpleDateFormat("d/MM kk:mm");
        Date date = null;
        if (chartmode == FragmentStockSummary.CHART_MODE_1D) {
            date = new Date((long) Long.parseLong(dataToFormat) * 1000);
            return formatter1dTo.format(date);

        } else if (chartmode == FragmentStockSummary.CHART_MODE_7D) {
            date = new Date((long) Long.parseLong(dataToFormat) * 1000);
            return formatter7dTo.format(date);

        } else if (chartmode == FragmentStockSummary.CHART_MODE_1M) {

            dataToFormat = dataToFormat.substring(6, 8);
            return dataToFormat;
        } else if (chartmode == FragmentStockSummary.CHART_MODE_6M) {
            try {
                date = formatter6m2y.parse(dataToFormat);
                return formatter6mTo.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (chartmode == FragmentStockSummary.CHART_MODE_2Y) {
            try {
                date = formatter6m2y.parse(dataToFormat);
                return formatter2yTo.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (chartmode == FragmentStockSummary.CHART_MODE_5Y) {
            try {
                date = formatter6m2y.parse(dataToFormat);
                return formatter5yTo.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (chartmode == FragmentStockSummary.CHART_MODE_MAX) {
            try {
                date = formatter6m2y.parse(dataToFormat);
                return formatter5yTo.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public static String stockExchangeMapper(String market) {
        //i thought there were only three categories: NMS;NQY and ASE, but
        //for example for ticker EEMA there is PCX

        //in case change in the future to lowercase
        if (market == null) return market;
        market = market.toUpperCase();
        if (market.equals("NMS")) {
            return "NASDAQ";
        } else if (market.equals("NYQ")) {
            return "NYSE";
        } else if (market.equals("ASE")) {
            return "AMEX";
        }
        return market;
    }

    public static String formatChartDescription(@FragmentStockSummary.ChartMode int chartMode, String ticker) {


        String description = null;
        switch (chartMode) {
            case FragmentStockSummary.CHART_MODE_1D:
                description = "Interactive 1 day chart ";
                break;
            case FragmentStockSummary.CHART_MODE_7D:
                description = "Interactive 7 days chart ";
                break;
            case FragmentStockSummary.CHART_MODE_1M:
                description = "Interactive 1 month chart ";
                break;
            case FragmentStockSummary.CHART_MODE_6M:
                description = "Interactive 6 months chart ";
                break;
            case FragmentStockSummary.CHART_MODE_2Y:
                description = "Interactive 2 years chart ";
                break;
            case FragmentStockSummary.CHART_MODE_5Y:
                description = "Interactive 5 years chart ";
                break;
            case FragmentStockSummary.CHART_MODE_MAX:
                description = "Interactive max span chart ";
        }
        ticker = ticker.toUpperCase();
        description = description + ticker;
        return description;
    }

    public static void setUpDownIcon(ImageView imageView, Double change, Context context) {
        int drawable;
        String talkbackDesc;
        if (change == null) return;

        if (change < 0) {
            drawable = R.drawable.down;
            talkbackDesc=context.getString(R.string.u_icon_down);
        } else if (change > 0) {
            drawable = R.drawable.up;
            talkbackDesc=context.getString(R.string.u_icon_up);
        } else {
            drawable = R.drawable.equal;
            talkbackDesc=context.getString(R.string.u_icon_equal);
        }
        imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable));
        imageView.setContentDescription(talkbackDesc);
    }

    public static void setUpDownColors(AutoResizeTextView textView, Double change, Context context) {
        int color = 0;
        if (change < 0) {
            color = ContextCompat.getColor(context, R.color.down_red_600);
        } else if (change > 0) {
            color = ContextCompat.getColor(context, R.color.up_green_900);
        } else if (change == 0) {
            color = ContextCompat.getColor(context, R.color.equal_lime_500);
        }
        textView.setTextColor(color);
    }

    public static void setUpDownColorsMaterial(AutoResizeTextView textView, Double change, Context context) {
        Drawable drawable = null;
        int sdk = Build.VERSION.SDK_INT;
        if (change < 0) {
            drawable = ContextCompat.getDrawable(context, R.drawable.percent_change_red_box);
        } else if (change > 0) {
            drawable = ContextCompat.getDrawable(context, R.drawable.percent_change_green_box);
        } else if (change == 0) {
            drawable = ContextCompat.getDrawable(context, R.drawable.percent_change_equal_box);
        }//if
        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(drawable);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
        } else {
            setUpDownColors(textView, change, context);
        }


    }

    public static String formatLastTradeDateSummary(String lastTradeDate, String lastTradeTime) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy h:mma", Locale.US);
        //parser.setTimeZone(TimeZone.getTimeZone("EST"));//incorrect
        //http://www.timeanddate.com/time/zones/na
        //to handle appropriately daylight saving time  http://stackoverflow.com/questions/10545960/how-to-tackle-daylight-savings-using-timezone-in-java
        parser.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        DateFormat dateTimeFormatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        String str = lastTradeDate + " " + lastTradeTime;


        try {

            return dateTimeFormatter.format(parser.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void formatChangeSummary(Context context, TextView textView, String changeLocalized,
                                           String changePercentLocalized, double change) {
        int color;
        if (change < 0) {
            color = ContextCompat.getColor(context, R.color.down_red_600);
        } else if (change > 0) {
            color = ContextCompat.getColor(context, R.color.ltgreen);
        } else {
            color = ContextCompat.getColor(context, R.color.equal_lime_500);
        }
        textView.setTextColor(color);
        textView.setText(changeLocalized + "(" + changePercentLocalized + "%)");
    }

    public static String localizePercentWithPrefixValue(Context context, String value) {
        if (value == null) {
            return null;
        }
        DecimalFormat parser = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setPositivePrefix(context.getString(R.string.plus_string));
        formatter.setNegativePrefix(context.getString(R.string.minus_string));
        formatter.setMaximumFractionDigits(2);
        parser.setPositivePrefix(context.getString(R.string.plus_string));
        parser.setNegativePrefix(context.getString(R.string.minus_string));
        parser.setMaximumFractionDigits(2);
        double parsedValue = 0;
        String formattedValue = null;
        try {
            parsedValue = parser.parse(value).doubleValue();
            formattedValue = formatter.format(parsedValue);
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag), "" + e.getMessage());
        }
        return formattedValue;
    }

    public static String localizePercentValue(Context context, float value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setPositivePrefix("+");
        return context.getString(R.string.format_change_percent_without_parenthesis2, formatter.format(value));
    }

    public static String localizeDecimalValue(float value) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        return formatter.format(value);


    }

    public static String localizeMarket_Cap(Context context, String marketCap) {

        if (marketCap == null) return context.getString(R.string.na);

        DecimalFormat parser = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        parser.setMaximumFractionDigits(2);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(2);

        String suffix = marketCap.substring(marketCap.length() - 1);
        marketCap = marketCap.substring(0, marketCap.length() - 1);
        String formattedValue = null;

        double parsedValue = 0;
        try {
            parsedValue = parser.parse(marketCap).doubleValue();
            formattedValue = formatter.format(parsedValue);
            return formattedValue + suffix;
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag), "" + e.getMessage());
            return context.getString(R.string.na);
        }

    }

    public static String localizeDays_Range(Context context, String range) {

        if (range == null) return context.getString(R.string.na);
        //String[] values=range.split(" - ");
        String[] values = range.split(context.getString(R.string.day_range_split_string));

        DecimalFormat parser = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        parser.setMaximumFractionDigits(2);

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(2);

        String finalString = "";
        for (int i = 0; i < values.length; i++) {
            String formattedValue = null;
            double parsedValue = 0;
            try {
                parsedValue = parser.parse(values[i]).doubleValue();
                formattedValue = formatter.format(parsedValue);
            } catch (ParseException e) {
                Log.d(LOG_TAG, "" + e.getMessage());
                return context.getString(R.string.na);
            }
            if (i == 0) {
                formattedValue = formattedValue + " - ";
            }
            finalString = finalString + formattedValue;
        }
        return finalString;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    public static String formatValueMonetaryUnits(String value) {
        if (value.equals("")) {
            return value;
        }

        double val = Double.parseDouble(value);

        if (val >= ONE_BILLION || val <= ONE_BILLION * (-1)) {
            return localizeDecimalValue((float) val / ONE_BILLION) + "B";
        } else if ((val >= ONE_MILLION && val < ONE_BILLION) || (val <= ONE_MILLION * (-1)) && val > ONE_BILLION * (-1)) {
            return localizeDecimalValue((float) val / ONE_MILLION) + "M";

        } else if ((val > (-1) * ONE_MILLION) && val < ONE_MILLION) {
            return localizeDecimalValue((float) val / ONE_THOUSAND) + "K";
        }

        return value;
    }

    public static String formatValueMonetaryUnits(Context context, double val) {


        if (val >= ONE_BILLION || val <= ONE_BILLION * (-1)) {
            return context.getString(R.string.format_billions, val / ONE_BILLION);

        } else if ((val >= ONE_MILLION && val < ONE_BILLION) || (val <= ONE_MILLION * (-1)) && val > ONE_BILLION * (-1)) {
            return context.getString(R.string.format_millions, val / ONE_MILLION);

        } else if ((val > (-1) * ONE_MILLION) && val < ONE_MILLION) {
            return context.getString(R.string.format_thounsands, val / ONE_THOUSAND);
        }

        return null;
    }

    public static String stripOutParentheses(String date) {
        final Pattern PARENTHESES = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = PARENTHESES.matcher(date);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            //  throw new IllegalArgumentException("Unknown format");
            return date;
        }

    }

    public static String parseUrl(String url) {
        int result = url.indexOf("*");
        if (result == -1) {
            return url;
        } else if (result > 0) {
            url = url.substring(url.indexOf("*") + 1);
            return url;
        } else {
            return "";
        }

    }

    public static String formatPercentages(String value) {

        return value + "%";
    }

    public static void lockScreenOrientation(Context ctx) {
        int currentOrientation = ctx.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            ((AppCompatActivity) ctx).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            ((AppCompatActivity) ctx).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public static void unlockScreenOrientation(Context ctx) {
        ((AppCompatActivity) ctx).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public static Bitmap screenShot(View view, Context context, boolean drawBackground) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (drawBackground) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        view.draw(canvas);
        return bitmap;
    }

    public static String getShareModeFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.pref_share_key),
                context.getString(R.string.pref_share_default));
    }

    public static void saveIndexChartStateInPreferences(Context context,
                                                        @FragmentStockSummary.ChartMode int chartMode,
                                                        int buttonId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("chart_mode_index", chartMode);
        editor.putInt("button_id_index", buttonId);
        editor.commit();
    }


    public static void saveChartStateInPreferences(Context context,
                                                   @FragmentStockSummary.ChartMode int chartMode,
                                                   int buttonId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("chart_mode", chartMode);
        editor.putInt("button_id", buttonId);
        editor.commit();
    }

    @SuppressWarnings("ResourceType")
    public static
    @FragmentStockSummary.ChartMode
    int getIndexChartModeFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("chart_mode", FragmentStockSummary.CHART_MODE_1D);

    }


    @SuppressWarnings("ResourceType")
    public static
    @FragmentStockSummary.ChartMode
    int getChartModeFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("chart_mode", FragmentStockSummary.CHART_MODE_1D);

    }

    public static int getIndexButtonIdFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("button_id", R.id.btn1d);
    }

    public static int getButtonIdFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("button_id", R.id.btn1d);
    }

    public static String extractNewsFromRss(String content) {
        String description = null;
        description = content;
        int index = description.indexOf("<a");
        //index==-1 no existe <a ->solo existe <p> </p>, ed, solo hay texto
        if (index == -1) {
            Pattern pattern = Pattern.compile("<p>(.*)</p>");
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                //   Log.d("VILLANUEVA","counter:"+(++counter)+",p:"+matcher.group(1));
                description = matcher.group(1);
            }


        } else {//hay texto y fotos
            //extraer texto
            Pattern pattern = Pattern.compile("</a>(.*)</p>");
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                //Log.d("VILLANUEVA","counter:"+(++counter)+",p:"+matcher.group(1));
                description = matcher.group(1);
            }
        }
        return description;
    }

    public static IndexDataUnit extractToIndexesData(List<HistoricalDataResponseTimestamp.SeriesEntity> lista,
                                                     double previousClose) {
        IndexDataUnit indexDataUnit = new IndexDataUnit();
        for (int i = 0; i < lista.size(); i++) {
            indexDataUnit.getyValues().add(calculatePercentaje(lista.get(i).getClose(), previousClose));
            indexDataUnit.getxLabels().add(Utilities.formatXLabel(String.valueOf(lista.get(i).getTimestamp())));
            indexDataUnit.getyValuesMarkerView().add(lista.get(i).getClose());
        }
        return indexDataUnit;
    }

    public static double calculatePercentaje(double value, double reference) {

        return ((value - reference) / reference) * 100;

    }

    public static ArrayList<IndexDataUnit> normalizeDataUnits(List<IndexDataUnit> list) {
        int[] xSizes = new int[3];
        ArrayList<IndexDataUnit> copy = new ArrayList<IndexDataUnit>();
        for (int i = 0; i < list.size(); i++) {
            xSizes[i] = list.get(i).getxLabels().size();
            if (list.size() == 1) {
                xSizes[i + 2] = xSizes[i + 1] = xSizes[i];
            } else if (list.size() == 2) {
                xSizes[i + 1] = xSizes[i];
            }
        }
        int min = Math.min(xSizes[0], Math.min(xSizes[1], xSizes[2]));

        // IndexDataUnit indexDataUnit=new IndexDataUnit();
        for (int i = 0; i < list.size(); i++) {
            List<Double> yValues = list.get(i).getyValues();
            List<Double> yValuesMV = list.get(i).getyValuesMarkerView();
            List<String> xValues = list.get(i).getxLabels();
            List<Double> yValuesCopy = new ArrayList<Double>();
            List<Double> yValuesMVCopy = new ArrayList<Double>();
            List<String> xValuesCopy = new ArrayList<String>();
//            FATAL EXCEPTION: pool-10-thread-3
//            java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0
//            at java.util.ArrayList.throwIndexOutOfBoundsException(ArrayList.java:255)
//            at java.util.ArrayList.get(ArrayList.java:308)
//            at com.carlos.capstone.utils.Utilities.normalizeDataUnits(Utilities.java:598)
//
//           598     yValuesMVCopy.add(yValuesMV.get(j));
//
//            at com.carlos.capstone.sync.CapstoneSyncAdapter.sendDataUnitToFragmentMain(CapstoneSyncAdapter.java:527)
//            at com.carlos.capstone.sync.CapstoneSyncAdapter$WorkerThreadAmerica.run(CapstoneSyncAda
            for (int j = 0; j < min; j++) {
                if(yValues!=null && yValues.size()!=0) {
                    yValuesCopy.add(yValues.get(j));
                }
                if(yValuesMV!=null && yValuesMV.size()!=0) {
                    yValuesMVCopy.add(yValuesMV.get(j));
                }
                if(xValues!=null && xValues.size()!=0) {
                    xValuesCopy.add(xValues.get(j));
                }
            }
            IndexDataUnit indexDataUnit = new IndexDataUnit();
            indexDataUnit.setName(list.get(i).getName());
            indexDataUnit.setTicker(list.get(i).getTicker());
            indexDataUnit.setyValues(yValuesCopy);
            indexDataUnit.setyValuesMarkerView(yValuesMVCopy);
            indexDataUnit.setxLabels(xValuesCopy);
            copy.add(indexDataUnit);
        }
        return copy;

    }

    public static String indexTickerCountryMapper(String ticker) {

        if (ticker.equals("^IXIC")) {
            return "Usa";
        } else if (ticker.equals("^NDX")) {
            return "Usa";
        } else if (ticker.equals("^DJI")) {
            return "Usa";
        } else if (ticker.equals("^GSPC")) {
            return "Usa";
        } else if (ticker.equals("TX60.TS")) {
            return "Canada";
        } else if (ticker.equals("^GSPTSE")) {
            return "Canada";
        } else if (ticker.equals("^BVSP")) {
            return "Brazil";
        } else if (ticker.equals("^MXX")) {
            return "Mexico";
        } else if (ticker.equals("^MERV")) {
            return "Argentina";
        } else if (ticker.equals("^IPSA")) {
            return "Chile";
        } else if (ticker.equals("^FTSE")) {
            return "UK";
        } else if (ticker.equals("^GDAXI")) {
            return "Germany";
        } else if (ticker.equals("^FCHI")) {
            return "France";
        } else if (ticker.equals("FTSEMIB.MI")) {
            return "Italy";
        } else if (ticker.equals("^IBEX")) {
            return "Spain";
        } else if (ticker.equals("PSI20.LS")) {
            return "Portugal";
        } else if (ticker.equals("BEL20.BR")) {
            return "Belgium (BEL20.BR)";
        } else if (ticker.equals("^STOXX50E")) {
            return "Europe (Eurostoxx 50)";
        } else if (ticker.equals("^SSMI")) {
            return "Switzerland";
        } else if (ticker.equals("OBX.OL")) {
            return "Norway";
        } else if (ticker.equals("RTS.RS")) {
            return "Russia";
        } else if (ticker.equals("^BFX")) {
            return "Belgium (^BFX)";
        } else if (ticker.equals("^OMXC20")) { //this is a special case
            //the ticker in the query is different from the ticker in the symbol field returned by
            //the query, for that reason i changed from  OMXC20.CO to ^OMXC20
            return "Denmark";
        } else if (ticker.equals(("^OMXSPI"))) {
            return "Sweden";
        } else if (ticker.equals("FPXAA.PR")) {
            return "Czech Republic";
        } else if (ticker.equals("GD.AT")) {
            return "Greece";
        } else if (ticker.equals("^ATX")) {
            return "Austria";
        } else if (ticker.equals("^ISEQ")) {
            return "Ireland";
        } else if (ticker.equals("^N225")) {
            return "Japan";
        } else if (ticker.equals("000001.SS")) {
            return "China";
        } else if (ticker.equals("^AXJO")) {
            return "Australia";
        } else if (ticker.equals("^HSI")) {
            return "Hong Kong";
        } else if (ticker.equals("^BSESN")) {
            return "India";
        } else if (ticker.equals("^NSEI")) {
            return "India";
        } else if (ticker.equals("^NZ50")) {
            return "New Zealand";
        } else if (ticker.equals("^TWII")) {
            return "Taiwan";
        } else if (ticker.equals("^AORD")) {
            return "Australia";
        } else if (ticker.equals("^JKSE")) {
            return "Indonesia";
        } else if (ticker.equals("^KLSE")) {
            return "Malaysia";
        } else if (ticker.equals("^KS11")) {
            return "Korea";
        } else if (ticker.equals("^STI")) {
            return "Singapore";
        } else if (ticker.equals("PSEI.PS")) {
            return "Philippines";
        }


        return ticker;
    }

    public static boolean indexHasTickerComponents(String ticker) {

        if (ticker == null) return false;

        //nasdaq composite
        if (ticker.equals("^IXIC")) {
            return false;
        //nasdaq 100
        } else if (ticker.equals("^NDX")) {
            return true;
        } else if (ticker.equals("^DJI")) {
            return true;
        } else if (ticker.equals("^GSPC")) {
            return false;
        } else if (ticker.equals("TX60.TS")) {
            return false;
        } else if (ticker.equals("^GSPTSE")) {
            return false;
        } else if (ticker.equals("^BVSP")) {
            return true;
        } else if (ticker.equals("^MXX")) {
            return true;
        } else if (ticker.equals("^MERV")) {
            return true;
        } else if (ticker.equals("^IPSA")) {
            return true;
        } else if (ticker.equals("^FTSE")) {
            return true;
        } else if (ticker.equals("^GDAXI")) {
            return true;
        } else if (ticker.equals("^FCHI")) {
            return true;
        } else if (ticker.equals("FTSEMIB.MI")) {
            return true;
        } else if (ticker.equals("^IBEX")) {
            return true;
        } else if (ticker.equals("PSI20.LS")) {
            return true;
        } else if (ticker.equals("BEL20.BR")) {
            return true;
        } else if (ticker.equals("^STOXX50E")) {
            return true;
        } else if (ticker.equals("^SSMI")) {
            return false;
        } else if (ticker.equals("OBX.OL")) {
            return false;
        } else if (ticker.equals("RTS.RS")) {
            return false;
        } else if (ticker.equals("^BFX")) {
            return false;
        } else if (ticker.equals("^OMXC20")) { //this is a special case
            //the ticker in the query is different from the ticker in the symbol field returned by
            //the query, for that reason i changed from  OMXC20.CO to ^OMXC20
            return false;
        } else if (ticker.equals(("^OMXSPI"))) {
            return false;
        } else if (ticker.equals("FPXAA.PR")) {
            return false;
        } else if (ticker.equals("GD.AT")) {
            return false;
        } else if (ticker.equals("^ATX")) {
            return false;
        } else if (ticker.equals("^ISEQ")) {
            return false;
        } else if (ticker.equals("^N225")) {
            return false;
        } else if (ticker.equals("000001.SS")) {
            return false;
        } else if (ticker.equals("^AXJO")) {
            return false;
        } else if (ticker.equals("^HSI")) {
            return false;
        } else if (ticker.equals("^BSESN")) {
            return false;
        } else if (ticker.equals("^NSEI")) {
            return false;
        } else if (ticker.equals("^NZ50")) {
            return false;
        } else if (ticker.equals("^TWII")) {
            return false;
        } else if (ticker.equals("^AORD")) {
            return false;
        } else if (ticker.equals("^JKSE")) {
            return false;
        } else if (ticker.equals("^KLSE")) {
            return false;
        } else if (ticker.equals("^KS11")) {
            return false;
        } else if (ticker.equals("^STI")) {
            return false;
        } else if (ticker.equals("PSEI.PS")) {
            return false;
        } else {
            return false;
        }

    }

    public static String indexTickerMapperComponents(String ticker) {

        if (ticker.equals("^NDX")) {
            return "NASDAQ-100";
        } else if (ticker.equals("^DJI")) {
            return "DOW-JONES";
        } else if (ticker.equals("^BVSP")) {
            return "BOVESPA-SAO-PAOLO";
        } else if (ticker.equals("^MXX")) {
            return "IPC-MEXICO";
        } else if (ticker.equals("^MERV")) {
            return "MERVAL";
        } else if (ticker.equals("^IPSA")) {
            return "IPSA";
        } else if (ticker.equals("^FTSE")) {
            return "FTSE-100";
        } else if (ticker.equals("^GDAXI")) {
            return "DAX-30";
        } else if (ticker.equals("^FCHI")) {
            return "CAC-40";
        } else if (ticker.equals("FTSEMIB.MI")) {
            return "FTSE-MIB-INDEX";
        } else if (ticker.equals("^IBEX")) {
            return "IBEX-35";
        } else if (ticker.equals("PSI20.LS")) {
            return "PSI-20";
        } else if (ticker.equals("BEL20.BR")) {
            return "BEL-20";
        } else if (ticker.equals("^STOXX50E")) {
            return "EUROSTOXX-50";
        }
        return null;

    }


    public static long getLastFullSyncFromPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(context.getString(R.string.last_sync_key), System.currentTimeMillis());


    }

    public static boolean getNeedEuropeSync(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.needs_europe_sync_key), true);
    }

    public static boolean getNeedAsiaSync(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.needs_asia_sync_key), true);

    }

    public static void setNeedEuropeSync(Context context, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.needs_europe_sync_key), value);
        editor.apply();
    }

    public static void setNeedAsiaSync(Context context, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.needs_asia_sync_key), value);
        editor.apply();

    }

    public static String grabPage(TimeMeasure tm, String location) {

        String output = null;
        boolean success = false;
        try {
            URL url = new URL(location);
            tm.log("#### before url.openConnection");
            URLConnection con = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            tm.log("#### before while");
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");

            }
            tm.log("#### after while");
            output = stringBuilder.toString();
            tm.log("#### after toString");
            success = true;

        } catch (Exception e) {
            Log.d("VILLANUEVA", "Grab Page Error: " + e.getMessage());
        }
        if (!success) {
            return "";
        }
        return output;
    }

    public static Drawable getFavoriteDrawable(Context context, @DrawableRes int drawableResId) {
        final Drawable favoriteIcon;
        favoriteIcon = ContextCompat.getDrawable(context, drawableResId);
        favoriteIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);
        return favoriteIcon;
    }

    public static boolean isSecurityInFavorites(Context context, String symbol) {
        Cursor c = getSecurityFromDb(context, symbol);
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }
        c.close();
        return false;

    }

    public static Cursor getSecurityFromDb(Context context, String symbol) {
        return context.getContentResolver().query(
                CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(symbol),
                null,
                CapstoneContract.FavoritesEntity.COMPANY_TICKER + "=?",
                new String[]{symbol},
                null);

    }

    public static boolean hasTwoPane(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getBoolean(context.getString(R.string.two_pane_key), false);
        return context.getResources().getBoolean(R.bool.hasTwoPane)?true:false;

//        if(context.getResources().getBoolean(R.bool.hasTwoPane)) {
//            return true;
//        }
//        return false;


    }

    public static boolean hasOnePane(Context context) {
        return !hasTwoPane(context);
    }

    public static void analyticsTrackScreen(Application context, String screenName) {
        Tracker tracker;
        tracker=((MyApplication)context).getTracker();
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    public static Intent getSharedIntent(String fileName) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File sharedFile = new File(path,fileName);
        Uri uri = Uri.fromFile(sharedFile);

        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return shareIntent;
    }
    public static  void saveImageToSD(Context context,Bitmap sourceBitmap,String fileName) {
        Bitmap bm= sourceBitmap;
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(Utilities.getTempFile(fileName));
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
  //          bm.recycle();
//            MediaScannerConnection.scanFile(context,
//                    new String[] { Utilities.getTempFile(fileName).toString() }, null,
//                    new MediaScannerConnection.OnScanCompletedListener() {
//                        public void onScanCompleted(String path, Uri uri) {
//                            Log.i("ExternalStorage", "Scanned " + path + ":");
//                            Log.i("ExternalStorage", "-> uri=" + uri);
//                        }
//                    });
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG,"error in saveImageToSD:"+e.getMessage());
        } catch (IOException e) {
            Log.d(LOG_TAG,"error in saveImageToSD:"+e.getMessage());
        }


    }

    public static File getTempFile(String fileName) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File path=Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File file = new File(path ,fileName);

            try  {
                file.createNewFile();
            }  catch (IOException e) {
                Log.d(LOG_TAG,"error in getTempFile:"+e.getMessage());
            }

            return file;
        } else  {
            return null;
        }
    }


    public static void deleteFileFromSD(String fileName) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File path=Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File file = new File(path ,fileName);

            if(file.exists()) {
                file.delete();
            }

        } else  {
            Log.d(LOG_TAG,"External storage not mounted,couldn't delete file");
        }
    }
}
