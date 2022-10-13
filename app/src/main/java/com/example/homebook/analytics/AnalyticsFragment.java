package com.example.homebook.analytics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.MainActivity;
import com.example.homebook.catalog.CatalogViewModel;
import com.example.homebook.data.analyticsdata.AnalyticsItem;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.databinding.FragmentAnalyticsBinding;
import com.example.homebook.services.DateTimeUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private MainActivity mainActivity;
    private AnalyticsViewModel analyticsViewModel;
    private List<AnalyticsItem> analyticsItemList = new ArrayList<>();
    private CatalogViewModel catalogViewModel;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        analyticsViewModel = new ViewModelProvider(mainActivity).get(AnalyticsViewModel.class);
        catalogViewModel = new ViewModelProvider(mainActivity).get(CatalogViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container,false);

        analyticsViewModel.getAnalyticsItemList().observe(getViewLifecycleOwner(), this::createPieChart);
        createBarChart();

        createBarChart();
        return binding.getRoot();
    }

    private void createBarChart() {
        catalogViewModel.getAllCatalogs().observe(getViewLifecycleOwner(), catalogs -> {

            int[] result = calculateNumberOfCatalogsByMonth(catalogs);
            String[] months = setMonths();

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);
            colors.add(Color.MAGENTA);
            colors.add(Color.GREEN);
            colors.add(Color.CYAN);
            colors.add(Color.GRAY);
            colors.add(Color.rgb(0, 255, 209));
            colors.add(Color.rgb(92, 46, 126));
            colors.add(Color.rgb(139, 188, 204));
            colors.add(Color.rgb(208, 184, 168));
            colors.add(Color.rgb(249, 102, 102));

            BarChart barChart = binding.barChart;
            Description description = new Description();
            description.setText("How many times a month you did the shopping");
            description.setTextColor(Color.parseColor("#0A03DA"));
            description.setTextSize(15);
            barChart.animateY(1000);
            barChart.setBorderColor(Color.parseColor("#0A03DA"));
            barChart.setHorizontalScrollBarEnabled(true);
            barChart.setVerticalScrollBarEnabled(true);
            barChart.setEnabled(true);
            barChart.setDescription(description);
            barChart.setDrawBorders(false);

            YAxis rightAxis = barChart.getAxisRight();
            barChart.getAxisLeft().setAxisMaximum(10f);
            barChart.getAxisLeft().setGridColor(Color.parseColor("#0A03DA"));
            barChart.getAxisLeft().setAxisLineColor(Color.parseColor("#0A03DA"));
            rightAxis.setAxisMaximum(10f);
            rightAxis.setGridColor(Color.parseColor("#0A03DA"));
            rightAxis.setAxisLineColor(Color.parseColor("#0A03DA"));

            ArrayList<BarEntry> entries = new ArrayList<>();

            for (int i = 0; i < result.length; i++) {
                BarEntry barEntry = new BarEntry(i, result[i]);
                entries.add(barEntry);
            }

            Legend legend = barChart.getLegend();
            legend.setEnabled(false);

            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
            barChart.getXAxis().setGranularity(1f);
            barChart.getXAxis().setLabelCount(months.length);
            barChart.getXAxis().setLabelRotationAngle(270);
            barChart.getXAxis().setGridColor(Color.parseColor("#0A03DA"));
            barChart.getXAxis().setTextColor(Color.parseColor("#0A03DA"));

            BarDataSet barDataSet = new BarDataSet(entries, "Number of times");
            barDataSet.setColors(colors);
            barDataSet.setValueTextSize(12);

            BarData barData = new BarData(barDataSet);
            barData.setValueTextColor(Color.parseColor("#0A03DA"));
            barChart.setData(barData);
            barChart.invalidate();
        });
    }

    private void createPieChart(List<AnalyticsItem> analyticsItems){
        PieChart pieChart = binding.pieChart;
        Description description = new Description();
        description.setText("Top five most purchased items in your inventory");
        description.setTextColor(Color.parseColor("#0A03DA"));
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

        PieDataSet pieDataSet = new PieDataSet(yEntries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.GREEN);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(15);
        legend.setTextColor(Color.parseColor("#0A03DA"));
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);

        List<LegendEntry> legendEntries = new ArrayList<>();

        for (int i = 0; i < analyticsItems.size(); i++) {
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.formColor = colors.get(i);
            legendEntry.label = xEntries.get(i);
            legendEntries.add(legendEntry);
        }
        legend.setCustom(legendEntries);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private int[] calculateNumberOfCatalogsByMonth(List<Catalog> catalogs){

        int[] numberOfShopping = new int[12];
        for (int i = 0; i < 12; i++) {
            numberOfShopping[i] = 0;
        }
        for(Catalog catalog : catalogs){
            try {
                Date date = DateTimeUtil.getSimpleDateFormat().parse(catalog.getDate());
                Calendar calendar = Calendar.getInstance();
                if (date != null) {
                    calendar.setTime(date);
                    switch (calendar.get(Calendar.MONTH)){
                        case Calendar.JANUARY: {
                            numberOfShopping[0]++;
                            break;
                        }
                        case Calendar.FEBRUARY: {
                            numberOfShopping[1]++;
                            break;
                        }
                        case Calendar.MARCH: {
                            numberOfShopping[2]++;
                            break;
                        }
                        case Calendar.APRIL: {
                            numberOfShopping[3]++;
                            break;
                        }
                        case Calendar.MAY: {
                            numberOfShopping[4]++;
                            break;
                        }
                        case Calendar.JUNE: {
                            numberOfShopping[5]++;
                            break;
                        }
                        case Calendar.JULY: {
                            numberOfShopping[6]++;
                            break;
                        }
                        case Calendar.AUGUST: {
                            numberOfShopping[7]++;
                            break;
                        }
                        case Calendar.SEPTEMBER: {
                            numberOfShopping[8]++;
                            break;
                        }
                        case Calendar.OCTOBER: {
                            numberOfShopping[9]++;
                            break;
                        }
                        case Calendar.NOVEMBER: {
                            numberOfShopping[10]++;
                            break;
                        }
                        case Calendar.DECEMBER: {
                            numberOfShopping[11]++;
                            break;
                        }
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return numberOfShopping;
    }

    private String[] setMonths(){

        String[] months = new String[12];
        months[0] = "Jan";
        months[1] = "Feb";
        months[2] = "Mar";
        months[3] = "Apr";
        months[4] = "May";
        months[5] = "Jun";
        months[6] = "Jul";
        months[7] = "Aug";
        months[8] = "Sep";
        months[9] = "Oct";
        months[10] = "Nov";
        months[11] = "Dec";

        return months;
    }
}