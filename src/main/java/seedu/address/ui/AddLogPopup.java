package seedu.address.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class AddLogPopup {

    public static void display(Consumer<String> onSave, Runnable onCancel) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Log Entry");

        // Ensure that the onCancel action runs if the window is closed directly
        window.setOnCloseRequest(e -> onCancel.run());
        TextArea logEntryArea = new TextArea();
        logEntryArea.setPromptText("Enter your log entry here...");

        // Save button initially disabled
        Button saveButton = new Button("Save");
        saveButton.setDisable(true);

        // Enable the Save button only if the TextArea has text
        logEntryArea.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });
        saveButton.setOnAction(e -> {
            String logEntry = logEntryArea.getText();
            onSave.accept(logEntry);  // Pass logEntry to onSave callback
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            onCancel.run();  // Run onCancel callback
            window.close();
        });

        // HBox to align buttons to the right
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        buttonLayout.getChildren().addAll(saveButton, cancelButton);
        VBox layout = new VBox(10);

        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(logEntryArea, buttonLayout);

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}