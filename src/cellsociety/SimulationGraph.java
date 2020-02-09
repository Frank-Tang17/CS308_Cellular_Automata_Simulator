package cellsociety;

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
  private LineChart<Number,Number> lineChart =
      new LineChart<>(xAxis, yAxis);

  private XYChart.Series series1 = new XYChart.Series();
  private XYChart.Series series2 = new XYChart.Series();
  private XYChart.Series series3 = new XYChart.Series();


  public SimulationGraph(){
    BorderPane graphRoot = new BorderPane();
    Scene scene = new Scene(graphRoot, 400,400);
    Stage stage = new Stage();
    stage.setTitle("Simulation Graph");

    series1.setName("State 1");
    series2.setName("State 2");
    series3.setName("State 3");

    lineChart.getData().addAll(series1, series2, series3);

    xAxis.setLabel("Simulation Frame Number");
    yAxis.setLabel("Number of Cells");

    lineChart.setTitle("Cell Simulation");
    graphRoot.setCenter(lineChart);
    stage.setScene(scene);
    stage.show();
  }

  public void updateGraph(int frameNumber, int state1Total, int state2Total, int state3Total){
    series1.getData().add(new XYChart.Data(frameNumber, state1Total));
    series2.getData().add(new XYChart.Data(frameNumber, state2Total));
    series3.getData().add(new XYChart.Data(frameNumber, state3Total));

  }

}
