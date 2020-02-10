import cellsociety.view.SimulationWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class SimulationApplication extends Application {

  /**
   * Starts a new Simulation Window
   */
  public void start(Stage stage) {
    new SimulationWindow(stage);
  }

  /**
   * Starts the program
   */
  public static void main(String[] args) {
    launch(args);
  }
}


