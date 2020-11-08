package seedu.address.model.util;

import seedu.address.model.ReadOnlyTravelPlanner;
import seedu.address.model.TravelPlanner;
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

public class SampleWanderlustDataUtil {
    private static TravelPlan sampleTravelPlan1 = new TravelPlan(new Name("Trip to Hawaii"),
            new WanderlustDate("2020-11-01"), new WanderlustDate("2020-12-31"));
    private static TravelPlan sampleTravelPlan2 = new TravelPlan(new Name("Netherlands Getaway"),
            new WanderlustDate("2021-01-01"), new WanderlustDate("2021-12-31"));

    public static Friend[] getSampleFriends1() {
        return new Friend[] {
            new Friend(new Name("Alex Yeoh"), new Passport("E1234567R"), new Mobile("87438807")),
            new Friend(new Name("Bernice Yu"), new Passport("E1234567J"), new Mobile("99272758")),
            new Friend(new Name("Charlotte Oliveiro"), new Passport("E1234567U"), new Mobile("93210283")),
            new Friend(new Name("David Li"), new Passport("E1234567W"), new Mobile("99103182"))
        };
    }

    public static Friend[] getSampleFriends2() {
        return new Friend[] {
            new Friend(new Name("Lye Yi Xian"), new Passport("E7654321T"), new Mobile("92421847")),
            new Friend(new Name("Teo Jia Wei"), new Passport("E7654321J"), new Mobile("81238032")),
            new Friend(new Name("Timothy Ong"), new Passport("E7654321E"), new Mobile("91274810")),
            new Friend(new Name("Lim Hai Shan"), new Passport("E7654521E"), new Mobile("89002939")),
            new Friend(new Name("Jeanne Toh"), new Passport("E7654321W"), new Mobile("88234200"))
        };
    }

    public static Accommodation[] getSampleAccommodations1() {
        return new Accommodation[] {
            new Accommodation(new Name("Pooh's House"), new WanderlustDate("2020-12-12"),
                    new WanderlustDate("2020-12-31"), new Cost("50"), new Location("100 Acre Woods")),
            new Accommodation(new Name("Hogwarts"), new WanderlustDate("2020-11-11"),
                    new WanderlustDate("2020-11-25"), new Cost("10000"), new Location("London"))
        };
    }

    public static Accommodation[] getSampleAccommodations2() {
        return new Accommodation[] {
            new Accommodation(new Name("River Island"), new WanderlustDate("2021-07-12"),
                    new WanderlustDate("2021-08-31"), new Cost("6000"), new Location("Stardew Valley")),
            new Accommodation(new Name("Snow Cabin"), new WanderlustDate("2021-09-01"),
                    new WanderlustDate("2021-09-25"), new Cost("200"), new Location("Ice Valley"))
        };
    }

    public static Activity[] getSampleActivities1() {
        return new Activity[] {
            new Activity(new Name("Bungee Jumping"), new Location("Sentosa"), new Cost("200"),
                    new Importance("2"), new WanderlustDateTime("2020-12-12 11:00")),
            new Activity(new Name("Eat Bingsu"), new Location("Snowflake Street"), new Cost("5"),
                    new Importance("3"), new WanderlustDateTime("2020-12-23 17:00")),
            new Activity(new Name("Cherry Picking"), new Location("The Woods"), new Cost("50"),
                    new Importance("5"), new WanderlustDateTime("2020-12-13 09:00")),
            new Activity(new Name("Music Festival"), new Location("Tomorrowland Avenue"), new Cost("500"),
                    new Importance("1"), new WanderlustDateTime("2020-12-12 23:59"))
        };
    }

    public static Activity[] getSampleActivities2() {
        return new Activity[] {
            new Activity(new Name("Ice Fishing"), new Location("Ice Park"), new Cost("20"),
                    new Importance("5"), new WanderlustDateTime("2021-07-22 23:00")),
            new Activity(new Name("Visit the National Museum"), new Location("Museum Lane 5"), new Cost("5"),
                    new Importance("2"), new WanderlustDateTime("2021-06-09 17:40")),
            new Activity(new Name("Ice Skating"), new Location("Skating Rink Avenue"), new Cost("30"),
                    new Importance("1"), new WanderlustDateTime("2021-01-05 09:21")),
            new Activity(new Name("Mountain Climbing"), new Location("Tomorrowland"), new Cost("100"),
                    new Importance("4"), new WanderlustDateTime("2021-02-23 20:59"))
        };
    }

    public static TravelPlan getSampleTravelPlan(TravelPlan sampleTravelPlan, Activity[] sampleActivities,
            Accommodation[] sampleAccommodations, Friend[] sampleFriends) {
        for (Friend sampleFriend : sampleFriends) {
            sampleTravelPlan.addTpo(sampleFriend);
        }
        for (Activity sampleActivity : sampleActivities) {
            sampleTravelPlan.addTpo(sampleActivity);
        }
        for (Accommodation sampleAccommodation : sampleAccommodations) {
            sampleTravelPlan.addTpo(sampleAccommodation);
        }
        return sampleTravelPlan;
    }

    public static TravelPlan[] getSampleTravelPlans() {
        return new TravelPlan[] {
            getSampleTravelPlan(sampleTravelPlan1, getSampleActivities1(), getSampleAccommodations1(),
                    getSampleFriends1()),
            getSampleTravelPlan(sampleTravelPlan2, getSampleActivities2(), getSampleAccommodations2(),
                    getSampleFriends2())
        };
    }

    public static ReadOnlyTravelPlanner getSampleTravelPlanner() {
        TravelPlanner tp = new TravelPlanner();
        for (TravelPlan sampleTravelPlan: getSampleTravelPlans()) {
            tp.addTravelPlan(sampleTravelPlan);
        }
        for (Activity sampleActivity : getSampleActivities1()) {
            tp.addActivity(sampleActivity);
        }
        for (Activity sampleActivity : getSampleActivities2()) {
            tp.addActivity(sampleActivity);
        }
        return tp;
    }
}
