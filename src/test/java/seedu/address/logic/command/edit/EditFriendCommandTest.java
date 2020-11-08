package seedu.address.logic.command.edit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FRIEND;
import static seedu.address.logic.command.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.command.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.command.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.command.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.command.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.command.edit.EditFriendCommand.MESSAGE_EDIT_FRIEND_SUCCESS;
import static seedu.address.logic.parser.ParserUtil.FRIEND_INDEX;
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
import seedu.address.model.friend.Friend;
import seedu.address.testutil.EditFriendDescriptorBuilder;
import seedu.address.testutil.builders.FriendBuilder;

public class EditFriendCommandTest {
    private Model model;

    @BeforeEach
    public void setup() {
        //inside a travelplan
        model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());
        model.setDirectory(1);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Friend editedFriend = new FriendBuilder().build();
        EditDescriptor descriptor = new EditFriendDescriptorBuilder(editedFriend).build();
        EditFriendCommand editFriendCommand = new EditFriendCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(MESSAGE_EDIT_FRIEND_SUCCESS, editedFriend);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(1);
        expectedModel.setTravelPlanObject(model.getFilteredFriendList().get(0), editedFriend);

        assertCommandSuccess(editFriendCommand, model, expectedMessage, expectedModel, FRIEND_INDEX);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditFriendCommand editFriendCommand = new EditFriendCommand(INDEX_FIRST, new EditDescriptor());
        Friend editedFriend = model.getFilteredFriendList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(MESSAGE_EDIT_FRIEND_SUCCESS, editedFriend);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());

        assertCommandSuccess(editFriendCommand, model, expectedMessage, expectedModel, FRIEND_INDEX);
    }

    @Test
    public void execute_filteredList_success() {

        Friend friendInFilteredList = model.getFilteredFriendList().get(INDEX_FIRST.getZeroBased());
        Friend editedFriend = new FriendBuilder(friendInFilteredList).withName(VALID_NAME_BOB).build();
        EditFriendCommand editFriendCommand = new EditFriendCommand(INDEX_FIRST,
                new EditFriendDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(MESSAGE_EDIT_FRIEND_SUCCESS, editedFriend);

        ModelManager expectedModel = new ModelManager(new TravelPlanner(model.getTravelPlanner()), new UserPrefs());
        expectedModel.setDirectory(1);

        expectedModel.setTravelPlanObject(model.getFilteredFriendList().get(0), editedFriend);

        assertCommandSuccess(editFriendCommand, model, expectedMessage, expectedModel, FRIEND_INDEX);
    }

    @Test
    public void execute_duplicateFriendUnfilteredList_failure() {
        Friend firstFriend = model.getFilteredFriendList().get(INDEX_FIRST.getZeroBased());
        EditDescriptor descriptor = new EditFriendDescriptorBuilder(firstFriend).build();
        EditFriendCommand editFriendCommand = new EditFriendCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editFriendCommand, model, MESSAGE_DUPLICATE_FRIEND);
    }

    @Test
    public void execute_duplicateFriendFilteredList_failure() {

        // edit Friend in filtered list into a duplicate in address book
        Friend friendInList = model.getFilteredFriendList().get(INDEX_SECOND.getZeroBased());
        EditFriendCommand editFriendCommand = new EditFriendCommand(INDEX_FIRST,
                new EditFriendDescriptorBuilder(friendInList).build());

        assertCommandFailure(editFriendCommand, model, MESSAGE_DUPLICATE_FRIEND);
    }

    @Test
    public void execute_invalidFriendIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFriendList().size() + 1);
        EditDescriptor descriptor = new EditFriendDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditFriendCommand editFriendCommand = new EditFriendCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editFriendCommand, model, Messages.MESSAGE_INVALID_FRIEND_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditFriendCommand standardCommand = new EditFriendCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_AMY);
        EditFriendCommand commandWithSameValues = new EditFriendCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditFriendCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditFriendCommand(INDEX_FIRST, DESC_BOB)));
    }
}
