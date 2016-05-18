package com.carlos.capstone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.carlos.capstone.R;

import java.util.List;

/**
 * Created by Carlos on 28/12/2015.
 */
public class StatsExpandableAdapter
        extends ExpandableRecyclerAdapter<StatsParentViewHolder, StatsChildViewHolder> {

    private View mEmptyview;
    private LayoutInflater mInflater;

    public StatsExpandableAdapter(Context context, List<ParentListItem> parentItemList,View emptyView) {
        super( parentItemList);
        mInflater=LayoutInflater.from(context);
        this.mEmptyview=emptyView;
        mEmptyview.setVisibility(parentItemList.size()==0?View.VISIBLE:View.GONE);

    }

    @Override
    public StatsParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.stats_parent, viewGroup, false);
        return new StatsParentViewHolder(view);
    }

    @Override
    public StatsChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.stats_row, viewGroup, false);
        return new StatsChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(StatsParentViewHolder statsParentViewHolder, int i,  ParentListItem  o) {
        Stats stat=(Stats) o;


        statsParentViewHolder.mStatsTitleTextView.setText(stat.getNombreGrupo());
    }

    @Override
    public void onBindChildViewHolder(StatsChildViewHolder statsChildViewHolder, int i, Object o) {
        StatsChild statsChild=(StatsChild)o;
        statsChildViewHolder.txtMeaure.setText(statsChild.getmMeasure());
        statsChildViewHolder.txtValue.setText(statsChild.getmValue());
    }
}
