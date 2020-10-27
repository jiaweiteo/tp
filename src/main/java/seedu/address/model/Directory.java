package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.WanderlustDate;

/**
 * Represents the current directory of wanderlust, which can be instance of TravelPlan or Wishlist
 */
public abstract class Directory {
    public abstract ObservableList<Activity> getActivityList();

    public abstract boolean isTravelPlan();

    public abstract Name getName();

    public abstract String getTotalCost();

    public abstract WanderlustDate getStartDate();

    public abstract WanderlustDate getEndDate();
}
