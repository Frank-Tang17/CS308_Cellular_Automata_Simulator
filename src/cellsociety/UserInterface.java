package cellsociety;
import java.awt.Window;
import java.io.File;
import java.io.ObjectInputFilter.Config;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * Class to launch the simulation program
 *
 * @author Frank Tang
 */
public class UserInterface {

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + "/";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  private static final String STYLESHEET = "userInterface.css";
  private static final String BLANK = " ";

  private Group grid = new Group();

  private static final int FIRST_ROW = 0;
  private static final int SECOND_ROW = 1;
  private static final int THIRD_ROW = 2;


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
  private Button saveSimulationButton;
  private Button selectSimulationButton;
  private Button randomizeSimulationButton;
  private Slider simulationSpeedSlider;
  private Text simulationTitle;
  private File initialDirectory = new File("./resources/");
  private File currentSimulationFile;

  private String selectedSimulationName;

  private ResourceBundle userInterfaceResources;
  private Simulator currentSimulation;
  private Configuration currentSimulationConfig;
  private String languageSelected;

  public UserInterface(String language){
    languageSelected = language;
    userInterfaceResources = ResourceBundle.getBundle(languageSelected);
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));
    root.getChildren().add(grid);
    enableAndDisableButtons();
    userInterfaceScene = new Scene(root, width, height);
    // activate CSS styling
    userInterfaceScene.getStylesheets().add(getClass().getClassLoader().getResource(STYLESHEET).toExternalForm());

    return userInterfaceScene;
  }

  // makes a button using either an image or a label
  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button resultButton = new Button();
    String label = userInterfaceResources.getString(property);
    if (label.matches(IMAGEFILE_SUFFIXES)) {
      resultButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
    }
    else {
      resultButton.setText(label);
    }
    resultButton.setOnAction(handler);
    resultButton.setId(property);
    return resultButton;
  }

  private Slider makeSlider (String property) {
    // represent all supported image suffixes
    Slider slider = new Slider();
    slider.setId(property);

    slider.setMin(minSimulationRate);
    slider.setMax(maxSimulationRate);
    slider.setValue(minSimulationRate);
    slider.setMajorTickUnit(minSimulationRate);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.valueProperty().addListener(
        (ov, old_val, new_val) -> currentSimulation.setSimulationRate((Double) new_val));
    return slider;
  }

  private void enableAndDisableButtons(){
    pauseButton.setDisable(controlDisabled);
    forwardButton.setDisable(controlDisabled);
    resetButton.setDisable(controlDisabled);
    saveSimulationButton.setDisable(controlDisabled);
    randomizeSimulationButton.setDisable(controlDisabled);
    simulationSpeedSlider.setDisable(controlDisabled);
  }

  private Node makeSimulationControlPanel (String nodeID) {
    GridPane controlPanel = new GridPane();

    pauseButton = makeButton("pauseButton", e -> currentSimulation.pauseResume());
    controlPanel.add(pauseButton, FIRST_COL, FIRST_ROW);

    forwardButton = makeButton("forwardButton", e -> currentSimulation.stepForward());
    controlPanel.add(forwardButton, SECOND_COL, FIRST_ROW);

    resetButton = makeButton("resetButton", e -> resetSimulation());
    controlPanel.add(resetButton, THIRD_COL, FIRST_ROW);

    simulationSpeedSlider = makeSlider("simulationSpeedSlider");
    controlPanel.add(simulationSpeedSlider, SECOND_COL, THIRD_ROW);

    selectSimulationButton = makeButton("selectSimulationButton", e -> chooseFile());
    controlPanel.add(selectSimulationButton, SECOND_COL, SECOND_ROW);

    loadSimulationButton = makeButton("loadSimulationButton", e -> loadSimulation(currentSimulationFile));
    controlPanel.add(loadSimulationButton, THIRD_COL, SECOND_ROW);

    randomizeSimulationButton = makeButton("randomizeSimulationButton", e -> loadSimulation(currentSimulationFile));
    controlPanel.add(randomizeSimulationButton, FIRST_COL, SECOND_ROW);

    controlPanel.setId(nodeID);
    return controlPanel;
  }


  private Node makeGameDisplayPanel (String nodeID) {
    HBox gameDisplay = new HBox();

    makeSimulationWindow = makeButton("makeNewSimulationButton", e -> new SimulationWindow(new Stage()));
    gameDisplay.getChildren().add(makeSimulationWindow);

    simulationTitle = new Text();
    gameDisplay.getChildren().add(simulationTitle);

    saveSimulationButton = makeButton("saveSimulationButton", e -> chooseFile());
    gameDisplay.getChildren().add(saveSimulationButton);

    gameDisplay.setId(nodeID);
    return gameDisplay;
  }

  private void loadSimulation(File simulationFile){
    if (simulationFile == null) {
      new DisplayError(languageSelected, "NullSelection");
    }
    else{
      currentSimulationConfig = new Configuration(this.currentSimulationFile);
      selectedSimulationName = currentSimulationConfig.getType();
      makeSimulation(selectedSimulationName);
      simulationTitle.setText(selectedSimulationName);
      controlDisabled = false;
      enableAndDisableButtons();
    }
  }

  public void resetSimulation() {
    currentSimulation = null;
    grid.getChildren().clear();
    makeSimulation(selectedSimulationName);
  }

  public void makeSimulation(String selectedSimulationName){
    currentSimulation = new Simulator(currentSimulationConfig, selectedSimulationName, userInterfaceScene, languageSelected);
    currentSimulation.runSimulation(grid);
  }

  public void chooseFile () {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(initialDirectory);
    fileChooser.setTitle("Choose Simulation XML Configuration File");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("XML Files", "*.xml"));
    File selectedFile = fileChooser.showOpenDialog(new Stage());
    currentSimulationFile = selectedFile;
  }

}

