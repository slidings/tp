package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;
import seedu.address.ui.ResultDisplay;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddLogCommand.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.*;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() throws ParseException {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        //TODO: Do proper validation for 1) addlog command 2) Validate NRIC and date
        // before popup window is allowed. Throw correct exceptions as well
        // Check if all fields' prefix are present

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(commandText, PREFIX_IDENTITY_NUMBER, PREFIX_LOG, PREFIX_DATE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IDENTITY_NUMBER, PREFIX_LOG, PREFIX_DATE);

        if (!argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty() && argMultimap.getValue(PREFIX_LOG).isEmpty() &&
                !argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            IdentityNumber identityNumber;

            // Check if identity number exists
            try {
                // Parse identity number
                identityNumber = ParserUtil.parseIdentityNumber(
                        argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());
            } catch (ParseException pe) {
                new ResultDisplay().setFeedbackToUser(MESSAGE_INVALID_ID);
                setStyleToIndicateCommandFailure();
                return;
            }

            try {
                // Parse date
                String date = argMultimap.getValue(PREFIX_DATE).get();
                if (!AppointmentDate.isValidDateString(date)) {
                    throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
                }

            } catch (ParseException pe) {
                commandTextField.setText(AppointmentDate.MESSAGE_CONSTRAINTS);
                setStyleToIndicateCommandFailure();
                return;
            }
            // Disable commandTextField and trigger the popup for log entry
            commandTextField.setDisable(true);
            AddLogPopup.display(
                    logEntry -> {
                        // Replace actual newline characters with \\n
                        String encodedLogEntry = logEntry.replace("\n", "\\n");
                        String commandWithLog = commandText + " l/" + encodedLogEntry;
                        executeCommand(commandWithLog);
                        commandTextField.setDisable(false);
                    },
                    () -> {
                        // Callback for cancel action
                        commandTextField.setDisable(false);
                    }
            );

        } else {
            // Handle command normally if l/ is present or if it's not an addlog command
            executeCommand(commandText);
        }
    }

    /**
     * Validates if the input command is an addlog command with the required format.
     * Basic regex can be customized based on the exact expected format.
     */
    private boolean isAddLogCommand(String input) {
        input = input.toLowerCase();
        return input.matches("^addlog .*");
    }

    /**
     * Executes the given command text through the command executor and handles exceptions.
     */
    private void executeCommand(String commandText) {
        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
