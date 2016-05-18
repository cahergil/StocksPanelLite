package com.carlos.capstone.customcomponents;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.carlos.capstone.R;
import com.carlos.capstone.models.IndexDataUnit;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 06/01/2016.
 */
public class CustomIndexMarkerView extends MarkerView {
    private List<String> xLabels;
    private List<IndexDataUnit> list;
    private TextView tvPercent,tvValue,tvHour;
    private Context context;
    private LineChart chart;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomIndexMarkerView(Context context, int layoutResource, List<String> xLabels, List<IndexDataUnit> list,LineChart chart) {
        super(context, layoutResource);
        this.context=context;
        this.tvPercent = (TextView) findViewById(R.id.tvContent);
        this.tvValue=(TextView)findViewById(R.id.tvContent1);
        this.tvHour=(TextView) findViewById(R.id.tvContent2);
        this.xLabels=xLabels;
        this.list=list;
        this.chart=chart;
    }

    private void generatePoint(float value,int  xIndex) {
        LineData d=new LineData();
        ArrayList<Entry> valPoint=new ArrayList<Entry>();
        valPoint.add(new Entry(value,xIndex));
        LineDataSet lineDataSet=new LineDataSet(valPoint,null);
        lineDataSet.setCircleSize(4.f);
        lineDataSet.setCircleColor(ContextCompat.getColor(context,R.color.gray800));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setColor(ContextCompat.getColor(context,R.color.white));
        //remove previos highlight if there is one
        int count=chart.getLineData().getDataSetCount();
        if(count>3) {
            chart.getLineData().removeDataSet(count-1);
        }
        chart.getLineData().addDataSet(lineDataSet);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvPercent.setText("\u200e"+String.format("%.2f",e.getVal())+"%"); // set the entry-value as the display text
        tvValue.setText(""+ String.format("%.2f",list.get(highlight.getDataSetIndex()).getyValuesMarkerView().get(highlight.getXIndex()).floatValue()));
        //     Entry entry=dataSets.get(highlight.getDataSetIndex()).getEntryForXIndex(e.getXIndex());
        //      list.get(highlight.getDataSetIndex()).getyValuesMarkerView().get(highlight.getXIndex()).floatValue();
        tvHour.setText(""+xLabels.get(highlight.getXIndex()));
        generatePoint(e.getVal(),highlight.getXIndex());
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
