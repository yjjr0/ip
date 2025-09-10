package yapbot.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import yapbot.YapBot;
import yapbot.storage.CommandList;
import yapbot.storage.Storage;

public class Launcher extends Application {
    private static AnchorPane mainLayout = new AnchorPane();
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox dialogContainer = new VBox();
    private static TextField userInput = new TextField();
    private static Button sendButton = new Button("Send");
    private static Button downloadButton = new Button("↓");
    private static Scene scene = new Scene(mainLayout, 400, 500);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        renderGreeting();
        renderPage();
        format(stage);
    }

    public static void renderGreeting() {
        DialogBox greeting = new DialogBox(UI.greeting(), false);
        dialogContainer.getChildren().add(greeting);
    }

    public void renderPage() {
        scrollPane.setContent(dialogContainer);

        userInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    setPreviousCommand();
                } case DOWN -> {
                    setNextCommand();
                } default -> {
                    return;
                }
            }
        });

        userInput.setOnAction(event -> addDialogBox());
        sendButton.setOnMouseClicked(event -> addDialogBox());
        downloadButton.setOnMouseClicked(event -> downloadTxtFile());
    }

    public static void addDialogBox() {
        String command = userInput.getText();
        CommandList.addCommand(command);
        DialogBox input = new DialogBox(command, true);
        DialogBox response = new DialogBox(YapBot.getResponse(command), false);
        dialogContainer.getChildren().addAll(input, response);
        userInput.clear();
    }

    public static void downloadTxtFile() {
        Storage.writeTasks();
    }

    public static void setPreviousCommand() {
        String prevCommand = CommandList.getPreviousCommand();
        userInput.setText(prevCommand);
    }

    public static void setNextCommand() {
        String nextCommand = CommandList.getNextCommand();
        userInput.setText(nextCommand);
    }

    public void format(Stage stage) {
        formatStage(stage);
        formatMainLayout();
        formatAnchorPane();
        formatScrollPane();
        formatDialogContainer();
        formatUserInput();
        formatSendButton();
        formatDownloadButton();
    }

    public void formatStage(Stage stage) {
        stage.setTitle("YapBot");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public void formatMainLayout() {
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton, downloadButton);
    }

    public void formatAnchorPane() {
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0); // Leave space for input box

        AnchorPane.setLeftAnchor(userInput, 10.0);
        AnchorPane.setBottomAnchor(userInput, 10.0);
        AnchorPane.setRightAnchor(sendButton, 50.0);
        AnchorPane.setBottomAnchor(sendButton, 10.0);
        AnchorPane.setRightAnchor(downloadButton, 10.0);
        AnchorPane.setBottomAnchor(downloadButton, 10.0);
        AnchorPane.setRightAnchor(userInput, 110.0); // Adjusted for send button width
    }

    public void formatScrollPane() {
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: #FFFFFF; -fx-border-color: #CCCCCC;");
    }

    public void formatDialogContainer() {
        dialogContainer.setSpacing(10);
        dialogContainer.setStyle("-fx-padding: 10;");
        dialogContainer.setPrefWidth(380);
    }

    public void formatUserInput() {
        userInput.setPromptText("Type your message...");
        userInput.setFont(Font.font(14));
        userInput.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
    }

    public void formatSendButton() {
        sendButton.setFont(Font.font(14));
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
    }

    public void formatDownloadButton() {
        downloadButton.setFont(Font.font(14));
        downloadButton.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-background-radius: 5;");
    }
}
