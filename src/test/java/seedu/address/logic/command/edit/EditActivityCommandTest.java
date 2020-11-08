package seedu.address.logic.command.edit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ACTIVITY;
import static seedu.address.logic.command.CommandTestUtil.DESC_SKI;
import static seedu.address.logic.command.CommandTestUtil.DESC_ZOO;
import static seedu.address.logic.command.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.command.CommandTestUtil.VALID_NAME_ZOO;
import static seedu.address.logic.command.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.command.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.command.edit.EditActivityCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS;
import static seedu.address.logic.parser.ParserUtil.ACTIVITY_INDEX;
import static seedu.address.testutil.typicals.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.typicals.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.typicals.TypicalTravelPlans.getTypicalTravelPlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.command.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TravelPlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.testutil.EditActivityDescriptorBuilder;
import seedu.address.testutil.builders.ActivityBuilder;

//temp handling within travelplan only, wishlist directory tests to be added
public class EditActivityCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());
        //inside a travelplan
        model.setDirectory(0);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Activity editedActivity = new ActivityBuilder().build();
        EditDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(0);

        expectedModel.setTravelPlanObject(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel, ACTIVITY_INDEX);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST, new EditDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(0);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel, ACTIVITY_INDEX);
    }

    @Test
    public void execute_filteredList_success() {

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        Activity editedActivity = new ActivityBuilder(activityInFilteredList).withName(VALID_NAME_AMY).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST,
                new EditActivityDescriptorBuilder().withName(VALID_NAME_AMY).build());

        String expectedMessage = String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(0);

        expectedModel.setTravelPlanObject(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editActivityCommand, model, expectedMessage, expectedModel, ACTIVITY_INDEX);
    }


    @Test
    public void execute_duplicateActivityUnfilteredList_failure() {
        model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());

        Activity firstActivity = model.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        EditDescriptor descriptor = new EditActivityDescriptorBuilder(firstActivity).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editActivityCommand, model, MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_duplicateActivityFilteredList_failure() {
        model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());

        // edit Activity in filtered list into a duplicate in address book
        Activity activityInList = model.getFilteredActivityList().get(INDEX_SECOND.getZeroBased());
        EditActivityCommand editActivityCommand = new EditActivityCommand(INDEX_FIRST,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertCommandFailure(editActivityCommand, model, MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_invalidActivityIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        EditDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_ZOO).build();
        EditActivityCommand editActivityCommand = new EditActivityCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editActivityCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditActivityCommand standardCommand = new EditActivityCommand(INDEX_FIRST, DESC_SKI);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_SKI);
        EditActivityCommand commandWithSameValues = new EditActivityCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_SECOND, DESC_SKI)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditActivityCommand(INDEX_FIRST, DESC_ZOO)));
    }
}
