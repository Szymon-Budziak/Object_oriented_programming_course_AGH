package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            GrassField map = new GrassField(2);
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();

            GridPane gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);

            int minX = min(map.getLowerLeft().x, map.getUpperRight().x);
            int maxX = max(map.getLowerLeft().x, map.getUpperRight().x);
            int minY = min(map.getLowerLeft().y, map.getUpperRight().y);
            int maxY = max(map.getLowerLeft().y, map.getUpperRight().y);
            Label indexZero = new Label("y/x");
            int width = 20;
            int height = 20;
            gridPane.add(indexZero, 0, 0, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            gridPane.getRowConstraints().add(new RowConstraints(height));


            int starting = 1;
            int i = minX;
            while (i <= maxX) {
                Label c = new Label("" + i);
                gridPane.add(c, starting, 0, 1, 1);
                GridPane.setHalignment(c, HPos.CENTER);
                gridPane.getColumnConstraints().add(new ColumnConstraints(width));
                i++;
                starting++;
            }

            starting = 1;
            i = maxY;
            while (i >= minY) {
                Label r = new Label("" + i);
                gridPane.add(r, 0, starting, 1, 1);
                GridPane.setHalignment(r, HPos.CENTER);
                gridPane.getRowConstraints().add(new RowConstraints(height));
                i--;
                starting++;
            }
            Vector2d[] grassAndAnimals = map.getAnimalsAndGrass();
            for (Vector2d position : grassAndAnimals) {
                Object object = map.objectAt(position);
                Label label = new Label(object.toString());
                System.out.println(position);
                gridPane.add(label, 1 + position.x - minX, 1 + maxY - position.y, 1, 1);
            }

            Scene scene = new Scene(gridPane, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
