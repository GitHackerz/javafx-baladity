package org.example.javafxbaladity.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.example.javafxbaladity.Services.CitoyenService;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class GenderStatisticsChart {
    CitoyenService c = new CitoyenService();

    public LineChart<Number, Number> createChart() {
        final int lowerBoundYear = 2000;
        final int upperBoundYear = LocalDate.now().getYear();

        NumberAxis xAxis = new NumberAxis(lowerBoundYear, upperBoundYear, 1);
        xAxis.setLabel("Year");
        xAxis.setAutoRanging(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Citoyens");
        yAxis.setAutoRanging(true);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Gender Distribution Over Years");

        XYChart.Series<Number, Number> maleSeries = new XYChart.Series<>();
        maleSeries.setName("Male");
        XYChart.Series<Number, Number> femaleSeries = new XYChart.Series<>();
        femaleSeries.setName("Female");

        Map<Integer, Map<String, Integer>> statistics = c.getGenderStatisticsOverTime();
        Map<Integer, Map<String, Integer>> sortedStatistics = new TreeMap<>(statistics);

        for (Map.Entry<Integer, Map<String, Integer>> entry : sortedStatistics.entrySet()) {
            int year = entry.getKey();
            Map<String, Integer> counts = entry.getValue();
            maleSeries.getData().add(new XYChart.Data<>(year, counts.getOrDefault("male", 0)));
            femaleSeries.getData().add(new XYChart.Data<>(year, counts.getOrDefault("female", 0)));
        }

        lineChart.getData().addAll(maleSeries, femaleSeries);
        return lineChart;
    }
}
