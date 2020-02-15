package cellsociety.view;

/**
 * This class is well-designed because it allows us to display an Error message whenever an
 * error can occur in the program. By being its own object with a specific purpose, we can use it
 * anywhere in the program as needed.
 */

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class that is used to help catch errors and display messages about the type of error
 *
 * @author Frank Tang
 */
public class DisplayError {

  /**
   * Makes an alert pop up with a specific message in it
   */
  public DisplayError(ResourceBundle languageBundle, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(languageBundle.getString("ErrorTitle"));
    alert.setContentText(languageBundle.getString(message));
    alert.showAndWait();
  }
}

