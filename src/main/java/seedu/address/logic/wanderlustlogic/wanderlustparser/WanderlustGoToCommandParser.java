package seedu.address.logic.wanderlustlogic.wanderlustparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.wanderlustlogic.wanderlustcommands.GoToCommand;
import seedu.address.logic.wanderlustlogic.wanderlustparser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class WanderlustGoToCommandParser implements WanderlustParserInterface<GoToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCommand
     * and returns a GoToCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform
     * the expected format.
     */
    public GoToCommand parse(String args) throws ParseException {
        try {
            String[] keywords = args.split(" ");
            String directoryType = keywords[1].substring(1);

            switch (directoryType) {
            case GoToCommand.TRAVEL_PLAN:
                Index index = ParserUtil.parseIndex(keywords[2]);
                return new GoToCommand(index, true);

            case GoToCommand.WISHLIST:
                return new GoToCommand(ParserUtil.parseIndex("1000"), false);

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE), pe);
        }
    }
}