package com.example.virtualtravelapp.fragment;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.database.DBManager;
import com.example.virtualtravelapp.model.DiaDanh;
import com.example.virtualtravelapp.widget.CustomChartView;

import java.util.Collections;
import java.util.List;

public class BaoCaoFragment extends Fragment {

    private DBManager dbManager;
    private CustomChartView customChartView, customChartView2;
    private TextView topDestinationsTextView, topDestinationsTextView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baocao, container, false);
        getActivity().setTitle("Báo Cáo/Thống kê");
        customChartView = view.findViewById(R.id.customChartView);
        customChartView2 = view.findViewById(R.id.customChartView2);
        topDestinationsTextView = view.findViewById(R.id.topDestinationsTextView);
        topDestinationsTextView2 = view.findViewById(R.id.topDestinationsTextView2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbManager = new DBManager(getContext());
        List<DiaDanh> data = dbManager.getDiaDanh();

        List<DiaDanh> top4ByQuantity = getTop4DestinationsByQuantity(data);
        updateChartView(customChartView, top4ByQuantity, true);
        updateTextView(topDestinationsTextView, top4ByQuantity, true);

        List<DiaDanh> top4ByPrice = getTop4DestinationsByPrice(data);
        updateChartView(customChartView2, top4ByPrice, false);
        updateTextView(topDestinationsTextView2, top4ByPrice, false);
    }

    private List<DiaDanh> getTop4DestinationsByQuantity(List<DiaDanh> data) {
        Collections.sort(data, (o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity()));
        return data.subList(0, Math.min(data.size(), 4));
    }

    private List<DiaDanh> getTop4DestinationsByPrice(List<DiaDanh> data) {
        Collections.sort(data, (o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice()));
        return data.subList(0, Math.min(data.size(), 4));
    }

    private void updateChartView(CustomChartView chartView, List<DiaDanh> topDestinations, boolean useQuantity) {
        float[] chartData = new float[topDestinations.size()];
        int[] chartColors = new int[topDestinations.size()];
        String[] labels = new String[topDestinations.size()];

        for (int i = 0; i < topDestinations.size(); i++) {
            DiaDanh diaDanh = topDestinations.get(i);
            chartData[i] = useQuantity ? diaDanh.getQuantity() : diaDanh.getPrice();
            chartColors[i] = getColorForIndex(i);
            labels[i] = diaDanh.getNameDiaDanh();
        }

        chartView.setData(chartData, chartColors, labels);
    }


    private void updateTextView(TextView textView, List<DiaDanh> topDestinations, boolean showQuantity) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topDestinations.size(); i++) {
            DiaDanh diaDanh = topDestinations.get(i);
            int color = getColorForIndex(i);
            String text = diaDanh.getNameDiaDanh() + " - " + (showQuantity ? diaDanh.getQuantity() + " tours" : diaDanh.getPrice() + " VNĐ") + "\n";
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(color), 0, diaDanh.getNameDiaDanh().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(spannableString);
        }
        textView.setText(sb.toString());
    }

    private int getColorForIndex(int index) {
        switch (index) {
            case 0:
                return getResources().getColor(android.R.color.holo_red_dark);
            case 1:
                return getResources().getColor(android.R.color.holo_green_dark);
            case 2:
                return getResources().getColor(android.R.color.holo_blue_dark);
            case 3:
                return getResources().getColor(android.R.color.holo_orange_dark);
            default:
                return getResources().getColor(android.R.color.darker_gray);
        }
    }
}
