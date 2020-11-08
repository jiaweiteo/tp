package seedu.address.logic.command.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOBILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSPORT;
import static seedu.address.logic.parser.ParserUtil.FRIEND_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Name;
import seedu.address.model.friend.Friend;
import seedu.address.model.friend.Mobile;
import seedu.address.model.friend.Passport;

/**
 * Edits existing Friend in wanderlust. This command can only be used within the travel plan directory.
 * A friend contains the parameters: name, passport, mobile
 */
public class EditFriendCommand extends EditCommand {

    public static final String COMMAND_WORD = "friend";

    public static final String MESSAGE_FORMAT = "Edit a friend in the current travel plan "
            + "by its index in the displayed friend list using the format:\n"
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " INDEX "
            + PREFIX_NAME + "NAME "
            + PREFIX_MOBILE + "MOBILE_NUMBER "
            + PREFIX_PASSPORT + "PASSPORT_NUMBER ";

    public static final String MESSAGE_EXAMPLE = "Example: "
            + EditCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MOBILE + "91234567 "
            + PREFIX_PASSPORT + "E1234567K";

    public static final String MESSAGE_USAGE = MESSAGE_FORMAT + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_EDIT_FRIEND_SUCCESS = "Edited Friend: %1$s";

    private final Index targetIndex;
    private final EditDescriptor editFriendDescriptor;

    /**
     * Constructor for edit friend command
     */
    public EditFriendCommand(Index targetIndex, EditDescriptor editFriendDescriptor) {
        super(targetIndex);

        assert !editFriendDescriptor.wrongFieldEdited(COMMAND_WORD);

        this.targetIndex = targetIndex;
        this.editFriendDescriptor = editFriendDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isTravelPlan = model.isDirectoryTypeTravelPlan();

        if (!isTravelPlan) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST);
        }

        List<Friend> filteredFriendList = model.getFilteredFriendList();

        if (targetIndex.getZeroBased() >= filteredFriendList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FRIEND_DISPLAYED_INDEX);
        }

        Friend friendToEdit = filteredFriendList.get(targetIndex.getZeroBased());
        Friend editedFriend = createEditedFriend(friendToEdit, editFriendDescriptor);

        if (!friendToEdit.isSameFriend(editedFriend) && model.hasTravelPlanObject(editedFriend)) {
            throw new CommandException(MESSAGE_DUPLICATE_FRIEND);
        }

        model.setTravelPlanObject(friendToEdit, editedFriend);
        assert model.hasTravelPlanObject(editedFriend);
        return new CommandResult(String.format(MESSAGE_EDIT_FRIEND_SUCCESS, editedFriend), FRIEND_INDEX);
    }

    /**
     * Creates and returns a {@code Friend} with the details of {@code friendToEdit}
     *
     * @param friendToEdit contains the old fields
     * @param editFriendDescriptor contains updated fields
     * @return Friend to be updated in the friend list
     */
    private static Friend createEditedFriend(Friend friendToEdit, EditDescriptor editFriendDescriptor) {
        assert friendToEdit != null;

        Name updatedName = editFriendDescriptor.getName().orElse(friendToEdit.getName());

        Passport updatedPassport = editFriendDescriptor.getPassport().orElse(friendToEdit.getPassport());
        Mobile updatedMobile = editFriendDescriptor.getMobile().orElse(friendToEdit.getMobile());

        return new Friend(updatedName, updatedPassport, updatedMobile);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && targetIndex.equals(((EditFriendCommand) other).targetIndex)) // state check
                && (editFriendDescriptor.equals(((EditFriendCommand) other).editFriendDescriptor)
                || editFriendDescriptor.isSameDescriptor(((EditFriendCommand) other).editFriendDescriptor));
    }


}
