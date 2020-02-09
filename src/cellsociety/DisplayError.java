package cellsociety;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DisplayError {
  private static final String ERROR_EXTENSION = "ErrorMessages";
  ResourceBundle errorEnglishResources;

  public DisplayError(String language, String message){
    errorEnglishResources = ResourceBundle.getBundle(language + ERROR_EXTENSION);
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(errorEnglishResources.getString("ErrorTitle"));
    alert.setContentText(errorEnglishResources.getString(message));
    alert.showAndWait();
    }
}

