package seedu.address.model.travelplan.exceptions;

import seedu.address.commons.exceptions.DuplicateException;

/**
 * Signals that the operation will result in duplicate TravelPlans (TravelPlans are considered duplicates if they have
 * the same
 * identity).
 */
public class DuplicateTravelPlanException extends DuplicateException {
    public DuplicateTravelPlanException() {
        super("Operation would result in duplicate travel plans.");
    }
}
