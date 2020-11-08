package seedu.address.model.travelplan;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.friend.Friend;
import seedu.address.model.friend.UniqueFriendList;

/**
 * Represents the list of friends in a travel plan
 * Duplicates are not allowed (by .isSameFriend comparison)
 */
public class FriendList {

    public static final ObservableList<Friend> EMPTY_FRIEND_LIST = new FriendList().getObservableFriendList();
    private final UniqueFriendList friends;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        friends = new UniqueFriendList();
    }

    public FriendList() {}

    /**
     * Creates an seedu.address.model.FriendList using the Friends in the {@code toBeCopied}
     */
    public FriendList(FriendList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the friend list with {@code friends}.
     * {@code friends} must not contain duplicate friends.
     */
    public void setFriends(List<Friend> friends) {
        this.friends.setFriends(friends);
    }

    /**
     * Resets the existing data of this {@code seedu.address.model.FriendList} with {@code newData}.
     */
    public void resetData(FriendList newData) {
        requireNonNull(newData);

        setFriends(newData.getObservableFriendList());
    }

    //// friend-level operations

    /**
     * Returns true if a friend with the same identity as {@code friend} exists in the friend list.
     */
    public boolean hasFriend(Friend friend) {
        requireNonNull(friend);
        return friends.contains(friend);
    }

    /**
     * Adds a friend to the friend list.
     * The friend must not already exist in the friend list.
     */
    public void addFriend(Friend p) {
        friends.add(p);
    }

    /**
     * Replaces the given friend {@code target} in the list with {@code editedFriend}.
     * {@code target} must exist in the friend list.
     * The friend identity of {@code editedFriend} must not be the same as another existing friend in the friend list.
     */
    public void setFriend(Friend target, Friend editedFriend) {
        requireNonNull(editedFriend);

        friends.setFriend(target, editedFriend);
    }

    /**
     * Removes {@code key} from this {@code seedu.address.model.FriendList}.
     * {@code key} must exist in the friend list.
     */
    public void removeFriend(Friend key) {
        friends.remove(key);
    }

    //// util methods

    /**
     * Sorts the list of friends according to the comparator.
     * @param comparator Comparator to sort the list of friends with.
     */
    public void sort(Comparator<Friend> comparator) {
        friends.sort(comparator);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Friends: \n");
        getObservableFriendList().forEach(friend -> builder.append(friend).append("\n"));
        return builder.toString();
    }

    /**
     * Returns the friend list as an {@ObservableList}.
     */
    public ObservableList<Friend> getObservableFriendList() {
        return friends.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FriendList // instanceof handles nulls
                && friends.equals(((FriendList) other).friends));
    }

    @Override
    public int hashCode() {
        return friends.hashCode();
    }
}
