package seedu.address.logic.command.sort;

import seedu.address.logic.command.Command;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.ShowCommand;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;

public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String KEYWORD_NAME = "name";
    public static final String KEYWORD_COST = "cost";
    public static final String KEYWORD_IMPORTANCE = "importance";
    public static final String KEYWORD_DATE = "date";
    public static final String KEYWORD_DATETIME = "datetime";
    public static final String KEYWORD_PASSPORT = "passport";
    public static final String KEYWORD_MOBILE = "mobile";

    public static final int COMMAND_TOKENS = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the respective object type list based on the keyword input.\n"
            + "Object Type: -activity\n"
            + "             -accommodation\n"
            + "             -friend\n"
            + "Sorting Keyword: " + KEYWORD_NAME + ", " + KEYWORD_COST + ", " + KEYWORD_IMPORTANCE + ", " + KEYWORD_DATE
            + ", " + KEYWORD_DATETIME + ", " + KEYWORD_MOBILE + ", " + KEYWORD_PASSPORT + "\n"
            + "Example: " + COMMAND_WORD + " -activity cost";

    private final String sortKeyword;

    /**
     * Constructor for ShowCommand.
     * @param sortKeyword string of the keyword used to sort.
     */
    public SortCommand(String sortKeyword) {
        this.sortKeyword = sortKeyword;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && sortKeyword.equals(((SortCommand) other).sortKeyword));
    }
}
