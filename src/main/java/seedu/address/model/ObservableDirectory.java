package seedu.address.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.friend.Friend;
import seedu.address.model.travelplan.TravelPlan;

/**
 * Represents an observable view of the current directory.
 */
public class ObservableDirectory {
    private static final String TOTAL_COST = "Total Cost: $";

    private ObservableList<Activity> observableActivityList = FXCollections.observableArrayList();
    private ObservableList<Accommodation> observableAccommodationList = FXCollections.observableArrayList();
    private ObservableList<Friend> observableFriendList = FXCollections.observableArrayList();
    private ObservableList<Object> observableDirectoryInformation = FXCollections.observableArrayList();
    private ObjectProperty<Directory> dir = new SimpleObjectProperty<>();
    private StringProperty observableCost = new SimpleStringProperty();


    /**
     * Instantiates an observable view of the current directory.
     * @param directory The directory representing the default directory.
     */
    public ObservableDirectory(Directory directory) {
        dir.setValue(directory);
        observableCost.setValue(TOTAL_COST + directory.getTotalCost());
        this.observableActivityList.setAll(directory.getObservableActivityList());
        if (directory instanceof TravelPlan) {
            this.observableAccommodationList.setAll(((TravelPlan) directory).getObservableAccommodationList());
            this.observableFriendList.setAll(((TravelPlan) directory).getObservableFriendList());
        }
    }

    /**
     * Returns the cost of the directory as an observable StringProperty.
     */
    public StringProperty getObservableCost() {
        return observableCost;
    }

    /**
     * Returns the directory as an observable ObjectProperty.
     */
    public ObjectProperty<Directory> get() {
        return dir;
    }

    /**
     * Returns the activities in the directory as an ObservableList.
     */
    public ObservableList<Activity> getObservableActivityList() {
        return this.observableActivityList;
    }

    /**
     * Returns the accommodations in the directory as an ObservableList.
     */
    public ObservableList<Accommodation> getObservableAccommodationList() {
        return this.observableAccommodationList;
    }

    /**
     * Returns the friends in the directory as an ObservableList.
     */
    public ObservableList<Friend> getObservableFriendList() {
        return this.observableFriendList;
    }

    /**
     * Sets the observable directory to observe the specified directory.
     */
    public void setObservableDirectory(Directory directory) {
        dir.setValue(directory);
        observableCost.setValue(TOTAL_COST + directory.getTotalCost());
        setObservableActivityList(directory.getObservableActivityList());
        if (directory instanceof TravelPlan) {
            setObservableAccommodationList(((TravelPlan) directory).getObservableAccommodationList());
            setObservableFriendList(((TravelPlan) directory).getObservableFriendList());
        } else {
            clearObservableAccommodationList();
            clearObservableFriendList();
        }
    }

    /**
     * Sets the observable directory to observe the activity list {@code newActivities}.
     */
    private void setObservableActivityList(ObservableList<Activity> newActivities) {
        this.observableActivityList.setAll(newActivities);
    }

    /**
     * Sets the observable directory to observe the friend list {@code newFriends}.
     */
    private void setObservableFriendList(ObservableList<Friend> newFriends) {
        this.observableFriendList.setAll(newFriends);
    }

    /**
     * Sets the observable directory to observe the accommodation list {@code newAccommodations}.
     */
    private void setObservableAccommodationList(ObservableList<Accommodation> newAccommodations) {
        this.observableAccommodationList.setAll(newAccommodations);
    }

    /**
     * Clears the observable friend list.
     */
    private void clearObservableFriendList() {
        this.observableFriendList.clear();
    }

    /**
     * Clears the observable accommodation list.
     */
    private void clearObservableAccommodationList() {
        this.observableAccommodationList.clear();
    }

}
