package com.carlos.capstone.adapters;

import android.view.View;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.carlos.capstone.R;
import com.carlos.capstone.customcomponents.AutoResizeTextView;

/**
 * Created by Carlos on 28/12/2015.
 */
public class StatsChildViewHolder extends ChildViewHolder {
    public AutoResizeTextView txtMeaure;
    public AutoResizeTextView txtValue;

    public StatsChildViewHolder(View itemView) {
        super(itemView);
        txtMeaure = (AutoResizeTextView) itemView.findViewById(R.id.measure);
        txtValue = (AutoResizeTextView) itemView.findViewById(R.id.value);
    }
}
