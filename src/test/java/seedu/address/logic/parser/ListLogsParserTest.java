package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.model.person.IdentityNumber;

public class ListLogsParserTest {

    private ListLogsParser parser = new ListLogsParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIdentityNumber_throwsParseException() {
        // Input too short/long
        assertParseFailure(parser, "i/S123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/S12345678901", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));

        // Missing prefix
        assertParseFailure(parser, "S1234567D", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListLogsCommand() {
        // valid identity number with correct prefix
        IdentityNumber identityNumber = new IdentityNumber("S1234567D");
        ListLogsCommand expectedCommand = new ListLogsCommand(identityNumber);
        assertParseSuccess(parser, "i/S1234567D", expectedCommand);

        // valid identity number with leading/trailing whitespaces
        assertParseSuccess(parser, " i/S1234567D  ", expectedCommand);

        // valid identity number with extra spaces between prefix and NRIC
        assertParseSuccess(parser, "  i/ S1234567D  ", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid identity number with invalid characters
        assertParseFailure(parser, "i/!S1234567D", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/S1234567Z", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE)); // Test for invalid NRIC format
    }
}
