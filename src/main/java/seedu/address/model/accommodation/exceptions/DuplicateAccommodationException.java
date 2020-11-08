package seedu.address.model.accommodation.exceptions;

import seedu.address.commons.exceptions.DuplicateException;

/**
 * Signals that the operation will result in duplicate Accommodations
 * Accommodations are considered duplicates if they have the same identity.
 */
public class DuplicateAccommodationException extends DuplicateException {
    public DuplicateAccommodationException() {
        super("Operation would result in duplicate accommodations.");
    }
}
