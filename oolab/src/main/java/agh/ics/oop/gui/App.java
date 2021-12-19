package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    GridPane gridPane = new GridPane();
    MapDirection orientation = MapDirection.NORTH;
    int width = 50;
    int height = 50;

    // f r l f b r f l r f f b l
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            TextField textField = new TextField();
            Button startButton = getStartButton(textField);
            Button directionButton = getDirectionButton();
            HBox hBox = new HBox(gridPane, textField, startButton, directionButton);
            Scene scene = new Scene(hBox, 700, 700);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void createGrid(GrassField newMap) {
        int left = newMap.getLowerLeft().x;
        int right = newMap.getUpperRight().x;
        int lower = newMap.getLowerLeft().y;
        int upper = newMap.getUpperRight().y;
        Label label = new Label("y/x");
        gridPane.add(label, 0, 0, 1, 1);
        gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        gridPane.getRowConstraints().add(new RowConstraints(height));
        GridPane.setHalignment(label, HPos.CENTER);

        for (int i = 1; i <= right - left + 1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            label = new Label("" + (left + i - 1));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, i, 0, 1, 1);
        }
        for (int i = 1; i <= upper - lower + 1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(height));
            label = new Label("" + (upper - i + 1));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i, 1, 1);
        }

        Vector2d[] grassAndAnimals = newMap.getAnimalsAndGrass();
        for (Vector2d position : grassAndAnimals) {
            GuiElementBox vBox = new GuiElementBox((IMapElement) newMap.objectAt(position));
            label = new Label();
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(vBox.vBox, 1 + position.x - left, 1 + upper - position.y, 1, 1);
        }

    }

    public void renderMap(GrassField newMap) {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        createGrid(newMap);
    }

    public Button getStartButton(TextField textField) {
        Button startButton = new Button("Start");
        startButton.setOnAction((action) -> {
            String text = textField.getText();
            MoveDirection[] directions = OptionsParser.parse(text.split(" "));
            Vector2d[] positions = {new Vector2d(1, 3), new Vector2d(2, -1)};
            GrassField map = new GrassField(10);
            IEngine engine = new SimulationEngine(directions, map, positions, this, this.orientation);
            Thread engineThread = new Thread(engine::run);
            engineThread.start();
        });
        return startButton;
    }

    public Button getDirectionButton() {
        Button directionButton = new Button(orientation.toString());
        directionButton.setOnAction((action) -> {
            this.orientation = orientation.next();
            directionButton.setText(this.orientation.toString());
        });
        return directionButton;
    }
}