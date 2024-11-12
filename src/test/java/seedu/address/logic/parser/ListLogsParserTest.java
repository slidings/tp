package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLogsCommand;
//import seedu.address.model.person.IdentityNumber;

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
    public void parse_multipleIdentityNumber_throwsParseException() {
        // User decides to list multiple users' NRIC
        assertParseFailure(parser, "i/S8642950J i/S0894499G", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
    }

    //TODO: Cant parse succesfully - perhaps due to missing person someetimes?
    /*
    @Test
    public void parse_validArgs_returnsListLogsCommand() {
        // no leading and trailing whitespaces
        IdentityNumber identityNumber = new IdentityNumber("S1234567D");
        ListLogsCommand expectedCommand = new ListLogsCommand(identityNumber);
        assertParseSuccess(parser, "i/S1234567D", expectedCommand);

        // multiple whitespaces between arguments
        assertParseSuccess(parser, "  i/S1234567D  ", expectedCommand);
    }
    */
}