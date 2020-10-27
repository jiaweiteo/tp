package seedu.address.model.travelplan;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.Directory;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.Nameable;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.friend.Friend;

/**
 * Represents a travel plan in the travel planner
 * Duplicates are not allowed (by .isSameTravelPlan comparison)
 */
public class TravelPlan extends Directory implements Nameable {

    public static final String MESSAGE_CONSTRAINTS = "Start Date should be before or on the same date as End Date.";

    // Identity fields
    private final Name name;
    private final WanderlustDate startDate;
    private final WanderlustDate endDate;

    // Data fields
    private final ActivityList activities = new ActivityList();
    private final AccommodationList accommodations = new AccommodationList();
    private final FriendList friends = new FriendList();

    /**
     * Creates an empty TravelPlan with only the name, startDate and endDate.
     */
    public TravelPlan(Name name, WanderlustDate startDate, WanderlustDate endDate) {
        requireAllNonNull(name, startDate, endDate);
        checkArgument(isValidStartAndEndDate(startDate, endDate), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Creates an TravelPlan using the Accommodations, Activities and Friends in the {@code accommodationsToBeCopied},
     * {@code activitiesToBeCopied} and {@code friendsTobeCopied}
     */
    public TravelPlan(Name name, WanderlustDate startDate, WanderlustDate endDate,
                      ActivityList activitiesToBeCopied,
                      AccommodationList accommodationsToBeCopied,
                      FriendList friendsTobeCopied) {
        requireAllNonNull(name, startDate, endDate);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        accommodations.resetData(accommodationsToBeCopied);
        activities.resetData(activitiesToBeCopied);
        friends.resetData(friendsTobeCopied);
    }

    /**
     * Creates a TravelPlan using the TravelPlan in the {@code toBeCopied}
     */
    public TravelPlan(TravelPlan toBeCopied) {
        requireAllNonNull(toBeCopied.name, toBeCopied.startDate, toBeCopied.endDate);
        this.name = toBeCopied.name;
        this.startDate = toBeCopied.startDate;
        this.endDate = toBeCopied.endDate;
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code TravelPlan} with {@code newData}.
     */
    public void resetData(TravelPlan newData) {
        accommodations.resetData(newData.getAccommodations());
        activities.resetData(newData.getActivities());
        friends.resetData(newData.getFriends());
    }

    /**
     * Returns true if the start date is before or on the same day as end date.
     */
    public static boolean isValidStartAndEndDate(WanderlustDate startDate, WanderlustDate endDate) {
        return startDate.getValue().compareTo(endDate.getValue()) <= 0;
    }

    /**
     * Returns true for travel plan.
     */
    @Override
    public boolean isTravelPlan() {
        return true;
    }
    //// travel plan object-level operations

    /**
     * Returns true if a travel plan object with the same identity as {@code travelPlanObject} exists in the travel plan
     * object's list.
     */
    public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
        if (travelPlanObject instanceof Accommodation) {
            return accommodations.hasAccommodation((Accommodation) travelPlanObject);
        } else if (travelPlanObject instanceof Activity) {
            return activities.hasActivity((Activity) travelPlanObject);
        } else { // if travelPlanObject instanceof Friend
            return friends.hasFriend((Friend) travelPlanObject);
        }
    }

    /**
     * Adds a travel plan object to its corresponding list.
     * The travel plan object must not already exist in its corresponding list.
     */
    public void addTravelPlanObject(TravelPlanObject travelPlanObject) {
        if (travelPlanObject instanceof Accommodation) {
            accommodations.addAccommodation((Accommodation) travelPlanObject);
        } else if (travelPlanObject instanceof Activity) {
            activities.addActivity((Activity) travelPlanObject);
        } else { // if travelPlanObject instanceof Friend
            friends.addFriend((Friend) travelPlanObject);
        }
    }

    /**
     * Replaces the given travel plan object {@code target} in the list with {@code editedTravelPlanObject}.
     * {@code target} must exist in the corresponding travel plan object list.
     * The travel plan object identity of {@code editedTravelPlanObject} must not be the same as another existing
     * travel plan object in the corresponding travel plan object list.
     */
    public void setTravelPlanObject(TravelPlanObject target, TravelPlanObject editedTravelPlanObject) {
        if (editedTravelPlanObject instanceof Accommodation) {
            accommodations.setAccommodation((Accommodation) target, (Accommodation) editedTravelPlanObject);
        } else if (editedTravelPlanObject instanceof Activity) {
            activities.setActivity((Activity) target, (Activity) editedTravelPlanObject);
        } else { // if editedTravelPlanObject instanceof Friend
            friends.setFriend((Friend) target, (Friend) editedTravelPlanObject);
        }
    }

    /**
     * Removes {@code key} from the corresponding travel plan object list.
     * {@code key} must exist in the corresponding travel plan object list.
     */
    public void removeTravelPlanObject(TravelPlanObject key) {
        if (key instanceof Accommodation) {
            accommodations.removeAccommodation((Accommodation) key);
        } else if (key instanceof Activity) {
            activities.removeActivity((Activity) key);
        } else { // if key instanceof Friend
            friends.removeFriend((Friend) key);
        }
    }

    /**
     * Generates the total cost of the travel plan, considering the cost of activities and accommodations.
     * @return String Total cost in string.
     */
    public String getTotalCost() {
        int totalCost = 0;
        for (int i = 0; i < activities.getActivityList().size(); i++) {
            Activity tempActivity = activities.getActivityList().get(i);
            totalCost += Integer.parseInt(tempActivity.getCostAsString());
        }
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            Accommodation tempAccommodation = accommodations.getAccommodationList().get(i);
            totalCost += Integer.parseInt(tempAccommodation.getCostAsString());
        }
        return Integer.toString(totalCost);
    }

    //// util methods

    //// travel plan identity methods

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

    /**
     * Returns true if both travel plans of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two travel plans.
     */
    public boolean isSameTravelPlan(TravelPlan otherTravelPlan) {
        if (otherTravelPlan == this) {
            return true;
        }

        return otherTravelPlan != null
                && otherTravelPlan.getName().equals(getName())
                && (otherTravelPlan.getStartDate().equals(getStartDate())
                || otherTravelPlan.getEndDate().equals(getEndDate()));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate());
        builder.append(accommodations)
                .append(activities)
                .append(friends);
        return builder.toString();
    }

    //// travel plan data methods

    public AccommodationList getAccommodations() {
        return accommodations;
    }

    public ObservableList<Accommodation> getAccommodationList() {
        return accommodations.getAccommodationList();
    }

    public ObservableList<TravelPlanObject> getAccommodationTpoList() {
        return accommodations.getTpoList();
    }

    public ActivityList getActivities() {
        return activities;
    }

    public ObservableList<Activity> getActivityList() {
        return activities.getActivityList();
    }

    public ObservableList<TravelPlanObject> getActivityTpoList() {
        return activities.getTpoList();
    }

    public FriendList getFriends() {
        return friends;
    }

    public ObservableList<Friend> getFriendList() {
        return friends.getFriendList();
    }

    public ObservableList<TravelPlanObject> getFriendTpoList() {
        return friends.getTpoList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TravelPlan // instanceof handles nulls
                && name.equals(((TravelPlan) other).name)
                && startDate.equals(((TravelPlan) other).startDate)
                && endDate.equals(((TravelPlan) other).endDate)
                && accommodations.equals(((TravelPlan) other).accommodations)
                && activities.equals(((TravelPlan) other).activities)
                && friends.equals(((TravelPlan) other).friends));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, accommodations, activities, friends);
    }
}
