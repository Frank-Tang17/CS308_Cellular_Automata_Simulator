package cellsociety.view;

/**
 * This piece of code is well designed because it represents the User Interface of the program
 * and is very separate from the rest of the code. It tells and instantiates objects that it
 * needs in order for the program to function, such as making Simulator objects when the user loads
 * one, and also has private methods for the specific creation of its GUI. I was tempted to split this
 * into separate classes, and while I do see some value in splitting it up into classes like GameDisplay
 * and ControlPanel; however, I felt that this was unnecessary because these interface pieces are only going
 * to be made once per simulation instance. By having them as separate objects, it introduces the ability for
 * a programmer to call these objects in another place where they do not belong. Thus, I kept the creation methods
 * of the interface private in this file.
 */

import cellsociety.configuration.Configuration;
import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Class to make user interface (buttons, sliders, etc.) of the program
 *
 * @author Frank Tang
 */
public class UserInterface {

  private static final String STYLESHEET = "userInterface.css";

  private Group grid = new Group();

  private static final int FIRST_ROW = 0;
  private static final int SECOND_ROW = 1;

  private static final int FIRST_COL = 0;
  private static final int SECOND_COL = 1;
  private static final int THIRD_COL = 2;

  private final double maxSimulationRate = 10;
  private final double minSimulationRate = 1;

  private boolean controlDisabled = true;

  private Scene userInterfaceScene;

  private String controlPanelID = "controlPanel";
  private String gameDisplayID = "gameDisplay";

  private Button pauseButton;
  private Button forwardButton;
  private Button resetButton;
  private Button loadSimulationButton;
  private Button makeSimulationWindow;
  private Button selectSimulationButton;
  private Button randomizeSimulationButton;
  private Slider simulationSpeedSlider;
  private Text simulationTitle;
  private File initialDirectory = new File("./resources/");
  private File currentSimulationFile;

  private String selectedSimulationName;

  private ResourceBundle languageBundle;
  private Simulator currentSimulation;
  private Configuration currentSimulationConfig;

  public UserInterface(ResourceBundle selectedLanguageBundle) {
    languageBundle = selectedLanguageBundle;

  }

