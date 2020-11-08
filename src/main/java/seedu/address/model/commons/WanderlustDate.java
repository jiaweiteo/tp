package seedu.address.model.commons;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Represents a TravelPlan's start/end date in the travel planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidWanderlustDate(String)}
 */
public class WanderlustDate {

    public static final String FORMAT = "YYYY-MM-DD";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format " + FORMAT;

    /**
     * Dates must be in the format YYYY-MM-DD.
     */
    public static final String VALIDATION_REGEX = "\\d{4}-[01]\\d-[0-3]\\d";
    public static final DateFormat VALID_DATE_STRING = new SimpleDateFormat("yyyy-MM-dd");

    private final String date;
    private final LocalDate value;

    /**
     * Constructs a {@code WanderlustDate}.
     *
     * @param date A valid travel date.
     */
    public WanderlustDate(String date) {
        requireNonNull(date);
        checkArgument(isValidWanderlustDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
        value = LocalDate.parse(date);
    }

    /**
     * Returns if a given string is a valid travel date.
     */
    public static boolean isValidWanderlustDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            VALID_DATE_STRING.parse(test);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Checks if the current WanderlustDate comes before the given WanderlustDate chronologically.
     * @return true if current WanderlustDate is before the given WanderlustDate.
     */
    public boolean isBefore(WanderlustDate toCompare) {
        return value.isBefore(toCompare.getValue());
    }

    /**
     * Checks if the current WanderlustDate comes after the given WanderlustDate chronologically.
     * @return true if current WanderlustDate is after the given WanderlustDate.
     */
    public boolean isAfter(WanderlustDate toCompare) {
        return value.isAfter(toCompare.getValue());
    }

    /**
     * Returns true if the start date is before or on the same day as end date.
     */
    public static boolean isValidStartAndEndDate(WanderlustDate startDate, WanderlustDate endDate) {
        return startDate.getValue().compareTo(endDate.getValue()) <= 0;
    }

    /**
     * Return number of days between {@code startDate} and {@code endDate} inclusive.
     * @return Number of days between {@code startDate} and {@code endDate} inclusive.
     */
    public static long getNumOfDays(WanderlustDate startDate, WanderlustDate endDate) {
        return ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue()) + 1;
    }

    /**
     * Returns number of days and nights between the start and end date in a String format (e.g. "2D1N").
     *
     * @return Number of days (x) and nights (y) between the start and end date in the "xDyN" format.
     */
    public static String getNumOfDaysAndNights(WanderlustDate startDate, WanderlustDate endDate) {
        long numOfDays = getNumOfDays(startDate, endDate);
        return String.format("%dD%dN", numOfDays, numOfDays - 1);
    }


    // travel plan object-level operations

    /**
     * Returns this date as a LocalDate object.
     */
    public LocalDate getValue() {
        return value;
    }

    /**
     * Returns this date as a string.
     */
    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WanderlustDate // instanceof handles nulls
                && value.equals(((WanderlustDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
