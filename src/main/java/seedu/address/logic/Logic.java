package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Directory;
import seedu.address.model.Model;
import seedu.address.model.ObservableDirectory;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.friend.Friend;
import seedu.address.model.travelplan.TravelPlan;
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the TravelPlanner.
     *
     * @see Model#getTravelPlanner()
     */
    ReadOnlyTravelPlanner getTravelPlanner();

    /** Returns an unmodifiable view of the filtered list of travelPlans. */
    ObservableList<TravelPlan> getFilteredTravelPlanList();

    /** Returns an unmodifiable view of the filtered list of wishlist activities. */
    ObservableList<Activity> getFilteredWishlist();

    /** Returns an unmodifiable view of the filtered list of activities. */
    ObservableList<Activity> getFilteredActivityList();

    /** Returns an unmodifiable view of the filtered list of accommodations. */
    ObservableList<Accommodation> getFilteredAccommodationList();

    /** Returns an unmodifiable view of the filtered list of friends. */
    ObservableList<Friend> getFilteredFriendList();

    /**
     * Returns the current directory.
     */
    Directory getDirectory();

    /**
     * Returns the ObservableDirectory that is observed by the Ui.
     */
    ObservableDirectory getObservableDirectory();

    /**
     * Returns the user prefs' travel planner file path.
     */
    Path getTravelPlannerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
