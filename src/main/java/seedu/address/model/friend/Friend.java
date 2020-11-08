package seedu.address.model.friend;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.commons.Name;
import seedu.address.model.commons.TravelPlanObject;

/**
 * Represents a Friend in the travel plan.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Friend extends TravelPlanObject {

    public static final String TPO_WORD = "friend";

    // Identity fields
    private final Passport passport;

    // Data fields
    private final Name name; // name can be the same for different friends
    private final Mobile mobile;

    /**
     * Every field must be present and not null.
     */
    public Friend(Name name, Passport passport, Mobile mobile) {
        requireAllNonNull(name, passport, mobile);
        this.name = name;
        this.passport = passport;
        this.mobile = mobile;
    }

    @Override
    public Name getName() {
        return name;
    }

    public String getNameAsString() {
        return name.toString();
    }

    public Passport getPassport() {
        return passport;
    }

    public Mobile getMobile() {
        return mobile;
    }

    /**
     * Returns true if both friends have the same passport.
     */
    public boolean isSameFriend(Friend otherFriend) {
        if (otherFriend == this) {
            return true;
        }

        return otherFriend != null
                && otherFriend.getPassport().equals(getPassport());
    }

    /**
     * Returns true if both friends have the same identity and data fields.
     * This defines a stronger notion of equality between two friends.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Friend)) {
            return false;
        }

        Friend otherFriend = (Friend) other;
        return otherFriend.getName().equals(getName())
                && otherFriend.getPassport().equals(getPassport())
                && otherFriend.getMobile().equals(getMobile());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, passport, mobile);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + "\n")
                .append("Passport number: ")
                .append(getPassport())
                .append("\n")
                .append("Mobile phone: ")
                .append(getMobile()).append("\n");
        return builder.toString();
    }

}
