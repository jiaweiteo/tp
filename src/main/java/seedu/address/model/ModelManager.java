package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.WanderlustDateTime;
import seedu.address.model.commons.Nameable;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.friend.Friend;
import seedu.address.model.travelplan.AccommodationList;
import seedu.address.model.travelplan.ActivityList;
import seedu.address.model.travelplan.FriendList;
import seedu.address.model.travelplan.TravelPlan;
import seedu.address.model.travelplan.UniqueTravelPlanList;
import seedu.address.model.wishlist.Wishlist;

/**
 * Represents the in-memory model of the travel planner data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int WISHLIST_INDEX = -1;

    private final TravelPlanner travelPlanner;
    private final UserPrefs userPrefs;
    private int directoryIndex;

    // Ui
    private final FilteredList<TravelPlan> filteredTravelPlans;
    private final FilteredList<Activity> filteredWishlist;
    private final FilteredList<Activity> filteredActivityList;
    private final FilteredList<Accommodation> filteredAccommodationList;
    private final FilteredList<Friend> filteredFriendList;
    private final ObservableDirectory observableDirectory;

    // Logic
    private Directory directory;
    private ActivityList activityList;
    private AccommodationList accommodationList;
    private FriendList friendList;

    /**
     * Initializes a ModelManager with the given travelPlanner and userPrefs.
     */
    public ModelManager(ReadOnlyTravelPlanner travelPlanner, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(travelPlanner, userPrefs);

        logger.fine("Initializing with travel planner: " + travelPlanner + " and user prefs " + userPrefs);

        this.travelPlanner = new TravelPlanner(travelPlanner);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTravelPlans = new FilteredList<>(this.travelPlanner.getObservableTravelPlanList());
        filteredWishlist = new FilteredList<>(this.travelPlanner.getObservableWishlist());
        directory = this.travelPlanner.getWishlist();
        directoryIndex = WISHLIST_INDEX;
        observableDirectory = new ObservableDirectory(directory);
        filteredActivityList = new FilteredList<>(observableDirectory.getObservableActivityList());
        filteredAccommodationList = new FilteredList<>(observableDirectory.getObservableAccommodationList());
        filteredFriendList = new FilteredList<>(observableDirectory.getObservableFriendList());
    }

    public ModelManager() {
        this(new TravelPlanner(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTravelPlannerFilePath() {
        return userPrefs.getTravelPlannerFilePath();
    }

    @Override
    public void setTravelPlannerFilePath(Path travelPlannerFilePath) {
        requireNonNull(travelPlannerFilePath);
        userPrefs.setTravelPlannerFilePath(travelPlannerFilePath);
    }

    //=========== TravelPlanner ================================================================================

    @Override
    public void setTravelPlanner(ReadOnlyTravelPlanner travelPlanner) {
        this.travelPlanner.resetData(travelPlanner);
        setDirectory(WISHLIST_INDEX);
    }

    @Override
    public ReadOnlyTravelPlanner getTravelPlanner() {
        return travelPlanner;
    }

    @Override
    public boolean hasTravelPlan(TravelPlan travelPlan) {
        requireNonNull(travelPlan);
        return travelPlanner.hasTravelPlan(travelPlan);
    }

    @Override
    public void deleteTravelPlan(TravelPlan target) {
        travelPlanner.removeTravelPlan(target);
        if (directory.equals(target)) {
            setDirectory(WISHLIST_INDEX);
        }
    }

    @Override
    public void addTravelPlan(TravelPlan travelPlan) {
        travelPlanner.addTravelPlan(travelPlan);
        updateFilteredTravelPlanList(PREDICATE_SHOW_ALL_TRAVEL_PLAN);
    }

    @Override
    public void setTravelPlan(TravelPlan target, TravelPlan editedTravelPlan) {
        requireAllNonNull(target, editedTravelPlan);
        travelPlanner.setTravelPlan(target, editedTravelPlan);
        setDirectory(directoryIndex);
    }

    //=========== Wishlist =============================================================

    @Override
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return travelPlanner.hasActivity(activity);
    }

    @Override
    public void deleteActivity(Activity target) {
        travelPlanner.removeActivity(target);
        observableDirectory.setObservableDirectory(directory);
    }

    @Override
    public void addActivity(Activity activity) {
        travelPlanner.addActivity(activity);
        updateFilteredWishlist(PREDICATE_SHOW_ALL_ACTIVITY);
        observableDirectory.setObservableDirectory(directory);
    }

    @Override
    public void addActivity(Activity activity, Index travelPlanIndex) {
        TravelPlan travelPlan = filteredTravelPlans.get(travelPlanIndex.getZeroBased());
        travelPlan.addTpo(activity);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        travelPlanner.setActivity(target, editedActivity);
        observableDirectory.setObservableDirectory(directory);
    }

    //=========== Directory =============================================================


    @Override
    public void setDirectory(int index) {
        if (index == WISHLIST_INDEX) {
            directoryIndex = WISHLIST_INDEX;
            directory = travelPlanner.getWishlist();
        } else {
            directoryIndex = index;
            directory = travelPlanner.getObservableTravelPlanList().get(index);
            TravelPlan travelPlan = (TravelPlan) directory;
            activityList = travelPlan.getActivityList();
            accommodationList = travelPlan.getAccommodationList();
            friendList = travelPlan.getFriendList();
        }
        observableDirectory.setObservableDirectory(directory);
    }

    @Override
    public Directory getDirectory() {
        return directory;
    }

    @Override
    public boolean isDirectoryTypeTravelPlan() {
        return directory.isTravelPlan();
    }

    @Override
    public ObservableDirectory getObservableDirectory() {
        return observableDirectory;
    }

    //=========== TravelPlanObject =============================================================

    @Override
    public boolean hasTravelPlanObject(TravelPlanObject tPObj) {
        requireNonNull(tPObj);
        return directory.contains(tPObj);
    }

    @Override
    public boolean hasTravelPlanObject(TravelPlanObject tPObj, int travelPlanIndex) {
        requireNonNull(tPObj);
        return filteredTravelPlans.get(travelPlanIndex).contains(tPObj);
    }

    @Override
    public void deleteTravelPlanObject(TravelPlanObject tPObj) {
        requireNonNull(tPObj);
        directory.remove(tPObj);
        observableDirectory.setObservableDirectory(directory);
    }

    @Override
    public void addTravelPlanObject(TravelPlanObject tPObj) {
        requireNonNull(tPObj);
        directory.addTpo(tPObj);
        observableDirectory.setObservableDirectory(directory);
    }

    @Override
    public void setTravelPlanObject(TravelPlanObject target, TravelPlanObject editedTravelPlanObject) {
        requireAllNonNull(target, editedTravelPlanObject);
        directory.setTpo(target, editedTravelPlanObject);
        observableDirectory.setObservableDirectory(directory);
    }

    //=========== Filtered TravelPlan List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code TravelPlan} backed by the internal list of
     * {@code TravelPlanner}
     */
    @Override
    public ObservableList<TravelPlan> getFilteredTravelPlanList() {
        return filteredTravelPlans;
    }

    @Override
    public void updateFilteredTravelPlanList(Predicate<Nameable> predicate) {
        requireNonNull(predicate);
        filteredTravelPlans.setPredicate(predicate);
    }

    //=========== Filtered Wishlist List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Activity} backed by the internal list of
     * {@code Wishlist}
     */
    @Override
    public ObservableList<Activity> getFilteredWishlist() {
        return filteredWishlist;
    }

    @Override
    public void updateFilteredWishlist(Predicate<Nameable> predicate) {
        requireNonNull(predicate);
        filteredWishlist.setPredicate(predicate);
    }

    //=========== Filtered TravelPLanObject List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ? extends TravelPlanObject} backed by the internal list of
     * {@code TravelPlan}
     */
    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivityList;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Nameable> predicate) {
        requireNonNull(predicate);
        filteredActivityList.setPredicate(predicate);
    }

    @Override
    public ObservableList<Accommodation> getFilteredAccommodationList() {
        return filteredAccommodationList;
    }

    @Override
    public void updateFilteredAccommodationList(Predicate<Nameable> predicate) {
        requireNonNull(predicate);
        filteredAccommodationList.setPredicate(predicate);
    }

    @Override
    public ObservableList<Friend> getFilteredFriendList() {
        return filteredFriendList;
    }

    @Override
    public void updateFilteredFriendList(Predicate<Nameable> predicate) {
        requireNonNull(predicate);
        filteredFriendList.setPredicate(predicate);
    }

    //=========== Logic List Accessors =============================================================

    public UniqueTravelPlanList getTravelPlanList() {
        return travelPlanner.getTravelPlanList();
    }

    public Wishlist getWishlist() {
        return travelPlanner.getWishlist();
    }

    public ActivityList getActivityList() {
        return activityList;
    }

    public AccommodationList getAccommodationList() {
        return accommodationList;
    }

    public FriendList getFriendList() {
        return friendList;
    }

    /**
     * Sorts the wishlist with the given comparator.
     */
    public void sortWishlist(Comparator<Activity> comparator) {
        travelPlanner.sortWishlist(comparator);
        observableDirectory.setObservableDirectory(directory);
    }

    /**
     * Sorts the activity list with the given comparator.
     */
    public void sortActivityList(Comparator<Activity> comparator) {
        activityList.sort(comparator);
        observableDirectory.setObservableDirectory(directory);
    }

    /**
     * Sorts the accommodation list with the given comparator.
     */
    public void sortAccommodationList(Comparator<Accommodation> comparator) {
        accommodationList.sort(comparator);
        observableDirectory.setObservableDirectory(directory);
    }

    /**
     * Sorts the friend list with the given comparator.
     */
    public void sortFriendList(Comparator<Friend> comparator) {
        friendList.sort(comparator);
        observableDirectory.setObservableDirectory(directory);
    }

    /**
     * Checks if accommodation object date is within the model's travel plan start and end date.
     * @return false if accommodation start date and end date is within travel plan start date and end date.
     */
    public boolean isValidAccommodationDate(WanderlustDate startDate, WanderlustDate endDate) {
        WanderlustDate travelPlanStartDate = directory.getStartDate();
        WanderlustDate travelPlanEndDate = directory.getEndDate();

        if (startDate.isBefore(travelPlanStartDate) || startDate.isAfter(travelPlanEndDate)) {
            return false;
        }

        if (endDate.isBefore(travelPlanStartDate) || endDate.isAfter(travelPlanEndDate)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if activity object date time is within the model's travel plan start and end date.
     * @return true if activity date is within travel plan start date and end date range.
     */
    public boolean isValidActivityDate(WanderlustDateTime activityDateTime) {
        WanderlustDate travelPlanStartDate = directory.getStartDate();
        WanderlustDate travelPlanEndDate = directory.getEndDate();

        if (activityDateTime.isBefore(travelPlanStartDate) || activityDateTime.isAfter(travelPlanEndDate)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if activity object date time is within the model's travel plan start and end date.
     * @return true if activity date is within travel plan start date and end date range.
     */
    public boolean isValidActivityDate(WanderlustDateTime activityDateTime, TravelPlan travelPlan) {
        WanderlustDate travelPlanStartDate = travelPlan.getStartDate();
        WanderlustDate travelPlanEndDate = travelPlan.getEndDate();
        if (activityDateTime.isBefore(travelPlanStartDate)
                || activityDateTime.isAfter(travelPlanEndDate)) {
            return false;
        }

        return true;
    }



    @Override
    public void copyActivity(Activity activity, Index travelPlanIndex) {
        Activity copiedActivity = new Activity(activity);
        addActivity(copiedActivity, travelPlanIndex);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return travelPlanner.equals(other.travelPlanner)
                && userPrefs.equals(other.userPrefs)
                && filteredTravelPlans.equals(other.filteredTravelPlans)
                && filteredWishlist.equals(other.filteredWishlist);
    }

}
