package cellsociety.view;

import java.awt.Dimension;
import javafx.stage.Stage;

/**
 * Class that makes a new simulation window so that multiple simulations can be run side-by-side
 *
 * @author Frank Tang
 */

public class SimulationWindow {

  private static final String TITLE = "Simulation";
  private static final Dimension WINDOW_SIZE = new Dimension(600, 600);

  /**
   * Makes a new simulation window where another simulation can be run separately.
   */
  public SimulationWindow(Stage stage) {
    UserInterface GUI = new UserInterface("English");

    stage.setTitle(TITLE);
    stage.setScene(GUI.setupUserInterface(WINDOW_SIZE.width, WINDOW_SIZE.height));
    stage.show();

  }

}
