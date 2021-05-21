# Currencies

## About

A simple library to handle monetary values.

## Examples

```java
Currency usd = Currency.forSymbol("USD"); // create currency object with symbol "USD"

Money accountBalance = Money.nothing(usd); // create monetary value of 0 for this currency
System.out.println(accountBalance); // USD 0.00

accountBalance = accountBalance.plus(Money.parse("USD 1.00")); // add 2 monetary values
accountBalance = accountBalance.plus(Money.parse("USD 1.00")); // add even more
System.out.println(accountBalance); // USD 2.00
```

# Test Driven Java Development

## Task

- Clone and setup in IntelliJ
- Familiarise yourself with the code
- Reproduce, understand, and fix the bug below, and make sure that it can't accidentally be introduced again

## TDD

Test driven development means that you'll find and fix/add tests about the bug first (they will fail!), then fix the code (the tests will then pass), and you automatically ensured that the bug can't be introduced again (whoever changes your code back, will run into failing tests).

## Bug report

_Hey support,_

_we've been using your currencies library for quite a while now and had overall good results with it. However, yesterday we've run into a huge issue:_

_A customer opened an account in our New York branch and obviously her local currency has been set to USD. The next day however, she deposited 1,000,000 venezuelan bolivars (VEF) and that amount HAS BEEN CREDITED TO HER ACCOUNT IN US DOLLARS. (In case you don't know: 1,000,000 VEF are clearly not worth 1,000,000 USD!)_

_We are in a lot of debt now!!!_

_WTF guys??_

_Can you look into this? Adding foreign currencies to an account seems like a bad thing and should be prevented._


