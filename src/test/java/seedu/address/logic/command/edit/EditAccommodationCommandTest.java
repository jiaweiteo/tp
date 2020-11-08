package seedu.address.logic.command.edit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ACCOMMODATION;
import static seedu.address.logic.command.CommandTestUtil.DESC_HOME;
import static seedu.address.logic.command.CommandTestUtil.DESC_INN;
import static seedu.address.logic.command.CommandTestUtil.VALID_NAME_INN;
import static seedu.address.logic.command.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.command.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.command.edit.EditAccommodationCommand.MESSAGE_EDIT_ACCOMMODATION_SUCCESS;
import static seedu.address.logic.parser.ParserUtil.ACCOMMODATION_INDEX;
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
import seedu.address.model.accommodation.Accommodation;
import seedu.address.testutil.EditAccommodationDescriptorBuilder;
import seedu.address.testutil.builders.AccommodationBuilder;


public class EditAccommodationCommandTest {

    private Model model;

    @BeforeEach
    public void setup() {
        //inside a travelplan
        model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());
        model.setDirectory(1);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Accommodation editedAccommodation = new AccommodationBuilder().build();
        EditDescriptor descriptor = new EditAccommodationDescriptorBuilder(editedAccommodation).build();
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_ACCOMMODATION_SUCCESS, editedAccommodation);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(1);
        expectedModel.setTravelPlanObject(model.getFilteredAccommodationList().get(0), editedAccommodation);

        assertCommandSuccess(editAccommodationCommand, model, expectedMessage, expectedModel, ACCOMMODATION_INDEX);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(INDEX_FIRST,
                new EditDescriptor());
        Accommodation editedAccommodation = model.getFilteredAccommodationList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_ACCOMMODATION_SUCCESS, editedAccommodation);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(1);

        assertCommandSuccess(editAccommodationCommand, model, expectedMessage, expectedModel, ACCOMMODATION_INDEX);
    }

    @Test
    public void execute_filteredList_success() {

        Accommodation accommodationInFilteredList = model.getFilteredAccommodationList()
                .get(INDEX_FIRST.getZeroBased());
        Accommodation editedAccommodation = new AccommodationBuilder(accommodationInFilteredList)
                .withName(VALID_NAME_INN).build();
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(INDEX_FIRST,
                new EditAccommodationDescriptorBuilder().withName(VALID_NAME_INN).build());

        String expectedMessage = String.format(MESSAGE_EDIT_ACCOMMODATION_SUCCESS, editedAccommodation);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(1);
        expectedModel.setTravelPlanObject(model.getFilteredAccommodationList().get(0), editedAccommodation);

        assertCommandSuccess(editAccommodationCommand, model, expectedMessage, expectedModel, ACCOMMODATION_INDEX);
    }

    @Test
    public void execute_duplicateAccommodationUnfilteredList_failure() {
        Accommodation firstAccommodation = model.getFilteredAccommodationList().get(INDEX_FIRST.getZeroBased());
        EditDescriptor descriptor = new EditAccommodationDescriptorBuilder(firstAccommodation).build();
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editAccommodationCommand, model, MESSAGE_DUPLICATE_ACCOMMODATION);
    }

    @Test
    public void execute_duplicateAccommodationFilteredList_failure() {

        // edit Accommodation in filtered list into a duplicate in address book
        Accommodation accommodationInList = model.getFilteredAccommodationList().get(INDEX_SECOND.getZeroBased());
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(INDEX_FIRST,
                new EditAccommodationDescriptorBuilder(accommodationInList).build());

        assertCommandFailure(editAccommodationCommand, model, MESSAGE_DUPLICATE_ACCOMMODATION);
    }

    @Test
    public void execute_invalidAccommodationIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAccommodationList().size() + 1);
        EditDescriptor descriptor = new EditAccommodationDescriptorBuilder().withName(VALID_NAME_INN).build();
        EditAccommodationCommand editAccommodationCommand = new EditAccommodationCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAccommodationCommand, model, Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditAccommodationCommand standardCommand = new EditAccommodationCommand(INDEX_FIRST, DESC_INN);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_INN);
        EditAccommodationCommand commandWithSameValues = new EditAccommodationCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAccommodationCommand(INDEX_SECOND, DESC_INN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAccommodationCommand(INDEX_FIRST, DESC_HOME)));
    }
}
