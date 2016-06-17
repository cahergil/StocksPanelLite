package com.carlos.capstone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.capstone.R;
import com.carlos.capstone.customcomponents.AutoResizeTextView;
import com.carlos.capstone.utils.Utilities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos on 30/01/2016.
 */
public class IndexesAdapter extends CursorAdapter {

    public static final int COL_REGION = 1;
    public static final int COL_INDEX_NAME = 2;
    public static final int COL_INDEX_TICKER = 3;
    public static final int COL_VALUE = 4;
    public static final int COL_VOLUME = 5;
    public static final int COL_CHANGE = 6;
    public static final int COL_CHANGE_PERCENT = 7;
    public static final int COL_DATE = 8;
    private int region;
    private static final int AMERICA = 1;
    private static final int EUROPE = 2;
    private static final int ASIA = 3;

    private static class ViewHolder {
        public final TextView tvName;
        public final AutoResizeTextView tvValue;
        public final AutoResizeTextView tvVolume;
        public final AutoResizeTextView tvChange;
        public final AutoResizeTextView tvChangePercent;
        public final AutoResizeTextView tvPadder;

        public final TextView tvTicker;
        public final AutoResizeTextView tvCountry;
        public final ImageView ivEye;
        public final AutoResizeTextView tvDate;


        public ViewHolder(View view) {

            tvName = (TextView) view.findViewById(R.id.txtindexName);
            tvValue = (AutoResizeTextView) view.findViewById(R.id.txtValue);
            tvVolume = (AutoResizeTextView) view.findViewById(R.id.txtVolume);
            tvChange = (AutoResizeTextView) view.findViewById(R.id.txtChange);
            tvChangePercent = (AutoResizeTextView) view.findViewById(R.id.txtChangePercent);
            tvPadder = (AutoResizeTextView) view.findViewById(R.id.padder);

            tvTicker = (TextView) view.findViewById(R.id.ticker);
            tvCountry = (AutoResizeTextView) view.findViewById(R.id.txtCountry);
            ivEye = (ImageView) view.findViewById(R.id.ivEye);
            tvDate = (AutoResizeTextView) view.findViewById(R.id.txtDate);
        }
    }

    public IndexesAdapter(Context context, Cursor c, int flags, int region) {
        super(context, c, flags);
        this.region = region;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.index_item_no_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        String ticker = cursor.getString(COL_INDEX_TICKER);
        viewHolder.tvTicker.setText(ticker);
        viewHolder.tvTicker.setContentDescription(context.getString(R.string.talkback_ia_ticker) + ticker);

        int color = 0;
        //padder rectangle color
//        switch (region) {
//            case AMERICA:
//                color= ContextCompat.getColor(context,R.color.padder_america);
//                break;
//            case EUROPE:
//                color=ContextCompat.getColor(context,R.color.padder_europe);
//                break;
//            case ASIA:
//                color= ContextCompat.getColor(context, R.color.padder_asia);
//
//        }
        float change_percent = cursor.getFloat(COL_CHANGE_PERCENT);
        if (change_percent >= 0 && change_percent < 1) {
            color = ContextCompat.getColor(context, R.color.green_300);
        } else if (change_percent >= 1 && change_percent < 3) {
            color = ContextCompat.getColor(context, R.color.green_500);
        } else if (change_percent >= 3 && change_percent < 6) {
            color = ContextCompat.getColor(context, R.color.green_700);
        } else if (change_percent >= 6) {
            color = ContextCompat.getColor(context, R.color.green_900);
        } else if (change_percent <= 0 && change_percent > -1) {
            color = ContextCompat.getColor(context, R.color.red_300);
        } else if (change_percent <= -1 && change_percent > -3) {
            color = ContextCompat.getColor(context, R.color.red_500);
        } else if (change_percent <= -3 && change_percent > -6) {
            color = ContextCompat.getColor(context, R.color.red_700);
        } else if (change_percent <= -6) {
            color = ContextCompat.getColor(context, R.color.red_900);
        }


        viewHolder.tvPadder.setBackgroundColor(color);
        viewHolder.tvPadder.setContentDescription(context.getString(R.string.talkback_ia_decorative_element));

        // index name
        String indexName = cursor.getString(COL_INDEX_NAME);
        viewHolder.tvName.setText(indexName);
        viewHolder.tvName.setContentDescription(context.getString(R.string.talkback_ia_index_name) + indexName);

        //index country
        String country = Utilities.indexTickerCountryMapper(cursor.getString(COL_INDEX_TICKER));
        viewHolder.tvCountry.setText(country);
        viewHolder.tvCountry.setContentDescription(context.getString(R.string.talkback_ia_country) + country);
        //index eye(components)
        if (Utilities.indexHasTickerComponents(cursor.getString(COL_INDEX_TICKER))) {
            viewHolder.ivEye.setImageResource(R.drawable.eye);
            viewHolder.ivEye.setContentDescription(context.getString(R.string.talkback_ia_eye));
        }
        //index price
        String price = new DecimalFormat(context.getString(R.string.format_decimal_value)).format(cursor.getFloat(COL_VALUE));
        viewHolder.tvValue.setText(price);
        viewHolder.tvValue.setContentDescription(context.getString(R.string.talkback_is_price) + price);

        //volume
        int volumen = cursor.getInt(COL_VOLUME);
        if (volumen == 0) {
            viewHolder.tvVolume.setText(context.getString(R.string.volume_plus_point) + 0);
        } else {

            viewHolder.tvVolume.setText(context.getString(R.string.volume_plus_point) + Utilities.formatValueMonetaryUnits(String.valueOf(volumen)));
        }

        //date
        //could have used timestamp instead of this
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZZZ", Locale.ENGLISH);
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        //SimpleDateFormat formatter=new SimpleDateFormat("MM/dd kk:mm");
        Date date = null;
        String formattedDate = null;
        try {
            date = parser.parse(cursor.getString(COL_DATE));
            formattedDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(System.currentTimeMillis());
            formattedDate = formatter.format(date);
        }
        viewHolder.tvDate.setText(formattedDate);


        Utilities.setUpDownColorsMaterial(viewHolder.tvChangePercent, (double) cursor.getFloat(COL_CHANGE), context);
        viewHolder.tvChangePercent.setText(context.getString(R.string.format_change_percent_without_parenthesis, cursor.getFloat(COL_CHANGE_PERCENT)));
        viewHolder.tvChangePercent.setContentDescription(context.getString(R.string.talkback_is_change_percent) + context.getString(R.string.format_change_percent_without_parenthesis, cursor.getFloat(COL_CHANGE_PERCENT)));

        String change = Utilities.localizeDecimalValue(cursor.getFloat(COL_CHANGE));
        viewHolder.tvChange.setText(change);
        viewHolder.tvChange.setContentDescription(context.getString(R.string.talkback_change) + change);


    }
}
