package seedu.address.model.wishlist;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.Directory;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;

/**
 * Represents a Wishlist in Wanderlust.
 * There should only be one Wishlist in entire Wanderlust.
 */
public class Wishlist extends Directory {

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
     * Creates an Wishlist using the Activities in the {@code toBeCopied}.
     */
    public Wishlist(Wishlist toBeCopied) {
        this();
        requireNonNull(toBeCopied);
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
     * Resets the existing data of this {@code Wishlist} with {@code newData}.
     */
    public void resetData(Wishlist newData) {
        requireNonNull(newData);

        setActivities(newData.getObservableActivityList());
    }

    //// activity-level operations

    /**
     * Returns true if an activity with the same identity as {@code activity} exists in the wishlist.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    @Override
    public boolean contains(TravelPlanObject travelPlanObject) {
        requireNonNull(travelPlanObject);
        assert travelPlanObject instanceof Activity;
        return activities.contains((Activity) travelPlanObject);
    }

    /**
     * Adds an activity in the form of Activity to the wishlist.
     * The activity must not already exist in the wishlist.
     */
    public void addActivity(Activity p) {
        activities.add(p);
    }

    @Override
    public void addTpo(TravelPlanObject travelPlanObject) {
        assert travelPlanObject instanceof Activity;
        activities.add((Activity) travelPlanObject);
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

    @Override
    public void setTpo(TravelPlanObject target, TravelPlanObject editedTravelPlanObject) {
        requireNonNull(editedTravelPlanObject);

        assert target instanceof Activity && editedTravelPlanObject instanceof Activity;
        activities.setActivity((Activity) target, (Activity) editedTravelPlanObject);
    }

    @Override
    public Name getName() {
        return new Name("Wishlist");
    }

    /**
     * Removes {@code key} from this {@code Wishlist}.
     * {@code key} must exist in the wishlist.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    /**
     * Generates the total cost of the wishlist, which includes the cost of activities and accommodations.
     * @return Total cost of the wishlist as a string.
     */
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

    @Override
    public void remove(TravelPlanObject travelPlanObject) {
        activities.remove((Activity) travelPlanObject);
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
    public boolean isTravelPlan() {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Wishlist: ");
        getObservableActivityList().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns the activity list in the wishlist as an {@code ObservableList}.
     */
    public ObservableList<Activity> getObservableActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public String getDateTitle() {
        return null;
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
