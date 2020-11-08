package seedu.address.logic.command.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_NOT_IN_RANGE_ACTIVITY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.ACTIVITY_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Importance;
import seedu.address.model.activity.WanderlustDateTime;
import seedu.address.model.commons.Cost;
import seedu.address.model.commons.Location;
import seedu.address.model.commons.Name;


/**
 * Edits an existing Activity in the address book and updates the travel plan/wishlist in the current directory
 * An activity contains the field name, location, cost, level of importance and date time
 */
public class EditActivityCommand extends EditCommand {
    public static final String COMMAND_WORD = "activity";

    public static final String MESSAGE_FORMAT = "Edit an activity in the current travel plan or "
            + "wishlist by its index in the displayed activity list using the format:\n"
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " INDEX "
            + PREFIX_NAME + "NAME "
            + PREFIX_IMPORTANCE + "LEVEL_OF_IMPORTANCE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_COST + "COST "
            + PREFIX_DATETIME + WanderlustDateTime.FORMAT;

    public static final String MESSAGE_EXAMPLE = "Example: "
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Universal Studios Singapore "
            + PREFIX_IMPORTANCE + "5 "
            + PREFIX_LOCATION + "Sentosa "
            + PREFIX_COST + "88 "
            + PREFIX_DATETIME + "2021-09-16 ";

    public static final String MESSAGE_USAGE = MESSAGE_FORMAT + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";


    private final Index targetIndex;
    private final EditDescriptor editActivityDescriptor;

    /**
     * Constructor for edit activity command
     */
    public EditActivityCommand(Index targetIndex, EditDescriptor editActivityDescriptor) {
        super(targetIndex);

        assert !editActivityDescriptor.wrongFieldEdited(COMMAND_WORD);

        this.targetIndex = targetIndex;
        this.editActivityDescriptor = editActivityDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        boolean isTravelPlan = model.isDirectoryTypeTravelPlan();

        List<Activity> lastShownList = model.getFilteredActivityList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToEdit = lastShownList.get(targetIndex.getZeroBased());
        Activity editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor, model);

        if (isTravelPlan) {
            if (!activityToEdit.isSameActivity(editedActivity) && model.hasTravelPlanObject(editedActivity)) {
                throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            model.setTravelPlanObject(activityToEdit, editedActivity);
            assert model.hasTravelPlanObject(editedActivity);
        } else {
            if (!activityToEdit.isSameActivity(editedActivity) && model.hasActivity(editedActivity)) {
                throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            model.setActivity(activityToEdit, editedActivity);
            assert model.hasActivity(editedActivity);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity), ACTIVITY_INDEX);

    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     *
     * @param activityToEdit contains the old fields
     * @param editActivityDescriptor contains updated fields
     * @return Activity to be updated in the activity list in current directory
     */
    private static Activity createEditedActivity(Activity activityToEdit, EditDescriptor editActivityDescriptor,
                                                 Model model) throws CommandException {
        assert activityToEdit != null;

        Name updatedName = editActivityDescriptor.getName().orElse(activityToEdit.getName());

        Location updatedLocation = editActivityDescriptor.getLocation().orElse(activityToEdit.getLocation());
        Cost updatedCost = editActivityDescriptor.getCost().orElse(activityToEdit.getCost());
        Importance updatedLevelOfImportance = editActivityDescriptor.getLevelOfImportance()
                .orElse(activityToEdit.getLevelOfImportance());
        WanderlustDateTime updatedActivityDateTime = editActivityDescriptor.getActivityDateTime()
                .orElse(activityToEdit.getActivityDateTime());

        boolean isTravelPlan = model.isDirectoryTypeTravelPlan();

        if (isTravelPlan) {
            boolean isDateInTravelPlanDate = model.isValidActivityDate(updatedActivityDateTime);

            if (!isDateInTravelPlanDate) {
                throw new CommandException(MESSAGE_DATE_NOT_IN_RANGE_ACTIVITY);
            }
        }

        return new Activity(updatedName, updatedLocation, updatedCost, updatedLevelOfImportance,
                updatedActivityDateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && targetIndex.equals(((EditActivityCommand) other).targetIndex)) // state check
                && (editActivityDescriptor.equals(((EditActivityCommand) other).editActivityDescriptor)
                || editActivityDescriptor.isSameDescriptor(((EditActivityCommand) other).editActivityDescriptor));
    }

}
