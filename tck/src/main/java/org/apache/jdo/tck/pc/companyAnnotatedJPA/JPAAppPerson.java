/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jdo.tck.pc.companyAnnotatedJPA;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.jdo.tck.pc.company.IAddress;
import org.apache.jdo.tck.pc.company.IPerson;
import org.apache.jdo.tck.util.DeepEquality;
import org.apache.jdo.tck.util.EqualityHelper;
import org.apache.jdo.tck.util.JDOCustomDateEditor;

/** This class represents a person. */
@Entity
@Table(name = "persons")
@IdClass(org.apache.jdo.tck.pc.companyAnnotatedJPA.JPAAppPerson.Oid.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "DISCRIMINATOR")
public class JPAAppPerson
    implements IPerson, Serializable, Comparable<IPerson>, Comparator<IPerson>, DeepEquality {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "PERSONID")
  private long personid;

  @Column(name = "FIRSTNAME")
  private String firstname;

  @Column(name = "LASTNAME")
  private String lastname;

  @Basic(optional = true, fetch = FetchType.LAZY)
  @Column(name = "MIDDLENAME")
  private String middlename;

  @Column(name = "BIRTHDATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date birthdate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "street", column = @Column(name = "STREET")),
    @AttributeOverride(name = "city", column = @Column(name = "CITY")),
    @AttributeOverride(name = "state", column = @Column(name = "STATE")),
    @AttributeOverride(name = "zipcode", column = @Column(name = "ZIPCODE")),
    @AttributeOverride(name = "country", column = @Column(name = "COUNTRY"))
  })
  private JPAAppAddress address;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @MapKey(name = "type")
  private Map<String, JPAAppPhoneNumber> phoneNumbers = new HashMap<>();

  @ElementCollection
  @CollectionTable(name = "employee_languages", joinColumns = @JoinColumn(name = "EMPID"))
  private Set<String> languages = new HashSet<>();

  /** This is the JDO-required no-args constructor. */
  protected JPAAppPerson() {}

  /**
   * Construct a <code>JPAAppPerson</code> instance.
   *
   * @param personid The person identifier.
   * @param firstname The person's first name.
   * @param lastname The person's last name.
   * @param middlename The person's middle name.
   * @param birthdate The person's birthdate.
   */
  public JPAAppPerson(
      long personid, String firstname, String lastname, String middlename, Date birthdate) {
    this.personid = personid;
    this.firstname = firstname;
    this.lastname = lastname;
    this.middlename = middlename;
    this.birthdate = birthdate;
  }

  /**
   * Construct a <code>JPAAppPerson</code> instance.
   *
   * @param personid The person identifier.
   * @param firstname The person's first name.
   * @param lastname The person's last name.
   * @param middlename The person's middle name.
   * @param birthdate The person's birthdate.
   * @param address The person's address.
   */
  public JPAAppPerson(
      long personid,
      String firstname,
      String lastname,
      String middlename,
      Date birthdate,
      IAddress address) {
    this(personid, firstname, lastname, middlename, birthdate);
    this.address = (JPAAppAddress) address;
  }

  /**
   * Set the id associated with this object.
   *
   * @param id the id.
   */
  public void setPersonid(long id) {
    if (this.personid != 0) throw new IllegalStateException("Id is already set.");
    this.personid = id;
  }

  /**
   * Get the person's id.
   *
   * @return The personid.
   */
  public long getPersonid() {
    return personid;
  }

  /**
   * Get the person's last name.
   *
   * @return The last name.
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Set the person's last name.
   *
   * @param lastname The last name.
   */
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
   * Get the person's first name.
   *
   * @return The first name.
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Set the person's first name.
   *
   * @param firstname The first name.
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Get the person's middle name.
   *
   * @return The middle name.
   */
  public String getMiddlename() {
    return middlename;
  }

  /**
   * Set the person's middle name.
   *
   * @param middlename The middle name.
   */
  public void setMiddlename(String middlename) {
    this.middlename = middlename;
  }

  /**
   * Get the address.
   *
   * @return The address.
   */
  public IAddress getAddress() {
    return address;
  }

  /**
   * Set the address.
   *
   * @param address The address.
   */
  public void setAddress(IAddress address) {
    this.address = (JPAAppAddress) address;
  }

  /**
   * Get the person's birthdate.
   *
   * @return The person's birthdate.
   */
  public Date getBirthdate() {
    return birthdate;
  }

  /**
   * Set the person's birthdate.
   *
   * @param birthdate The person's birthdate.
   */
  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  /**
   * Get the map of phone numbers as an unmodifiable map.
   *
   * @return A Map of phone numbers.
   */
  public Map<String, String> getPhoneNumbers() {
    return (convertPhone2String(phoneNumbers));
  }

  /**
   * Get the phone number for the specified phone number type.
   *
   * @param type The phone number type ("home", "work", "mobile", etc.).
   * @return The phone number associated with specified type, or <code>null</code> if there was no
   *     phone number for the type.
   */
  public String getPhoneNumber(String type) {
    JPAAppPhoneNumber pnum = phoneNumbers.get(type);
    return pnum.getPhoneNumber();
  }

  /**
   * Associates the specified phone number with the specified type in the map of phone numbers of
   * this person.
   *
   * @param type The phone number type ("home", "work", "mobile", etc.).
   * @param phoneNumber The phone number
   * @return The previous phone number associated with specified type, or <code>null</code> if there
   *     was no phone number for the type.
   */
  public String putPhoneNumber(String type, String phoneNumber) {
    JPAAppPhoneNumber pnum = phoneNumbers.get(type);
    String pnumAsString = null;
    if (pnum != null) {
      pnumAsString = pnum.getPhoneNumber(); // old val
    }
    pnum = phoneNumbers.put(type, new JPAAppPhoneNumber(this, type, phoneNumber));
    return pnumAsString;
  }

  /**
   * Remove a phoneNumber from the map of phone numbers.
   *
   * @param type The phone number type ("home", "work", "mobile", etc.).
   * @return The previous phone number associated with specified type, or <code>null</code> if there
   *     was no phone number for the type.
   */
  public String removePhoneNumber(String type) {
    JPAAppPhoneNumber pnum = phoneNumbers.get(type);
    if (pnum == null) return null;
    String pnumAsString = pnum.getPhoneNumber(); // old val
    pnum = phoneNumbers.remove(type);
    return pnumAsString;
  }

  /**
   * Set the phoneNumber map to be in this person.
   *
   * @param phoneNumbers A Map of phoneNumbers for this person.
   */
  public void setPhoneNumbers(Map<String, String> phoneNumbers) {
    this.phoneNumbers = (phoneNumbers != null) ? convertString2Phone(phoneNumbers) : null;
  }

  /**
   * Get the map of languages as an unmodifiable Set.
   *
   * @return The set of languages, as an unmodifiable set.
   */
  public Set<String> getLanguages() {
    return Collections.unmodifiableSet(languages);
  }

  /**
   * Set the languages set to be in this person.
   *
   * @param languages The map of phoneNumbers for this person.
   */
  public void setLanguages(Set<String> languages) {
    this.languages = new HashSet(languages);
  }

  /**
   * Converts HashMap of String, String to HashMap of String, JPAAppPhoneNmber
   *
   * @param pnums Map of phoneNumbers
   * @return Map of phoneNumbers
   */
  protected Map<String, JPAAppPhoneNumber> convertString2Phone(Map<String, String> pnums) {
    Map<String, JPAAppPhoneNumber> retval = new HashMap<>();
    for (Map.Entry<String, String> entry : pnums.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      JPAAppPhoneNumber newValue = new JPAAppPhoneNumber(this, key, value);
      retval.put(key, newValue);
    }
    return retval;
  }

  /**
   * Converts HashMap of String, JPAAppPhoneNumber to HashMap of String, String
   *
   * @param pnums Map of phoneNumbers
   * @return Map of phoneNumbers
   */
  protected Map<String, String> convertPhone2String(Map<String, JPAAppPhoneNumber> pnums) {
    Map<String, String> retval = new HashMap<>();
    for (Map.Entry<String, JPAAppPhoneNumber> entry : pnums.entrySet()) {
      String key = entry.getKey();
      JPAAppPhoneNumber value = entry.getValue();
      String newValue = value.getPhoneNumber();
      retval.put(key, newValue);
    }
    return retval;
  }

  /**
   * Returns a String representation of a <code>JPAAppPerson</code> object.
   *
   * @return a string representation of a <code>JPAAppPerson</code> object.
   */
  public String toString() {
    return "JPAPerson(" + getFieldRepr() + ")";
  }

  /**
   * Returns a String representation of the non-relationship fields.
   *
   * @return a String representation of the non-relationship fields.
   */
  protected String getFieldRepr() {
    StringBuilder rc = new StringBuilder();
    rc.append(personid);
    rc.append(", ").append(lastname);
    rc.append(", ").append(firstname);
    rc.append(", born ").append(JDOCustomDateEditor.getDateRepr(birthdate));
    rc.append(", phone ").append(convertPhone2String(phoneNumbers));
    rc.append(", languages ").append(languages);
    return rc.toString();
  }

  /**
   * Returns <code>true</code> if all the fields of this instance are deep equal to the coresponding
   * fields of the specified JPAAppPerson.
   *
   * @param other the object with which to compare.
   * @param helper EqualityHelper to keep track of instances that have already been processed.
   * @return <code>true</code> if all the fields are deep equal; <code>false</code> otherwise.
   * @throws ClassCastException if the specified instances' type prevents it from being compared to
   *     this instance.
   */
  public boolean deepCompareFields(Object other, EqualityHelper helper) {
    JPAAppPerson otherPerson = (JPAAppPerson) other;
    String where = "JPAPerson<" + personid + ">";
    return helper.equals(personid, otherPerson.getPersonid(), where + ".personid")
        & helper.equals(firstname, otherPerson.getFirstname(), where + ".firstname")
        & helper.equals(lastname, otherPerson.getLastname(), where + ".lastname")
        & helper.equals(middlename, otherPerson.getMiddlename(), where + ".middlename")
        & helper.equals(birthdate, otherPerson.getBirthdate(), where + ".birthdate")
        & helper.deepEquals(address, otherPerson.getAddress(), where + ".address")
        & helper.deepEquals(
            convertPhone2String(phoneNumbers),
            otherPerson.getPhoneNumbers(),
            where + ".phoneNumbers")
        & helper.deepEquals(languages, otherPerson.getLanguages(), where + ".languages");
  }

  /**
   * Compares this object with the specified JPAAppPerson object for order. Returns a negative
   * integer, zero, or a positive integer as this object is less than, equal to, or greater than the
   * specified object.
   *
   * @param other The JPAAppPerson object to be compared.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *     or greater than the specified JPAAppPerson object.
   */
  public int compareTo(IPerson other) {
    return compare(this, other);
  }

  /**
   * Compares its two IPerson arguments for order. Returns a negative integer, zero, or a positive
   * integer as the first argument is less than, equal to, or greater than the second.
   *
   * @param o1 the first IPerson object to be compared.
   * @param o2 the second IPerson object to be compared.
   * @return a negative integer, zero, or a positive integer as the first object is less than, equal
   *     to, or greater than the second object.
   */
  public int compare(IPerson o1, IPerson o2) {
    return EqualityHelper.compare(o1.getPersonid(), o2.getPersonid());
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the object with which to compare.
   * @return <code>true</code> if this object is the same as the obj argument; <code>false</code>
   *     otherwise.
   */
  public boolean equals(Object obj) {
    if (obj instanceof JPAAppPerson) {
      return compareTo((JPAAppPerson) obj) == 0;
    }
    return false;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object.
   */
  public int hashCode() {
    return (int) personid;
  }

  /**
   * This class is used to represent the application identifier for the <code>Person</code> class.
   */
  public static class Oid implements Serializable, Comparable<Oid> {

    private static final long serialVersionUID = 1L;

    /**
     * This field represents the identifier for the <code>Person</code> class. It must match a field
     * in the <code>Person</code> class in both name and type.
     */
    public long personid;

    /** The required public no-arg constructor. */
    public Oid() {}

    /**
     * Initialize the identifier.
     *
     * @param personid The person identifier.
     */
    public Oid(long personid) {
      this.personid = personid;
    }

    public Oid(String s) {
      personid = Long.parseLong(justTheId(s));
    }

    public String toString() {
      return this.getClass().getName() + ": " + personid;
    }

    /** */
    public boolean equals(java.lang.Object obj) {
      if (obj == null || !this.getClass().equals(obj.getClass())) return (false);
      Oid o = (Oid) obj;
      if (this.personid != o.personid) return (false);
      return (true);
    }

    /** */
    public int hashCode() {
      return ((int) personid);
    }

    protected static String justTheId(String str) {
      return str.substring(str.indexOf(':') + 1);
    }

    /** */
    public int compareTo(Oid obj) {
      return Long.compare(personid, obj.personid);
    }
  }
}
