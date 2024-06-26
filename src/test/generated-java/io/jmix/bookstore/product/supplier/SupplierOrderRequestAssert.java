package io.jmix.bookstore.product.supplier;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;

/**
 * {@link SupplierOrderRequest} specific assertions - Generated by CustomAssertionGenerator.
 */
@jakarta.annotation.Generated(value="assertj-assertions-generator")
public class SupplierOrderRequestAssert extends AbstractObjectAssert<SupplierOrderRequestAssert, SupplierOrderRequest> {

  /**
   * Creates a new <code>{@link SupplierOrderRequestAssert}</code> to make assertions on actual SupplierOrderRequest.
   * @param actual the SupplierOrderRequest we want to make assertions on.
   */
  public SupplierOrderRequestAssert(SupplierOrderRequest actual) {
    super(actual, SupplierOrderRequestAssert.class);
  }

  /**
   * An entry point for SupplierOrderRequestAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(mySupplierOrderRequest)</code> and get specific assertion with code completion.
   * @param actual the SupplierOrderRequest we want to make assertions on.
   * @return a new <code>{@link SupplierOrderRequestAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static SupplierOrderRequestAssert assertThat(SupplierOrderRequest actual) {
    return new SupplierOrderRequestAssert(actual);
  }

  /**
   * Verifies that the actual SupplierOrderRequest's comment is equal to the given one.
   * @param comment the given comment to compare the actual SupplierOrderRequest's comment to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's comment is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasComment(String comment) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting comment of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualComment = actual.getComment();
    if (!Objects.deepEquals(actualComment, comment)) {
      failWithMessage(assertjErrorMessage, actual, comment, actualComment);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrderRequest's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual SupplierOrderRequest's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's createdBy is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasCreatedBy(String createdBy) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual SupplierOrderRequest's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's createdDate is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual SupplierOrderRequest's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's deletedBy is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasDeletedBy(String deletedBy) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual SupplierOrderRequest's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's deletedDate is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's id is equal to the given one.
   * @param id the given id to compare the actual SupplierOrderRequest's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's id is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasId(java.util.UUID id) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual SupplierOrderRequest's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's lastModifiedBy is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual SupplierOrderRequest's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's lastModifiedDate is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's product is equal to the given one.
   * @param product the given product to compare the actual SupplierOrderRequest's product to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's product is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasProduct(io.jmix.bookstore.product.Product product) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting product of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.product.Product actualProduct = actual.getProduct();
    if (!Objects.deepEquals(actualProduct, product)) {
      failWithMessage(assertjErrorMessage, actual, product, actualProduct);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrderRequest's requestedAmount is equal to the given one.
   * @param requestedAmount the given requestedAmount to compare the actual SupplierOrderRequest's requestedAmount to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's requestedAmount is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasRequestedAmount(Integer requestedAmount) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting requestedAmount of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    Integer actualRequestedAmount = actual.getRequestedAmount();
    if (!Objects.deepEquals(actualRequestedAmount, requestedAmount)) {
      failWithMessage(assertjErrorMessage, actual, requestedAmount, actualRequestedAmount);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrderRequest's requestedBy is equal to the given one.
   * @param requestedBy the given requestedBy to compare the actual SupplierOrderRequest's requestedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's requestedBy is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasRequestedBy(io.jmix.bookstore.entity.User requestedBy) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting requestedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.entity.User actualRequestedBy = actual.getRequestedBy();
    if (!Objects.deepEquals(actualRequestedBy, requestedBy)) {
      failWithMessage(assertjErrorMessage, actual, requestedBy, actualRequestedBy);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrderRequest's status is equal to the given one.
   * @param status the given status to compare the actual SupplierOrderRequest's status to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's status is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasStatus(SupplierOrderRequestStatus status) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting status of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    SupplierOrderRequestStatus actualStatus = actual.getStatus();
    if (!Objects.deepEquals(actualStatus, status)) {
      failWithMessage(assertjErrorMessage, actual, status, actualStatus);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual SupplierOrderRequest's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual SupplierOrderRequest's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's tenant is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasTenant(String tenant) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
   * Verifies that the actual SupplierOrderRequest's version is equal to the given one.
   * @param version the given version to compare the actual SupplierOrderRequest's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual SupplierOrderRequest's version is not equal to the given one.
   */
  public SupplierOrderRequestAssert hasVersion(Integer version) {
    // check that actual SupplierOrderRequest we want to make assertions on is not null.
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
