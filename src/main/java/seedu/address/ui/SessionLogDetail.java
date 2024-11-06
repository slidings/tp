package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.model.log.Log;

/**
 * A UI component that displays detailed information of a {@code Log}.
 */
public class SessionLogDetail extends UiPart<Region> {

    private static final String FXML = "SessionLogDetail.fxml";

    @FXML
    private Label detailDate;

    @FXML
    private TextArea detailSessionEntry;

    /**
     * Creates a {@code SessionLogDetail} with the given {@code Log}.
     */
    public SessionLogDetail(Log log) {
        super(FXML);
        setLogDetails(log);
    }

    /**
     * Sets the FXML labels with the given {@code Log}
     */
    public void setLogDetails(Log log) {
        detailDate.setText("Appointment Date: " + log.getAppointmentDate().toString());
        detailSessionEntry.setText(log.getFormattedEntry());
    }
}

