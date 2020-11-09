package seedu.address.logic.command.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TRAVELPLAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STARTANDENDDATE;
import static seedu.address.commons.core.Messages.MESSAGE_TPO_OUTSIDE_EDITED_TRAVELPLAN_DATE_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.model.commons.WanderlustDate.isValidStartAndEndDate;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.travelplan.AccommodationList;
import seedu.address.model.travelplan.ActivityList;
import seedu.address.model.travelplan.FriendList;
import seedu.address.model.travelplan.TravelPlan;


/**
 * Edits an existing TravelPlan in Wanderlust.
 * A travelplan contains the parameters: name, start date and end date
 */
public class EditTravelPlanCommand extends EditCommand {

    public static final String COMMAND_WORD = "travelplan";

    public static final String MESSAGE_FORMAT = "Edit a travel plan in the travel planner "
            + "by its index in the displayed travel plan list using the format:\n"
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " INDEX "
            + PREFIX_NAME + "NAME "
            + PREFIX_START + WanderlustDate.FORMAT + " "
            + PREFIX_END + WanderlustDate.FORMAT;

    public static final String MESSAGE_EXAMPLE = "Example: "
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " 1 "
            + PREFIX_NAME + " France "
            + PREFIX_START + "2021-09-15 "
            + PREFIX_END + "2021-09-30 ";

    public static final String MESSAGE_USAGE = MESSAGE_FORMAT + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_EDIT_TRAVELPLAN_SUCCESS = "Edited Travel Plan: %1$s";

    private final Index targetIndex;
    private final EditDescriptor editTravelPlanDescriptor;

    /**
     * Constructor for edit travel plan
     */
    public EditTravelPlanCommand(Index targetIndex, EditDescriptor editTravelPlanDescriptor) {
        super(targetIndex);

        assert !editTravelPlanDescriptor.wrongFieldEdited(COMMAND_WORD);

        this.targetIndex = targetIndex;
        this.editTravelPlanDescriptor = editTravelPlanDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TravelPlan> lastShownList = model.getFilteredTravelPlanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRAVELPLAN_DISPLAYED_INDEX);
        }

        TravelPlan travelPlanToEdit = lastShownList.get(targetIndex.getZeroBased());
        TravelPlan editedTravelPlan = createEditedTravelPlan(travelPlanToEdit, editTravelPlanDescriptor);

        if (!travelPlanToEdit.isSameTravelPlan(editedTravelPlan) && model.hasTravelPlan(editedTravelPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRAVELPLAN);
        }

        if (editedTravelPlan.hasEarlierDateObject() || editedTravelPlan.hasLaterDateObject()) {
            throw new CommandException(MESSAGE_TPO_OUTSIDE_EDITED_TRAVELPLAN_DATE_REGION);
        }

        model.setTravelPlan(travelPlanToEdit, editedTravelPlan);
        assert model.hasTravelPlan(editedTravelPlan);
        return new CommandResult(String.format(MESSAGE_EDIT_TRAVELPLAN_SUCCESS, editedTravelPlan));
    }

    /**
     * Creates and returns a {@code TravelPlan} with the details of {@code travelPlanToEdit}
     *
     * @param travelPlanToEdit contains the old fields
     * @param editTravelPlanDescriptor contains updated fields
     * @return TravelPlan to be updated in the travelplan list
     */
    private static TravelPlan createEditedTravelPlan(TravelPlan travelPlanToEdit,
                                                     EditDescriptor editTravelPlanDescriptor) throws CommandException {
        assert travelPlanToEdit != null;

        Name updatedName = editTravelPlanDescriptor.getName().orElse(travelPlanToEdit.getName());
        WanderlustDate updatedStartDate = editTravelPlanDescriptor.getStartDate()
                .orElse(travelPlanToEdit.getStartDate());
        WanderlustDate updatedEndDate = editTravelPlanDescriptor.getEndDate().orElse(travelPlanToEdit.getEndDate());

        boolean isValidDate = isValidStartAndEndDate(updatedStartDate, updatedEndDate);

        if (!isValidDate) {
            throw new CommandException(MESSAGE_INVALID_STARTANDENDDATE);
        }

        ActivityList activities = travelPlanToEdit.getActivityList();
        AccommodationList accommodations = travelPlanToEdit.getAccommodationList();
        FriendList friends = travelPlanToEdit.getFriendList();

        return new TravelPlan(updatedName, updatedStartDate, updatedEndDate, activities, accommodations, friends);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && targetIndex.equals(((EditTravelPlanCommand) other).targetIndex)) // state check
                && (editTravelPlanDescriptor.equals(((EditTravelPlanCommand) other).editTravelPlanDescriptor)
                || (editTravelPlanDescriptor.isSameDescriptor(((EditTravelPlanCommand) other)
                .editTravelPlanDescriptor)));
    }

}
