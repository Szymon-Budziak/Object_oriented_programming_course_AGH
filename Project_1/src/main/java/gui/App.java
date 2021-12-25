package gui;

import elements.Vector2d;
import interfaces.IMapElement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;
import simulation.Simulation;

public class App extends Application {
    private TextField heightTextField;
    private TextField widthTextField;
    private TextField startingEnergyTextField;
    private TextField dailyEnergyUsageTextField;
    private TextField grassProfitTextField;
    private TextField jungleRatioTextField;
    private TextField animalsAtTheBeginningTextField;
    private TextField refreshTimeTextField;
    private GridPane mapWithBoundariesGridPane = new GridPane();
    private GridPane mapWithoutBoundariesGridPane = new GridPane();
    private int gridHeight = 30;
    private int gridWidth = 30;
    private WorldMapWithBoundaries mapWithBoundaries;
    private WorldMapWithoutBoundaries mapWithoutBoundaries;
    private int sceneHeight = 1920;
    private int sceneWidth = 1080;
    private VBox mainVBox;

    // App specific function - start
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Evolution Simulator");
            // Welcome text
            HBox welcomeText = createHeadLineText("Welcome in Evolution Simulator!", 24);

            // Map properties
            HBox mapProperties = createHeadLineText("Map properties, you can pass your parameters", 16);

            Text heightText = createText("Map height:");
            this.heightTextField = new TextField("5");
            HBox heightBox = createHBox(heightText, this.heightTextField);
            Text widthText = createText("Map width:");
            this.widthTextField = new TextField("5");
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
            this.grassProfitTextField = new TextField("3");
            HBox grassProfitBox = createHBox(grassProfitText, this.grassProfitTextField);

            // Jungle ratio
            HBox jungleRatio = createHeadLineText("Jungle ratio, you can pass your parameters", 16);

            Text jungleRatioText = createText("Jungle ratio to savanna:");
            this.jungleRatioTextField = new TextField("0.5");
            HBox jungleRatioBox = createHBox(jungleRatioText, this.jungleRatioTextField);

            // Spawning properties
            HBox spawningProperties = createHeadLineText("Spawning properties, you can pass your parameters", 16);

            Text animalsAtTheBeginningText = createText("Animals spawned at the beginning:");
            this.animalsAtTheBeginningTextField = new TextField("5");
            HBox animalsAtTheBeginningBox = createHBox(animalsAtTheBeginningText, this.animalsAtTheBeginningTextField);

            // Other options
            HBox otherOptions = createHeadLineText("Other options, you can pass your parameters", 16);

            Text refreshTimeText = createText("Refresh time in ms:");
            this.refreshTimeTextField = new TextField("1000");
            HBox refreshTimeBox = createHBox(refreshTimeText, this.refreshTimeTextField);

            // Start Button
            HBox startButton = createStartButton();

            HBox gridPaneBox = new HBox(this.mapWithBoundariesGridPane, this.mapWithoutBoundariesGridPane);
            gridPaneBox.setSpacing(100);
            gridPaneBox.setAlignment(Pos.CENTER);

            this.mainVBox = new VBox(gridPaneBox, welcomeText, mapProperties, heightBox, widthBox, energyProperties, startingEnergyBox,
                    dailyEnergyUsageBox, grassProfitBox, jungleRatio, jungleRatioBox, spawningProperties, animalsAtTheBeginningBox, otherOptions,
                    refreshTimeBox, startButton);
            this.mainVBox.setSpacing(20);
            Scene scene = new Scene(this.mainVBox, this.sceneWidth, this.sceneHeight);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // JavaFx objects for start function
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

