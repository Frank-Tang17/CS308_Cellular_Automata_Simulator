package cellsociety.view;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Class that makes a simulation graph in a new window with a corresponding simulation
 *
 * @author Frank Tang
 */
public class SimulationGraph {

  private final int graphWindowHeight = 400;
  private final int graphWindowWidth = 400;


  private NumberAxis xAxis = new NumberAxis();
  private NumberAxis yAxis = new NumberAxis();

  private LineChart<Number, Number> simulationGraphChart;

  private XYChart.Series series1 = new XYChart.Series();
  private XYChart.Series series2 = new XYChart.Series();
  private XYChart.Series series3 = new XYChart.Series();
  private ResourceBundle graphResources;

  /**
   * Makes a graph in a new window that is instantiated when a valid simulation is loaded
   */

  public SimulationGraph(String selectedSimulation, ResourceBundle languageBundle) {
    graphResources = languageBundle;

    BorderPane graphRoot = new BorderPane();
    Scene scene = new Scene(graphRoot, graphWindowWidth, graphWindowHeight);
    Stage stage = new Stage();
    stage.setTitle(graphResources.getString("simulationGraphWindowTitle"));

    simulationGraphChart = makeLineChart(selectedSimulation);

    graphRoot.setCenter(simulationGraphChart);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Method that makes a line chart object
   */

  private LineChart makeLineChart(String selectedSimulation) {
    LineChart<Number, Number> newChart =
        new LineChart<>(xAxis, yAxis);
    newChart.setCreateSymbols(false);
    yAxis.setLabel(graphResources.getString("yAxis"));
    xAxis.setLabel(graphResources.getString("xAxis"));
    newChart.setTitle(selectedSimulation);

    series1.setName(graphResources.getString("cell1"));
    series2.setName(graphResources.getString("cell2"));
    series3.setName(graphResources.getString("cell3"));
    newChart.getData().addAll(series1, series2, series3);

    return newChart;
  }

  /**
   * Method that updates the line chart as the simulation is running
   */

  public void updateGraph(int frameNumber, int state1Total, int state2Total, int state3Total) {
    series1.getData().add(new XYChart.Data(frameNumber, state1Total));
    series2.getData().add(new XYChart.Data(frameNumber, state2Total));
    series3.getData().add(new XYChart.Data(frameNumber, state3Total));

  }

}
