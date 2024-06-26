package io.jmix.bookstore.product.supplier;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.internal.Iterables;

/**
 * {@link SupplierOrder} specific assertions - Generated by CustomAssertionGenerator.
 */
@jakarta.annotation.Generated(value="assertj-assertions-generator")
public class SupplierOrderAssert extends AbstractObjectAssert<SupplierOrderAssert, SupplierOrder> {

  /**
   * Creates a new <code>{@link SupplierOrderAssert}</code> to make assertions on actual SupplierOrder.
   * @param actual the SupplierOrder we want to make assertions on.
   */
  public SupplierOrderAssert(SupplierOrder actual) {
    super(actual, SupplierOrderAssert.class);
  }

  /**
   * An entry point for SupplierOrderAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(mySupplierOrder)</code> and get specific assertion with code completion.
   * @param actual the SupplierOrder we want to make assertions on.
   * @return a new <code>{@link SupplierOrderAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static SupplierOrderAssert assertThat(SupplierOrder actual) {
    return new SupplierOrderAssert(actual);
  }

  /**
   * Verifies that the actual SupplierOrder's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual SupplierOrder's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's createdBy is not equal to the given one.
   */
  public SupplierOrderAssert hasCreatedBy(String createdBy) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualCreatedBy = actual.getCreatedBy();
    if (!Objects.deepEquals(actualCreatedBy, createdBy)) {
      failWithMessage(assertjErrorMessage, actual, createdBy, actualCreatedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual SupplierOrder's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's createdDate is not equal to the given one.
   */
  public SupplierOrderAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting createdDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualCreatedDate = actual.getCreatedDate();
    if (!Objects.deepEquals(actualCreatedDate, createdDate)) {
      failWithMessage(assertjErrorMessage, actual, createdDate, actualCreatedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual SupplierOrder's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's deletedBy is not equal to the given one.
   */
  public SupplierOrderAssert hasDeletedBy(String deletedBy) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualDeletedBy = actual.getDeletedBy();
    if (!Objects.deepEquals(actualDeletedBy, deletedBy)) {
      failWithMessage(assertjErrorMessage, actual, deletedBy, actualDeletedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual SupplierOrder's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's deletedDate is not equal to the given one.
   */
  public SupplierOrderAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting deletedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualDeletedDate = actual.getDeletedDate();
    if (!Objects.deepEquals(actualDeletedDate, deletedDate)) {
      failWithMessage(assertjErrorMessage, actual, deletedDate, actualDeletedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's id is equal to the given one.
   * @param id the given id to compare the actual SupplierOrder's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's id is not equal to the given one.
   */
  public SupplierOrderAssert hasId(java.util.UUID id) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.UUID actualId = actual.getId();
    if (!Objects.deepEquals(actualId, id)) {
      failWithMessage(assertjErrorMessage, actual, id, actualId);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual SupplierOrder's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's lastModifiedBy is not equal to the given one.
   */
  public SupplierOrderAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualLastModifiedBy = actual.getLastModifiedBy();
    if (!Objects.deepEquals(actualLastModifiedBy, lastModifiedBy)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedBy, actualLastModifiedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual SupplierOrder's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's lastModifiedDate is not equal to the given one.
   */
  public SupplierOrderAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting lastModifiedDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.util.Date actualLastModifiedDate = actual.getLastModifiedDate();
    if (!Objects.deepEquals(actualLastModifiedDate, lastModifiedDate)) {
      failWithMessage(assertjErrorMessage, actual, lastModifiedDate, actualLastModifiedDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderDate is equal to the given one.
   * @param orderDate the given orderDate to compare the actual SupplierOrder's orderDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's orderDate is not equal to the given one.
   */
  public SupplierOrderAssert hasOrderDate(java.time.LocalDate orderDate) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting orderDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    java.time.LocalDate actualOrderDate = actual.getOrderDate();
    if (!Objects.deepEquals(actualOrderDate, orderDate)) {
      failWithMessage(assertjErrorMessage, actual, orderDate, actualOrderDate);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderForm is equal to the given one.
   * @param orderForm the given orderForm to compare the actual SupplierOrder's orderForm to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's orderForm is not equal to the given one.
   */
  public SupplierOrderAssert hasOrderForm(io.jmix.core.FileRef orderForm) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting orderForm of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.core.FileRef actualOrderForm = actual.getOrderForm();
    if (!Objects.deepEquals(actualOrderForm, orderForm)) {
      failWithMessage(assertjErrorMessage, actual, orderForm, actualOrderForm);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines contains the given SupplierOrderLine elements.
   * @param orderLines the given elements that should be contained in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines does not contain all given SupplierOrderLine elements.
   */
  public SupplierOrderAssert hasOrderLines(SupplierOrderLine... orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines contains the given SupplierOrderLine elements in Collection.
   * @param orderLines the given elements that should be contained in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines does not contain all given SupplierOrderLine elements.
   */
  public SupplierOrderAssert hasOrderLines(java.util.Collection<? extends SupplierOrderLine> orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContains(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines contains <b>only</b> the given SupplierOrderLine elements and nothing else in whatever order.
   * @param orderLines the given elements that should be contained in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines does not contain all given SupplierOrderLine elements.
   */
  public SupplierOrderAssert hasOnlyOrderLines(SupplierOrderLine... orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines contains <b>only</b> the given SupplierOrderLine elements in Collection and nothing else in whatever order.
   * @param orderLines the given elements that should be contained in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines does not contain all given SupplierOrderLine elements.
   */
  public SupplierOrderAssert hasOnlyOrderLines(java.util.Collection<? extends SupplierOrderLine> orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
    Iterables.instance().assertContainsOnly(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines does not contain the given SupplierOrderLine elements.
   *
   * @param orderLines the given elements that should not be in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines contains any given SupplierOrderLine elements.
   */
  public SupplierOrderAssert doesNotHaveOrderLines(SupplierOrderLine... orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine varargs is not null.
    if (orderLines == null) failWithMessage("Expecting orderLines parameter not to be null.");

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrderLines(), orderLines);

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's orderLines does not contain the given SupplierOrderLine elements in Collection.
   *
   * @param orderLines the given elements that should not be in actual SupplierOrder's orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines contains any given SupplierOrderLine elements.
   */
  public SupplierOrderAssert doesNotHaveOrderLines(java.util.Collection<? extends SupplierOrderLine> orderLines) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // check that given SupplierOrderLine collection is not null.
    if (orderLines == null) {
      failWithMessage("Expecting orderLines parameter not to be null.");
      return this; // to fool Eclipse "Null pointer access" warning on toArray.
    }

    // check with standard error message (use overridingErrorMessage before contains to set your own message).
    Iterables.instance().assertDoesNotContain(info, actual.getOrderLines(), orderLines.toArray());

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder has no orderLines.
   * @return this assertion object.
   * @throws AssertionError if the actual SupplierOrder's orderLines is not empty.
   */
  public SupplierOrderAssert hasNoOrderLines() {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // we override the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting :\n  <%s>\nnot to have orderLines but had :\n  <%s>";

    // check
    if (actual.getOrderLines().iterator().hasNext()) {
      failWithMessage(assertjErrorMessage, actual, actual.getOrderLines());
    }

    // return the current assertion for method chaining
    return this;
  }


  /**
   * Verifies that the actual SupplierOrder's status is equal to the given one.
   * @param status the given status to compare the actual SupplierOrder's status to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's status is not equal to the given one.
   */
  public SupplierOrderAssert hasStatus(SupplierOrderStatus status) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting status of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    SupplierOrderStatus actualStatus = actual.getStatus();
    if (!Objects.deepEquals(actualStatus, status)) {
      failWithMessage(assertjErrorMessage, actual, status, actualStatus);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's supplier is equal to the given one.
   * @param supplier the given supplier to compare the actual SupplierOrder's supplier to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's supplier is not equal to the given one.
   */
  public SupplierOrderAssert hasSupplier(Supplier supplier) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting supplier of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Supplier actualSupplier = actual.getSupplier();
    if (!Objects.deepEquals(actualSupplier, supplier)) {
      failWithMessage(assertjErrorMessage, actual, supplier, actualSupplier);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual SupplierOrder's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's tenant is not equal to the given one.
   */
  public SupplierOrderAssert hasTenant(String tenant) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting tenant of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualTenant = actual.getTenant();
    if (!Objects.deepEquals(actualTenant, tenant)) {
      failWithMessage(assertjErrorMessage, actual, tenant, actualTenant);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrder's version is equal to the given one.
   * @param version the given version to compare the actual SupplierOrder's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrder's version is not equal to the given one.
   */
  public SupplierOrderAssert hasVersion(Integer version) {
    // check that actual SupplierOrder we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting version of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualVersion = actual.getVersion();
    if (!Objects.deepEquals(actualVersion, version)) {
      failWithMessage(assertjErrorMessage, actual, version, actualVersion);
    }

    // return the current assertion for method chaining
    return this;
  }

}
