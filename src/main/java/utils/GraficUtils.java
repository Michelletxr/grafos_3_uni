package utils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
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

    private static JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(
                "Gráfico de Linha",
                "Total Requests",
                "Response Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gráfico de Linha");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(createChart(readData("/home/smart-retail/Documentos/grafo/grafos_3_uni/src/main/resources/client_C.txt"))));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
