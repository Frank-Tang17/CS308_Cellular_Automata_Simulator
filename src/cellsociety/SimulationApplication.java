package cellsociety;

import javafx.application.Application;
import javafx.stage.Stage;

public class SimulationApplication extends Application {

  public void start(Stage stage) {
    new SimulationWindow(stage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


