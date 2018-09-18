package com.roonit.jikinge.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAlign;
import com.anychart.anychart.LegendLayout;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.anychart.anychart.chart.common.Event;
import com.anychart.anychart.chart.common.ListenersInterface;
import com.roonit.jikinge.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisFragment extends Fragment {

    private final static String BUG="debug";




    public StatisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_statis, container, false);
        AnyChartView anyChartView1 = view.findViewById(R.id.any_chart_view);
        anyChartView1.setProgressBar(view.findViewById(R.id.progress_bar));


        Pie pie1 = AnyChart.pie();

        pie1.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Confirmés", 120));
        data.add(new ValueDataEntry("Probables", 10));
        data.add(new ValueDataEntry("Suspects", 70));
        data.add(new ValueDataEntry("Guéris", 15));
        data.add(new ValueDataEntry("Décédés", 5));

        pie1.setData(data);

        pie1.setTitle("Ebola à Mangina 2018(%)");

        pie1.getLabels().setPosition("outside");

        pie1.getLegend().getTitle().setEnabled(true);
        pie1.getLegend().getTitle()
                .setText("Synthèse Riposte")
                .setPadding(0d, 0d, 10d, 0d);

        pie1.getLegend()
                .setPosition("center-bottom")
                .setItemsLayout(LegendLayout.HORIZONTAL)
                .setAlign(EnumsAlign.CENTER);

        anyChartView1.setChart(pie1);




        return view;
    }

}
