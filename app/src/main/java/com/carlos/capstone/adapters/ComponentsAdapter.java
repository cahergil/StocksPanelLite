package com.carlos.capstone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.capstone.R;
import com.carlos.capstone.utils.Utilities;

import org.apache.commons.lang3.text.WordUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Carlos on 13/02/2016.
 */
public class ComponentsAdapter extends RecyclerView.Adapter<ComponentsAdapter.ComponentAdapterViewHolder> {

    public static final int COL_INDEX_NAME=1;
    public static final int COL_COMPANY_NAME=2;
    public static final int COL_LAST=3;
    public static final int COL_VAR_DOLLAR=4;
    public static final int COL_VAR_EURO=6;
    public static final int COL_VAR=6;
    public static final int COL_VOLUME=7;
    public static final int COL_TIME=8;
    private Context context;
    private Cursor mCursor;

    public ComponentsAdapter(Context context) {
        this.context=context;
    }
    @Override
    public ComponentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.index_component_item,parent,false);
        return new  ComponentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComponentAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String companyName=mCursor.getString(COL_COMPANY_NAME);
        companyName=companyName.toLowerCase();
        companyName= WordUtils.capitalize(companyName);
        //company name
        holder.tvCompanyName.setText(companyName);


        //last
        //the data comes from a spanish feed, ed, "," insted of "." for decimal digits
        Locale spanishLocale = new Locale(context.getString(R.string.locale_es),
                context.getString(R.string.locale_ES));
        NumberFormat parser=NumberFormat.getNumberInstance(spanishLocale);
        //we are interested only in the two first digit
        parser.setMaximumFractionDigits(2);
        float last=0;
        try {
            //if the string has a "," then it returns a double by default
            //convert to float.
            last= parser.parse(mCursor.getString(COL_LAST)).floatValue();
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag),""+e.getMessage());
        }
        holder.tvLast.setText(String.valueOf(last));

        //var: ex:+3.34% in string format
        DecimalFormat parserVarPercent=(DecimalFormat) NumberFormat.getNumberInstance(spanishLocale);
        parserVarPercent.setPositivePrefix(context.getString(R.string.plus_string));
        parserVarPercent.setNegativePrefix(context.getString(R.string.minus_string));
        parserVarPercent.setMaximumFractionDigits(2);
        String  val=mCursor.getString(COL_VAR).replace(context.getString(R.string.percent_string),
                context.getString(R.string.empty_string));
        float varPercent=0;
        try {
            varPercent=parserVarPercent.parse(val).floatValue();
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag),""+e.getMessage());
        }
        holder.tvVarPercent.setText(context.getString(
                R.string.format_change_percent_without_parenthesis,varPercent));
        holder.tvVarPercent.setTextColor(ContextCompat.getColor(context,varPercent>=0? R.color.green_500:R.color.down_red_600));

        //var_dollar,var_euro, we use the same parser as last
        val=mCursor.getString(COL_VAR_DOLLAR);
        val=val.equals("null")? mCursor.getString(COL_VAR_EURO):val;
        float varCurrency=0;
        try {
            varCurrency=parser.parse(val).floatValue();
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag),""+e.getMessage());
        }
        holder.tvVarD_E.setText(String.valueOf(varCurrency));
        holder.tvVarD_E.setTextColor(ContextCompat.getColor(context,varPercent>=0? R.color.green_500:R.color.down_red_600));

        //up/down icon
        holder.tvIcon.setText(varPercent>=0?context.getString(R.string.triangle_up):context.getString(R.string.triangle_down));
        holder.tvIcon.setTextColor(ContextCompat.getColor(context,varPercent>=0? R.color.triangle_up_color:R.color.triangle_down_color));
        //volume same case as last, use the same parser
        val=mCursor.getString(COL_VOLUME);
        float varVolume=0;
        try {
            varVolume=parser.parse(val).floatValue();
        } catch (ParseException e) {
            Log.d(context.getString(R.string.log_tag),""+e.getMessage());
        }
        String str_formatted=Utilities.formatValueMonetaryUnits(String.valueOf(varVolume));
        holder.tvVolume.setText("Vol:"+str_formatted);

        //inconsistent data
        //holder.tvTime.setText(mCursor.getString(COL_TIME));
    }

    @Override
    public int getItemCount() {
        if ( null == mCursor ) return 0;
        return mCursor.getCount();
    }

    public static class ComponentAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvCompanyName,tvIcon;
        public TextView tvLast, tvVarD_E,tvTime,tvVolume, tvVarPercent;

        public ComponentAdapterViewHolder(View v){
            super(v);
            this.tvIcon= (TextView) v.findViewById(R.id.tvIcon);
            this.tvCompanyName= (TextView) v.findViewById(R.id.companyName);
            this.tvLast = (TextView) v.findViewById(R.id.price);
            this.tvVarD_E = (TextView) v.findViewById(R.id.change);
            this.tvTime= (TextView) v.findViewById(R.id.tvTime);
            this.tvVolume= (TextView) v.findViewById(R.id.volume);
            this.tvVarPercent = (TextView) v.findViewById(R.id.percentChange);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),tvCompanyName.getText(),Toast.LENGTH_SHORT).show();
        }
    }


    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();

    }

    public Cursor getCursor() {
        return mCursor;
    }
}
