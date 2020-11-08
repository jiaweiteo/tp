package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.commons.Cost;
import seedu.address.model.commons.Location;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.TravelPlanObject;

/**
 * Represents an Activity in Wanderlust.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Activity extends TravelPlanObject {

    public static final String TPO_WORD = "activity";

    //Identity fields
    private final Name name;

    //Data fields
    private final Location location;
    private final Cost cost;
    private final Importance levelOfImportance;
    private final WanderlustDateTime activityDateTime;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Location location, Cost cost, Importance levelOfImportance,
                    WanderlustDateTime activityDateTime) {
        requireAllNonNull(name, location, cost, levelOfImportance, activityDateTime);
        this.name = name;
        this.location = location;
        this.cost = cost;
        this.levelOfImportance = levelOfImportance;
        this.activityDateTime = activityDateTime;
    }

    /**
     * Creates a deep copy of the given activity.
     */
    public Activity(Activity activity) {
        this.name = activity.getName();
        this.location = activity.getLocation();
        this.cost = activity.getCost();
        this.levelOfImportance = activity.getLevelOfImportance();
        this.activityDateTime = activity.getActivityDateTime();
    }

    @Override
    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Cost getCost() {
        return cost;
    }

    /**
     * Returns cost as string.
     * @return String value.
     */
    public String getCostAsString() {
        return cost.getValue();
    }

    /**
     * Returns cost as an integer.
     * @return int value.
     */
    public int getCostAsInt() {
        return Integer.parseInt(cost.getValue());
    }


    public Importance getLevelOfImportance() {
        return levelOfImportance;
    }

    /**
     * Returns importance as an integer.
     * @return int value.
     */
    public int getImportanceAsInt() {
        return Integer.parseInt(levelOfImportance.getValue());
    }

    public WanderlustDateTime getActivityDateTime() {
        return activityDateTime;
    }

    /**
     * Returns true if both activities are of the same name.
     * This defines a weaker notion of equality between two activities.
     */
    public boolean isSameActivity(Activity otherActivity) {
        if (otherActivity == this) {
            return true;
        }

        return otherActivity != null
                && otherActivity.getName().equals(getName())
                && otherActivity.getLocation().equals(getLocation())
                && otherActivity.getActivityDateTime().equals(getActivityDateTime());
    }

    /**
     * Returns true if both activities have the same identity and data fields.
     * This defines a stronger notion of equality between two activities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.getName().equals(getName())
                && otherActivity.getLocation().equals(getLocation())
                && otherActivity.getCost().equals(getCost())
                && otherActivity.getLevelOfImportance().equals(getLevelOfImportance())
                && otherActivity.getActivityDateTime().equals(getActivityDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, cost, levelOfImportance, activityDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n")
                .append("Location: ").append(getLocation()).append("\n")
                .append("Importance Level: ").append(getLevelOfImportance()).append("\n")
                .append("Cost: ").append(getCost()).append("\n")
                .append("Date and time: ").append(getActivityDateTime()).append("\n");

        return builder.toString();
    }

}
