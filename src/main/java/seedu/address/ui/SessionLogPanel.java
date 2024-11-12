package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.log.Log;


/**
 * Panel containing the list of persons.
 */
public class SessionLogPanel extends UiPart<Region> {
    private static final String FXML = "SessionLogPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ListView<Log> logListView;

    @FXML
    private VBox detailedSessionLogContainer;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public SessionLogPanel(ObservableList<Log> logList) {
        super(FXML);
        logListView.setItems(logList);
        logListView.setCellFactory(listView -> new SessionLogViewCell(logList));

        // Listener for logListView. Used to show detailed log.
        logListView.getSelectionModel().selectedItemProperty().addListener((observable, oldLog, newLog) -> {
            if (newLog != null) {
                logger.info("New Log Chosen:" + newLog);
                handleDetailedLog(newLog);
            }
        });
    }

    /**
     * Shows detailed log of currently focused SessionLogViewCell.
     */
    private void handleDetailedLog(Log selectedLog) {
        SessionLogDetail detailedSessionLog = new SessionLogDetail(selectedLog);
        detailedSessionLogContainer.getChildren().clear();
        detailedSessionLogContainer.getChildren().add(detailedSessionLog.getRoot());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a SessionLog
     */
    class SessionLogViewCell extends ListCell<Log> {
        private final ObservableList<Log> logList;
        public SessionLogViewCell(ObservableList<Log> logList) {
            this.logList = logList;
        }
        @Override
        protected void updateItem(Log log, boolean empty) {
            super.updateItem(log, empty);

            if (empty || log == null) {
                setGraphic(null);
                setText(null);
            } else {
                int reverseIndex = logList.size() - getIndex() - 1;
                // The oldest one should be lowest index.
                // Note that this is edited as the session log was wrongly displayed in reverse order.
                Index logIndex = Index.fromZeroBased(reverseIndex);
                setGraphic(new SessionLogCard(log, logIndex).getRoot());
            }
        }
    }

}
