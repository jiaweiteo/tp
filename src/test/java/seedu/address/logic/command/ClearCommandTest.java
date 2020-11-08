package seedu.address.logic.command;

import static seedu.address.logic.command.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.typicals.TypicalTravelPlans.getTypicalTravelPlanner;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TravelPlanner;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTravelPlanner_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTravelPlanner_success() {
        Model model = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTravelPlanner(), new UserPrefs());
        expectedModel.setTravelPlanner(new TravelPlanner());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
