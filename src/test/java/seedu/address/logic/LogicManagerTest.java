package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.command.CommandTestUtil.COMMAND_PREFIX_DESC;
import static seedu.address.logic.command.CommandTestUtil.END_DATE_DESC_EUROPE;
import static seedu.address.logic.command.CommandTestUtil.NAME_DESC_EUROPE;
import static seedu.address.logic.command.CommandTestUtil.START_DATE_DESC_EUROPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.typicals.TypicalTravelPlans.EUROPE;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.add.AddCommand;
import seedu.address.logic.command.add.AddTravelPlanCommand;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.travelplan.TravelPlan;
import seedu.address.storage.JsonTravelPlannerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.builders.TravelPlanBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonTravelPlannerStorage travelPlannerStorage =
                new JsonTravelPlannerStorage(temporaryFolder.resolve("travelPlanner.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(travelPlannerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteActivityCommand = "delete -activity 9";
        assertCommandException(deleteActivityCommand, MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

        String deleteAccommodationCommand = "delete -accommodation 9";
        assertCommandException(deleteAccommodationCommand, MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST);

        String deleteFriendCommand = "delete -friend 9";
        assertCommandException(deleteFriendCommand, MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonTravelPlannerIoExceptionThrowingStub
        JsonTravelPlannerStorage travelPlannerStorage =
                new JsonTravelPlannerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionTravelPlanner.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(travelPlannerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addTravelPlanCommand = AddCommand.COMMAND_WORD + COMMAND_PREFIX_DESC + AddTravelPlanCommand.COMMAND_WORD
                + NAME_DESC_EUROPE + START_DATE_DESC_EUROPE + END_DATE_DESC_EUROPE;
        TravelPlan expectedTravelPlan = new TravelPlanBuilder(EUROPE).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTravelPlan(expectedTravelPlan);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addTravelPlanCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredActivityList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredAccommodationList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFriendList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTravelPlanList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredWishlist().remove(0));
    }

    @Test
    public void getDirectory_onInitialisation_equalsWishlist() {
        assertEquals(model.getDirectory(), model.getWishlist());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getTravelPlanner(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonTravelPlannerIoExceptionThrowingStub extends JsonTravelPlannerStorage {
        private JsonTravelPlannerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTravelPlanner(ReadOnlyTravelPlanner travelPlanner, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
