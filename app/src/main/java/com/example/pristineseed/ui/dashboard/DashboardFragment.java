package com.example.pristineseed.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.PeriodicWorkRequest;

import com.example.pristineseed.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    BarChart barchart,barchart_grouped;
    PieChart pichart;
    RadarChart radar_chart;
    LineChart line_chart;

    PeriodicWorkRequest workRequest;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindBarchart(view);
        bindPiaChart(view);
        bindRadarChart(view);
        bindLineChart(view);
        bingGourpBarChart(view);

    }


    void bindBarchart(View view){
        barchart = view.findViewById(R.id.barchart);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(2010, 170));
        visitor.add(new BarEntry(2011, 270));
        visitor.add(new BarEntry(2012, 370));
        visitor.add(new BarEntry(2013, 70));
        visitor.add(new BarEntry(2014, 420));
        visitor.add(new BarEntry(2015, 475));
        visitor.add(new BarEntry(2016, 508));
        visitor.add(new BarEntry(2017, 660));
        visitor.add(new BarEntry(2018, 550));
        visitor.add(new BarEntry(2019, 630));
        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(8f);
        BarData barData = new BarData(barDataSet);
        barchart.setFitBars(true);
        barchart.setData(barData);
        barchart.getDescription().setText("Bar Chart Example");
        barchart.animateY(1000);
        barchart.setScaleEnabled(false);
    }
    void bindPiaChart(View view){
        //todo pia chart
        pichart=view.findViewById(R.id.pichart);
        ArrayList<PieEntry> visitors12=new ArrayList<>();
        visitors12.add(new PieEntry(508,"2016"));
        visitors12.add(new PieEntry(600,"2017"));
        visitors12.add(new PieEntry(700,"2018"));
        visitors12.add(new PieEntry(800,"2019"));
        visitors12.add(new PieEntry(900,"20120"));
        PieDataSet pieDataSet=new PieDataSet(visitors12,"Visitors");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(8f);

        PieData pieData=new PieData(pieDataSet);
        pichart.setData(pieData);
        pichart.getDescription().setEnabled(false);
        pichart.setCenterText("Visitor");
        pichart.animate();

    }
    void bindRadarChart(View view){
        //todo Radar chart
        radar_chart=view.findViewById(R.id.radar_chart);
        //red chart
        ArrayList<RadarEntry> redarvisitor=new ArrayList<>();
        redarvisitor.add(new RadarEntry(420));
        redarvisitor.add(new RadarEntry(470));
        redarvisitor.add(new RadarEntry(520));
        redarvisitor.add(new RadarEntry(620));
        redarvisitor.add(new RadarEntry(720));
        redarvisitor.add(new RadarEntry(820));
        redarvisitor.add(new RadarEntry(920));

        RadarDataSet radarDataSet=new RadarDataSet(redarvisitor,"Website User");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(1f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(8f);

        //blue chart
        ArrayList<RadarEntry> redarvisitor1=new ArrayList<>();
        redarvisitor1.add(new RadarEntry(220));
        redarvisitor1.add(new RadarEntry(270));
        redarvisitor1.add(new RadarEntry(320));
        redarvisitor1.add(new RadarEntry(520));
        redarvisitor1.add(new RadarEntry(620));
        redarvisitor1.add(new RadarEntry(720));
        redarvisitor1.add(new RadarEntry(820));

        RadarDataSet radarDataSet1=new RadarDataSet(redarvisitor1,"Website 2");
        radarDataSet1.setColor(Color.BLUE);
        radarDataSet1.setLineWidth(1f);
        radarDataSet1.setValueTextColor(Color.BLUE);
        radarDataSet1.setValueTextSize(8f);


        RadarData radarData=new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.addDataSet(radarDataSet1);

        String[] labels={"2014","2015","2016","2017","2018","2019","2020"};
        XAxis xAxis=radar_chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radar_chart.getDescription().setText("Website 1");
        radar_chart.setData(radarData);
    }

    void bindLineChart(View view){
        line_chart=view.findViewById(R.id.line_chart);
        ArrayList<Entry> value=new ArrayList<>();
        value.add(new Entry(0,60));
        value.add(new Entry(1,70));
        value.add(new Entry(2,60));
        value.add(new Entry(3,80));
        value.add(new Entry(4,50));
        value.add(new Entry(5,90));
        value.add(new Entry(6,100));
        LineDataSet set1=new LineDataSet(value,"Data Set 1");
        set1.setColor(Color.BLUE);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setFillAlpha(110);

        ArrayList<Entry> value2=new ArrayList<>();
        value2.add(new Entry(0,10));
        value2.add(new Entry(1,20));
        value2.add(new Entry(2,30));
        value2.add(new Entry(3,20));
        value2.add(new Entry(4,50));
        value2.add(new Entry(5,90));
        value2.add(new Entry(6,70));
        LineDataSet set2=new LineDataSet(value2,"Data Set 2");
        set2.setColor(Color.RED);
        set2.setFillAlpha(110);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        ArrayList<ILineDataSet> datasetsArray=new ArrayList<>();
        datasetsArray.add(set1);
        datasetsArray.add(set2);

        LineData data=new LineData(datasetsArray);
        line_chart.setData(data);

    }
    void bingGourpBarChart(View view){
        barchart_grouped = view.findViewById(R.id.barchart_grouped);
        //set one

        BarDataSet barDataSet1 = new BarDataSet(getVisiter1(), "Set 1");
        barDataSet1.setColors(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(getVisiter2(), "Set 2");
        barDataSet2.setColors(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(getVisiter3(), "Set 3");
        barDataSet3.setColors(Color.MAGENTA);
        BarDataSet barDataSet4 = new BarDataSet(getVisiter4(), "Set 4");
        barDataSet4.setColors(Color.GREEN);

        BarData barData = new BarData(barDataSet1,barDataSet2,barDataSet3,barDataSet4);
        barchart_grouped.setData(barData);

        String[] days=new String[]{"Sunday","Monday","Tuesday","Wednesday","thrusday","Friday","Saturday"};
        XAxis xAxis=barchart_grouped.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barchart_grouped.setDragEnabled(true);
        barchart_grouped.setVisibleXRangeMaximum(2);

        float barspace=0.08f;
        float groupspace=0.44f;
        barData.setBarWidth(0.10f);

        barchart_grouped.groupBars(0,groupspace,barspace);
        barchart_grouped.invalidate();

        barchart_grouped.getDescription().setText("Bar Chart Example");
        barchart_grouped.animateY(1000);
        barchart_grouped.setScaleEnabled(true);
    }

    ArrayList<BarEntry> getVisiter1(){
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1, 900));
        visitor.add(new BarEntry(2, 630));
        visitor.add(new BarEntry(3, 1040));
        visitor.add(new BarEntry(4, 350));
        visitor.add(new BarEntry(5, 2614));
        visitor.add(new BarEntry(6, 5000));
        visitor.add(new BarEntry(7, 1173));
        return visitor;
    }
    ArrayList<BarEntry> getVisiter2(){
        ArrayList<BarEntry> visitor1 = new ArrayList<>();
        visitor1.add(new BarEntry(1, 2000));
        visitor1.add(new BarEntry(2, 791));
        visitor1.add(new BarEntry(3, 630));
        visitor1.add(new BarEntry(4, 457));
        visitor1.add(new BarEntry(5, 2724));
        visitor1.add(new BarEntry(6, 500));
        visitor1.add(new BarEntry(7, 2173));
        return visitor1;
    }
    ArrayList<BarEntry> getVisiter3(){
        ArrayList<BarEntry> visitor1 = new ArrayList<>();
        visitor1.add(new BarEntry(1, 23));
        visitor1.add(new BarEntry(2, 7491));
        visitor1.add(new BarEntry(3, 34));
        visitor1.add(new BarEntry(4, 345));
        visitor1.add(new BarEntry(5, 78));
        visitor1.add(new BarEntry(6, 23));
        visitor1.add(new BarEntry(7, 695));
        return visitor1;
    }
    ArrayList<BarEntry> getVisiter4(){
        ArrayList<BarEntry> visitor1 = new ArrayList<>();
        visitor1.add(new BarEntry(1, 456));
        visitor1.add(new BarEntry(2, 7691));
        visitor1.add(new BarEntry(3, 456));
        visitor1.add(new BarEntry(4, 5));
        visitor1.add(new BarEntry(5, 890));
        visitor1.add(new BarEntry(6, 345));
        visitor1.add(new BarEntry(7, 89));
        return visitor1;
    }



}