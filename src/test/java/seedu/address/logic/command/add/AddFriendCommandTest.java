package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FRIEND;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.TravelPlanner;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.friend.Friend;
import seedu.address.model.travelplan.FriendList;
import seedu.address.testutil.builders.FriendBuilder;
import seedu.address.testutil.typicals.TypicalFriends;


public class AddFriendCommandTest {

    @Test
    public void constructor_nullFriend_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFriendCommand(null));
    }

    @Test
    public void execute_friendAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFriendAdded modelStub = new ModelStubAcceptingFriendAdded();
        Friend validFriend = new FriendBuilder().build();

        CommandResult commandResult = new AddFriendCommand(validFriend).execute(modelStub);

        assertEquals(String.format(AddFriendCommand.MESSAGE_SUCCESS, validFriend), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFriend), modelStub.friendsAdded);
    }

    @Test
    public void execute_duplicateFriend_throwsCommandException() {
        Friend validFriend = new FriendBuilder().build();
        AddFriendCommand addFriendCommand = new AddFriendCommand(validFriend);
        ModelStub modelStub = new ModelStubWithActivity(validFriend);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_FRIEND, () ->
                addFriendCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        AddFriendCommand addAliceCommand = new AddFriendCommand(TypicalFriends.ALICE);
        AddFriendCommand addBensonCommand = new AddFriendCommand(TypicalFriends.BENSON);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddFriendCommand addAliceCommandCopy = new AddFriendCommand(TypicalFriends.ALICE);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different activity -> returns false
        assertFalse(addAliceCommand.equals(addBensonCommand));
    }

    /**
     * A Model stub that contains a single activity.
     */
    private class ModelStubWithActivity extends ModelStub {
        private final Friend friend;

        ModelStubWithActivity(Friend friend) {
            requireNonNull(friend);
            this.friend = friend;
        }

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Friend friend = (Friend) travelPlanObject;
            return this.friend.isSameFriend(friend);
        }
    }

    /**
     * A Model stub that always accept the friend being added.
     */
    private class ModelStubAcceptingFriendAdded extends ModelStub {
        final ArrayList<Friend> friendsAdded = new ArrayList<>();

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Friend friend = (Friend) travelPlanObject;
            return friendsAdded.stream().anyMatch(friend::isSameFriend);
        }

        @Override
        public void addTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Friend friend = (Friend) travelPlanObject;
            friendsAdded.add(friend);
        }

        @Override
        public FriendList getFriendList() {
            FriendList friendList = new FriendList();
            friendList.setFriends(friendsAdded);
            return friendList;
        }

        @Override
        public ReadOnlyTravelPlanner getTravelPlanner() {
            return new TravelPlanner();
        }
    }

}
