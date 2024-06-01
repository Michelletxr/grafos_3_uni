package Test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;

import org.jfree.chart.util.ShapeUtils;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;




public class NetworkGraphPlotter extends ApplicationFrame {


    public static class NetworkGraph {

        private Graph<String, DefaultEdge> graph;
        private Map<String, Integer> capacities;
        private Map<String, Integer> costs;

        public NetworkGraph() {
            graph = new DefaultDirectedWeightedGraph<>(DefaultEdge.class);
            capacities = new HashMap<>();
            costs = new HashMap<>();
        }

        public void addNode(String node) {
            graph.addVertex(node);
        }

        public void addEdge(String from, String to, int capacity, int cost) {
            DefaultEdge edge = graph.addEdge(from, to);
            graph.setEdgeWeight(edge, capacity); // Use capacity as edge weight
            String edgeKey = from + "->" + to;
            capacities.put(edgeKey, capacity);
            costs.put(edgeKey, cost);
        }

        public Graph<String, DefaultEdge> getGraph() {
            return graph;
        }

        public Map<String, Integer> getCapacities() {
            return capacities;
        }

        public Map<String, Integer> getCosts() {
            return costs;
        }
    }

    public NetworkGraphPlotter(String title, NetworkGraph networkGraph) {
        super(title);

        JFreeChart chart = createChart(networkGraph);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(NetworkGraph networkGraph) {
        XYSeriesCollection dataset = createDataset(networkGraph);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Network Graph",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        // Customizing the plot
        plot.getRenderer().setDefaultItemLabelsVisible(true);
        plot.getRenderer().setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
        plot.getRenderer().setDefaultItemLabelPaint(Color.black);

        // Set shapes for the nodes
        Shape circle = ShapeUtils.createRegularCross(3, 1);
        plot.getRenderer().setDefaultShape(circle);
        plot.getRenderer().setDefaultPaint(Color.blue);

        return chart;
    }

    private XYSeriesCollection createDataset(NetworkGraph networkGraph) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Map<String, XYSeries> nodeSeries = new HashMap<>();

        Set<String> nodes = networkGraph.getGraph().vertexSet();
        int i = 0;
        for (String node : nodes) {
            XYSeries series = new XYSeries(node);
            series.add(i, i);
            nodeSeries.put(node, series);
            i++;
        }

        for (XYSeries series : nodeSeries.values()) {
            dataset.addSeries(series);
        }

        return dataset;
    }

    public static void main(String[] args) {
        NetworkGraph networkGraph = new NetworkGraph();
        networkGraph.addNode("source");
        networkGraph.addNode("clientA");
        networkGraph.addNode("clientB");
        networkGraph.addNode("clientC");
        networkGraph.addNode("serverA");
        networkGraph.addNode("serverB");
        networkGraph.addNode("serverC");
        networkGraph.addNode("databaseSink");

        networkGraph.addEdge("source", "clientA", 10, 0);
        networkGraph.addEdge("source", "clientB", 15, 0);
        networkGraph.addEdge("source", "clientC", 20, 0);

        networkGraph.addEdge("clientA", "serverA", 5, 5);
        networkGraph.addEdge("clientB", "serverB", 10, 15);
        networkGraph.addEdge("clientC", "serverC", 15, 5);

        networkGraph.addEdge("serverA", "databaseSink", 50, 10);
        networkGraph.addEdge("serverB", "databaseSink", 50, 10);
        networkGraph.addEdge("serverC", "databaseSink", 50, 10);

        NetworkGraphPlotter plotter = new NetworkGraphPlotter("Network Graph", networkGraph);
        plotter.pack();
        RefineryUtilities.centerFrameOnScreen(plotter);
        plotter.setVisible(true);
    }
}
