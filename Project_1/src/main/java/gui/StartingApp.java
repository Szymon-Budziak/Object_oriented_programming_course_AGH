package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartingApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            primaryStage.setTitle("Evolution Simulator");
            HBox welcomeText = createText("Welcome in Evolution Simulator!", 20);
            HBox mapProperties = createText("Map properties", 16);
            HBox mapHeight = createTextAndTextField("Map height:");
            HBox mapWidth = createTextAndTextField("Map width:");
            HBox jungleHeight = createTextAndTextField("Jungle height:");
            HBox jungleWidth = createTextAndTextField("Jungle width:");
            HBox energyProperties = createText("Energy properties", 16);
            HBox grassProfit = createTextAndTextField("Grass energy profit:");
            HBox energyCopulation = createTextAndTextField("Energy for copulation:");
            HBox startingEnergy = createTextAndTextField("Animal starting energy:");
            HBox dailyEnergyCost = createTextAndTextField("Daily energy cost:");
            HBox spawningProperties = createText("Spawning properties", 16);
            HBox animalsAtTheBeginning = createTextAndTextField("Animals spawned at the beginning:");
            HBox grassEachDay = createTextAndTextField("Grass spawned each day:");
            HBox otherOptions = createText("Other options:", 16);
            HBox refreshTime = createTextAndTextField("Refresh time in ms:");
            HBox startButton = createButton("Start");

            VBox vBox = createVbox(welcomeText, mapProperties, mapHeight, mapWidth, jungleHeight, jungleWidth, energyProperties,
                    grassProfit, energyCopulation, startingEnergy, dailyEnergyCost, spawningProperties, animalsAtTheBeginning,
                    grassEachDay, otherOptions, refreshTime, startButton);
            Scene scene = new Scene(vBox, 700, 700);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Creating JavaFx objects
    private VBox createVbox(HBox welcomeText, HBox mapProperties, HBox mapHeight, HBox mapWidth, HBox jungleHeight,
                            HBox jungleWidth, HBox energyProperties, HBox grassProfit, HBox energyCopulation, HBox startingEnergy,
                            HBox dailyEnergyCost, HBox spawningProperties, HBox animalsAtTheBeginning, HBox grassEachDay,
                            HBox otherOptions, HBox refreshTime, HBox startButton) {
        VBox newVbox = new VBox(welcomeText, mapProperties, mapHeight, mapWidth, jungleHeight, jungleWidth, energyProperties,
                grassProfit, energyCopulation, startingEnergy, dailyEnergyCost, spawningProperties, animalsAtTheBeginning,
                grassEachDay, otherOptions, refreshTime, startButton);
        newVbox.setAlignment(Pos.TOP_CENTER);
        newVbox.setSpacing(15);
        return newVbox;
    }

    private HBox createText(String text, int size) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.BOLD, size));
        HBox hbox = new HBox(newText);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createTextAndTextField(String text) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        TextField newTextField = new TextField();
        HBox hbox = new HBox(newText, newTextField);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        return hbox;
    }

    private HBox createButton(String text) {
        Button startButton = new Button(text);
        startButton.setOnAction((action) -> {
        });
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox hbox = new HBox(startButton);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

}
