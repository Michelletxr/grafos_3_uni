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


public interface GraficUtils {
    private static DefaultCategoryDataset readData(String fileName) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String totalRequestsStr = parts[0].split(":")[1].trim();
                String responseTimesStr = parts[1].split(":")[1].trim();
                responseTimesStr = responseTimesStr.substring(1, responseTimesStr.length() - 1); // Remove brackets
                String[] responseTimes = responseTimesStr.split(",");

                double averageResponseTime = Arrays.stream(responseTimes)
                        .mapToDouble(s -> Double.parseDouble(s.trim()))
                        .average()
                        .orElse(0.0);

                dataset.addValue(averageResponseTime, "Tempo de Resposta Médio", totalRequestsStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private static JFreeChart createChart(DefaultCategoryDataset dataset, String clienteName) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Cliente " + clienteName,
                "Total de Requisições",
                "Tempo de Resposta",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        // Define as cores das linhas
        renderer.setSeriesPaint(0, Color.MAGENTA);
        plot.setRenderer(renderer);

        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String clienteName = "A";
            JFrame frame = new JFrame("Gráfico de Linha");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(createChart(readData("src/main/resources/client_"+clienteName+".txt"), clienteName)));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
