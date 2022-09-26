package com.example.homebook.analytics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.MainActivity;
import com.example.homebook.data.analyticsdata.AnalyticsItem;
import com.example.homebook.databinding.FragmentAnalyticsBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private MainActivity mainActivity;
    private AnalyticsViewModel analyticsViewModel;
    private List<AnalyticsItem> analyticsItemList = new ArrayList<>();

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        analyticsViewModel = new ViewModelProvider(mainActivity).get(AnalyticsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container,false);

        analyticsViewModel.getAnalyticsItemList().observe(getViewLifecycleOwner(), analyticsItems -> {

            PieChart pieChart = binding.chart;
            Description description = new Description();
            description.setText("Top five most purchased items in your inventory");
            description.setTextSize(15);
            pieChart.setDescription(description);
            pieChart.setHoleRadius(0f);
            pieChart.setTransparentCircleAlpha(0);
            pieChart.setUsePercentValues(true);

            ArrayList<PieEntry> yEntries = new ArrayList<>();
            ArrayList<String> xEntries = new ArrayList<>();

            for (int i = 0; i < analyticsItems.size(); i++) {
                yEntries.add(new PieEntry(analyticsItems.get(i).getAmountBought(), i));
            }

            for (int i = 0; i < analyticsItems.size(); i++) {
                xEntries.add(analyticsItems.get(i).getItemName());
            }

            PieDataSet pieDataSet = new PieDataSet(yEntries, "Item number of purchase");
            pieDataSet.setSliceSpace(2);
            pieDataSet.setValueTextSize(12);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);
            colors.add(Color.MAGENTA);
            colors.add(Color.GREEN);

            pieDataSet.setColors(colors);

            Legend legend = pieChart.getLegend();
            legend.setEnabled(true);
            legend.setTextSize(15);
            legend.setTextColor(Color.BLACK);
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            legend.setForm(Legend.LegendForm.SQUARE);

            List<LegendEntry> legendEntries = new ArrayList<>();

            for (int i = 0; i < analyticsItems.size(); i++) {
                LegendEntry legendEntry = new LegendEntry();
                legendEntry.formColor = colors.get(i);
                legendEntry.label = xEntries.get(i);
                legendEntries.add(legendEntry);
            }
            legend.setCustom(legendEntries);

            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
        });

        return binding.getRoot();
    }
}