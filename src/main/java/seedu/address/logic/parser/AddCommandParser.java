package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STARTANDENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOBILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.ParserUtil.OBJECT_TYPE_POSITION;
import static seedu.address.logic.parser.ParserUtil.removeDash;
import static seedu.address.model.commons.WanderlustDate.isValidStartAndEndDate;

import java.util.stream.Stream;

import seedu.address.logic.command.add.AddAccommodationCommand;
import seedu.address.logic.command.add.AddActivityCommand;
import seedu.address.logic.command.add.AddCommand;
import seedu.address.logic.command.add.AddFriendCommand;
import seedu.address.logic.command.add.AddTravelPlanCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Importance;
import seedu.address.model.activity.WanderlustDateTime;
import seedu.address.model.commons.Cost;
import seedu.address.model.commons.Location;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.WanderlustDate;
import seedu.address.model.friend.Friend;
import seedu.address.model.friend.Mobile;
import seedu.address.model.friend.Passport;
import seedu.address.model.travelplan.TravelPlan;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        try {
            String[] keywords = args.split(" ");

            String addType = removeDash(keywords[OBJECT_TYPE_POSITION], AddCommand.MESSAGE_USAGE);

            switch (addType) {

            case AddActivityCommand.COMMAND_WORD:
                return parseActivity(args);

            case AddAccommodationCommand.COMMAND_WORD:
                return parseAccommodation(args);

            case AddFriendCommand.COMMAND_WORD:
                return parseFriend(args);

            case AddTravelPlanCommand.COMMAND_WORD:
                return parseTravelPlan(args);

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddActivityCommand
     * and returns an AddActivityCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddActivityCommand parseActivity(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_IMPORTANCE, PREFIX_LOCATION, PREFIX_COST,
                        PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_IMPORTANCE, PREFIX_LOCATION, PREFIX_COST,
                PREFIX_DATETIME) || !argMultimap.getPreamble().equals("-activity")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddActivityCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Importance importance = ParserUtil.parseImportance(argMultimap.getValue(PREFIX_IMPORTANCE).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Cost cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        WanderlustDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());

        Activity activity = new Activity(name, location, cost, importance, dateTime);

        return new AddActivityCommand(activity);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddAccommodationCommand
     * and returns an AddAccommodationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAccommodationCommand parseAccommodation(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_COST, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LOCATION, PREFIX_COST, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().equals("-accommodation")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAccommodationCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Cost cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        WanderlustDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get());
        WanderlustDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get());

        if (!isValidStartAndEndDate(startDate, endDate)) {
            throw new ParseException(MESSAGE_INVALID_STARTANDENDDATE);
        }

        Accommodation accommodation = new Accommodation(name, startDate, endDate, cost, location);

        return new AddAccommodationCommand(accommodation);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddFriendCommand
     * and returns an AddFriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFriendCommand parseFriend(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MOBILE, PREFIX_PASSPORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MOBILE, PREFIX_PASSPORT)
                || !argMultimap.getPreamble().equals("-friend")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddFriendCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Mobile mobile = ParserUtil.parseMobile(argMultimap.getValue(PREFIX_MOBILE).get());
        Passport passport = ParserUtil.parsePassport(argMultimap.getValue(PREFIX_PASSPORT).get());

        Friend friend = new Friend(name, passport, mobile);

        return new AddFriendCommand(friend);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddTravelPlanCommand
     * and returns an AddTravelPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTravelPlanCommand parseTravelPlan(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().equals("-travelplan")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTravelPlanCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        WanderlustDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get());
        WanderlustDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get());

        if (!isValidStartAndEndDate(startDate, endDate)) {
            throw new ParseException(MESSAGE_INVALID_STARTANDENDDATE);
        }

        TravelPlan travelPlan = new TravelPlan(name, startDate, endDate);

        return new AddTravelPlanCommand(travelPlan);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
