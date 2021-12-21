package gui;

import interfaces.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    VBox vBox = new VBox();

    public GuiElementBox(IMapElement element) {
        try {
            String a = element.getImagePath();
            Image image = new Image(new FileInputStream(element.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            this.vBox.getChildren().addAll(imageView);
            this.vBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File was not found.");
        }
    }
}
