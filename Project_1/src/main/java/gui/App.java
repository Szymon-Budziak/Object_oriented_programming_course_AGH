package gui;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;
import simulation.Simulation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class App extends Application {
    private TextField heightTextField;
    private TextField widthTextField;
    private TextField startingEnergyTextField;
    private TextField dailyEnergyUsageTextField;
    private TextField grassProfitTextField;
    private TextField jungleRatioTextField;
    private TextField animalsAtTheBeginningTextField;
    private TextField refreshTimeTextField;
    private TextField magicSimulationTextField;
    private final GridPane mapWithBoundariesGridPane = new GridPane();
    private final GridPane mapWithoutBoundariesGridPane = new GridPane();
    private final int gridHeight = 30;
    private final int gridWidth = 30;
    private WorldMapWithBoundaries mapWithBoundaries;
    private WorldMapWithoutBoundaries mapWithoutBoundaries;
    private final int sceneHeight = 1920;
    private final int sceneWidth = 1080;
    private VBox mainVBox;
    private Stage primaryStage;
    private VBox optionsVBox;
    private LineChart mapWithBoundariesLineChart;
    private LineChart mapWithoutBoundariesLineChart;
    private final XYChart.Series mapWithBoundariesDataSeries1 = new XYChart.Series();
    private final XYChart.Series mapWithoutBoundariesDataSeries1 = new XYChart.Series();
    private final XYChart.Series mapWithBoundariesDataSeries2 = new XYChart.Series();
    private final XYChart.Series mapWithoutBoundariesDataSeries2 = new XYChart.Series();
    private final XYChart.Series mapWithBoundariesDataSeries3 = new XYChart.Series();
    private final XYChart.Series mapWithoutBoundariesDataSeries3 = new XYChart.Series();
    private final XYChart.Series mapWithBoundariesDataSeries4 = new XYChart.Series();
    private final XYChart.Series mapWithoutBoundariesDataSeries4 = new XYChart.Series();
    private final XYChart.Series mapWithBoundariesDataSeries5 = new XYChart.Series();
    private final XYChart.Series mapWithoutBoundariesDataSeries5 = new XYChart.Series();
    private HBox genotypeHBox;
    private Thread engineThread;
    private Stage dominantGenotypeWindow;
    private int magicCount;
    private boolean threadWaiting = false;

    // App specific function - start
    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Evolution Simulator");
            // Welcome text
            HBox welcomeText = createHeadLineText("Welcome in Evolution Simulator!", 24);

            // Map properties
            HBox mapProperties = createHeadLineText("Map properties, you can pass your parameters", 16);

            Text heightText = createText("Map height:");
            this.heightTextField = new TextField("10");
            HBox heightBox = createHBox(heightText, this.heightTextField);
            Text widthText = createText("Map width:");
            this.widthTextField = new TextField("10");
            HBox widthBox = createHBox(widthText, this.widthTextField);

            // Energy properties
            HBox energyProperties = createHeadLineText("Energy properties, you can pass your parameters", 16);

            Text startingEnergyText = createText("Animal starting energy:");
            this.startingEnergyTextField = new TextField("30");
            HBox startingEnergyBox = createHBox(startingEnergyText, this.startingEnergyTextField);
            Text dailyEnergyUsageText = createText("Daily energy usage:");
            this.dailyEnergyUsageTextField = new TextField("1");
            HBox dailyEnergyUsageBox = createHBox(dailyEnergyUsageText, this.dailyEnergyUsageTextField);
            Text grassProfitText = createText("Grass profit:");
            this.grassProfitTextField = new TextField("10");
            HBox grassProfitBox = createHBox(grassProfitText, this.grassProfitTextField);

            // Jungle ratio
            HBox jungleRatio = createHeadLineText("Jungle ratio, you can pass your parameters", 16);

            Text jungleRatioText = createText("Jungle ratio to savanna:");
            this.jungleRatioTextField = new TextField("0.4");
            HBox jungleRatioBox = createHBox(jungleRatioText, this.jungleRatioTextField);

            // Spawning properties
            HBox spawningProperties = createHeadLineText("Spawning properties, you can pass your parameters", 16);

            Text animalsAtTheBeginningText = createText("Animals spawned at the beginning:");
            this.animalsAtTheBeginningTextField = new TextField("10");
            HBox animalsAtTheBeginningBox = createHBox(animalsAtTheBeginningText, this.animalsAtTheBeginningTextField);

            // Other options
            HBox otherOptions = createHeadLineText("Other options, you can pass your parameters", 16);

            Text refreshTimeText = createText("Refresh time in ms:");
            this.refreshTimeTextField = new TextField("1000");
            HBox refreshTimeBox = createHBox(refreshTimeText, this.refreshTimeTextField);

            Text magicSimulationText = createText("Magic simulation (yes/no):");
            this.magicSimulationTextField = new TextField("yes");
            HBox magicSimulationBox = createHBox(magicSimulationText, this.magicSimulationTextField);

            // Start Button
            HBox startButton = createStartButton();

            this.optionsVBox = new VBox(welcomeText, mapProperties, heightBox, widthBox, energyProperties, startingEnergyBox,
                    dailyEnergyUsageBox, grassProfitBox, jungleRatio, jungleRatioBox, spawningProperties, animalsAtTheBeginningBox, otherOptions,
                    refreshTimeBox, magicSimulationBox, startButton);
            this.optionsVBox.setSpacing(20);
            this.optionsVBox.setAlignment(Pos.CENTER);

            this.mainVBox = new VBox(this.optionsVBox);
            this.mainVBox.setSpacing(50);

            createScene();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void createScene() {
        Scene scene = new Scene(this.mainVBox, this.sceneWidth, this.sceneHeight);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
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
                    Integer.parseInt(this.startingEnergyTextField.getText()), Integer.parseInt(this.refreshTimeTextField.getText()),
                    this.magicSimulationTextField.getText());
            if (this.magicSimulationTextField.getText().toLowerCase().equals("yes"))
                this.magicCount++;

            this.mainVBox.getChildren().remove(this.optionsVBox);

            // Options
            VBox optionButtons = createOptionButtons();
            HBox gridPaneHBox = new HBox(this.mapWithBoundariesGridPane, optionButtons, this.mapWithoutBoundariesGridPane);
            gridPaneHBox.setSpacing(40);
            gridPaneHBox.setAlignment(Pos.CENTER);

            // Plot
            VBox mapWithBoundariesPlotVBox = createPlot(this.mapWithBoundaries);
            VBox mapWithoutBoundariesPlotVBox = createPlot(this.mapWithoutBoundaries);
            HBox plotHBox = new HBox(mapWithBoundariesPlotVBox, mapWithoutBoundariesPlotVBox);
            plotHBox.setAlignment(Pos.CENTER);

            // Dominant genotype
            VBox mapWithBoundariesGenotypeVBox = createDominantGenotype(this.mapWithBoundaries);
            VBox mapWithoutBoundariesGenotypeVBox = createDominantGenotype(this.mapWithoutBoundaries);
            this.genotypeHBox = new HBox(mapWithBoundariesGenotypeVBox, mapWithoutBoundariesGenotypeVBox);
            this.genotypeHBox.setAlignment(Pos.CENTER);

            VBox plotAndGenotypeHBox = new VBox(plotHBox, this.genotypeHBox);

//            gridPaneHBox.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            this.mainVBox.getChildren().add(gridPaneHBox);
            this.mainVBox.getChildren().add(plotAndGenotypeHBox);
            this.engineThread = new Thread(engine);
            this.engineThread.start();
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
        updateLineChart(map);
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
        Vector2d[] grassesAndAnimals = createAnimalsAndGrasses(map);
//        if (map.getAnimals().size() == 5 && this.magicSimulationTextField.getText().toLowerCase().equals(5) && this.magicCount < 3) {
//            map.placeMagicAnimals(Integer.parseInt(this.startingEnergyTextField.getText()));
//            this.magicCount++;
//        }
        for (Vector2d position : grassesAndAnimals) {
            VBox vBox;
            GuiElement guiElement = new GuiElement((IMapElement) map.objectAt(position));
            ImageView imageView = guiElement.getImageView();
            vBox = new VBox(imageView);
            if (map instanceof WorldMapWithBoundaries) {
                if (map.objectAt(position) instanceof Animal && Objects.equals(this.engineThread.getState().toString(), "TIMED_WAITING")) {
                    vBox.setOnMouseClicked((action) -> {
                        List animalInfo = this.mapWithBoundaries.getAnimalInfo((Animal) map.objectAt(position));
                    });
                }
                this.mapWithBoundariesGridPane.add(vBox, position.getX(), position.getY(), 1, 1);
            } else {
                if (map.objectAt(position) instanceof Animal)
                    if (map.objectAt(position) instanceof Animal && Objects.equals(this.engineThread.getState().toString(), "TIMED_WAITING")) {
                        vBox.setOnMouseClicked((action) -> {
                            List animalInfo = this.mapWithoutBoundaries.getAnimalInfo((Animal) map.objectAt(position));

                        });
                    }
                this.mapWithoutBoundariesGridPane.add(vBox, position.getX(), position.getY(), 1, 1);
            }
        }
    }

    private Vector2d[] createAnimalsAndGrasses(AbstractWorldMap map) {
        List<Grass> grasses = map.getGrass();
        List<Animal> animals = map.getAnimals();
        Vector2d[] animalsAndGrasses = new Vector2d[grasses.size() + animals.size()];
        int index = 0;
        for (Animal animal : animals) {
            animalsAndGrasses[index] = animal.getPosition();
            index++;
        }
        for (Grass grass : grasses) {
            animalsAndGrasses[index] = grass.getPosition();
            index++;
        }
        return animalsAndGrasses;
    }

    private VBox createPlot(AbstractWorldMap map) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day");
        NumberAxis yAxis = new NumberAxis();
        if (map instanceof WorldMapWithBoundaries) {
            this.mapWithBoundariesLineChart = new LineChart(xAxis, yAxis);
            this.mapWithBoundariesDataSeries1.setName("Number of animals");
            this.mapWithBoundariesDataSeries2.setName("Number of grasses");
            this.mapWithBoundariesDataSeries3.setName("Average energy if living animals");
            this.mapWithBoundariesDataSeries4.setName("Average life of dead animals");
            this.mapWithBoundariesDataSeries5.setName("Average number of children for living animals");

            updateLineChart(map);
            return new VBox(this.mapWithBoundariesLineChart);
        } else {
            this.mapWithoutBoundariesLineChart = new LineChart(xAxis, yAxis);
            this.mapWithoutBoundariesDataSeries1.setName("Number of animals");
            this.mapWithoutBoundariesDataSeries2.setName("Number of grasses");
            this.mapWithoutBoundariesDataSeries3.setName("Average energy if living animals");
            this.mapWithoutBoundariesDataSeries4.setName("Average life of dead animals");
            this.mapWithoutBoundariesDataSeries5.setName("Average number of children for living animals");

            updateLineChart(map);
            return new VBox(this.mapWithoutBoundariesLineChart);
        }
    }

    private void updateLineChart(AbstractWorldMap map) {
        if (map instanceof WorldMapWithBoundaries) {
            this.mapWithBoundariesDataSeries1.getData().add(new XYChart.Data(map.getEra(), map.getAnimals().size()));
            this.mapWithBoundariesDataSeries2.getData().add(new XYChart.Data(map.getEra(), map.getGrass().size()));
            this.mapWithBoundariesDataSeries3.getData().add(new XYChart.Data(map.getEra(), map.getAverageAnimalsEnergy()));
            this.mapWithBoundariesDataSeries4.getData().add(new XYChart.Data(map.getEra(), map.getAverageDeadAnimalsLife()));
            this.mapWithBoundariesDataSeries5.getData().add(new XYChart.Data(map.getEra(), map.getAverageNumberOfChildren()));

            this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries1);
            this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries2);
            this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries3);
            this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries4);
            this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries5);
        } else {
            this.mapWithoutBoundariesDataSeries1.getData().add(new XYChart.Data(map.getEra(), map.getAnimals().size()));
            this.mapWithoutBoundariesDataSeries2.getData().add(new XYChart.Data(map.getEra(), map.getGrass().size()));
            this.mapWithoutBoundariesDataSeries3.getData().add(new XYChart.Data(map.getEra(), map.getAverageAnimalsEnergy()));
            this.mapWithoutBoundariesDataSeries4.getData().add(new XYChart.Data(map.getEra(), map.getAverageDeadAnimalsLife()));
            this.mapWithoutBoundariesDataSeries5.getData().add(new XYChart.Data(map.getEra(), map.getAverageNumberOfChildren()));

            this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries1);
            this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries2);
            this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries3);
            this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries4);
            this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries5);
        }
    }

    private VBox createOptionButtons() {
        Button stopButton = new Button("Stop");
        stopButton.setOnAction((action) -> {
            if (this.engineThread.isAlive()) {
                this.engineThread.suspend();
                this.threadWaiting = true;
            }
            // TODO save to file
        });
        stopButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        stopButton.setMaxWidth(Double.MAX_VALUE);

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction((action) -> {
            if (this.threadWaiting) {
                this.engineThread.resume();
                this.threadWaiting = false;
            }
        });
        resumeButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        resumeButton.setMaxWidth(Double.MAX_VALUE);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction((action) -> {
            Platform.exit();
            System.exit(0);
        });
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        exitButton.setMaxWidth(Double.MAX_VALUE);

        Button showDominantGenotype = new Button("Dominant genotype\n (only while stop)");
        showDominantGenotype.setOnAction((action) -> {
            if (this.threadWaiting) {
                this.dominantGenotypeWindow = new Stage();
                this.dominantGenotypeWindow.setTitle("Animals with dominant genotype");
//                Scene newScene = new Scene(this.dominantGenotypeWindow, 400, 400);
//                this.dominantGenotypeWindow.setScene(newScene);
                this.dominantGenotypeWindow.setX(600);
                this.dominantGenotypeWindow.setY(600);
                this.dominantGenotypeWindow.show();
            }
        });
        showDominantGenotype.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        showDominantGenotype.setMaxWidth(Double.MAX_VALUE);

        VBox vBox = new VBox(stopButton, resumeButton, exitButton, showDominantGenotype);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        return vBox;
    }

    private VBox createDominantGenotype(AbstractWorldMap map) {
        int[] dominantGenotype = map.getDominantGenotype();
        String genotype = Arrays.toString(dominantGenotype);
        Label label = new Label(genotype);
        return new VBox(label);
    }

//    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
////        @Override
////        public void handle(MouseEvent event) {
////            if (this..getState().toString() == "TIMED_WAITING")
////                System.out.println("Helolololo");
////        }
//    };
}
