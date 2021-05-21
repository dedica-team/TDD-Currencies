package team.dedica.currencies;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * You can run this test class directly from IntelliJ, just like an application. It will show
 * you which tests passed and which tests failed.
 *
 * Each test case (method) is a small piece of code, often in the AAA pattern:
 *
 * Arrange: Create the objects you want to test, including the SUT (subject under test, the
 *          thing you want to test).
 *
 * Act: Do some stuff that often modifies the objects. (This can be better seen in the MoneyTest
 *      class.)
 *
 * Assert: Check if our expectations hold true. By using the Assert classes and methods, we
 *         can report things in a way that work nicely with reporting in IntelliJ and other tools
 */
public class CurrencyTest {

    @Test
    void testValidSymbol() {
        Currency usd = Currency.forSymbol("USD");
        assertThat(usd.getSymbol()).isEqualTo("USD");
    }

    @Test
    void testInvalidSymbol() {
        // By expecting an exception, we declare that this
        // test will only pass _if_ that exception is thrown.
        // The test will actually _fail_ if no error happens!
        assertThatThrownBy(() -> Currency.forSymbol("bibergeld")) // Just calling this simple method will fail! (Rightly so!)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testNullSymbol() {
        assertThatThrownBy(() -> Currency.forSymbol(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testDifferentCurrenciesAreNotEqual() {
        Currency a = Currency.forSymbol("AAA");
        Currency b = Currency.forSymbol("BBB");
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void testEqualCurrenciesAreNotTheSame() {
        Currency a1 = Currency.forSymbol("AAA");
        Currency a2 = Currency.forSymbol("AAA");
        assertThat(a1).isNotSameAs(a2);
    }

    @Test
    void testEqualCurrenciesAreEqual() {
        Currency a1 = Currency.forSymbol("AAA");
        Currency a2 = Currency.forSymbol("AAA");
        assertThat(a1).isEqualTo(a2);
    }

}
