package cellsociety.view;

/**
 * This class is well-designed because it instantiates a resource bundle for the specific type of language
 * that we want in the simulation program. In the future, a selection menu can be added here in order
 * to choose the language that we want. This class also allows us to have multiple instances of the simulation
 * stage/window.
 */

import java.awt.Dimension;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * Class that makes a new simulation window so that multiple simulations can be run side-by-side
 *
 * @author Frank Tang
 */

public class SimulationWindow {

  private String language;
  private ResourceBundle languageBundle;
  private static final Dimension WINDOW_SIZE = new Dimension(600, 600);

  /**
   * Makes a new simulation window where another simulation can be run separately.
   */
  public SimulationWindow(Stage stage) {
    language = "English";
    languageBundle = ResourceBundle.getBundle(language);
    UserInterface GUI = new UserInterface(languageBundle);

    stage.setTitle(languageBundle.getString("TITLE"));
    stage.setScene(GUI.setupUserInterface(WINDOW_SIZE.width, WINDOW_SIZE.height));
    stage.show();

  }

}
