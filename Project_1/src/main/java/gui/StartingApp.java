package gui;

import interfaces.IWorldMap;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
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
            HBox startButtonBox = new HBox(startButton);
            startButtonBox.setAlignment(Pos.CENTER);

            VBox vBox = createVbox(welcomeText, mapProperties, heightBox, widthBox, energyProperties, startingEnergyBox,
                    dailyEnergyUsageBox, grassProfitBox, jungleRatio, jungleRatioBox, spawningProperties, animalsAtTheBeginningBox,
                    otherOptions, refreshTimeBox);
            Scene scene = new Scene(vBox, 700, 700);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Creating JavaFx objects
    private VBox createVbox(HBox welcomeText, HBox mapProperties, HBox heightBox, HBox widthBox, HBox energyProperties,
                            HBox startingEnergyBox, HBox dailyEnergyUsageBox, HBox grassProfitBox, HBox jungleRatio,
                            HBox jungleHeightBox, HBox spawningProperties, HBox animalsAtTheBeginningBox,
                            HBox otherOptions, HBox refreshTimeBox) {
        VBox newVbox = new VBox(welcomeText, mapProperties, heightBox, widthBox, energyProperties, startingEnergyBox,
                dailyEnergyUsageBox, grassProfitBox, jungleRatio, jungleHeightBox, spawningProperties, animalsAtTheBeginningBox,
                otherOptions, refreshTimeBox);
        newVbox.setAlignment(Pos.TOP_CENTER);
        newVbox.setSpacing(15);
        return newVbox;
    }

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
            WorldMapWithBoundaries mapWithBoundaries = new WorldMapWithBoundaries(this.heightTextField.getText(), this.widthTextField.getText(),
                    this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
                    this.jungleRatioTextField.getText(),this.refreshTimeTextField.getText());
            WorldMapWithoutBoundaries mapWithoutBoundaries = new WorldMapWithoutBoundaries(this.heightTextField.getText(), this.widthTextField.getText(),
                    this.dailyEnergyUsageTextField.getText(), this.grassProfitTextField.getText(),
                    this.jungleRatioTextField.getText(),this.refreshTimeTextField.getText());
            Simulation engine = new Simulation(mapWithBoundaries, mapWithoutBoundaries, this,
                    Integer.parseInt(String.valueOf(this.animalsAtTheBeginningTextField)),
                    Integer.parseInt(String.valueOf(this.startingEnergyTextField)),
                    Integer.parseInt(String.valueOf(this.refreshTimeTextField.getText())));
            Thread engineThread = new Thread(engine::run);
            engineThread.start();
        });
        return startButton;
    }

    // Creating new map
    public void createGrid(IWorldMap newMap) {
        ;
    }

    public void renderMap(IWorldMap newMap) {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        createGrid(newMap);
    }


}
