package cellsociety;

import java.awt.Dimension;
import javafx.application.Application;
import javafx.stage.Stage;


public class SimulationApplication extends Application {

  public static final String TITLE = "Simulation";
  public static final Dimension WINDOW_SIZE = new Dimension(600, 600);

  public void start(Stage stage) {

    // attach scene to the stage and display it
    UserInterface GUI = new UserInterface("English");

    stage.setTitle(TITLE);
    stage.setScene(GUI.setupUserInterface(WINDOW_SIZE.width, WINDOW_SIZE.height));
    stage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}


