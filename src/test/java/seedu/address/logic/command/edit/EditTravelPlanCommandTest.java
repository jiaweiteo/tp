package seedu.address.logic.command.edit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TRAVELPLAN;
import static seedu.address.logic.command.CommandTestUtil.DESC_EUROPE;
import static seedu.address.logic.command.CommandTestUtil.DESC_NYC;
import static seedu.address.logic.command.CommandTestUtil.VALID_NAME_NYC;
import static seedu.address.logic.command.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.command.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.command.edit.EditTravelPlanCommand.MESSAGE_EDIT_TRAVELPLAN_SUCCESS;
import static seedu.address.testutil.typicals.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.typicals.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.typicals.TypicalTravelPlans.AUSTRALIA_TRIP;
import static seedu.address.testutil.typicals.TypicalTravelPlans.getTypicalTravelPlanner;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.command.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TravelPlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.travelplan.TravelPlan;
import seedu.address.testutil.EditTravelPlanDescriptorBuilder;
import seedu.address.testutil.builders.TravelPlanBuilder;

public class EditTravelPlanCommandTest {

    private Model model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        TravelPlan editedTravelPlan = new TravelPlanBuilder(AUSTRALIA_TRIP).build();
        EditDescriptor descriptor = new EditTravelPlanDescriptorBuilder(editedTravelPlan).build();
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_TRAVELPLAN_SUCCESS, editedTravelPlan);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setTravelPlan(model.getFilteredTravelPlanList().get(0), editedTravelPlan);

        assertCommandSuccess(editTravelPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(INDEX_FIRST, new EditDescriptor());
        TravelPlan editedTravelPlan = model.getFilteredTravelPlanList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_TRAVELPLAN_SUCCESS, editedTravelPlan);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());

        assertCommandSuccess(editTravelPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

        TravelPlan travelPlanInFilteredList = model.getFilteredTravelPlanList().get(INDEX_FIRST.getZeroBased());
        TravelPlan editedTravelPlan = new TravelPlanBuilder(travelPlanInFilteredList).withName(VALID_NAME_NYC).build();
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(INDEX_FIRST,
                new EditTravelPlanDescriptorBuilder().withName(VALID_NAME_NYC).build());

        String expectedMessage = String.format(MESSAGE_EDIT_TRAVELPLAN_SUCCESS, editedTravelPlan);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setTravelPlan(model.getFilteredTravelPlanList().get(0), editedTravelPlan);

        assertCommandSuccess(editTravelPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTravelPlanUnfilteredList_failure() {
        TravelPlan firstTravelPlan = model.getFilteredTravelPlanList().get(INDEX_FIRST.getZeroBased());
        EditDescriptor descriptor = new EditTravelPlanDescriptorBuilder(firstTravelPlan).build();
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editTravelPlanCommand, model, MESSAGE_DUPLICATE_TRAVELPLAN);
    }

    @Test
    public void execute_duplicateTravelPlanFilteredList_failure() {

        // edit TravelPlan in filtered list into a duplicate in wanderlust
        TravelPlan travelPlanInList = model.getFilteredTravelPlanList().get(INDEX_SECOND.getZeroBased());
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(INDEX_FIRST,
                new EditTravelPlanDescriptorBuilder(travelPlanInList).build());

        assertCommandFailure(editTravelPlanCommand, model, MESSAGE_DUPLICATE_TRAVELPLAN);
    }

    @Test
    public void execute_invalidTravelPlanIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTravelPlanList().size() + 1);
        EditDescriptor descriptor = new EditTravelPlanDescriptorBuilder().withName(VALID_NAME_NYC).build();
        EditTravelPlanCommand editTravelPlanCommand = new EditTravelPlanCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTravelPlanCommand, model, Messages.MESSAGE_INVALID_TRAVELPLAN_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditTravelPlanCommand standardCommand = new EditTravelPlanCommand(INDEX_FIRST, DESC_EUROPE);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_EUROPE);
        EditTravelPlanCommand commandWithSameValues = new EditTravelPlanCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTravelPlanCommand(INDEX_SECOND, DESC_EUROPE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTravelPlanCommand(INDEX_FIRST, DESC_NYC)));
    }
}
