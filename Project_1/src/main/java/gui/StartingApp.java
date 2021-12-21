package gui;

import elements.Vector2d;
import interfaces.IMapElement;
import interfaces.IWorldMap;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;
import simulation.Simulation;
//import simulation.Simulation;

public class StartingApp extends Application {
    GridPane gridPane = new GridPane();
    private TextField heightTextField;
    private TextField widthTextField;
    private TextField jungleRatioTextField;
    private TextField grassProfitTextField;
    private TextField startingEnergyTextField;
    private TextField dailyEnergyUsageTextField;
    private TextField animalsAtTheBeginningTextField;
    private TextField refreshTimeTextField;
    private int constraintWidth = 50;
    private int constraintHeight = 50;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Evolution Simulator");

            // Welcome text
            HBox welcomeText = createHeadLineText("Welcome in Evolution Simulator!", 20);

            // Map properties
            HBox mapProperties = createHeadLineText("Map properties, you can pass your parameters", 16);

            Text heightText = createText("Map height:");
            this.heightTextField = new TextField("20");
            HBox heightBox = createHBox(heightText, this.heightTextField);
            Text widthText = createText("Map width:");
            this.widthTextField = new TextField("20");
            HBox widthBox = createHBox(widthText, this.widthTextField);

            // Energy properties
            HBox energyProperties = createHeadLineText("Energy properties, you can pass your parameters", 16);

            Text startingEnergyText = createText("Animal starting energy:");
            this.startingEnergyTextField = new TextField("10");
            HBox startingEnergyBox = createHBox(startingEnergyText, this.startingEnergyTextField);
            Text dailyEnergyUsageText = createText("Daily energy usage:");
            this.dailyEnergyUsageTextField = new TextField("1");
            HBox dailyEnergyUsageBox = createHBox(dailyEnergyUsageText, this.dailyEnergyUsageTextField);
            Text grassProfitText = createText("Grass profit:");
            this.grassProfitTextField = new TextField("1");
            HBox grassProfitBox = createHBox(grassProfitText, this.grassProfitTextField);


            // Jungle ratio
            HBox jungleRatio = createHeadLineText("Jungle ratio, you can pass your parameters", 16);

            Text jungleRatioText = createText("Jungle ratio to savanna:");
            this.jungleRatioTextField = new TextField("0.25");
            HBox jungleRatioBox = createHBox(jungleRatioText, this.jungleRatioTextField);


            // Spawning properties
            HBox spawningProperties = createHeadLineText("Spawning properties, you can pass your parameters", 16);

            Text animalsAtTheBeginningText = createText("Animals spawned at the beginning:");
            this.animalsAtTheBeginningTextField = new TextField("10");
            HBox animalsAtTheBeginningBox = createHBox(animalsAtTheBeginningText, this.animalsAtTheBeginningTextField);

            // Other options
            HBox otherOptions = createHeadLineText("Other options, you can pass your parameters", 16);

            Text refreshTimeText = createText("Refresh time in ms:");
            this.refreshTimeTextField = new TextField("300");
            HBox refreshTimeBox = createHBox(refreshTimeText, this.refreshTimeTextField);

            // Start button
            Button startButton = createStartButton();

            VBox newVbox = new VBox(welcomeText, mapProperties, heightBox, widthBox, energyProperties, startingEnergyBox,
                    dailyEnergyUsageBox, grassProfitBox, jungleRatio, jungleRatioBox, spawningProperties, animalsAtTheBeginningBox,
                    otherOptions, refreshTimeBox, startButton);
            newVbox.setAlignment(Pos.TOP_CENTER);
            newVbox.setSpacing(15);
            Scene scene = new Scene(newVbox, 700, 700);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Creating JavaFx objects
    private HBox createHeadLineText(String text, int size) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.BOLD, size));
        HBox hbox = new HBox(newText);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }


    private Text createText(String text) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        return newText;
    }

    private HBox createHBox(Text text, TextField textField) {
        HBox hbox = new HBox(text, textField);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        return hbox;
    }

    private Button createStartButton() {
        Button startButton = new Button("Start");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        startButton.setOnAction((action) -> {
//            WorldMapWithBoundaries mapWithBoundaries = new WorldMapWithBoundaries(this.heightTextField.getText(), this.widthTextField.getText(),
//                    this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
//                    this.jungleRatioTextField.getText());
//            WorldMapWithoutBoundaries mapWithoutBoundaries = new WorldMapWithoutBoundaries(this.heightTextField.getText(), this.widthTextField.getText(),
//                    this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
//                    this.jungleRatioTextField.getText());
            AbstractWorldMap map = new AbstractWorldMap(Integer.parseInt(heightTextField.getText()), Integer.parseInt(this.widthTextField.getText()),
                    Integer.parseInt(this.dailyEnergyUsageTextField.getText()), Integer.parseInt(this.grassProfitTextField.getText()),
                    Integer.parseInt(this.jungleRatioTextField.getText()));
            Simulation engine = new Simulation(map, this,
                    Integer.parseInt(this.animalsAtTheBeginningTextField.getText()),
                    Integer.parseInt(this.startingEnergyTextField.getText()),
                    Integer.parseInt(this.refreshTimeTextField.getText()));
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
        return startButton;
    }

    // Creating new map
    public void createGrid(AbstractWorldMap newMap) {
        int mapLeft = newMap.getMapLowerLeft().x;
        int mapRight = newMap.getMapUpperRight().x;
        int mapLower = newMap.getMapLowerLeft().y;
        int mapUpper = newMap.getMapUpperRight().y;

        Vector2d jungleLowerLeft = newMap.getJungleLowerLeft();
        Vector2d jungleUpperRight = newMap.getJungleUpperRight();
        Label label = new Label("y/x");
        this.gridPane.add(label, 0, 0, 1, 1);
        VBox newVBox;
        for (int i = mapLeft; i <= mapRight; i++) {
            for (int j = mapLower; j <= mapUpper; j++) {
                Vector2d newPosition = new Vector2d(i, j);
                newVBox = new VBox();
                if (newPosition.follows(jungleLowerLeft) && newPosition.precedes(jungleUpperRight)) {
                    newVBox.setStyle("-fx-background-color: #135E46");
                } else {
                    newVBox.setStyle("-fx-background-color: #A1724E");
                }
                newVBox.setAlignment(Pos.CENTER);
                this.gridPane.add(newVBox, i, mapUpper + 1 - j, 1, 1);
            }
        }
        Vector2d[] animalsAndGrasses = newMap.getAnimalsAndGrasses();
        for (Vector2d position : animalsAndGrasses) {
            GuiElementBox vBox = new GuiElementBox((IMapElement) newMap.objectAt(position));
            this.gridPane.add(vBox.vBox, 1 + position.x - mapLeft, 1 + mapUpper - position.y, 1, 1);
        }
    }

    public void renderMap(AbstractWorldMap newMap) {
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.getChildren().clear();
        this.gridPane.setGridLinesVisible(true);
        createGrid(newMap);
    }
}
