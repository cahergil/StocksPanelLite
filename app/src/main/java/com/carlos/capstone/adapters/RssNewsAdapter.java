package com.carlos.capstone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carlos.capstone.R;
import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 25/01/2016.
 */
public class RssNewsAdapter extends CursorAdapter {
    private static final String LOG_TAG=RssNewsAdapter.class.getSimpleName();
    public static final int COL_REGION=1;
    public static final int COL_TITLE=2;
    public static final int COL_DATE=3;
    public static final int COL_CONTENT=4;
    public static final int COL_IMG_URL=5;
    public static final int COL_LINK_URL=6;

    // adb shell setprop log.tag.GenericRequest DEBUG to enable
    // adb shell setprop log.tag.GenericRequest ERROR to disable
    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            // to do log exception
            Log.d(LOG_TAG,"target:"+target);
            Log.d(LOG_TAG,"exception:"+e.getMessage());
            // important to return false so the error placeholder can be placed
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

            return false;
        }
    };
    public static class ViewHolder{

        public final TextView txtTitulo;
        public final TextView txtFecha;
        public final ImageView ivImagen;
        public final TextView txtContent;
        public final TextView txtLink;

        public ViewHolder(View v) {
            txtTitulo= (TextView) v.findViewById(R.id.txtTitulo);
            txtFecha= (TextView) v.findViewById(R.id.txtFecha);
            ivImagen= (ImageView) v.findViewById(R.id.ivImgaen);
            txtContent= (TextView) v.findViewById(R.id.txtContent);
            txtLink= (TextView) v.findViewById(R.id.txtUrlLink);
        }
    }


    public RssNewsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.rss_news_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);

        view.setTag(viewHolder);
        return view;

    }

    @Override
    public void bindView(View view,final Context context, Cursor cursor) {
        final ViewHolder viewHolder= (ViewHolder) view.getTag();

        //title
        String title=Html.fromHtml(cursor.getString(COL_TITLE)).toString();
        viewHolder.txtTitulo.setText(title);
        viewHolder.txtTitulo.setContentDescription(context.getString(R.string.talkback_ra_title)+title);

        //date
        // SimpleDateFormat parser=new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss ZZZ", Locale.ENGLISH);
        // DateUtils.getRelativeTimeSpanString(long time,long now,long minResolution,int flags);
        String formattedDate=null;
        formattedDate=(String) DateUtils.getRelativeTimeSpanString(cursor.getLong(COL_DATE),
                    System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS);
        viewHolder.txtFecha.setText(formattedDate);
        viewHolder.txtFecha.setContentDescription(context.getString(R.string.talkback_ra_date)+formattedDate);


        //image
        viewHolder.ivImagen.setContentDescription(context.getString(R.string.talkback_ra_image));
        if(cursor.getString(COL_IMG_URL)==null) {
            viewHolder.ivImagen.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(cursor.getString(COL_IMG_URL))
                   // .listener(requestListener)
                    .error(R.drawable.noimage)
                    .centerCrop()
                    .into(viewHolder.ivImagen);

        }
        //url link
        viewHolder.txtLink.setText(cursor.getString(COL_LINK_URL));

        //content of the article
        String description=cursor.getString(COL_CONTENT);
        description= Utilities.extractNewsFromRss(description);
        String content=Html.fromHtml(description).toString();
        viewHolder.txtContent.setText(content);
        viewHolder.txtContent.setContentDescription(context.getString(R.string.talkback_ra_content)+content);


    }
}
