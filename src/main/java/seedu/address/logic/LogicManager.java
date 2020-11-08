package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.command.Command;
import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.logic.parser.WanderlustParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Directory;
import seedu.address.model.Model;
import seedu.address.model.ObservableDirectory;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.friend.Friend;
import seedu.address.model.travelplan.TravelPlan;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final WanderlustParser wanderlustParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        wanderlustParser = new WanderlustParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = wanderlustParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTravelPlanner(model.getTravelPlanner());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTravelPlanner getTravelPlanner() {
        return model.getTravelPlanner();
    }

    @Override
    public ObservableList<TravelPlan> getFilteredTravelPlanList() {
        return model.getFilteredTravelPlanList();
    }

    @Override
    public ObservableList<Activity> getFilteredWishlist() {
        return model.getFilteredWishlist();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<Accommodation> getFilteredAccommodationList() {
        return model.getFilteredAccommodationList();
    }

    @Override
    public ObservableList<Friend> getFilteredFriendList() {
        return model.getFilteredFriendList();
    }

    @Override
    public Directory getDirectory() {
        return model.getDirectory();
    }

    @Override
    public ObservableDirectory getObservableDirectory() {
        return model.getObservableDirectory();
    }

    @Override
    public Path getTravelPlannerFilePath() {
        return model.getTravelPlannerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

}
