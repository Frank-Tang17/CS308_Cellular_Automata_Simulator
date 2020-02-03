package cellsociety;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
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


  private static int heightForGameStatusText = 20;

  public Group root = new Group();
  public Group grid = new Group();
  private Group buttons = new Group();

  private Scene userInterfaceScene;

  private String controlPanelID = "controlPanel";
  private String gameDisplayID = "gameDisplay";

  private Button pauseButton;
  private Button forwardButton;
  private Button resetButton;
  private Button speedUpButton;
  private Button slowDownButton;
  private Button loadSimulationButton;
  private ComboBox selectSimulationBox;
  private ObservableList<String> configurationArray = FXCollections.observableArrayList("Percolation", "GameofLife", "Fire", "Segregation", "Predator-Prey");
  private String selectedSimulation;

  private ResourceBundle myResources;
  private Simulator currentSimulation;

  public UserInterface(Simulator simulationLoaded, String language){
    currentSimulation = simulationLoaded;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

  }
  /**
   * Initialize what will be displayed and how it will be updated.
   */


//    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//    scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));
    root.getChildren().add(grid);
    enableButtons();
    userInterfaceScene = new Scene(root, width, height);
    // activate CSS styling

    userInterfaceScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    currentSimulation.test(grid);

    return userInterfaceScene;
  }

  // makes a button using either an image or a label
  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button resultButton = new Button();
    String label = myResources.getString(property);
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
    // represent all supported image suffixes
//    final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    ComboBox resultBox = new ComboBox(options);
    String label = myResources.getString(property);

    resultBox.setId(property);

    return resultBox;
  }

  // Display given message as an error in the GUI
  private void showError (String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(myResources.getString("ErrorTitle"));
    alert.setContentText(message);
    alert.showAndWait();
  }

  // only enable buttons when useful to user
  private void enableButtons () {
    forwardButton.setDisable(!currentSimulation.getSimulationStatus());
  }

  // make the panel where "would-be" clicked URL is displayed
  private Node makeSimulationControlPanel (String nodeID) {
    GridPane controlPanel = new GridPane();

    pauseButton = makeButton("pauseButton", e -> currentSimulation.pauseResume());
    controlPanel.add(pauseButton, 0, 0);

    forwardButton = makeButton("forwardButton", e -> System.out.println(12));
    controlPanel.add(forwardButton, 1, 0);

    resetButton = makeButton("resetButton", e -> resetSimulation("test"));
    controlPanel.add(resetButton, 2, 0);

    speedUpButton = makeButton("speedUpButton", e -> currentSimulation.speedUpSimulation());
    controlPanel.add(speedUpButton, 0, 1);

    slowDownButton = makeButton("slowDownButton", e -> currentSimulation.slowDownSimulation());
    controlPanel.add(slowDownButton, 1, 1);

    loadSimulationButton = makeButton("loadSimulationButton", e -> System.out.println("test"));
    controlPanel.add(loadSimulationButton, 2, 1);

    selectSimulationBox = makeComboBox("selectSimulationBox", configurationArray);
    controlPanel.add(selectSimulationBox, 2, 1);

    controlPanel.setId(nodeID);
    return controlPanel;
  }

  private Node makeGameDisplayPanel (String nodeID) {
    HBox gameDisplay = new HBox();

    gameDisplay.setId(nodeID);
    return gameDisplay;
  }

  public void resetSimulation(String selectedSimulation) {

    currentSimulation = null;
    grid.getChildren().clear();
    currentSimulation = new Simulator();
    currentSimulation.test(grid);

  }

  /**
   * Handles key inputs -- primarily used for cheat keys
   *
   * @param code is the KeyCode necessary to identify the key being pressed
   */
  private void handleKeyInput(KeyCode code) {
  }

  /**
   * Handles mouse input -- used to control the paddle <<<<<<< HEAD
   *
   * @param x is the double position of the mouse's x coordinate
   * @param y is the double position of the mouse's y coordinate
   */
  private void handleMouseInput(double x, double y) {
  }

  /**
   * Start the program.
   */
}

