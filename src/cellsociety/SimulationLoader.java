package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Class to launch the simulation program
 *
 * @author Frank Tang
 */
public class SimulationLoader extends Application{
    private static final String TITLE = "Simulation";

    private static final Paint BACKGROUND = Color.AZURE;
    private static int heightForGameStatusText = 20;
    public static final int SIZE = 600;
    public static int gameStatusDisplayHeight = 30;
    public static final int getGameStatusDisplayHeightBottom = SIZE - SIZE/4 + gameStatusDisplayHeight;


    public static Group root = new Group();
    private Group buttons = new Group();


    private Scene myScene;

    private Rectangle gameStatusDisplayBottom;
    private Rectangle gameStatusDisplayTop;
    private Text titleDisplay = new Text();
    private Text frameDisplay = new Text();

    Button pauseButton = new Button("Pause/Resume");
    Button forwardButton = new Button("Step Forward");
    Button resetButton = new Button("Reset");
    Button speedUpButton = new Button("Speed Up");
    Button speedDownButton = new Button("Speed Down");

    //A button with the specified text caption and icon.
    //Image imageOk = new Image(getClass().getResourceAsStream("ok.png"));
    //Button button3 = new Button("Accept", new ImageView(imageOk));
//
//    button2.setOnAction(new EventHandler<ActionEvent>() {
//        @Override public void handle(ActionEvent e) {
//            label.setText("Accepted");
//        }
//    });

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        Main.test();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        // make some shapes and set their properties
        // x and y represent the top left corner, so center it in window

        setUpGameStatusDisplay();
        setUpButtons();
        // order added to the group is the order in which they are drawn
        root.getChildren().add(buttons);
        buttons.getChildren().add(pauseButton);
        buttons.getChildren().add(forwardButton);
        buttons.getChildren().add(resetButton);
        buttons.getChildren().add(speedDownButton);
        buttons.getChildren().add(speedUpButton);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseMoved(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    public void setUpButtons(){
        pauseButton.setFont(Font.font(15));
        pauseButton.setLayoutX(60);
        pauseButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);
        pauseButton.setPrefWidth(120);
        pauseButton.setOnAction(e -> Main.pauseResume());

        forwardButton.setFont(Font.font(15));
        forwardButton.setLayoutX(pauseButton.getLayoutX() + 180);
        forwardButton.setPrefWidth(120);
        forwardButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);
        pauseButton.setOnAction(e -> Main.pauseResume());

        resetButton.setFont(Font.font(15));
        resetButton.setLayoutX(forwardButton.getLayoutX() + 180);
        resetButton.setPrefWidth(120);
        resetButton.setLayoutY(gameStatusDisplayBottom.getY() + 15);

        speedUpButton.setFont(Font.font(15));
        speedUpButton.setLayoutX(60);
        speedUpButton.setPrefWidth(120);
        speedUpButton.setLayoutY(gameStatusDisplayBottom.getY() + 60);
        speedUpButton.setOnAction(e -> Main.speedUpSimulation());

        speedDownButton.setFont(Font.font(15));
        speedDownButton.setLayoutX(240);
        speedDownButton.setPrefWidth(120);
        speedDownButton.setLayoutY(gameStatusDisplayBottom.getY() + 60);
        speedDownButton.setOnAction(e -> Main.slowDownSimulation());


    }


    /**
     * Sets up the game status display at the top of the game's screen
     */
    public void setUpGameStatusDisplay(){
        gameStatusDisplayTop = new Rectangle(0,0, SIZE, gameStatusDisplayHeight);
        gameStatusDisplayTop.setFill(Color.LIGHTGREY);
        gameStatusDisplayTop.setStroke(Color.GREY);

        gameStatusDisplayBottom = new Rectangle(0,getGameStatusDisplayHeightBottom, SIZE, SIZE/4);
        gameStatusDisplayBottom.setFill(Color.LIGHTGREY);
        gameStatusDisplayBottom.setStroke(Color.GREY);

        frameDisplay.setText("Frame:"); //will need to be retrieved from Main file
        titleDisplay.setText("Simulation Type"); //will need to be retrieved from configuration file

        titleDisplay.setX(SIZE/2 - titleDisplay.getBoundsInParent().getWidth()/2);
        titleDisplay.setY(heightForGameStatusText);

        frameDisplay.setX(frameDisplay.getBoundsInParent().getWidth());
        frameDisplay.setY(heightForGameStatusText);


        root.getChildren().add(gameStatusDisplayBottom);
        root.getChildren().add(gameStatusDisplayTop);
        root.getChildren().add(titleDisplay);
        root.getChildren().add(frameDisplay);
    }



    /**
     * Handles key inputs -- primarily used for cheat keys
     * @param code is the KeyCode necessary to identify the key being pressed
     */
    private void handleKeyInput (KeyCode code) {
    }

    /**
     * Handles mouse input -- used to control the paddle
     * @param x is the double position of the mouse's x coordinate
     * @param y is the double position of the mouse's y coordinate
     */
    private void handleMouseInput (double x, double y) {
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}