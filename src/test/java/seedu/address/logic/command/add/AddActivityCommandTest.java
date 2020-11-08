package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ACTIVITY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.TravelPlanner;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.WanderlustDateTime;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.travelplan.ActivityList;
import seedu.address.testutil.builders.ActivityBuilder;
import seedu.address.testutil.typicals.TypicalActivities;


public class AddActivityCommandTest {

    @Test
    public void constructor_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddActivityCommand(null));
    }

    @Test
    public void execute_activityAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingActivityAdded modelStub = new ModelStubAcceptingActivityAdded();
        Activity validActivity = new ActivityBuilder().build();

        CommandResult commandResult = new AddActivityCommand(validActivity).execute(modelStub);

        assertEquals(String.format(AddActivityCommand.MESSAGE_SUCCESS, validActivity),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validActivity), modelStub.activitiesAdded);
    }

    @Test
    public void execute_duplicateActivity_throwsCommandException() {
        Activity validActivity = new ActivityBuilder().build();
        AddActivityCommand addActivityCommand = new AddActivityCommand(validActivity);
        ModelStub modelStub = new ModelStubWithActivity(validActivity);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ACTIVITY, () ->
                addActivityCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        AddActivityCommand addArcheryCommand = new AddActivityCommand(TypicalActivities.ARCHERY);
        AddActivityCommand addBungeeCommand = new AddActivityCommand(TypicalActivities.BUNGEEJUMPING);

        // same object -> returns true
        assertTrue(addArcheryCommand.equals(addArcheryCommand));

        // same values -> returns true
        AddActivityCommand addArcheryCommandCopy = new AddActivityCommand(TypicalActivities.ARCHERY);
        assertTrue(addArcheryCommand.equals(addArcheryCommandCopy));

        // different types -> returns false
        assertFalse(addArcheryCommand.equals(1));

        // null -> returns false
        assertFalse(addArcheryCommand.equals(null));

        // different activity -> returns false
        assertFalse(addArcheryCommand.equals(addBungeeCommand));
    }

    /**
     * A Model stub that contains a single activity.
     */
    private class ModelStubWithActivity extends ModelStub {
        private final Activity activity;

        ModelStubWithActivity(Activity activity) {
            requireNonNull(activity);
            this.activity = activity;
        }

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Activity activity = (Activity) travelPlanObject;
            return this.activity.isSameActivity(activity);
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private class ModelStubAcceptingActivityAdded extends ModelStub {
        final ArrayList<Activity> activitiesAdded = new ArrayList<>();

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Activity activity = (Activity) travelPlanObject;
            return activitiesAdded.stream().anyMatch(activity::isSameActivity);
        }

        @Override
        public void addTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Activity activity = (Activity) travelPlanObject;
            activitiesAdded.add(activity);
        }

        @Override
        public ActivityList getActivityList() {
            ActivityList activityList = new ActivityList();
            activityList.setActivities(activitiesAdded);
            return activityList;
        }

        @Override
        public boolean isValidActivityDate(WanderlustDateTime activityDateTime) {
            return true;
        }

        @Override
        public ReadOnlyTravelPlanner getTravelPlanner() {
            return new TravelPlanner();
        }
    }

}
