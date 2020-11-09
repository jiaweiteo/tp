package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX =
            "The accommodation index provided is invalid";
    public static final String MESSAGE_MISSING_INDEX = "The index is missing!";
    public static final String MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX = "The activity index provided is invalid";
    public static final String MESSAGE_INVALID_FRIEND_DISPLAYED_INDEX = "The friend index provided is invalid";
    public static final String MESSAGE_INVALID_TRAVEL_PLAN_OBJECT_AT_WISHLIST = "The wishlist only stores activities,"
            + " not accommodations or friends! Use the goto command to navigate to a travel plan to "
            + "perform commands for accommodation/friend.";
    public static final String MESSAGE_INVALID_TRAVELPLAN_DISPLAYED_INDEX = "The travel plan index provided is invalid";
    public static final String MESSAGE_ACTIVITIES_LISTED_OVERVIEW = "%1$d activities listed!";
    public static final String MESSAGE_ACCOMMODATIONS_LISTED_OVERVIEW = "%1$d accommodations listed!";
    public static final String MESSAGE_FRIENDS_LISTED_OVERVIEW = "%1$d friends listed!";
    public static final String MESSAGE_INVALID_STARTANDENDDATE = "Start Date should be before or "
            + "on the same date as End Date.";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "This accommodation already exists in the travel"
            + " plan. Accommodations with the same name, start date and end date are considered duplicates.";
    public static final String MESSAGE_DATE_NOT_IN_RANGE_ACCOMMODATION = "The accommodation start date and/or end date"
            + " must be within the travel plan's start date and end date.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the directory. "
            + "Activities with the same name, location and datetime are considered duplicates.";
    public static final String MESSAGE_DATE_NOT_IN_RANGE_ACTIVITY = "The activity date and time must be within the "
            + "travel plan's start date and end date.";
    public static final String MESSAGE_DUPLICATE_FRIEND = "This friend already exists in the travel plan. "
            + "Friends cannot have the same passport number.";
    public static final String MESSAGE_DUPLICATE_TRAVELPLAN = "This travel plan already exists in the travel planner. "
            + "Travel plans cannot have the same name.";
    public static final String MESSAGE_STRING_EXCEED_CHARACTER_LIMIT = "%s exceeds character limit of %d characters!";
    public static final String MESSAGE_TPO_OUTSIDE_EDITED_TRAVELPLAN_DATE_REGION = "There are activities and/or "
            + "accommodations that lie outside of the edited date region of this travel plan. Please adjust their "
            + "dates before editing the travel plan dates.";
}
