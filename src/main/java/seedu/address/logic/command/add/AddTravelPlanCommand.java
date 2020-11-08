package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TRAVELPLAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.travelplan.TravelPlan;

public class AddTravelPlanCommand extends AddCommand {

    public static final String COMMAND_WORD = "travelplan";

    public static final String MESSAGE_FORMAT = "Add a travel plan to the travel planner using the format:\n"
            + AddCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_START + WanderlustDate.FORMAT + " "
            + PREFIX_END + WanderlustDate.FORMAT;

    public static final String MESSAGE_EXAMPLE = "Example: "
            + AddCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " "
            + PREFIX_NAME + " France "
            + PREFIX_START + "2021-09-15 "
            + PREFIX_END + "2021-09-30 ";

    public static final String MESSAGE_USAGE = MESSAGE_FORMAT + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New travel plan added: %1$s";

    private final TravelPlan toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddTravelPlanCommand(TravelPlan travelPlan) {
        requireNonNull(travelPlan);
        toAdd = travelPlan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTravelPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRAVELPLAN);
        }

        model.addTravelPlan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTravelPlanCommand // instanceof handles nulls
                && toAdd.equals(((AddTravelPlanCommand) other).toAdd));
    }
}
