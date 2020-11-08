package seedu.address.model.activity.exceptions;

import seedu.address.commons.exceptions.DuplicateException;

/**
 * Signals that the operation will result in duplicate Activities
 * (Activities are considered duplicates if they have the same identity).
 */
public class DuplicateActivityException extends DuplicateException {
    public DuplicateActivityException() {
        super("Operation would result in duplicate activities.");
    }
}
