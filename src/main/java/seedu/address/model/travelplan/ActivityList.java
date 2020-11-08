package seedu.address.model.travelplan;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;

/**
 * Represents the list of activities in a travel plan
 * Duplicates are not allowed (by .isSameTravelPlan comparison)
 */
public class ActivityList {

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

    public ActivityList() {}

    /**
     * Creates an ActivityList using the Activities in the {@code toBeCopied}
     */
    public ActivityList(ActivityList toBeCopied) {
        this();
        resetData(toBeCopied);
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
     * Resets the existing data of this {@code ActvityList} with {@code newData}.
     */
    public void resetData(ActivityList newData) {
        requireNonNull(newData);

        setActivities(newData.getObservableActivityList());
    }

    //// activity-level operations

    /**
     * Returns true if an activity with the same identity as {@code activity} exists in the activity list.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds an activity to the activity list.
     * The activity must not already exist in the activity list.
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the activity list.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity in the
     * activity list.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code ActivityList}.
     * {@code key} must exist in the activity list.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    //// util methods

    /**
     * Sorts the list of activities according to the comparator.
     * @param comparator Comparator to sort the list of activities with.
     */
    public void sort(Comparator<Activity> comparator) {
        activities.sort(comparator);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Activities: \n");
        getObservableActivityList().forEach(activity -> builder.append(activity).append("\n"));
        return builder.toString();
    }

    /**
     * Returns the activity list as an {@ObservableList}.
     */
    public ObservableList<Activity> getObservableActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityList // instanceof handles nulls
                && activities.equals(((ActivityList) other).activities));
    }

    @Override
    public int hashCode() {
        return activities.hashCode();
    }
}
