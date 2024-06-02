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
import java.util.Scanner;

public class PerformanceComparisonGrafico {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       // System.out.println("Digite o nome do arquivo contendo os dados do Cliente A:");
        String fileNameA = "src/main/resources/client_A.txt";

       // System.out.println("Digite o nome do arquivo contendo os dados do Cliente B:");
        String fileNameB ="src/main/resources/client_A_rb.txt";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        readData(fileNameA, "Cliente A", dataset);
        readData(fileNameB, "Cliente B", dataset);

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
        return ChartFactory.createLineChart(
                "Comparação de Desempenho de Microsserviços",
                "Total de Requisições",
                "Tempo de Resposta Médio (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }
}

