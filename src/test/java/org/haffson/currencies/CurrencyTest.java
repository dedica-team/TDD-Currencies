package org.haffson.currencies;

import org.junit.Assert;
import org.junit.Test;

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
    public void testValidSymbol() {
        Currency usd = Currency.forSymbol("USD");
        Assert.assertEquals("USD", usd.getSymbol());
    }

    @Test(expected = IllegalArgumentException.class) // By expecting an exception, we declare that this
                                                     // test will only pass _if_ that exception is thrown.
                                                     // The test will actually _fail_ if no error happens!
    public void testInvalidSymbol() {
        Currency.forSymbol("bibergeld"); // Just calling this simple method will fail! (Rightly so!)
    }

    @Test(expected = NullPointerException.class)
    public void testNullSymbol() {
        Currency.forSymbol(null);
    }

    @Test
    public void testDifferentCurrenciesAreNotEqual() {
        Currency a = Currency.forSymbol("AAA");
        Currency b = Currency.forSymbol("BBB");
        Assert.assertNotEquals(a, b);
    }

    @Test
    public void testEqualCurrenciesAreNotTheSame() {
        Currency a1 = Currency.forSymbol("AAA");
        Currency a2 = Currency.forSymbol("AAA");
        Assert.assertNotSame(a1, a2);
    }

    @Test
    public void testEqualCurrenciesAreEqual() {
        Currency a1 = Currency.forSymbol("AAA");
        Currency a2 = Currency.forSymbol("AAA");
        Assert.assertEquals(a1, a2);
    }

}
