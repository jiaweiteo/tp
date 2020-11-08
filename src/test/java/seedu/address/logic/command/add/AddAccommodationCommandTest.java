package seedu.address.logic.command.add;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ACCOMMODATION;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.command.CommandResult;
import seedu.address.logic.command.exceptions.CommandException;
import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.TravelPlanner;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.commons.TravelPlanObject;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.travelplan.AccommodationList;
import seedu.address.testutil.builders.AccommodationBuilder;
import seedu.address.testutil.typicals.TypicalAccommodations;


public class AddAccommodationCommandTest {

    @Test
    public void constructor_nullAccommodation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAccommodationCommand(null));
    }

    @Test
    public void execute_accommodationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAccommodationAdded modelStub = new ModelStubAcceptingAccommodationAdded();
        Accommodation validAccommodation = new AccommodationBuilder().build();

        CommandResult commandResult = new AddAccommodationCommand(validAccommodation).execute(modelStub);

        assertEquals(String.format(AddAccommodationCommand.MESSAGE_SUCCESS, validAccommodation),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAccommodation), modelStub.accommodationsAdded);
    }

    @Test
    public void execute_duplicateAccommodation_throwsCommandException() {
        Accommodation validAccommodation = new AccommodationBuilder().build();
        AddAccommodationCommand addAccommodationCommand = new AddAccommodationCommand(validAccommodation);
        ModelStub modelStub = new ModelStubWithAccommodation(validAccommodation);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ACCOMMODATION, () ->
                addAccommodationCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        AddAccommodationCommand addAliceCommand = new AddAccommodationCommand(TypicalAccommodations.ALICEHOTEL);
        AddAccommodationCommand addBobCommand = new AddAccommodationCommand(TypicalAccommodations.BOBHOTEL);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddAccommodationCommand addAliceCommandCopy = new AddAccommodationCommand(TypicalAccommodations.ALICEHOTEL);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different accommodation -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithAccommodation extends ModelStub {
        private final Accommodation accommodation;

        ModelStubWithAccommodation(Accommodation accommodation) {
            requireNonNull(accommodation);
            this.accommodation = accommodation;
        }

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Accommodation accommodation = (Accommodation) travelPlanObject;
            return this.accommodation.isSameAccommodation(accommodation);
        }
    }

    /**
     * A Model stub that always accept the accommodation being added.
     */
    private class ModelStubAcceptingAccommodationAdded extends ModelStub {
        final ArrayList<Accommodation> accommodationsAdded = new ArrayList<>();

        @Override
        public boolean hasTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Accommodation accommodation = (Accommodation) travelPlanObject;
            return accommodationsAdded.stream().anyMatch(accommodation::isSameAccommodation);
        }

        @Override
        public void addTravelPlanObject(TravelPlanObject travelPlanObject) {
            requireNonNull(travelPlanObject);
            Accommodation accommodation = (Accommodation) travelPlanObject;
            accommodationsAdded.add(accommodation);
        }

        @Override
        public AccommodationList getAccommodationList() {
            AccommodationList accommodationList = new AccommodationList();
            accommodationList.setAccommodations(accommodationsAdded);
            return accommodationList;
        }

        @Override
        public ReadOnlyTravelPlanner getTravelPlanner() {
            return new TravelPlanner();
        }

        @Override
        public boolean isValidAccommodationDate(WanderlustDate startDate, WanderlustDate endDate) {
            return true;
        }
    }
}
