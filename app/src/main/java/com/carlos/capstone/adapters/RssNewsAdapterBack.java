package com.carlos.capstone.adapters;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlos.capstone.R;
import com.carlos.capstone.models.RssNews;

import java.util.List;

/**
 * Created by Carlos on 23/01/2016.
 */
public class RssNewsAdapterBack extends BaseAdapter {

    List<RssNews> listNews;
    Activity context;

    public RssNewsAdapterBack(Activity context, List<RssNews> listNews){
        this.listNews=listNews;
        this.context =context;

    }
    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null) {

            view = context.getLayoutInflater().inflate(R.layout.rss_news_item, parent, false);
        }
        TextView tvTitulo= (TextView) view.findViewById(R.id.txtTitulo);
        Html.fromHtml((String) listNews.get(position).getTitle()).toString();
       // tvTitulo.setText(listNews.get(position).getTitle());
        tvTitulo.setText(Html.fromHtml((String) listNews.get(position).getTitle()).toString());
        TextView tvDate= (TextView) view.findViewById(R.id.txtFecha);
        tvDate.setText(listNews.get(position).getDate());
        ImageView iv= (ImageView) view.findViewById(R.id.ivImgaen);
        if(listNews.get(position).getImg()==null) {
            iv.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(listNews.get(position).getImg())

                    .into(iv);
        }
        TextView tvContent= (TextView) view.findViewById(R.id.txtContent);
        tvContent.setText(Html.fromHtml((String) listNews.get(position).getContent()).toString());
        return view;
    }
}
