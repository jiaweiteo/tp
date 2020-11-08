package seedu.address.model.accommodation;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STARTANDENDDATE;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.commons.WanderlustDate.getNumOfDaysAndNights;
import static seedu.address.model.commons.WanderlustDate.isValidStartAndEndDate;

import java.util.Objects;

import seedu.address.model.commons.Cost;
import seedu.address.model.commons.Location;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;

/**
 * Represents an Accommodation in the travel plan.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Accommodation extends TravelPlanObject {

    public static final String TPO_WORD = "accommodation";

    // identity fields
    private final Name name;
    private final WanderlustDate startDate;
    private final WanderlustDate endDate;

    // attributes
    private final Cost cost;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Accommodation(Name name, WanderlustDate startDate, WanderlustDate endDate, Cost cost, Location location) {
        requireAllNonNull(name, startDate, endDate, cost, location);
        checkArgument(isValidStartAndEndDate(startDate, endDate), MESSAGE_INVALID_STARTANDENDDATE);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.location = location;
    }

    @Override
    public Name getName() {
        return name;
    }

    public WanderlustDate getStartDate() {
        return startDate;
    }

    public WanderlustDate getEndDate() {
        return endDate;
    }

    public Cost getCost() {
        return cost;
    }

    public int getCostAsInt() {
        return Integer.parseInt(cost.getValue());
    }

    public String getCostAsString() {
        return cost.getValue();
    }

    public Location getLocation() {
        return location;
    }

    /** Returns true if both accommodations have the same name, startDate and endDate fields. */
    public boolean isSameAccommodation(Accommodation otherAccommodation) {
        if (otherAccommodation == this) {
            return true;
        }

        return otherAccommodation != null
                && otherAccommodation.getName().equals(getName())
                && otherAccommodation.getStartDate().equals(getStartDate())
                && otherAccommodation.getEndDate().equals(getEndDate());
    }

    /**
     * Returns a string representing the start and end date of the accommodation.
     * E.g. 20 Dec 2020 to 25 Dec 2020 (6D5N).
     * @return
     */
    public String dateString() {
        return getStartDate().toString() + " to " + getEndDate().toString() + " ("
                + getNumOfDaysAndNights(startDate, endDate) + ")";
    }

    /**
     * Returns true if both accommodations have identical fields.
     * This defines a stronger notion of equality between two accommodations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Accommodation)) {
            return false;
        }

        Accommodation otherAccommodation = (Accommodation) other;
        return otherAccommodation.getName().equals(getName())
                && otherAccommodation.getStartDate().equals(getStartDate())
                && otherAccommodation.getEndDate().equals(getEndDate())
                && otherAccommodation.getCost().equals(getCost())
                && otherAccommodation.getLocation().equals(getLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, endDate, cost, location);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n")
                .append("Date: ")
                .append(getStartDate())
                .append(" - ")
                .append(getEndDate()).append("\n")
                .append("Address: ")
                .append(getLocation()).append("\n")
                .append("Cost: ")
                .append(getCost()).append("\n");
        return builder.toString();
    }

}
