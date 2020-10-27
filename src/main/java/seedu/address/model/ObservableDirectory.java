package seedu.address.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private ObservableList<Activity> observableActivityList = FXCollections.observableArrayList();
    private ObservableList<Accommodation> observableAccommodationList = FXCollections.observableArrayList();
    private ObservableList<Friend> observableFriendList = FXCollections.observableArrayList();
    private ObservableList<Object> observableDirectoryInformation = FXCollections.observableArrayList();
    private Directory directory;
    private ObjectProperty<Directory> dir = new SimpleObjectProperty<>();

    /**
     * Instantiates an observable view of the current directory.
     * @param directory the directory representing the default directory.
     */
    public ObservableDirectory(Directory directory) {
        this.directory = directory;
        dir.setValue(directory);
        this.observableActivityList.setAll(directory.getObservableActivityList());
        if (directory instanceof TravelPlan) {
            this.observableAccommodationList.setAll(((TravelPlan) directory).getAccommodationList());
            this.observableFriendList.setAll(((TravelPlan) directory).getFriendList());
        }
    }

    public ObjectProperty<Directory> get() {
        return dir;
    }

    public ObservableList<Activity> getObservableActivityList() {
        return this.observableActivityList;
    }

    public ObservableList<Accommodation> getObservableAccommodationList() {
        return this.observableAccommodationList;
    }

    public ObservableList<Friend> getObservableFriendList() {
        return this.observableFriendList;
    }

    public void setObservableDirectory(Directory directory) {
        this.directory = directory;
        dir.setValue(directory);
        setObservableActivityList(directory.getObservableActivityList());
        if (directory instanceof TravelPlan) {
            setObservableAccommodationList(((TravelPlan) directory).getAccommodationList());
            setObservableFriendList(((TravelPlan) directory).getFriendList());
        } else {
            clearObservableAccommodationList();
            clearObservableFriendList();
        }
    }

    private void setObservableActivityList(ObservableList<Activity> newActivities) {
        this.observableActivityList.setAll(newActivities);
    }

    private void setObservableFriendList(ObservableList<Friend> newFriends) {
        this.observableFriendList.setAll(newFriends);
    }

    private void setObservableAccommodationList(ObservableList<Accommodation> newAccommodations) {
        this.observableAccommodationList.setAll(newAccommodations);
    }

    private void clearObservableActivityList() {
        this.observableActivityList.clear();
    }

    private void clearObservableFriendList() {
        this.observableFriendList.clear();
    }

    private void clearObservableAccommodationList() {
        this.observableAccommodationList.clear();
    }

}
