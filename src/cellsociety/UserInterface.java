package cellsociety;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Class to launch the simulation program
 *
 * @author Frank Tang
 */
public class UserInterface {

  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
  public static final String STYLESHEET = "default.css";
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

  Button pauseButton = new Button("Pause/Resume");
  Button forwardButton = new Button("Step Forward");
  Button resetButton = new Button("Reset");
  Button speedUpButton = new Button("Speed Up");
  Button speedDownButton = new Button("Speed Down");

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
    // must be first since other panels may refer to page
//    root.setCenter(makePageDisplay());
//    root.setTop(makeInputPanel());
//    root.setBottom(makeInformationPanel());
//    // control the navigation
//    enableButtons();
    // create scene to hold UI
    Scene scene = new Scene(root, width, height);
    // activate CSS styling
   // scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    return scene;
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

    speedDownButton.setFont(Font.font(15));
    speedDownButton.setLayoutX(240);
    speedDownButton.setPrefWidth(120);
    speedDownButton.setLayoutY(gameStatusDisplayBottom.getY() + 60);
    speedDownButton.setOnAction(e -> Simulator.slowDownSimulation());


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

