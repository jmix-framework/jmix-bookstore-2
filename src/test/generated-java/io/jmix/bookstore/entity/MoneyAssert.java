package io.jmix.bookstore.entity;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;

/**
 * {@link Money} specific assertions - Generated by CustomAssertionGenerator.
 */
@jakarta.annotation.Generated(value="assertj-assertions-generator")
public class MoneyAssert extends AbstractObjectAssert<MoneyAssert, Money> {

  /**
   * Creates a new <code>{@link MoneyAssert}</code> to make assertions on actual Money.
   * @param actual the Money we want to make assertions on.
   */
  public MoneyAssert(Money actual) {
    super(actual, MoneyAssert.class);
  }

  /**
   * An entry point for MoneyAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myMoney)</code> and get specific assertion with code completion.
   * @param actual the Money we want to make assertions on.
   * @return a new <code>{@link MoneyAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static MoneyAssert assertThat(Money actual) {
    return new MoneyAssert(actual);
  }

  /**
   * Verifies that the actual Money's amount is equal to the given one.
   * @param amount the given amount to compare the actual Money's amount to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Money's amount is not equal to the given one.
   */
  public MoneyAssert hasAmount(java.math.BigDecimal amount) {
    // check that actual Money we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting amount of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.math.BigDecimal actualAmount = actual.getAmount();
    if (!Objects.deepEquals(actualAmount, amount)) {
      failWithMessage(assertjErrorMessage, actual, amount, actualAmount);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual Money's currency is equal to the given one.
   * @param currency the given currency to compare the actual Money's currency to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Money's currency is not equal to the given one.
   */
  public MoneyAssert hasCurrency(Currency currency) {
    // check that actual Money we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting currency of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Currency actualCurrency = actual.getCurrency();
    if (!Objects.deepEquals(actualCurrency, currency)) {
      failWithMessage(assertjErrorMessage, actual, currency, actualCurrency);
    }

    // return the current assertion for method chaining
    return this;
  }

}
