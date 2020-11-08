package seedu.address.model.friend.exceptions;

import seedu.address.commons.exceptions.DuplicateException;

/**
 * Signals that the operation will result in duplicate Friends (Friends are considered duplicates if they have the same
 * passport).
 */
public class DuplicateFriendException extends DuplicateException {
    public DuplicateFriendException() {
        super("This operation will result in duplicate friends.");
    }
}
