package yapbot.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class DialogBox extends HBox {
    private Label message;
    private boolean isUser;

    public DialogBox(String message, boolean isUser) {
        this.message = new Label(message);
        this.isUser = isUser;
        formatDialogBox();
    }

    private void formatDialogBox() {
        this.message.setWrapText(true);
        this.message.setPadding(new Insets(10));
        this.message.setFont(Font.font("Arial", 14));
        this.message.setStyle(
                "-fx-background-color: " + (isUser ? "#DCF8C6" : "#E5E5EA") + ";" +
                        "-fx-background-radius: 15 15 0 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: lightgray;" +
                        "-fx-border-width: 1;" +
                        "-fx-text-fill: black;"
        );
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(this.message);
    }
}