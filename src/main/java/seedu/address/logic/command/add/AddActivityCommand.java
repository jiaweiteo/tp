package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.ACTIVITY_INDEX;
import static seedu.address.model.activity.Activity.MESSAGE_DUPLICATE_ACTIVITY;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

public class AddActivityCommand extends AddCommand {
    public static final String COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = AddCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds an activity to the current travel plan or wishlist\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_IMPORTANCE + "LEVEL_OF_IMPORTANCE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_COST + "COST "
            + PREFIX_DATETIME + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Universal Studios Singapore "
            + PREFIX_IMPORTANCE + "5 "
            + PREFIX_LOCATION + "Sentosa "
            + PREFIX_COST + "88 "
            + PREFIX_DATETIME + "2020-09-16 ";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";

    private final Activity toAdd;

    /**
     * Creates an AddActivityCommand to add the specified {@code Activity}
     */
    public AddActivityCommand(Activity activity) {
        requireNonNull(activity);
        toAdd = activity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isTravelPlan = model.isDirectoryTypeTravelPlan();
        if (isTravelPlan) {
            if (model.hasTravelPlanObject(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
            }

            model.addTravelPlanObject(toAdd);
            assert model.getActivityList().hasActivity(toAdd) : "Activity was not added";

        } else {
            if (model.hasActivity(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            model.addActivity(toAdd);
            assert model.getWishlist().hasActivity(toAdd) : "Activity was not added";

        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ACTIVITY_INDEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddActivityCommand // instanceof handles nulls
                && toAdd.equals(((AddActivityCommand) other).toAdd));
    }
}
