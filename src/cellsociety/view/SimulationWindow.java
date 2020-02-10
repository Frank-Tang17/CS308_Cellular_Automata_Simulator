package cellsociety.view;

import java.awt.Dimension;
import javafx.stage.Stage;

public class SimulationWindow {

  private static final String TITLE = "Simulation";
  private static final Dimension WINDOW_SIZE = new Dimension(600, 600);

  public SimulationWindow(Stage stage) {
    UserInterface GUI = new UserInterface("English");

    stage.setTitle(TITLE);
    stage.setScene(GUI.setupUserInterface(WINDOW_SIZE.width, WINDOW_SIZE.height));
    stage.show();


  }

}
