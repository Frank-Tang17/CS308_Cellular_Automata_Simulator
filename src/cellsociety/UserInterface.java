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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 * Class to launch the simulation program
 *
 * @author Frank Tang
 */
public class UserInterface {

  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  public static final String STYLESHEET = "userInterface.css";
  public static final String BLANK = " ";

  public Group root = new Group();
  public Group grid = new Group();
  private Group buttons = new Group();

  private static final int FIRST_ROW = 0;
  private static final int SECOND_ROW = 1;

  private static final int FIRST_COL = 0;
  private static final int SECOND_COL = 1;
  private static final int THIRD_COL = 2;



  private Scene userInterfaceScene;

  private String controlPanelID = "controlPanel";
  private String gameDisplayID = "gameDisplay";
  private String initialDirectionsID = "initialDirections";

  private Button pauseButton;
  private Button forwardButton;
  private Button resetButton;
  private Button speedUpButton;
  private Button slowDownButton;
  private Button loadSimulationButton;
  private ComboBox selectSimulationBox;
  private Text initialDirections;
  private ObservableList<String> configurationArray = FXCollections.observableArrayList("Percolation", "GameofLife", "Fire", "Segregation", "Predator-Prey");
  private String selectedSimulationName;

  private ResourceBundle userInterfaceResources;
  private Simulator currentSimulation;

  public UserInterface(String language){
    userInterfaceResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }
  /**
   * Initialize what will be displayed and how it will be updated.
   */

  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));
    //root.setCenter(makeInitialDirections(initialDirectionsID));
    root.getChildren().add(grid);
    //enableButtons();
    userInterfaceScene = new Scene(root, width, height);
    // activate CSS styling
    userInterfaceScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

//    currentSimulation.runSimulation(grid);

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

  private Node makeInitialDirections (String directionText){
    initialDirections = new Text();
    initialDirections.setText(userInterfaceResources.getString(directionText));
    initialDirections.setId(directionText);
    return initialDirections;
  }

  private void removeInitialDirections (){
    if (initialDirections != null){
      root.getChildren().remove(initialDirections);
    }
  }
  // make the panel where "would-be" clicked URL is displayed
  private Node makeSimulationControlPanel (String nodeID) {
    GridPane controlPanel = new GridPane();

    pauseButton = makeButton("pauseButton", e -> currentSimulation.pauseResume());
    controlPanel.add(pauseButton, FIRST_COL, FIRST_ROW);

    forwardButton = makeButton("forwardButton", e -> System.out.println(12));
    controlPanel.add(forwardButton, SECOND_COL, FIRST_ROW);

    resetButton = makeButton("resetButton", e -> resetSimulation());
    controlPanel.add(resetButton, THIRD_COL, FIRST_ROW);

    speedUpButton = makeButton("speedUpButton", e -> currentSimulation.speedUpSimulation());
    controlPanel.add(speedUpButton, FIRST_COL, SECOND_ROW);

    slowDownButton = makeButton("slowDownButton", e -> currentSimulation.slowDownSimulation());
    controlPanel.add(slowDownButton, SECOND_COL, SECOND_ROW);

    loadSimulationButton = makeButton("loadSimulationButton", e -> loadSimulation(selectSimulationBox.getValue()));
    controlPanel.add(loadSimulationButton, THIRD_COL, SECOND_ROW);

    selectSimulationBox = makeComboBox("selectSimulationBox", configurationArray);
    controlPanel.add(selectSimulationBox, THIRD_COL, SECOND_ROW);

    controlPanel.setId(nodeID);
    return controlPanel;
  }

  private Node makeGameDisplayPanel (String nodeID) {
    HBox gameDisplay = new HBox();
    gameDisplay.setId(nodeID);
    return gameDisplay;
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

