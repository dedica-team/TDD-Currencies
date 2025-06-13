package team.dedica.currencies;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// See CurrencyTest for details. This class could probably use _a lot_ more tests, mostly
// around parsing, but I'm lazy. :-)
public class MoneyTest {

    @Test
    void testParse() {
        Money.parse("USD 1,000.00");
    }

    @Test
    public void testParseFails() {
        assertThatThrownBy(() -> Money.parse("USD 1.000,24"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFormat() {
        Money money = Money.parse("USD 1,000.24");
        assertThat(money.toString()).isEqualTo("USD 1,000.24");
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

    @Test
    void shouldNotAllowAddingDifferentCurrencies() {
        Money usd = Money.parse("USD 10.00");
        Money vef = Money.parse("VEF 1,000,000.00");

        // Expect an exception when mixing currencies
        assertThatThrownBy(() -> usd.plus(vef))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot add Money values with different currencies");
    }

}
