package seedu.address.model.wishlist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.Directory;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.ReadOnlyActivityList;
import seedu.address.model.commons.WanderlustDate;

/**
 * Wraps all data at the travel plan level
 * Duplicates are not allowed (by .isSameTravelPlan comparison)
 */
public class Wishlist extends Directory implements ReadOnlyActivityList {
    private static final String NAME = "Wishlist";

    private final UniqueActivityList activities;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        activities = new UniqueActivityList();
    }

    public Wishlist() {}

    /**
     * Creates an Wishlist using the Activitys in the {@code toBeCopied}
     */
    public Wishlist(ReadOnlyActivityList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Returns false for wishlist
     */
    @Override
    public boolean isTravelPlan() {
        return false;
    }

    public Name getName() {
        return new Name(NAME);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the activity list with {@code activities}.
     * {@code activities} must not contain duplicate activities.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    /**
     * Resets the existing data of this {@code Wishlist} with {@code newData}.
     */
    public void resetData(ReadOnlyActivityList newData) {
        requireNonNull(newData);

        setActivities(newData.getActivityList());
    }

    //// activity-level operations

    /**
     * Returns true if an activity with the same identity as {@code activity} exists in the wishlist.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds an activity to the wishlist.
     * The activity must not already exist in the wishlist.
     */
    public void addActivity(Activity p) {
        activities.add(p);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the wishlist.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity in the
     * wishlist.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code Wishlist}.
     * {@code key} must exist in the wishlist.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    public String getTotalCost() {
        int totalCost = 0;
        for (Activity activity : activities) {
            totalCost += Integer.parseInt(activity.getCostAsString());
        }
        return Integer.toString(totalCost);
    }

    @Override
    public WanderlustDate getStartDate() {
        return null;
    }

    @Override
    public WanderlustDate getEndDate() {
        return null;
    }

    //// util methods

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Wishlist: ");
        getActivityList().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Wishlist // instanceof handles nulls
                && activities.equals(((Wishlist) other).activities));
    }

    @Override
    public int hashCode() {
        return activities.hashCode();
    }
}
