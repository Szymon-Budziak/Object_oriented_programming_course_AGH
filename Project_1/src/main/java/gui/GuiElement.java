package gui;

import interfaces.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElement {
    private ImageView imageView;

    public GuiElement(IMapElement element) {
        try {
            Image image = new Image(new FileInputStream(element.getImagePath()));
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(20);
            this.imageView.setFitHeight(20);
//            this.vBox.getChildren().addAll(imageView);
//            this.vBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File was not found.");
        }
    }

    // Getter
    public ImageView getImageView() {
        return this.imageView;
    }
}