  /**
   * makes the GUI of a simulation window
   *
   * @return the scene that the current simulation window is being displayed on
   */
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));
    root.getChildren().add(grid);
    enableAndDisableButtons();
    userInterfaceScene = new Scene(root, width, height);
    // activate CSS styling
    userInterfaceScene.getStylesheets()
        .add(getClass().getClassLoader().getResource(STYLESHEET).toExternalForm());

    return userInterfaceScene;
  }

  /**
   * Method to create a button
   *
   * @return Button with a label, ID, and action
   */
  private Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button resultButton = new Button();
    String label = languageBundle.getString(property);
    resultButton.setText(label);
    resultButton.setOnAction(handler);
    resultButton.setId(property);
    return resultButton;
  }

  /**
   * Method that makes the slider that controls the simulation rate
   *
   * @return Slider with an ID and values for representing the simulation rate
   */
  private Slider makeSimulationSpeedSlider(String property, double minSliderValue, double maxSliderValue) {
    Slider slider = new Slider();
    slider.setId(property);

    slider.setMin(minSliderValue);
    slider.setMax(maxSliderValue);
    slider.setValue(minSliderValue);
    slider.setMajorTickUnit(minSliderValue);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.valueProperty().addListener(
        (ov, old_val, new_val) -> currentSimulation.setSimulationRate((Double) new_val));
    return slider;
  }

  /**
   * Method to disable and enable buttons that will cause errors if run at the wrong time
   */
  private void enableAndDisableButtons() {
    pauseButton.setDisable(controlDisabled);
    forwardButton.setDisable(controlDisabled);
    resetButton.setDisable(controlDisabled);
    randomizeSimulationButton.setDisable(controlDisabled);
    simulationSpeedSlider.setDisable(controlDisabled);
  }

  /**
   * Method that makes the bottom part of the GUI that holds all the buttons
   *
   * @return GridPane with buttons for controlling the simulation
   */
  private Node makeSimulationControlPanel(String nodeID) {
    GridPane controlPanel = new GridPane();

    pauseButton = makeButton("pauseButton", e -> currentSimulation.pauseResume());
    controlPanel.add(pauseButton, FIRST_COL, FIRST_ROW);

    forwardButton = makeButton("forwardButton", e -> currentSimulation.stepForward());
    controlPanel.add(forwardButton, SECOND_COL, FIRST_ROW);

    resetButton = makeButton("resetButton", e -> resetSimulation());
    controlPanel.add(resetButton, THIRD_COL, FIRST_ROW);

    selectSimulationButton = makeButton("selectSimulationButton", e -> chooseFile());
    controlPanel.add(selectSimulationButton, SECOND_COL, SECOND_ROW);

    loadSimulationButton = makeButton("loadSimulationButton",
        e -> loadSimulation(currentSimulationFile));
    controlPanel.add(loadSimulationButton, THIRD_COL, SECOND_ROW);

    randomizeSimulationButton = makeButton("randomizeSimulationButton",
        e -> toggleRandomSimulationButton());
    controlPanel.add(randomizeSimulationButton, FIRST_COL, SECOND_ROW);

    controlPanel.setId(nodeID);
    return controlPanel;
  }

  /**
   * Method to turn on the randomize simulation property and displays a message
   */
  private void toggleRandomSimulationButton() {
    currentSimulationConfig.toggleRandomSimulationGeneration();
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(languageBundle.getString("Random"));
    alert.setContentText(
        languageBundle.getString("randomSimulationStatus") + currentSimulationConfig
            .getRandomSimulationGeneration());
    alert.showAndWait();
    currentSimulationConfig = new Configuration(currentSimulationFile, languageBundle);
  }

  /**
   * Method that makes the top part of the GUI with the new Simulation Window button and the
   * simulation rate slider
   *
   * @return HBox at the top of the GUI that acts as the top display panel
   */
  private Node makeGameDisplayPanel(String nodeID) {
    HBox gameDisplay = new HBox();

    makeSimulationWindow = makeButton("makeNewSimulationButton",
        e -> new SimulationWindow(new Stage()));
    gameDisplay.getChildren().add(makeSimulationWindow);

    simulationTitle = new Text();
    gameDisplay.getChildren().add(simulationTitle);

    simulationSpeedSlider = makeSimulationSpeedSlider("simulationSpeedSlider", minSimulationRate, maxSimulationRate);
    gameDisplay.getChildren().add(simulationSpeedSlider);

    gameDisplay.setId(nodeID);
    return gameDisplay;
  }

  /**
   * Method that acts as the action of the load simulation button Throws errors if a bad file is
   * given or no file is selected. Will start up a new simulation otherwise.
   */
  private void loadSimulation(File simulationFile) {
    if (simulationFile == null) {
      new DisplayError(languageBundle, "NullSelection");
    } else {
      try {
        currentSimulationConfig = new Configuration(this.currentSimulationFile, languageBundle);
        selectedSimulationName = currentSimulationConfig.getType();
        makeSimulation(selectedSimulationName);
        simulationTitle.setText(selectedSimulationName);
        controlDisabled = false;
        enableAndDisableButtons();
      } catch (Exception e) {
        new DisplayError(languageBundle, "BadFile");
      }
    }
  }

  /**
   * Method that resets the current simulation by loading a new simulation of the same type as the
   * previous one.
   */
  private void resetSimulation() {
    currentSimulation = null;
    grid.getChildren().clear();
    currentSimulationConfig = new Configuration(currentSimulationFile, languageBundle);
    makeSimulation(selectedSimulationName);
  }

  /**
   * Method that makes the Simulator object and runs the simulation
   */
  private void makeSimulation(String selectedSimulationName) {
    currentSimulation = new Simulator(currentSimulationConfig, selectedSimulationName,
        userInterfaceScene, languageBundle);
    currentSimulation.runSimulation(grid);
  }

  /**
   * Method that opens a file explorer to the XML file directory for the user to choose a new file.
   */
  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(initialDirectory);
    fileChooser.setTitle(languageBundle.getString("FileTitle"));
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("XML Files", "*.xml"));
    File selectedFile = fileChooser.showOpenDialog(new Stage());
    currentSimulationFile = selectedFile;
  }

}

