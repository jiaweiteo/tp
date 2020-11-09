package seedu.address.model.travelplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STARTANDENDDATE;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.commons.WanderlustDate.getNumOfDaysAndNights;
import static seedu.address.model.commons.WanderlustDate.isValidStartAndEndDate;

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
 * Represents a travel plan in the travel planner.
 * Duplicates are not allowed (by .isSameTravelPlan comparison).
 */
public class TravelPlan extends Directory implements Nameable {

    public static final String MESSAGE_CONSTRAINTS = "Start Date should be before or on the same date as End Date.";
    public static final String TRAVEL_PLAN_WORD = "travelplan";


    // Identity fields
    private final Name name;

    // Data fields
    private final WanderlustDate startDate;
    private final WanderlustDate endDate;
    private final ActivityList activities = new ActivityList();
    private final AccommodationList accommodations = new AccommodationList();
    private final FriendList friends = new FriendList();

    /**
     * Creates an empty TravelPlan with the name, startDate and endDate.
     *
     * @param name Name of the travel plan.
     * @param startDate Start date of the travel plan.
     * @param endDate End date of the travel plan.
     * @return An empty travel plan with the specified name, start date and end date.
     */
    public TravelPlan(Name name, WanderlustDate startDate, WanderlustDate endDate) {
        requireAllNonNull(name, startDate, endDate);
        checkArgument(isValidStartAndEndDate(startDate, endDate), MESSAGE_INVALID_STARTANDENDDATE);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Creates a TravelPlan using the specified Accommodations, Activities and Friends.
     *
     * @param name Name of the travel plan.
     * @param startDate Start date of the travel plan.
     * @param endDate End date of the travel plan.
     * @param accommodationsToBeCopied List of accommodations to be added to the travel plan.
     * @param activitiesToBeCopied List of activities to be added to the travel plan.
     * @param friendsTobeCopied List of friends to be added to the travel plan.
     * @return A travel plan with the specified name, start date, end date, accommodations, activities and friends.
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
     * Creates a TravelPlan using the specified TravelPlan.
     *
     * @param toBeCopied TravelPlan to copy into the new travel plan.
     * @return A copy of the travel plan {@code toBeCopied}.
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
        requireNonNull(newData);

        accommodations.resetData(newData.getAccommodationList());
        activities.resetData(newData.getActivityList());
        friends.resetData(newData.getFriendList());
    }

    /**
     * Returns true if a travel plan object with the same identity as {@code travelPlanObject} exists in the travel plan
     * object's list.
     */
    @Override
    public boolean contains(TravelPlanObject travelPlanObject) {
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
    @Override
    public void addTpo(TravelPlanObject travelPlanObject) {
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
    @Override
    public void setTpo(TravelPlanObject target, TravelPlanObject editedTravelPlanObject) {
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
    @Override
    public void remove(TravelPlanObject key) {
        if (key instanceof Accommodation) {
            accommodations.removeAccommodation((Accommodation) key);
        } else if (key instanceof Activity) {
            activities.removeActivity((Activity) key);
        } else { // if key instanceof Friend
            friends.removeFriend((Friend) key);
        }
    }

    //// util methods

    //// travel plan identity methods

    @Override
    public Name getName() {
        return name;
    }

    /**
     * Returns start date of the travel plan.
     * @return Start date of the travel plan.
     */
    public WanderlustDate getStartDate() {
        return startDate;
    }

    /**
     * Returns end date of the travel plan.
     * @return End date of the travel plan.
     */
    public WanderlustDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean isTravelPlan() {
        return true;
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
                && otherTravelPlan.getName().equals(getName());
    }

    /**
     * Returns a string representing the start and end date of the travel plan.
     * E.g. 20 Dec 2020 to 25 Dec 2020 (6D5N).
     * @return
     */
    public String getDateTitle() {
        return getStartDate().toString() + " to " + getEndDate().toString() + " ("
                + getNumOfDaysAndNights(startDate, endDate) + ")";
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nStart Date: ")
                .append(getStartDate())
                .append("\nEnd Date: ")
                .append(getEndDate())
                .append("\n(").append(getNumOfDaysAndNights(startDate, endDate)).append(")")
                .append("\n\n" + accommodations)
                .append(activities)
                .append(friends);
        return builder.toString();
    }

    //// travel plan data methods

    /**
     * Returns this travel plan's accommodation list as an {@AccommodationList}.
     */
    public AccommodationList getAccommodationList() {
        return accommodations;
    }

    /**
     * Returns this travel plan's accommodation list as an {@ObservableList}.
     */
    public ObservableList<Accommodation> getObservableAccommodationList() {
        return accommodations.getObservableAccommodationList();
    }

    /**
     * Returns this travel plan's activity list as an {@ActivityList}.
     */
    public ActivityList getActivityList() {
        return activities;
    }

    /**
     * Returns this travel plan's activity list as an {@ObservableList}.
     */
    public ObservableList<Activity> getObservableActivityList() {
        return activities.getObservableActivityList();
    }

    /**
     * Returns this travel plan's friend list as a {@FriendList}.
     */
    public FriendList getFriendList() {
        return friends;
    }

    /**
     * Returns this travel plan's friend list as an {@ObservableList}.
     */
    public ObservableList<Friend> getObservableFriendList() {
        return friends.getObservableFriendList();
    }

    /**
     * Generates the total cost of the travel plan, which includes the cost of activities and accommodations.
     * @return Total cost of the travel plan as a string.
     */
    public String getTotalCost() {
        int totalCost = 0;
        for (int i = 0; i < activities.getObservableActivityList().size(); i++) {
            Activity tempActivity = activities.getObservableActivityList().get(i);
            totalCost += Integer.parseInt(tempActivity.getCostAsString());
        }
        for (int i = 0; i < accommodations.getObservableAccommodationList().size(); i++) {
            Accommodation tempAccommodation = accommodations.getObservableAccommodationList().get(i);
            totalCost += Integer.parseInt(tempAccommodation.getCostAsString());
        }
        return Integer.toString(totalCost);
    }

    /**
     * Returns true if there is an activity or accommodation in the travel plan that is before its start date.
     */
    public boolean hasEarlierDateObject() {
        return activities.getObservableActivityList().parallelStream().filter(activity ->
                activity.getActivityDateTime().isBefore(this.startDate)).count() > 0
                || accommodations.getObservableAccommodationList().parallelStream().filter(accommodation ->
                accommodation.getStartDate().isBefore(this.startDate)).count() > 0;
    }

    /**
     * Returns true if there is an activity or accommodation in the travel plan that is after its end date.
     */
    public boolean hasLaterDateObject() {
        return activities.getObservableActivityList().parallelStream().filter(activity ->
                activity.getActivityDateTime().isAfter(this.endDate)).count() > 0
                || accommodations.getObservableAccommodationList().parallelStream().filter(accommodation ->
                accommodation.getEndDate().isAfter(this.endDate)).count() > 0;
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
