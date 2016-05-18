package com.carlos.capstone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.carlos.capstone.R;

import java.text.Normalizer;

/**
 * Created by Carlos on 20/02/2016.
 */
public class SuggestionsAdapter extends CursorAdapter {
    public static final int COL_TICKER=1;
    public static final int COL_NAME=2;
    public static final int COL_EXCHANGE=3;
    public static final int COL_SECURITY_TYPE=4;


    public SuggestionsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder{
        public TextView tvSymbol;
        public TextView tvName;
        public TextView tvStockExchange;
        public TextView tvSecurityType;

        public ViewHolder(View v) {
                tvSymbol= (TextView) v.findViewById(R.id.symbol);
                tvName= (TextView) v.findViewById(R.id.name);
                tvStockExchange= (TextView) v.findViewById(R.id.stockExchange);
                tvSecurityType= (TextView) v.findViewById(R.id.securityType);

        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.suggestion_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();

        //ticker
        String ticker=cursor.getString(COL_TICKER);
        viewHolder.tvSymbol.setText(ticker);
        viewHolder.tvSymbol.setContentDescription(context.getString(R.string.sua_talkback_ticker)+ticker);

        //name
        //creo que esto no hace falta despues de anadir Charset.forName("windows-1252") in DownloadSecurityFromTxt
        String name = Normalizer.normalize(cursor.getString(COL_NAME), Normalizer.Form.NFC);
        viewHolder.tvName.setText(name);
        viewHolder.tvName.setContentDescription(context.getString(R.string.sua_talkback_security_name)+name);

        //exchange
        String exchange=cursor.getString(COL_EXCHANGE);
        viewHolder.tvStockExchange.setText(exchange);
        viewHolder.tvStockExchange.setContentDescription(context.getString(R.string.sua_talkback_exchange)+exchange);

        //type
        String securityType=cursor.getString(COL_SECURITY_TYPE);
        viewHolder.tvSecurityType.setText(securityType);
        viewHolder.tvSecurityType.setContentDescription(context.getString(R.string.sua_talkback_security_type)+securityType);
        int color;
        if(securityType.equals(context.getString(R.string.index))) {
            color= ContextCompat.getColor(context,R.color.index_color);
        } else if (securityType.equals(context.getString(R.string.equity))){
            color=ContextCompat.getColor(context,R.color.equity_color);
        } else  { //etf
            color=ContextCompat.getColor(context,R.color.etf_color);
        }
        viewHolder.tvSecurityType.setTextColor(color);
    }
}
