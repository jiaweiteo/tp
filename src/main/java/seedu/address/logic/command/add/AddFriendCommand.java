package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOBILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSPORT;
import static seedu.address.logic.parser.ParserUtil.FRIEND_INDEX;

import seedu.address.commons.core.Messages;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.friend.Friend;

public class AddFriendCommand extends AddCommand {

    public static final String COMMAND_WORD = "friend";

    public static final String MESSAGE_FORMAT =
            "Add a friend to the current travel plan using the format:\n"
            + AddCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_MOBILE + "MOBILE_NUMBER "
            + PREFIX_PASSPORT + "PASSPORT_NUMBER ";

    public static final String MESSAGE_EXAMPLE = "Example: "
            + AddCommand.COMMAND_WORD + COMMAND_SEPARATOR + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MOBILE + "91234567 "
            + PREFIX_PASSPORT + "E1234567K";

    public static final String MESSAGE_USAGE = MESSAGE_FORMAT + "\n" + MESSAGE_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New friend added: %1$s";

    private final Friend toAdd;

    /**
     * Creates an AddActivityCommand to add the specified {@code Activity}
     */
    public AddFriendCommand(Friend friend) {
        requireNonNull(friend);
        toAdd = friend;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isTravelPlan = model.isDirectoryTypeTravelPlan();

        if (!isTravelPlan) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST);
        }

        if (model.hasTravelPlanObject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FRIEND);
        }

        model.addTravelPlanObject(toAdd);
        assert model.getFriendList().hasFriend(toAdd) : "Friend was not added";

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), FRIEND_INDEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && toAdd.equals(((AddFriendCommand) other).toAdd));
    }
}
