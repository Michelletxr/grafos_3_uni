package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public interface PerformanceComparisonGrafico {

    public static void main(String[] args) {

        String fileNameA = "src/main/resources/client_C.txt";

        String fileNameB ="src/main/resources/client_C_rb.txt";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        readData(fileNameA, "MinCost-MaxFlow", dataset);
        readData(fileNameB, "Round-Robin", dataset);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Comparação de Desempenho de Microsserviços");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(createChart(dataset)));
            frame.pack();
            frame.setVisible(true);
        });
    }

    private static void readData(String fileName, String clientName, DefaultCategoryDataset dataset) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String totalRequestsStr = parts[0].split(":")[1].trim();
                String responseTimesStr = parts[1].split(":")[1].trim();
                responseTimesStr = responseTimesStr.substring(1, responseTimesStr.length() - 1); // Remove brackets
                String[] responseTimes = responseTimesStr.split(",");

                double averageResponseTime = Arrays.stream(responseTimes)
                        .mapToDouble(rt -> Double.parseDouble(rt.trim()))
                        .average()
                        .orElse(0.0);

                dataset.addValue(averageResponseTime, clientName, totalRequestsStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JFreeChart createChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Comparação de Desempenho de Microsserviços",
                "Total de Requisições",
                "Tempo de Resposta Médio (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Define as cores das linhas
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.blue);

        plot.setRenderer(renderer);

        return chart;
    }
}