    private HBox createStartButton() {
        Button startButton = new Button("Start");
        startButton.setOnAction((action) -> {
            this.mapWithBoundaries = new WorldMapWithBoundaries(this.heightTextField.getText(),
                    this.widthTextField.getText(), this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
                    this.jungleRatioTextField.getText());
            this.mapWithoutBoundaries = new WorldMapWithoutBoundaries(this.heightTextField.getText(),
                    this.widthTextField.getText(), this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
                    this.jungleRatioTextField.getText());
            Simulation engine = new Simulation(this.mapWithBoundaries, this.mapWithoutBoundaries, this, Integer.parseInt(this.animalsAtTheBeginningTextField.getText()),
                    Integer.parseInt(this.startingEnergyTextField.getText()), Integer.parseInt(this.refreshTimeTextField.getText()));
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox hBox = new HBox(startButton);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public void renderMap(AbstractWorldMap map, boolean finishMap) {
        if (map instanceof WorldMapWithBoundaries) {
            this.mapWithBoundariesGridPane.setGridLinesVisible(false);
            this.mapWithBoundariesGridPane.getColumnConstraints().clear();
            this.mapWithBoundariesGridPane.getRowConstraints().clear();
            this.mapWithBoundariesGridPane.getChildren().clear();
            this.mapWithBoundariesGridPane.setGridLinesVisible(true);
        } else {
            this.mapWithoutBoundariesGridPane.setGridLinesVisible(false);
            this.mapWithoutBoundariesGridPane.getColumnConstraints().clear();
            this.mapWithoutBoundariesGridPane.getRowConstraints().clear();
            this.mapWithoutBoundariesGridPane.getChildren().clear();
            this.mapWithoutBoundariesGridPane.setGridLinesVisible(true);
        }
        createGrid(map);
        if (!finishMap)
            placeAnimals(map);
//        createPlot(map);

    }

    private void createGrid(AbstractWorldMap map) {
        if (map instanceof WorldMapWithBoundaries)
            this.mapWithBoundariesGridPane.setGridLinesVisible(true);
        else
            this.mapWithoutBoundariesGridPane.setGridLinesVisible(true);
        int mapRight = map.getMapUpperRight().getX();
        int mapUpper = map.getMapUpperRight().getY();

        Vector2d jungleLowerLeft = map.getJungleLowerLeft();
        Vector2d jungleUpperRight = map.getJungleUpperRight();

        for (int i = 0; i <= mapRight; i++) {
            if (map instanceof WorldMapWithBoundaries)
                this.mapWithBoundariesGridPane.getColumnConstraints().add(new ColumnConstraints(this.gridWidth));

            else
                this.mapWithoutBoundariesGridPane.getColumnConstraints().add(new ColumnConstraints(this.gridWidth));
        }
        for (int i = 0; i <= mapUpper; i++) {
            if (map instanceof WorldMapWithBoundaries)
                this.mapWithBoundariesGridPane.getRowConstraints().add(new RowConstraints(this.gridHeight));

            else
                this.mapWithoutBoundariesGridPane.getRowConstraints().add(new RowConstraints(this.gridHeight));
        }

        VBox newVBox;
        for (int i = 0; i <= mapRight; i++) {
            for (int j = 0; j <= mapUpper; j++) {
                Vector2d newPosition = new Vector2d(i, j);
                newVBox = new VBox();
                if (newPosition.follows(jungleLowerLeft) && newPosition.precedes(jungleUpperRight)) {
                    newVBox.setStyle("-fx-background-color: #135E46");
                } else {
                    newVBox.setStyle("-fx-background-color: #A1724E");
                }
                if (map instanceof WorldMapWithBoundaries)
                    this.mapWithBoundariesGridPane.add(newVBox, i, j, 1, 1);

                else
                    this.mapWithoutBoundariesGridPane.add(newVBox, i, j, 1, 1);
            }
        }
    }

    private void placeAnimals(AbstractWorldMap map) {
        Vector2d[] grassesAndAnimals = map.getAnimalsAndGrasses();
        for (Vector2d position : grassesAndAnimals) {
            GuiElementBox vBox = new GuiElementBox((IMapElement) map.objectAt(position));
            if (map instanceof WorldMapWithBoundaries)
                this.mapWithBoundariesGridPane.add(vBox.vBox, position.getX(), position.getY(), 1, 1);
            else
                this.mapWithoutBoundariesGridPane.add(vBox.vBox, position.getX(), position.getY(), 1, 1);
        }
    }

    private void createPlot(AbstractWorldMap map) {
    }
}
