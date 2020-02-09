package cellsociety;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SimulationGraph {
  private NumberAxis xAxis = new NumberAxis();
  private NumberAxis yAxis = new NumberAxis();
  //creating the chart
  private LineChart<Number,Number> simulationGraphChart;

  private XYChart.Series series1 = new XYChart.Series();
  private XYChart.Series series2 = new XYChart.Series();
  private XYChart.Series series3 = new XYChart.Series();
  private ResourceBundle graphResources;

  public SimulationGraph(String selectedSimulation, String languageSelected){
    graphResources = ResourceBundle.getBundle(languageSelected);

    BorderPane graphRoot = new BorderPane();
    Scene scene = new Scene(graphRoot, 400,400);
    Stage stage = new Stage();
    stage.setTitle(graphResources.getString("simulationGraphWindowTitle"));

    simulationGraphChart = makeLineChart(selectedSimulation);

    graphRoot.setCenter(simulationGraphChart);
    stage.setScene(scene);
    stage.show();
  }

  private LineChart makeLineChart(String selectedSimulation){
    LineChart<Number,Number> newChart =
        new LineChart<>(xAxis, yAxis);
    series1.setName(graphResources.getString("cell1"));
    series2.setName(graphResources.getString("cell2"));
    series3.setName(graphResources.getString("cell3"));

    newChart.getData().addAll(series1, series2, series3);
    newChart.setCreateSymbols(false);

    xAxis.setLabel(graphResources.getString("xAxis"));
    yAxis.setLabel(graphResources.getString("yAxis"));

    newChart.setTitle(selectedSimulation);
    return newChart;
  }

  public void updateGraph(int frameNumber, int state1Total, int state2Total, int state3Total){
    series1.getData().add(new XYChart.Data(frameNumber, state1Total));
    series2.getData().add(new XYChart.Data(frameNumber, state2Total));
    series3.getData().add(new XYChart.Data(frameNumber, state3Total));

  }

}
