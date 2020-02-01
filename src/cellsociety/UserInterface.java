package cellsociety;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

  //  public static final int SIZE = 600;
  public final int gameStatusDisplayHeight = 30;
//  public final int getGameStatusDisplayHeightBottom =
//      WINDOW_SIZE.height - WINDOW_SIZE.height / 4 + gameStatusDisplayHeight;

  public Group root = new Group();
  public Group grid = new Group();
  private Group buttons = new Group();

  private Scene userInterfaceScene;

  private Rectangle gameStatusDisplayBottom;
  private Rectangle gameStatusDisplayTop;
  private Text titleDisplay = new Text();
  private Text frameDisplay = new Text();
  private String controlPanelID = "controlPanel";
  private String gameDisplayID = "gameDisplay";

  private Button pauseButton;
  private Button forwardButton;
  private Button resetButton;
  private Button speedUpButton;
  private Button slowDownButton;

  private ResourceBundle myResources;

  public UserInterface(String language){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }
  /**
   * Initialize what will be displayed and how it will be updated.
   */

  // create one top level collection to organize the things in the scene
  // make some shapes and set their properties
  // x and y represent the top left corner, so center it in window
//  setUpGameStatusDisplay();
//  setUpButtons();
//  // order added to the group is the order in which they are drawn
//    root.getChildren().add(buttons);
//    root.getChildren().add(grid);
//    buttons.getChildren().add(pauseButton);
//    buttons.getChildren().add(forwardButton);
//    buttons.getChildren().add(resetButton);
//    buttons.getChildren().add(speedDownButton);
//    buttons.getChildren().add(speedUpButton);
//
//  // create a place to see the shapes
//  Scene scene = new Scene(root, width, height, background);
//  // respond to input
//    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//    scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupUserInterface(int width, int height) {
    BorderPane root = new BorderPane();
    root.setBottom(makeSimulationControlPanel(controlPanelID));
    root.setTop(makeGameDisplayPanel(gameDisplayID));

//    enableButtons();
    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    return scene;
  }

  // makes a button using either an image or a label
  private Button makeButton (String property, EventHandler<ActionEvent> handler) {
    // represent all supported image suffixes
    final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
    Button result = new Button();
    String label = myResources.getString(property);
    if (label.matches(IMAGEFILE_SUFFIXES)) {
      result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
    }
   else {
      result.setText(label);
    }
    result.setOnAction(handler);
    return result;
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
   // forwardButton.setDisable(!runSimulation);

  }

//  private Node makeSimulationDisplayPanel () {
//    HBox result = new HBox();
//
//
//    return result;
//  }

  // make the panel where "would-be" clicked URL is displayed
  private Node makeSimulationControlPanel (String nodeID) {
    GridPane controlPanel = new GridPane();

    pauseButton = makeButton("pauseButton", e -> Simulator.pauseResume());
    controlPanel.add(pauseButton, 0, 0);

    forwardButton = makeButton("forwardButton", e -> Simulator.pauseResume());
    controlPanel.add(forwardButton, 1, 0);

    resetButton = makeButton("resetButton", e -> Simulator.pauseResume());
    controlPanel.add(resetButton, 2, 0);

    speedUpButton = makeButton("speedUpButton", e -> Simulator.pauseResume());
    controlPanel.add(speedUpButton, 0, 1);

    slowDownButton = makeButton("slowDownButton", e -> Simulator.pauseResume());
    controlPanel.add(slowDownButton, 1, 1);

    controlPanel.setHgap(10);
    controlPanel.setVgap(10);

    controlPanel.setId(nodeID);
    return controlPanel;
  }
  private Node makeGameDisplayPanel (String nodeID) {
    HBox gameDisplay = new HBox();

    gameDisplay.setId(nodeID);
    return gameDisplay;
  }

  public void setUpButtons() {

    pauseButton.setFont(Font.font(15));
    pauseButton.setLayoutX(60);
    pauseButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);
    pauseButton.setPrefWidth(120);
    pauseButton.setOnAction(e -> Simulator.pauseResume());

    forwardButton.setFont(Font.font(15));
    forwardButton.setLayoutX(pauseButton.getLayoutX() + 180);
    forwardButton.setPrefWidth(120);
    forwardButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);
    pauseButton.setOnAction(e -> Simulator.pauseResume());

    resetButton.setFont(Font.font(15));
    resetButton.setLayoutX(forwardButton.getLayoutX() + 180);
    resetButton.setPrefWidth(120);
    resetButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);
    resetButton.setOnAction(e -> resetSimulation());

    speedUpButton.setFont(Font.font(15));
    speedUpButton.setLayoutX(60);
    speedUpButton.setPrefWidth(120);
    speedUpButton.setLayoutY(gameStatusDisplayBottom.getY() + 60);
    speedUpButton.setOnAction(e -> Simulator.speedUpSimulation());

    slowDownButton.setFont(Font.font(15));
    slowDownButton.setLayoutX(240);
    slowDownButton.setPrefWidth(120);
    slowDownButton.setLayoutY(gameStatusDisplayBottom.getY() + 60);
    slowDownButton.setOnAction(e -> Simulator.slowDownSimulation());


  }

  public void resetSimulation() {
    grid.getChildren().clear();
    Simulator.test();

  }

  /**
   * Sets up the game status display at the top of the game's screen
   */
  public void setUpGameStatusDisplay() {
//    gameStatusDisplayTop = new Rectangle(0, 0, WINDOW_SIZE.width, gameStatusDisplayHeight);
//    gameStatusDisplayTop.setFill(Color.LIGHTGREY);
//    gameStatusDisplayTop.setStroke(Color.GREY);
//
//    gameStatusDisplayBottom = new Rectangle(0, getGameStatusDisplayHeightBottom, WINDOW_SIZE.width,
//        WINDOW_SIZE.height / 4);
//    gameStatusDisplayBottom.setFill(Color.LIGHTGREY);
//    gameStatusDisplayBottom.setStroke(Color.GREY);
//
//    frameDisplay.setText("Frame:"); //will need to be retrieved from Main file
//    titleDisplay.setText("Simulation Type"); //will need to be retrieved from configuration file
//
//    titleDisplay.setX(WINDOW_SIZE.width / 2 - titleDisplay.getBoundsInParent().getWidth() / 2);
//    titleDisplay.setY(heightForGameStatusText);
//
//    frameDisplay.setX(frameDisplay.getBoundsInParent().getWidth());
//    frameDisplay.setY(heightForGameStatusText);
//
//    root.getChildren().add(gameStatusDisplayBottom);
//    root.getChildren().add(gameStatusDisplayTop);
//    root.getChildren().add(titleDisplay);
//    root.getChildren().add(frameDisplay);
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

