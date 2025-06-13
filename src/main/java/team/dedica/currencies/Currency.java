package team.dedica.currencies;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Instances of this class represent a specific currency and can be used
 * to construct monetary values, i.e. instances of the {@link Money} class.
 */
public class Currency {

    /* Regex to validate symbols:
     * From the class of uppercase letters A-Z, expect exactly 3
     *
     * private, so it can be used only internally in this class
     * static, because we only need it once, and all instances will use the same
     * final, because we will never assign something new
     * UPPERCASE name, because that's how you tend to name static things in Java
     */
    private static final Pattern SYMBOLS = Pattern.compile("[A-Z]{3}");

    /* Each separate instance of Currency will store its own symbol here.
     *
     * private, so it can be used only internally
     * final, because we'll only assign it once (in the constructor)
     * NOT static, because it's a variable for each instance, not globally for the class
     */
    private final String symbol;

    /* We make a choice here to make the constructor private. No one outside of the class
     * can call it. Why? So that people are forced to use the factory method (way below)
     * where we can validate the symbol before actually calling the constructor.
     *
     * Could we put the validation in the constructor itself? Totally. We could put the
     * validation here and make the constructor public and everyone and their mom could
     * call "new Currency("FOO")". It's a matter of taste: I prefer it when a constructor
     * is guaranteed to succeed, so I don't put potentially failing validation in it. A
     * separate method is closer to semantics of "hey, you're calling something that
     * might fail". But really, everyone does it a bit differently.
     */
    private Currency(String symbol) {
        this.symbol = symbol;
    }

    /*
     * Just return the symbol, for whatever we might need it. See the toString() method
     * for more thoughts on this.
     */
    public String getSymbol() {
        return symbol;
    }

    /* Java has no === operator. It only has ==, which checks if 2 references are exactly
     * the same:
     *
     * String a = "AAA";
     * String b = a; // b points to the same thing as a, so b == a.
     *
     * String a = "AAA";
     * String b = "AAA"; // b is a thing on its own, so b != a.
     *
     * Every class can implement an equals method with a _custom_ implementation to decide
     * if another thing is semantically/structurally the same.
     *
     * So, in both of the above cases, a.equals(b) == true, and b.equals(a) == true
     */
    @Override
    public boolean equals(Object o) { // Note that we're given only an Object - it could be
        // anything, even a String or something.

        // Exact same reference? Obviously equal then. (Someone called a.equals(a))
        if (this == o) {
            return true;
        }

        // Is the other object "null" or of a different class? Then it can't be equal!
        // Someone called a.equals(null) or a.equals("string, lol, meaningless").
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // Cast (i.e. force) the other object to be a Currency. (This could fail at runtime
        // obviously if o is not a Currency, but we checked that in the line above.)
        Currency currency = (Currency) o;

        // Finally, this and the other object are equal if and only if their symbols are equal.
        return Objects.equals(symbol, currency.symbol);
    }

    /* A hashCode (a number calculated from the properties of this object) allows to
     * quickly check if 2 objects are _potentially_ the same. Because there are less
     * numbers (in a computer) than object variations, obviously different objects
     * _can_ have the same hash code. So:
     *
     * Same hash code => _maybe_ the same, do more checks
     * Different hash code => guaranteed to be different
     *
     * We won't really use hash code in this library, but it's good practice to
     * implement it whenever you also implement equals. It will be used in more
     * advanced stuff at some point.
     */
    @Override
    public int hashCode() {
        // Basically just use utility methods from Java and tell it to calculate a
        // hash code based on the symbol.
        return Objects.hash(symbol);
    }

    /* It's also good practice to implement the standard toString method which returns
     * a String representation of this class, which is used for example when printing
     * the object:
     *
     * Currency o = Currency.forSymbol("USD");
     * System.out.println(o); // same as System.out.println(o.toString());
     */
    @Override
    public String toString() {
        // We choose to simply return the symbol, but it could be more than that.
        // Never ever use toString for something else than "debugging" purposes. It's
        // bad practice to return something and intend it to be for more advanced
        // stuff than temporarily inspecting an object.
        //
        // Example: We only have the symbol now, but let's say we add the country of
        // a currency later. We would definitely want to return both symbol and country
        // in the toString method, because that's what we see in logs, debuggers, etc.
        // and it will be helpful to inspect all parts of currency in those places.
        //
        // If however we used toString in places where we _wanted_ to show only the symbol,
        // e.g. when printing a price or something, in all those places we would suddenly
        // see the country as well - and things would be fucked. That's why we have a
        // dedicated getSymbol() method above and almost never use toString directly.
        return symbol;
    }

    /**
     * Validates the given symbol and creates an instance of Currency.
     *
     * @param symbol 3 uppercase letters, e.g. USD
     * @return the instance
     * @throws NullPointerException when symbol is null
     * @throws IllegalArgumentException when symbol is not 3 uppercase letters
     */
    /*
     * public, so that it can be called from outside this class
     * static, because obviously we don't have any instance before calling this, so it
     * needs to be tied to the _class_, not to _objects of the class_.
     */
    public static Currency forSymbol(String symbol) {
        if (symbol == null) {
            throw new NullPointerException("Symbol must not be null");
        }
        if (!SYMBOLS.matcher(symbol).matches()) {
            throw new IllegalArgumentException("Symbol must be 3 uppercase letters, but was: " + symbol);
        }
        return new Currency(symbol);
    }

}
