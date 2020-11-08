package seedu.address.logic.command;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.TravelPlanner;

/**
 * Clears the entire travel planner including wishlist and travel plans.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Wanderlust has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTravelPlanner(new TravelPlanner());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
