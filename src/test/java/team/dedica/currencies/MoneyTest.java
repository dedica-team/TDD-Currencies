package team.dedica.currencies;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

// See CurrencyTest for details. This class could probably use _a lot_ more tests, mostly
// around parsing, but I'm lazy. :-)
class MoneyTest {

    @Test
    void testParse() {
        assertThatNoException()
                .isThrownBy(() -> Money.parse("USD 1,000.00"));
    }

    @Test
    void testParseFails() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Money.parse("USD 1.000,24"));
    }

    @Test
    void testFormat() {
        Money money = Money.parse("USD 1,000.24");
        assertThat(money).hasToString("USD 1,000.24");
    }

    @Test
    void testEquals() {
        Money a = Money.parse("USD 1,000.00");
        Money b = Money.parse("USD 500.00").plus(Money.parse("USD 500.00"));
        assertThat(a).isEqualTo(b);
    }

    @Test
    void testInterestRates() {
        Money accountBalance = Money.parse("USD 1,000.00");
        accountBalance = accountBalance.times(1.2);
        assertThat(accountBalance).isEqualTo(Money.parse("USD 1,200.00"));
    }

    @Test
    void testDeposit() {
        Money accountBalance = Money.nothing(Currency.forSymbol("USD"));    // Arrange
        accountBalance = accountBalance.plus(Money.parse("USD 1,000.00"));  // Act
        accountBalance = accountBalance.plus(Money.parse("USD 230.00"));    // ...act more
        accountBalance = accountBalance.plus(Money.parse("USD 4.56"));      // ...act so much!
        assertThat(accountBalance).isEqualTo(Money.parse("USD 1,234.56")); // Assert
    }

    @Test
    void testWithdraw() {
        Money accountBalance = Money.nothing(Currency.forSymbol("USD"));
        accountBalance = accountBalance.plus(Money.parse("USD 1,000.00"));
        accountBalance = accountBalance.minus(Money.parse("USD 543.22"));
        assertThat(accountBalance).isEqualTo(Money.parse("USD 456.78"));
    }

}
