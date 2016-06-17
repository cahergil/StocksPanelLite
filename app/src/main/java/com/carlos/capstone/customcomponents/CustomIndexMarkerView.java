package com.carlos.capstone.customcomponents;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
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

        //America and Europe
        if(count==4) {
            chart.getLineData().removeDataSet(count-1);
        }
        //Asia
        if(count==2) {
            chart.getLineData().removeDataSet(count-1);
        }

        chart.getLineData().addDataSet(lineDataSet);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvPercent.setText(String.format("%.2f",e.getVal())+"%"); // set the entry-value as the display text
        tvValue.setText(""+ String.format("%.2f",list.get(highlight.getDataSetIndex()).getyValuesMarkerView().get(highlight.getXIndex()).floatValue()));
        //     Entry entry=dataSets.get(highlight.getDataSetIndex()).getEntryForXIndex(e.getXIndex());
        //      list.get(highlight.getDataSetIndex()).getyValuesMarkerView().get(highlight.getXIndex()).floatValue();
        tvHour.setText(""+xLabels.get(highlight.getXIndex()));
        generatePoint(e.getVal(),highlight.getXIndex());
    }

    @Override
    public int getXOffset(float xpos) {

        //si hacemos -xpos estamos diciendo que pinte quite toda la longitud donde se hizo click
        //que se reste y pinte ahi. Son coordenadas relativas

        //me da un getXOffset muy pequeno, no se porque deberia coger to do el ancho
        // al final pongo 136dp.
        //YAxis yAxis=chart.getAxisRight();
        //float rightAxisSpace=chart.getWidth()-yAxis.getXOffset();
        if(xpos>=(chart.getWidth()/2)) {
            //return -(getWidth() / 2);
            return -(int) (xpos - convertDpToPixel(10, context));
        } else {
            //la anchura del custom_index_markerview es fija, de 100dp y anadimos 36 dp que es el
            //espacio que hay al final donde estan las label de %
            return (int) (-xpos +(chart.getWidth()-convertDpToPixel(136,context)));
        }
    }

    @Override
    public int getYOffset(float ypos) {


        return -(int)(ypos-convertDpToPixel(12,context));
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

}
