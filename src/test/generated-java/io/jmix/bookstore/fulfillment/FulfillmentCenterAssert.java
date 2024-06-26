package io.jmix.bookstore.fulfillment;

import java.util.Objects;
import org.assertj.core.api.AbstractObjectAssert;

/**
 * {@link FulfillmentCenter} specific assertions - Generated by CustomAssertionGenerator.
 */
@jakarta.annotation.Generated(value="assertj-assertions-generator")
public class FulfillmentCenterAssert extends AbstractObjectAssert<FulfillmentCenterAssert, FulfillmentCenter> {

  /**
   * Creates a new <code>{@link FulfillmentCenterAssert}</code> to make assertions on actual FulfillmentCenter.
   * @param actual the FulfillmentCenter we want to make assertions on.
   */
  public FulfillmentCenterAssert(FulfillmentCenter actual) {
    super(actual, FulfillmentCenterAssert.class);
  }

  /**
   * An entry point for FulfillmentCenterAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
   * With a static import, one can write directly: <code>assertThat(myFulfillmentCenter)</code> and get specific assertion with code completion.
   * @param actual the FulfillmentCenter we want to make assertions on.
   * @return a new <code>{@link FulfillmentCenterAssert}</code>
   */
  @org.assertj.core.util.CheckReturnValue
  public static FulfillmentCenterAssert assertThat(FulfillmentCenter actual) {
    return new FulfillmentCenterAssert(actual);
  }

  /**
   * Verifies that the actual FulfillmentCenter's address is equal to the given one.
   * @param address the given address to compare the actual FulfillmentCenter's address to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's address is not equal to the given one.
   */
  public FulfillmentCenterAssert hasAddress(io.jmix.bookstore.entity.Address address) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting address of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.entity.Address actualAddress = actual.getAddress();
    if (!Objects.deepEquals(actualAddress, address)) {
      failWithMessage(assertjErrorMessage, actual, address, actualAddress);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual FulfillmentCenter's createdBy is equal to the given one.
   * @param createdBy the given createdBy to compare the actual FulfillmentCenter's createdBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's createdBy is not equal to the given one.
   */
  public FulfillmentCenterAssert hasCreatedBy(String createdBy) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's createdDate is equal to the given one.
   * @param createdDate the given createdDate to compare the actual FulfillmentCenter's createdDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's createdDate is not equal to the given one.
   */
  public FulfillmentCenterAssert hasCreatedDate(java.util.Date createdDate) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's deletedBy is equal to the given one.
   * @param deletedBy the given deletedBy to compare the actual FulfillmentCenter's deletedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's deletedBy is not equal to the given one.
   */
  public FulfillmentCenterAssert hasDeletedBy(String deletedBy) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's deletedDate is equal to the given one.
   * @param deletedDate the given deletedDate to compare the actual FulfillmentCenter's deletedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's deletedDate is not equal to the given one.
   */
  public FulfillmentCenterAssert hasDeletedDate(java.util.Date deletedDate) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's geometry is equal to the given one.
   * @param geometry the given geometry to compare the actual FulfillmentCenter's geometry to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's geometry is not equal to the given one.
   */
  public FulfillmentCenterAssert hasGeometry(org.locationtech.jts.geom.Point geometry) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting geometry of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    org.locationtech.jts.geom.Point actualGeometry = actual.getGeometry();
    if (!Objects.deepEquals(actualGeometry, geometry)) {
      failWithMessage(assertjErrorMessage, actual, geometry, actualGeometry);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual FulfillmentCenter's id is equal to the given one.
   * @param id the given id to compare the actual FulfillmentCenter's id to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's id is not equal to the given one.
   */
  public FulfillmentCenterAssert hasId(java.util.UUID id) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's lastModifiedBy is equal to the given one.
   * @param lastModifiedBy the given lastModifiedBy to compare the actual FulfillmentCenter's lastModifiedBy to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's lastModifiedBy is not equal to the given one.
   */
  public FulfillmentCenterAssert hasLastModifiedBy(String lastModifiedBy) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's lastModifiedDate is equal to the given one.
   * @param lastModifiedDate the given lastModifiedDate to compare the actual FulfillmentCenter's lastModifiedDate to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's lastModifiedDate is not equal to the given one.
   */
  public FulfillmentCenterAssert hasLastModifiedDate(java.util.Date lastModifiedDate) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's name is equal to the given one.
   * @param name the given name to compare the actual FulfillmentCenter's name to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's name is not equal to the given one.
   */
  public FulfillmentCenterAssert hasName(String name) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    String actualName = actual.getName();
    if (!Objects.deepEquals(actualName, name)) {
      failWithMessage(assertjErrorMessage, actual, name, actualName);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual FulfillmentCenter's region is equal to the given one.
   * @param region the given region to compare the actual FulfillmentCenter's region to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's region is not equal to the given one.
   */
  public FulfillmentCenterAssert hasRegion(io.jmix.bookstore.employee.Region region) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting region of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

    // null safe check
    io.jmix.bookstore.employee.Region actualRegion = actual.getRegion();
    if (!Objects.deepEquals(actualRegion, region)) {
      failWithMessage(assertjErrorMessage, actual, region, actualRegion);
    }

    // return the current assertion for method chaining
    return this;
  }

  /**
   * Verifies that the actual FulfillmentCenter's tenant is equal to the given one.
   * @param tenant the given tenant to compare the actual FulfillmentCenter's tenant to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's tenant is not equal to the given one.
   */
  public FulfillmentCenterAssert hasTenant(String tenant) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
   * Verifies that the actual FulfillmentCenter's version is equal to the given one.
   * @param version the given version to compare the actual FulfillmentCenter's version to.
   * @return this assertion object.
   * @throws AssertionError - if the actual FulfillmentCenter's version is not equal to the given one.
   */
  public FulfillmentCenterAssert hasVersion(Integer version) {
    // check that actual FulfillmentCenter we want to make assertions on is not null.
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
