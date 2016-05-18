package com.carlos.capstone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.capstone.R;
import com.carlos.capstone.customcomponents.AutoResizeTextView;
import com.carlos.capstone.database.CapstoneContract;
import com.carlos.capstone.interfaces.Callback;
import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 29/04/2016.
 */
public class SecurityAdapter extends RecyclerView.Adapter<SecurityAdapter.ViewHolder> {
    private View mEmptyView;
    private Cursor mCursor;

    public static final int COL_COMPANY_TICKER=1;
    public static final int COL_COMPANY_NAME=2;
    public static final int COL_MARKET=3;
    public static final int COL_VALUE=4;
    public static final int COL_CHANGE=5;
    public static final int COL_CHANGE_PERCENT=6;
    public static final int COL_MAX=7;
    public static final int COL_MIN=8;

    private Context context;

    public SecurityAdapter(Context context, View emptyView){
        this.context=context;
        this.mEmptyView=emptyView;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.security_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        //ticker
        String ticker=mCursor.getString(COL_COMPANY_TICKER);
        holder.tvTicker.setText(ticker);
        holder.tvTicker.setContentDescription(context.getString(R.string.sa_talkback_ticker)+ticker);

        //name
        String name=mCursor.getString(COL_COMPANY_NAME);
        holder.tvSecurityName.setText(name);
        holder.tvSecurityName.setContentDescription(context.getString(R.string.sa_talkback_security_name)+name);


        //type
        String type=mCursor.getString(COL_MARKET);
        holder.tvMarket.setText(type);
        holder.tvMarket.setContentDescription(context.getString(R.string.sa_talkback_security_type)+type);

        //price
        float price=mCursor.getFloat(COL_VALUE);
        String formattedPrice=Utilities.localizeDecimalValue(price);
        holder.tvPrice.setText(mCursor.isNull(COL_VALUE)? context.getString(R.string.na):formattedPrice);
        holder.tvPrice.setContentDescription(context.getString(R.string.sa_talkback_price)+formattedPrice);

        //change
        float change=mCursor.getFloat(COL_CHANGE);

        //   viewHolder.tvChange.setText(cursor.isNull(COL_CHANGE)? context.getString(R.string.na):Utilities.localizeDecimalValue(change));
        //   Utilities.setUpDownColors(viewHolder.tvChange,(double)change,context);
        //changePercent
        float changePercent=mCursor.getFloat(COL_CHANGE_PERCENT);
        String changePerct= Utilities.localizePercentValue(context,changePercent);
        holder.tvChangePercent.setText(mCursor.isNull(COL_CHANGE_PERCENT)?context.getString(R.string.na):
                changePerct);
       holder.tvChangePercent.setContentDescription(context.
                getString(R.string.sa_talkback_change_percent)+changePerct);
        Utilities.setUpDownColorsMaterial(holder.tvChangePercent,(double)change,context);


        //max
        float max=mCursor.getFloat(COL_MAX);
        String max_day=Utilities.localizeDecimalValue(max);
        holder.tvMax.setText(mCursor.isNull(COL_MAX)? context.getString(R.string.na):max_day);
        holder.tvMax.setContentDescription(context.getString(R.string.sa_talkback_day_max)+max_day);
        //min
        float min=mCursor.getFloat(COL_MIN);
        String min_day=Utilities.localizeDecimalValue(min);
        holder.tvMin.setText(mCursor.isNull(COL_MIN)? context.getString(R.string.na):min_day);
        holder.tvMin.setContentDescription(context.getString(R.string.sa_talkback_day_min)+min_day);
        //icon
        holder.ivOverflow.setContentDescription(context.getString(R.string.sa_talkback_overflow_icon));

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTicker,tvMarket,tvPrice;
        public AutoResizeTextView tvSecurityName,tvMax, tvMin,tvChangePercent;
        public ImageView ivOverflow;

        public ViewHolder(View v){
            super(v);
            this.tvTicker= (TextView) v.findViewById(R.id.ticker);
            this.tvMarket= (TextView) v.findViewById(R.id.market);
            this.tvSecurityName= (AutoResizeTextView) v.findViewById(R.id.securityName);
            this.tvPrice= (TextView) v.findViewById(R.id.price);
            this.tvChangePercent= (AutoResizeTextView) v.findViewById(R.id.percentChange);
            this.tvMax= (AutoResizeTextView) v.findViewById(R.id.dayMax);
            this.tvMin= (AutoResizeTextView) v.findViewById(R.id.dayMin);
            this.ivOverflow= (ImageView) v.findViewById(R.id.overflow);
            v.setOnClickListener(this);


            final PopupMenu popupMenu=new PopupMenu(context,ivOverflow);
            popupMenu.inflate(R.menu.menu_security);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int position=getAdapterPosition();
                    mCursor.moveToPosition(position);
                    String ticker=mCursor.getString(COL_COMPANY_TICKER);
                    if (item.getItemId() == R.id.action_delete) {
                        Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                        Uri uri= CapstoneContract.FavoritesEntity.buildFavoritesWithTicker(ticker);
                        context.getContentResolver().delete(uri,CapstoneContract.FavoritesEntity.COMPANY_TICKER + "=?",
                                new String[]{ticker});

                        return true;
                    }
                    return true;
                }
            });
            this.ivOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            mCursor.moveToPosition(position);
            String ticker=mCursor.getString(COL_COMPANY_TICKER);
            String companyName=mCursor.getString(COL_COMPANY_NAME);
            String securityType=mCursor.getString(COL_MARKET);
            ((Callback)context).onItemSelected(ticker,companyName,securityType);
        }
    }
}
