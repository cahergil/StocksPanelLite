package com.carlos.capstone.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlos.capstone.DetailNewsActivity;
import com.carlos.capstone.R;
import com.carlos.capstone.customtabs.CustomTabActivityHelper;
import com.carlos.capstone.utils.Utilities;


/**
 * Created by Carlos on 20/12/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();
    final private View mEmptyView;

    private Context context;
    private AppCompatActivity activity;
    private Cursor mCursor;

    public static final int COL_SYMBOL = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_DATE = 3;
    public static final int COL_PUBLISHER = 4;
    public static final int COL_LINK_URL = 5;


    public NewsAdapter(Context context, View EmptyView) {

        this.context = context;
        this.activity = (AppCompatActivity) context;
        this.mEmptyView = EmptyView;

    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);


        holder.titulo.setText(mCursor.getString(COL_TITLE));
        holder.fecha.setText(Utilities.stripOutParentheses(mCursor.getString(COL_DATE)));
        holder.publisher.setText(mCursor.getString(COL_PUBLISHER));
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
        public TextView titulo;
        public TextView fecha;
        public TextView publisher;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            fecha = (TextView) itemView.findViewById(R.id.tvFecha);
            publisher = (TextView) itemView.findViewById(R.id.tvPublisher);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);


            Bitmap backIcon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.arrow_left);

            //customization possibilities
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setShowTitle(true)
                    .setCloseButtonIcon(backIcon)
                    .build();

            final String url = Utilities.parseUrl(mCursor.getString(COL_LINK_URL));
            final String title = mCursor.getString(COL_TITLE);
            Uri uri = Uri.parse(url);
            CustomTabActivityHelper.openCustomTab(activity, customTabsIntent, uri,
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(context, DetailNewsActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("title", title);
                            context.startActivity(intent);

                        }
                    });
        }
    }


}
