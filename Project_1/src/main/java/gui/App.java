package gui;

import elements.Animal;
import elements.Genes;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapElement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.AbstractWorldMap;
import map.WorldMapWithBoundaries;
import map.WorldMapWithoutBoundaries;
import simulation.Simulation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

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
    private int gridHeight;
    private int gridWidth;
    private WorldMapWithBoundaries mapWithBoundaries;
    private WorldMapWithoutBoundaries mapWithoutBoundaries;
    private VBox mainVBox;
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
    private Thread engineThreadWithBoundaries;
    private Thread engineThreadWithoutBoundaries;
    private VBox plotAndGenotypeVBox;
    private HBox genotypeHBox;
    private int magicCount = 0;
    private boolean mapWithBoundariesWaitingThread = false;
    private boolean mapWithoutBoundariesWaitingThread = false;

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

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());

            Scene scene = new Scene(this.mainVBox);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // JavaFx objects for start function: createHeadLineText, createText, createHBox, createStartButton
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
            Simulation engineWithBoundaries = new Simulation(this.mapWithBoundaries, this, Integer.parseInt(this.animalsAtTheBeginningTextField.getText()),
                    Integer.parseInt(this.startingEnergyTextField.getText()), Integer.parseInt(this.refreshTimeTextField.getText()),
                    this.magicSimulationTextField.getText());
            Simulation engineWithoutBoundaries = new Simulation(this.mapWithoutBoundaries, this, Integer.parseInt(this.animalsAtTheBeginningTextField.getText()),
                    Integer.parseInt(this.startingEnergyTextField.getText()), Integer.parseInt(this.refreshTimeTextField.getText()),
                    this.magicSimulationTextField.getText());
            if (Integer.parseInt(this.animalsAtTheBeginningTextField.getText()) == 5 && this.magicSimulationTextField.getText().toLowerCase().equals("yes"))
                this.magicCount++;

            this.mainVBox.getChildren().remove(this.optionsVBox);

            // Options
            VBox optionButtons = createOptionButtons();
            HBox gridPaneHBox = new HBox(this.mapWithoutBoundariesGridPane, optionButtons, this.mapWithBoundariesGridPane);
            gridPaneHBox.setSpacing(10);
            gridPaneHBox.setAlignment(Pos.CENTER);

            // Plot
            VBox mapWithBoundariesPlotVBox = createPlot(this.mapWithBoundaries);
            VBox mapWithoutBoundariesPlotVBox = createPlot(this.mapWithoutBoundaries);
            HBox plotHBox = new HBox(mapWithoutBoundariesPlotVBox, mapWithBoundariesPlotVBox);
            plotHBox.setSpacing(100);
            plotHBox.setAlignment(Pos.CENTER);

            // Dominant genotype
            VBox mapWithBoundariesGenotypeVBox = createDominantGenotype(this.mapWithBoundaries);
            VBox mapWithoutBoundariesGenotypeVBox = createDominantGenotype(this.mapWithoutBoundaries);
            this.genotypeHBox = new HBox(mapWithoutBoundariesGenotypeVBox, mapWithBoundariesGenotypeVBox);
            this.genotypeHBox.setSpacing(10);
            this.genotypeHBox.setAlignment(Pos.CENTER);

            this.plotAndGenotypeVBox = new VBox(plotHBox, this.genotypeHBox);

            this.mainVBox.getChildren().addAll(gridPaneHBox, this.plotAndGenotypeVBox);
            this.mainVBox.setSpacing(10);
            this.engineThreadWithBoundaries = new Thread(engineWithBoundaries);
            this.engineThreadWithoutBoundaries = new Thread(engineWithoutBoundaries);
            this.engineThreadWithBoundaries.start();
            this.engineThreadWithoutBoundaries.start();
        });
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox hBox = new HBox(startButton);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    // Rendering and changing map
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
        updateDominantGenotype();
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

        this.gridWidth = 340 / Integer.parseInt(this.widthTextField.getText());
        this.gridHeight = 340 / Integer.parseInt(this.heightTextField.getText());
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
                if (newPosition.follows(jungleLowerLeft) && newPosition.precedes(jungleUpperRight))
                    newVBox.setStyle("-fx-background-color: #135E46");
                else
                    newVBox.setStyle("-fx-background-color: #A1724E");
                if (map instanceof WorldMapWithBoundaries)
                    this.mapWithBoundariesGridPane.add(newVBox, i, j, 1, 1);
                else
                    this.mapWithoutBoundariesGridPane.add(newVBox, i, j, 1, 1);
            }
        }
    }

    // Placing animals on map
    private void placeAnimals(AbstractWorldMap map) {
        if (map.getAnimals().size() == 5 && this.magicSimulationTextField.getText().toLowerCase().equals("yes") && this.magicCount < 3) {
            map.placeMagicAnimals(Integer.parseInt(this.startingEnergyTextField.getText()));
            this.magicCount++;
        }
        Vector2d[] grassesAndAnimals = getAnimalsAndGrasses(map);
        for (Vector2d position : grassesAndAnimals) {
            VBox vBox;
            GuiElement guiElement = new GuiElement((IMapElement) map.objectAt(position));
            ImageView imageView = guiElement.getImageView();
            imageView.setFitWidth(this.gridWidth);
            imageView.setFitHeight(this.gridHeight);
            vBox = new VBox(imageView);
            if (map instanceof WorldMapWithBoundaries) {
                if (map.objectAt(position) instanceof Animal)
                    vBox.setOnMouseClicked((action) -> createWindowWithAnimalInfo(map, (Animal) map.objectAt(position)));
                this.mapWithBoundariesGridPane.add(vBox, position.getX(), position.getY(), 1, 1);
            } else {
                if (map.objectAt(position) instanceof Animal)
                    vBox.setOnMouseClicked((action) -> createWindowWithAnimalInfo(map, (Animal) map.objectAt(position)));
                this.mapWithoutBoundariesGridPane.add(vBox, position.getX(), position.getY(), 1, 1);
            }
        }
    }

    private Vector2d[] getAnimalsAndGrasses(AbstractWorldMap map) {
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

    // Info to show about animal
    private void createWindowWithAnimalInfo(AbstractWorldMap map, Animal animal) {
        Stage window = new Stage();
        VBox animalInfoVBox = new VBox();
        if (map instanceof WorldMapWithBoundaries)
            window.setTitle("Animal info on map with boundaries");
        else
            window.setTitle("Animal info on map without boundaries");
        createAnimalInfoVBox(map, animalInfoVBox, animal, window);
        StackPane stackPane = new StackPane(animalInfoVBox);
        Scene newScene = new Scene(stackPane, 600, 320);
        window.setScene(newScene);
        if (map instanceof WorldMapWithBoundaries && this.mapWithBoundariesWaitingThread)
            window.show();
        else if (this.mapWithoutBoundariesWaitingThread)
            window.show();
    }

    private void createAnimalInfoVBox(AbstractWorldMap map, VBox animalInfoVBox, Animal animal, Stage window) {
        animalInfoVBox.getChildren().clear();

        // Animal image
        Label imageViewInfoLabel = new Label("Animal image");
        imageViewInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        GuiElement guiElement = new GuiElement(animal);
        ImageView imageView = guiElement.getImageView();
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        // Number of animal's children
        Label numberOfChildrenInfoLabel = new Label("Number of children");
        numberOfChildrenInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        int animalNumberOfChildren = animal.getNumberOfChildren();
        Label numberOfChildrenLabel = new Label(String.valueOf(animalNumberOfChildren));

        // Animal's children
        Label childrenInfoLabel = new Label("Animals' children");
        childrenInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        LinkedList<Animal> animalChildren = animal.getChildren();
        HBox animalChildrenHBox = new HBox();
        Button animalChildrenButton;
        if (animalChildren.size() > 0) {
            int index = 0;
            for (Animal children : animalChildren) {
                animalChildrenButton = new Button(index + 1 + " children");
                animalChildrenButton.setOnAction((action) -> {
                    createWindowWithAnimalInfo(map, children);
                });
                animalChildrenHBox.getChildren().add(animalChildrenButton);
                index++;
            }
        } else {
            Label animalChildrenLabel = new Label("Animal has no children");
            animalChildrenHBox.getChildren().add(animalChildrenLabel);
        }
        animalChildrenHBox.setSpacing(2);
        animalChildrenHBox.setAlignment(Pos.CENTER);

        // Animal genotype
        Label genotypeInfoLabel = new Label("Animal genotype");
        genotypeInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Genes animalGenes = animal.getAnimalGenes();
        Label genotypeLabel = new Label(animalGenes.toString());

        // Animal death date
        Label animalDeathInfoLabel = new Label("Animal death age");
        animalDeathInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label animalDeathLabel;
        int animalDeathDate;
        if (animal.isDead()) {
            animalDeathDate = animal.getAge();
            animalDeathLabel = new Label(String.valueOf(animalDeathDate));
        } else
            animalDeathLabel = new Label("Animal is still alive");

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction((action) -> {
            window.close();
        });
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        animalInfoVBox.getChildren().addAll(imageViewInfoLabel, imageView, numberOfChildrenInfoLabel, numberOfChildrenLabel,
                childrenInfoLabel, animalChildrenHBox, genotypeInfoLabel, genotypeLabel, animalDeathInfoLabel, animalDeathLabel, closeButton);
        animalInfoVBox.setSpacing(5);
        animalInfoVBox.setAlignment(Pos.TOP_CENTER);
    }

    // Line chart plot
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
        // Updating line chart with current data
        if (map instanceof WorldMapWithBoundaries) {
            this.mapWithBoundariesDataSeries1.getData().add(new XYChart.Data(map.getEra(), map.getAnimals().size()));
            this.mapWithBoundariesDataSeries2.getData().add(new XYChart.Data(map.getEra(), map.getGrass().size()));
            this.mapWithBoundariesDataSeries3.getData().add(new XYChart.Data(map.getEra(), map.getAverageAnimalsEnergy()));
            this.mapWithBoundariesDataSeries4.getData().add(new XYChart.Data(map.getEra(), map.getAverageDeadAnimalsLife()));
            this.mapWithBoundariesDataSeries5.getData().add(new XYChart.Data(map.getEra(), map.getAverageNumberOfChildren()));
            try {
                this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries1);
                this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries2);
                this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries3);
                this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries4);
                this.mapWithBoundariesLineChart.getData().add(this.mapWithBoundariesDataSeries5);
            } catch (IllegalArgumentException exception) {
                return;
            }
        } else {
            this.mapWithoutBoundariesDataSeries1.getData().add(new XYChart.Data(map.getEra(), map.getAnimals().size()));
            this.mapWithoutBoundariesDataSeries2.getData().add(new XYChart.Data(map.getEra(), map.getGrass().size()));
            this.mapWithoutBoundariesDataSeries3.getData().add(new XYChart.Data(map.getEra(), map.getAverageAnimalsEnergy()));
            this.mapWithoutBoundariesDataSeries4.getData().add(new XYChart.Data(map.getEra(), map.getAverageDeadAnimalsLife()));
            this.mapWithoutBoundariesDataSeries5.getData().add(new XYChart.Data(map.getEra(), map.getAverageNumberOfChildren()));
            try {
                this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries1);
                this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries2);
                this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries3);
                this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries4);
                this.mapWithoutBoundariesLineChart.getData().add(this.mapWithoutBoundariesDataSeries5);
            } catch (IllegalArgumentException exception) {
                return;
            }
        }
    }

    // Option buttons
    private VBox createOptionButtons() {
        // Paths for saving information to file
        String mapWithBoundariesFilePath = "src/main/resources/mapWithBoundaries.csv";
        String mapWithoutBoundariesFilePath = "src/main/resources/mapWithoutBoundaries.csv";

        // Map with boundaries option buttons
        Button stopButtonMapWithBoundaries = new Button("Stop map with boundaries");
        stopButtonMapWithBoundaries.setOnAction((action) -> {
            if (!this.mapWithBoundariesWaitingThread) {
                this.engineThreadWithBoundaries.suspend();
                this.mapWithBoundariesWaitingThread = true;
            }
        });
        stopButtonMapWithBoundaries.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        stopButtonMapWithBoundaries.setMaxWidth(Double.MAX_VALUE);

        Button resumeButtonMapWithBoundaries = new Button("Resume map with boundaries");
        resumeButtonMapWithBoundaries.setOnAction((action) -> {
            if (this.mapWithBoundariesWaitingThread) {
                this.engineThreadWithBoundaries.resume();
                this.mapWithBoundariesWaitingThread = false;
            }
        });
        resumeButtonMapWithBoundaries.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        resumeButtonMapWithBoundaries.setMaxWidth(Double.MAX_VALUE);

        VBox mapWithBoundariesOptions = new VBox(stopButtonMapWithBoundaries, resumeButtonMapWithBoundaries);
        mapWithBoundariesOptions.setSpacing(15);

        // Map without boundaries option buttons
        Button stopButtonMapWithoutBoundaries = new Button("Stop map without boundaries");
        stopButtonMapWithoutBoundaries.setOnAction((action) -> {
            if (!this.mapWithoutBoundariesWaitingThread) {
                this.engineThreadWithoutBoundaries.suspend();
                this.mapWithoutBoundariesWaitingThread = true;
            }
        });
        stopButtonMapWithoutBoundaries.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        stopButtonMapWithoutBoundaries.setMaxWidth(Double.MAX_VALUE);

        Button resumeButtonMapWithoutBoundaries = new Button("Resume map without boundaries");
        resumeButtonMapWithoutBoundaries.setOnAction((action) -> {
            if (this.mapWithoutBoundariesWaitingThread) {
                this.engineThreadWithoutBoundaries.resume();
                this.mapWithoutBoundariesWaitingThread = false;
            }
        });
        resumeButtonMapWithoutBoundaries.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        resumeButtonMapWithoutBoundaries.setMaxWidth(Double.MAX_VALUE);

        VBox mapWithoutBoundariesOptions = new VBox(stopButtonMapWithoutBoundaries, resumeButtonMapWithoutBoundaries);
        mapWithoutBoundariesOptions.setSpacing(20);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction((action) -> {
            saveFileToCSV(this.mapWithBoundaries, mapWithBoundariesFilePath, this.mapWithBoundaries.getEra());
            saveFileToCSV(this.mapWithoutBoundaries, mapWithoutBoundariesFilePath, this.mapWithoutBoundaries.getEra());
            Platform.exit();
            System.exit(0);
        });
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        exitButton.setMaxWidth(Double.MAX_VALUE);

        VBox vBox = new VBox(mapWithoutBoundariesOptions, mapWithBoundariesOptions, exitButton);
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        return vBox;
    }

    // Dominant genotype
    private VBox createDominantGenotype(AbstractWorldMap map) {
        Genes dominantGenotype = map.getDominantGenotype();
        String genotype = dominantGenotype.toString();
        Text text;
        if (map instanceof WorldMapWithBoundaries)
            text = new Text("Map with boundaries dominant genotype");
        else
            text = new Text("Map without boundaries dominant genotype");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label genotypeLabel = new Label(genotype);
        genotypeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        if (map instanceof WorldMapWithBoundaries) {
            VBox mapWithBoundariesDominantGenotypeBox = new VBox(text, genotypeLabel);
            mapWithBoundariesDominantGenotypeBox.setAlignment(Pos.CENTER);
            return mapWithBoundariesDominantGenotypeBox;
        } else {
            VBox mapWithoutBoundariesDominantGenotypeBox = new VBox(text, genotypeLabel);
            mapWithoutBoundariesDominantGenotypeBox.setAlignment(Pos.CENTER);
            return mapWithoutBoundariesDominantGenotypeBox;
        }
    }

    private void updateDominantGenotype() {
        this.plotAndGenotypeVBox.getChildren().remove(this.genotypeHBox);
        VBox mapWithBoundariesGenotypeVBox = createDominantGenotype(this.mapWithBoundaries);
        VBox mapWithoutBoundariesGenotypeVBox = createDominantGenotype(this.mapWithoutBoundaries);
        this.genotypeHBox = new HBox(mapWithoutBoundariesGenotypeVBox, mapWithBoundariesGenotypeVBox);
        this.genotypeHBox.setSpacing(20);
        this.genotypeHBox.setAlignment(Pos.CENTER);
        this.plotAndGenotypeVBox.getChildren().add(this.genotypeHBox);
    }

    // Saving to CSV file
    private void saveFileToCSV(AbstractWorldMap map, String filePath, int era) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Date | ");
            stringBuilder.append("Number of animals | ");
            stringBuilder.append("Number of grasses | ");
            stringBuilder.append("Average energy if living animals | ");
            stringBuilder.append("Average life of dead animals | ");
            stringBuilder.append("Average number of children for living animals");
            stringBuilder.append("\n");

            XYChart.Data animals;
            XYChart.Data grasses;
            XYChart.Data averageEnergy;
            XYChart.Data averageLife;
            XYChart.Data numberOfChildren;

            int averageAmountOfAnimalsOnMapWithBoundaries = 0;
            int averageAmountOfGrassesOnMapWithBoundaries = 0;
            double averageAvgEnergyOnMapWithBoundaries = 0;
            double averageAvgLifeOnMapWithBoundaries = 0;
            double averageNumberOfChildrenOnMapWithBoundaries = 0;

            int averageAmountOfAnimalsOnMapWithoutBoundaries = 0;
            int averageAmountOfGrassesOnMapWithoutBoundaries = 0;
            double averageAvgEnergyOnMapWithoutBoundaries = 0;
            double averageAvgLifeOnMapWithoutBoundaries = 0;
            double averageNumberOfChildrenOnMapWithoutBoundaries = 0;

            for (int i = 0; i < era; i++) {
                stringBuilder.append(i + 1 + " | ");
                if (map instanceof WorldMapWithBoundaries) {
                    animals = (XYChart.Data) this.mapWithBoundariesDataSeries1.getData().get(i);
                    averageAmountOfAnimalsOnMapWithBoundaries += (int) animals.getYValue();
                    stringBuilder.append(animals.getYValue() + " | ");
                    grasses = (XYChart.Data) this.mapWithBoundariesDataSeries2.getData().get(i);
                    averageAmountOfGrassesOnMapWithBoundaries += (int) grasses.getYValue();
                    stringBuilder.append(grasses.getYValue() + " | ");
                    averageEnergy = (XYChart.Data) this.mapWithBoundariesDataSeries3.getData().get(i);
                    averageAvgEnergyOnMapWithBoundaries += (double) averageEnergy.getYValue();
                    stringBuilder.append(averageEnergy.getYValue() + " | ");
                    averageLife = (XYChart.Data) this.mapWithBoundariesDataSeries4.getData().get(i);
                    averageAvgLifeOnMapWithBoundaries += (double) averageLife.getYValue();
                    stringBuilder.append(averageLife.getYValue() + " | ");
                    numberOfChildren = (XYChart.Data) this.mapWithBoundariesDataSeries5.getData().get(i);
                    averageNumberOfChildrenOnMapWithBoundaries += (double) numberOfChildren.getYValue();
                    stringBuilder.append(numberOfChildren.getYValue());
                    stringBuilder.append("\n");
                } else {
                    animals = (XYChart.Data) this.mapWithoutBoundariesDataSeries1.getData().get(i);
                    averageAmountOfAnimalsOnMapWithoutBoundaries += (int) animals.getYValue();
                    stringBuilder.append(animals.getYValue() + " | ");
                    grasses = (XYChart.Data) this.mapWithoutBoundariesDataSeries2.getData().get(i);
                    averageAmountOfGrassesOnMapWithoutBoundaries += (int) grasses.getYValue();
                    stringBuilder.append(grasses.getYValue() + " | ");
                    averageEnergy = (XYChart.Data) this.mapWithoutBoundariesDataSeries3.getData().get(i);
                    averageAvgEnergyOnMapWithoutBoundaries += (double) averageEnergy.getYValue();
                    stringBuilder.append(averageEnergy.getYValue() + " | ");
                    averageLife = (XYChart.Data) this.mapWithoutBoundariesDataSeries4.getData().get(i);
                    averageAvgLifeOnMapWithoutBoundaries += (double) averageLife.getYValue();
                    stringBuilder.append(averageLife.getYValue() + " | ");
                    numberOfChildren = (XYChart.Data) this.mapWithoutBoundariesDataSeries5.getData().get(i);
                    averageNumberOfChildrenOnMapWithoutBoundaries += (double) numberOfChildren.getYValue();
                    stringBuilder.append(numberOfChildren.getYValue());
                    stringBuilder.append("\n");
                }
            }
            stringBuilder.append("\n");

            stringBuilder.append("Date | ");
            stringBuilder.append("Average number of animals | ");
            stringBuilder.append("Average number of grasses | ");
            stringBuilder.append("Total average energy if living animals | ");
            stringBuilder.append("Total average life of dead animals | ");
            stringBuilder.append("Total average number of children for living animals");
            stringBuilder.append("\n");
            stringBuilder.append(era + " | ");
            if (map instanceof WorldMapWithBoundaries) {
                stringBuilder.append((double) averageAmountOfAnimalsOnMapWithBoundaries / era + " | ");
                stringBuilder.append(averageAmountOfGrassesOnMapWithBoundaries / era + " | ");
                stringBuilder.append(averageAvgEnergyOnMapWithBoundaries / era + " | ");
                stringBuilder.append(averageAvgLifeOnMapWithBoundaries / era + " | ");
                stringBuilder.append(averageNumberOfChildrenOnMapWithBoundaries / era);
            } else {
                stringBuilder.append((double) averageAmountOfAnimalsOnMapWithoutBoundaries / era + " | ");
                stringBuilder.append((double) averageAmountOfGrassesOnMapWithoutBoundaries / era + " | ");
                stringBuilder.append(averageAvgEnergyOnMapWithoutBoundaries / era + " | ");
                stringBuilder.append(averageAvgLifeOnMapWithoutBoundaries / era + " | ");
                stringBuilder.append(averageNumberOfChildrenOnMapWithoutBoundaries / era);
            }
            writer.write(stringBuilder.toString());
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}