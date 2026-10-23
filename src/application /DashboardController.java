package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class DashboardController {
	
	@FXML
	private VBox mainVBox;

    @FXML
    private TextField taskField;

    @FXML
    private ComboBox<String> priorityCombo;

    @FXML
    private ListView<String> taskList;

    @FXML
    private Label statusLabel;

    
    @FXML
    private TextArea messagesArea;

    
    @FXML
    private ComboBox<String> colorCombo;
    
    private Map<String, ObservableList<String>> farmItemsByOption =
            new HashMap<>();

    /*
     * Runs automatically after the FXML file is loaded
     */
    @FXML
    private void initialize() {

        priorityCombo.getItems().addAll(
                "Low",
                "Medium",
                "High"
        );

        priorityCombo.setValue("Medium");
        priorityCombo.getItems().clear();

        priorityCombo.getItems().addAll("Farm Equipment","Alerts","Flight Scheduled","Irrigation");

        priorityCombo.setValue("option ");

        colorCombo.getItems().addAll("White","Light Gray","Light Blue");

        colorCombo.setValue("White");
        
        farmItemsByOption.put("option 1",FXCollections.observableArrayList());

        farmItemsByOption.put("option 2",FXCollections.observableArrayList());

        taskList.setItems(farmItemsByOption.get("option 1"));


        // SWITCH FARM ITEMS LIST WHEN OPTION CHANGES
        priorityCombo.setOnAction(event -> {

            String selectedOption =
                    priorityCombo.getValue();

            if (selectedOption != null) {

                taskList.setItems(
                        farmItemsByOption.get(selectedOption)
                );
            }
        });
    }
    /*
     * 
     */
    @FXML
    private void handleAdd() {

        String task = taskField.getText().trim();
        String selectedOption = priorityCombo.getValue();

        if (!task.isEmpty() && selectedOption != null) {

            farmItemsByOption
                    .get(selectedOption)
                    .add(task);

            taskField.clear();

            if (statusLabel != null) {
                statusLabel.setText(
                        "Item added successfully."
                );
            }

            // Homework Messages area
            messagesArea.appendText(
                    "Item added successfully.\n"
                    + selectedOption + " selected\n"
            );
        }
    }


    /*
     * 
     */
    @FXML
    private void handleComplete() {

        if (statusLabel != null) {

            String selectedTask =taskList.getSelectionModel().getSelectedItem();
            String selectedOption = priorityCombo.getValue();

            if (selectedTask != null) {

                taskList.getItems().remove(selectedTask);

                statusLabel.setText(
                        "Completed: " + selectedTask
                );
            }
        }

        String selectedOption =
                priorityCombo.getValue();

        if (selectedOption != null) {

        	farmItemsByOption
            .get(selectedOption)
            .clear();

    taskList.refresh();

    messagesArea.appendText(
            selectedOption + " deleted\n"
            );
        }
    }
    @FXML
    private void handleClear() {

        taskList.getItems().clear();

        if (statusLabel != null) {
            statusLabel.setText(
                    "All tasks cleared."
            );
        }

        // c behavior
        String selectedColor = colorCombo.getValue();

        String borderColor = "white";
        String backgroundColor = "white";

        if ("Light Gray".equals(selectedColor)) {
            borderColor = "lightgray";
            backgroundColor = "lightgray";
        }
        else if ("Light Blue".equals(selectedColor)) {
            borderColor = "lightblue";
            backgroundColor = "lightblue";
        }

        String borderStyle =
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 3px;";

        taskField.setStyle(borderStyle);
        taskList.setStyle(borderStyle);
        messagesArea.setStyle(borderStyle);

        mainVBox.setStyle(
                "-fx-background-color: " + backgroundColor + ";"
        );
    }
   

}
