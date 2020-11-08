package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;

/**
 * Represents the current directory of wanderlust, which can be instance of TravelPlan or Wishlist
 */
public abstract class Directory {
    /**
     * Returns an unmodifiable view of the observable activity list under the current directory.
     */
    public abstract ObservableList<Activity> getObservableActivityList();

    /**
     * Returns the name of the current directory as {@code Name}.
     */
    public abstract Name getName();

    /**
     * Returns the total cost of the current directory as {@code String}.
     * Total cost includes Activity and Accommodation cost for TravelPlan, while only Activity cost for Wishlist.
     */
    public abstract String getTotalCost();

    /**
     * Returns the start date of the current directory. This method should not be called by {@code Wishlist}.
     */
    public abstract WanderlustDate getStartDate();

    /**
     * Returns the end date of the current directory. This method should not be called by {@code Wishlist}.
     */
    public abstract WanderlustDate getEndDate();

    /**
     * Returns true if the current directory is a {@code TravelPlan}, false otherwise.
     */
    public abstract boolean isTravelPlan();

    /**
     * Returns true if a travel plan object with the same identity as {@code travelPlanObject} exists in the
     * corresponding travel plan object list in the current directory.
     */
    public abstract boolean contains(TravelPlanObject travelPlanObject);

    /**
     * Removes {@code travelPlanObject} from the corresponding travel plan object list in the current directory.
     * {@code travelPlanObject} must exist in the corresponding travel plan object list.
     */
    public abstract void remove(TravelPlanObject travelPlanObject);

    /**
     * Adds a travel plan object to its corresponding travel plan object list in the current directory.
     * The travel plan object must not already exist in its corresponding list.
     */
    public abstract void addTpo(TravelPlanObject travelPlanObject);

    /**
     * Replaces the given travel plan object {@code target} in the corresponding travel plan object list in the current
     * directory with {@code editedTravelPlanObject}.
     * {@code target} must exist in the corresponding travel plan object list.
     * The travel plan object identity of {@code editedTravelPlanObject} must not be the same as another existing
     * travel plan object in the corresponding travel plan object list in the current directory.
     */
    public abstract void setTpo(TravelPlanObject target, TravelPlanObject editedTravelPlanObject);

    /**
     * Returns the date title of the current directory as {@code String}.
     */
    public abstract String getDateTitle();
}
