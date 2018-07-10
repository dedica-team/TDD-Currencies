package org.haffson.currencies;

import org.junit.Assert;
import org.junit.Test;

// See CurrencyTest for details. This class could probably use _a lot_ more tests, mostly
// around parsing, but I'm lazy. :-)
public class MoneyTest {

    @Test
    public void testParse() {
        Money.parse("USD 1,000.00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFails() {
        Money.parse("USD 1.000,24");
    }

    @Test
    public void testFormat() {
        Money money = Money.parse("USD 1,000.24");
        Assert.assertEquals("USD 1,000.24", money.toString());
    }

    @Test
    public void testEquals() {
        Money a = Money.parse("USD 1,000.00");
        Money b = Money.parse("USD 500.00").plus(Money.parse("USD 500.00"));
        Assert.assertEquals(a, b);
    }

    @Test
    public void testInterestRates() {
        Money accountBalance = Money.parse("USD 1,000.00");
        accountBalance = accountBalance.times(1.2);
        Assert.assertEquals(Money.parse("USD 1,200.00"), accountBalance);
    }

    @Test
    public void testDeposit() {
        Money accountBalance = Money.nothing(Currency.forSymbol("USD"));    // Arrange
        accountBalance = accountBalance.plus(Money.parse("USD 1,000.00"));  // Act
        accountBalance = accountBalance.plus(Money.parse("USD 230.00"));    // ...act more
        accountBalance = accountBalance.plus(Money.parse("USD 4.56"));      // ...act so much!
        Assert.assertEquals(Money.parse("USD 1,234.56"), accountBalance);   // Assert
    }

    @Test
    public void testWithdraw() {
        Money accountBalance = Money.nothing(Currency.forSymbol("USD"));
        accountBalance = accountBalance.plus(Money.parse("USD 1,000.00"));
        accountBalance = accountBalance.minus(Money.parse("USD 543.22"));
        Assert.assertEquals(Money.parse("USD 456.78"), accountBalance);
    }

}
