package team.dedica.currencies;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Instances of this class represent a single monetary value of a specific currency
 * and are immutable (i.e. they can't be modified). New monetary values can however
 * be created by applying simple arithmetic operations using other monetary values,
 * e.g. by using the {@link #plus(Money)} or {@link #minus(Money)} methods.
 */
public class Money {

    private static final Pattern MONEY = Pattern.compile("[A-Z]{3} \\d{1,3}(,\\d{3})*\\.\\d{2}");

    // The amount is always stored in cents. That way, we don't have to use floating
    // point numbers which lose precision as they become larger and suddenly we could
    // be losing (or gaining) cents! Not good.
    //
    // Also note that it is final and can never change. We can only create completely
    // new instances of Money if we want to have different amounts.
    //
    // See also the tests how to use the Money class.
    private final long amount;

    private final Currency currency;

    // See Currency for reasons for making the constructor private.
    private Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money times(double factor) {
        return new Money(Math.round(amount * factor), currency);
    }

    public Money plus(Money other) {
        return new Money(amount + other.amount, currency);
    }

    public Money minus(Money other) {
        return new Money(amount - other.amount, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;

        // Two moneys are the same if and only if their amount is equal and
        // their currencies are equal, too.
        return amount == money.amount &&
                Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {

        // The hash code is calculated based on both amount and currency. Remember:
        // If 2 hash codes of moneys are the same, then _maybe_ their amounts and currencies are equal.
        // If 2 hash codes are different, then certainly either their amounts or currencies or both are different.
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {

        // The cents are obviously the amount modulo 100.
        long cents = amount % 100;

        // When we subtract the cents from the amount, the remaining number is guaranteed to be
        // divisible by 100.
        long dollars = (amount - cents) / 100;

        // We use the ROOT locale (not German, or US, or Swiss, or...) for rendering, so we can be
        // 100% sure that decimal separators (1,000,000...) are rendered as commas (US-style).
        // This is maybe bad if we wanted to use this program truly internationally, but let's
        // optimise for constant outputs for now.
        //
        // We supply a placeholder string: %s insert a string here
        //                                 then a space
        //                                 %,d insert a number with decimal separators
        //                                 then a dot
        //                                 %d insert a number
        //
        // We supply the things that should be inserted, in order: The symbol into the %s placeholder,
        // the full dollar part as the first number, and the remaining cents as the 2nd number.
        return String.format(Locale.ROOT, "%s %,d.%d", currency.getSymbol(), dollars, cents);
    }

    // Create an instance of 0 value for a specific currency.
    public static Money nothing(Currency currency) {
        return new Money(0, currency);
    }

    // Parse expressions of the format "XXX 1,234.00" and create instances of Money from that.
    public static Money parse(String expression) {

        // We use the regex only to validate the format. We could also use it straightaway to
        // extract the pieces we need to construct the Money instance, but we'll show off some
        // parsing things today.
        if (!MONEY.matcher(expression).matches()) {
            throw new IllegalArgumentException("Failed to parse money expression: " + expression);
        }

        // Splitting the string into 2 parts will give us the left part (currency symbol) and
        // the right part (the number).
        String[] parts = expression.split(" ", 2);

        // From the left part, the symbol, we can create the currency.
        Currency currency = Currency.forSymbol(parts[0]);

        // We split the "number" (which is technically still a string, obviously) by ".", so we
        // have the full dollars in the left part, and the cents in the right part.
        //
        // split actually takes a regular expression, not a simple string by which we would split.
        // In regex, a . is special character with meaning, so we need to escape it with a \ so
        // that it's treated like the actual . we want it to be. However, we put the whole regex
        // in a string (in "quotes"), and in strings \ are special characters, so we need to escape
        // that as well, which gives us \\. Fun!
        String[] amountParts = parts[1].split("\\.");

        // We replace all , in the dollars part with nothing, then we can create an actual number
        // from it (long type).
        long dollars = Long.parseLong(amountParts[0].replace(",", ""));

        // The cents don't have commas and need no replacing; we can create the long straight away.
        long cents = Long.parseLong(amountParts[1]);

        // Do math to calculate the amount in cents.
        long amount = dollars * 100 + cents;

        // Finally, create the Money instance.
        return new Money(amount, currency);
    }

}