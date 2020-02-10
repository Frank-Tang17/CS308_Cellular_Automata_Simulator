package cellsociety.view;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class that is used to help catch errors and display messages about the type of error
 *
 * @author Frank Tang
 */
public class DisplayError {

  ResourceBundle errorEnglishResources;

  /**
   * Makes an alert pop up with a specific message in it
   */
  public DisplayError(String language, String message) {
    errorEnglishResources = ResourceBundle.getBundle(language);
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(errorEnglishResources.getString("ErrorTitle"));
    alert.setContentText(errorEnglishResources.getString(message));
    alert.showAndWait();
  }
}

