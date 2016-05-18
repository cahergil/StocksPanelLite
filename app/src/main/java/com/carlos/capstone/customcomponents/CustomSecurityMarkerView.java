package com.carlos.capstone.customcomponents;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.carlos.capstone.FragmentStockSummary;
import com.carlos.capstone.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 26/02/2016.
 */
public class CustomSecurityMarkerView extends MarkerView {
    private TextView tvValue, tvMoment;
    List<String> xLabels;
    List<Float> yValuesLinear;
    CombinedChart combinedChart;
    @FragmentStockSummary.ChartMode int chartMode;
    Context context;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomSecurityMarkerView(Context context,
                                    int layoutResource,
                                    List<String> xLabels,
                                    List<Float> yValuesLinear,
                                    CombinedChart chart,
                                    @FragmentStockSummary.ChartMode int chartMode) {
        super(context, layoutResource);
        this.context=context;
        this.tvValue=(TextView)findViewById(R.id.tvContent1);
        this.tvMoment =(TextView) findViewById(R.id.tvContent2);
        this.xLabels=xLabels;
        this.yValuesLinear=yValuesLinear;
        this.combinedChart=chart;
        this.chartMode=chartMode;
    }
    private void generatePoint(float value,int  xIndex) {
        LineData d=new LineData();
        ArrayList<Entry> valPoint=new ArrayList<Entry>();
        valPoint.add(new Entry(value,xIndex));
        LineDataSet lineDataSet=new LineDataSet(valPoint,"");
        lineDataSet.setCircleSize(4.f);
        lineDataSet.setCircleColor(ContextCompat.getColor(context,R.color.gray800));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);



        //remove previos highlight if there is one
        int count=combinedChart.getLineData().getDataSetCount();
        if(chartMode==FragmentStockSummary.CHART_MODE_1D) {
            if (count > 2) {
                combinedChart.getLineData().removeDataSet(count - 1);
            }
        } else {
            if (count > 1) {
                combinedChart.getLineData().removeDataSet(count - 1);
            }
        }
        combinedChart.getLineData().addDataSet(lineDataSet);
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvValue.setText(yValuesLinear.get(highlight.getXIndex()).toString());
        tvMoment.setText(xLabels.get(highlight.getXIndex()));
        generatePoint(yValuesLinear.get(highlight.getXIndex()),highlight.getXIndex());

    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -(getHeight());
    }
}
