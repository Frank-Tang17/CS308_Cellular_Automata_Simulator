package cellsociety;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 * Class to launch the simulation program
 *
 * @author Frank Tang
 */
public class UserInterface {

  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  private static final String STYLESHEET = "userInterface.css";
  private static final String BLANK = " ";

  private Group grid = new Group();

  private static final int FIRST_ROW = 0;
  private static final int SECOND_ROW = 1;

  private static final int FIRST_COL = 0;
  private static final int SECOND_COL = 1;
  private static final int THIRD_COL = 2;



  private Scene userInterfaceScene;

  private String controlPanelID = "controlPanel";
  private String gameDisplayID = "gameDisplay";
  private String initialDirectionsID = "initialDirections";


  private Button forwardButton;
  private ComboBox selectSimulationBox;
  private ObservableList<String> configurationArray = FXCollections.observableArrayList("Percolation", "GameOfLife", "Fire", "Segregation", "PredatorPrey");
  private String selectedSimulationName;

  private ResourceBundle userInterfaceResources;
  private Simulator currentSimulation;

  public UserInterface(String language){
    userInterfaceResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "/" +language);
  }
  /**
   * Initialize what will be displayed and how it will be updated.
   */

  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));
    root.setCenter(makeText(initialDirectionsID));
    root.getChildren().add(grid);
    //enableButtons();
    userInterfaceScene = new Scene(root, width, height);
    // activate CSS styling
    userInterfaceScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

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

  private ComboBox makeComboBox (String property, ObservableList options) {
    ComboBox resultBox = new ComboBox(options);
    resultBox.setId(property);
    return resultBox;
  }


  // Display given message as an error in the GUI
  private void showError (String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(userInterfaceResources.getString("ErrorTitle"));
    alert.setContentText(message);
    alert.showAndWait();
  }

  // only enable buttons when useful to user
  private void enableButtons () {
    forwardButton.setDisable(!currentSimulation.getSimulationStatus());
  }

//  private Node makeInitialDirections (String directionText){
//    initialDirections = new Text();
//    initialDirections.setText(userInterfaceResources.getString(directionText));
//    initialDirections.setId(directionText);
//    return initialDirections;
//  }
//
//  private void removeInitialDirections (){
//    if (initialDirections != null){
//      root.getChildren().remove(initialDirections);
//    }
//  }
  // make the panel where "would-be" clicked URL is displayed
  private Node makeSimulationControlPanel (String nodeID) {
    GridPane controlPanel = new GridPane();

    Button pauseButton = makeButton("pauseButton", e -> currentSimulation.pauseResume());
    controlPanel.add(pauseButton, FIRST_COL, FIRST_ROW);

    forwardButton = makeButton("forwardButton", e -> currentSimulation.stepForward());
    controlPanel.add(forwardButton, SECOND_COL, FIRST_ROW);

    Button resetButton = makeButton("resetButton", e -> resetSimulation());
    controlPanel.add(resetButton, THIRD_COL, FIRST_ROW);

    Button speedUpButton = makeButton("speedUpButton", e -> currentSimulation.speedUpSimulation());
    controlPanel.add(speedUpButton, FIRST_COL, SECOND_ROW);

    Button slowDownButton = makeButton("slowDownButton", e -> currentSimulation.slowDownSimulation());
    controlPanel.add(slowDownButton, SECOND_COL, SECOND_ROW);

    Button loadSimulationButton = makeButton("loadSimulationButton", e -> loadSimulation(selectSimulationBox.getValue()));
    controlPanel.add(loadSimulationButton, THIRD_COL, SECOND_ROW);

    selectSimulationBox = makeComboBox("selectSimulationBox", configurationArray);
    controlPanel.add(selectSimulationBox, THIRD_COL, SECOND_ROW);

    controlPanel.setId(nodeID);
    return controlPanel;
  }

  private Node makeGameDisplayPanel (String nodeID) {
    GridPane gameDisplay = new GridPane();
    Text frameCounter = makeText("frameCounterID");
    gameDisplay.add(frameCounter, FIRST_COL, FIRST_ROW);

    Text simulationTitle = makeText("simulationTypeID");
    gameDisplay.add(simulationTitle, SECOND_COL, FIRST_ROW);

    Text simulationRate = makeText("simulationRateID");
    gameDisplay.add(simulationRate, THIRD_COL, FIRST_ROW);

    gameDisplay.setId(nodeID);
    return gameDisplay;
  }

  private Text makeText(String inputTextID){
    Text newText = new Text();
    newText.setText(userInterfaceResources.getString(inputTextID));
    newText.setId(inputTextID);
    return newText;
  }


  private void loadSimulation(Object selectBoxObject){
    if(selectBoxObject == null){
      showError(userInterfaceResources.getString("NullSelection"));
    }
    else{
      //removeInitialDirections();
      selectedSimulationName = selectBoxObject.toString();
      makeSimulation(selectedSimulationName);
    }
  }

  public void resetSimulation() {
    currentSimulation = null;
    grid.getChildren().clear();
    makeSimulation(selectedSimulationName);
  }

  public void makeSimulation(String selectedSimulationName){
    currentSimulation = new Simulator(selectedSimulationName);
    currentSimulation.runSimulation(grid);
  }
}

