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
package org.apache.jdo.tck.query.jdoql.methods;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.apache.jdo.tck.pc.query.LocalDateSample;
import org.apache.jdo.tck.query.QueryTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * <B>Title:</B> Supported LocalDate methods. <br>
 * <B>Keywords:</B> query <br>
 * <B>Assertion ID:</B> A14.6.2-60. <br>
 * <B>Assertion Description: </B> New supported LocalDate methods:
 *
 * <ul>
 *   <li>getDayOfMonth()
 *   <li>getMonthValue()
 *   <li>getYear()
 * </ul>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupportedLocalDateMethods extends QueryTest {

  /** */
  private static final String ASSERTION_FAILED =
      "Assertion A14.6.2-60 (SupportedLocalDateMethods) failed: ";

  /** */
  private Object oidOfLocalDate1;

  /** */
  private Object oidOfLocalDate2;

  /** */
  @Test
  @Execution(ExecutionMode.CONCURRENT)
  public void testDayOfMonth() {
    final String filter = "localDate.getDayOfMonth() == 12";
    PersistenceManager pm = getPMF().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      List<LocalDateSample> expectedResult = new ArrayList<>();
      expectedResult.add((LocalDateSample) pm.getObjectById(oidOfLocalDate1));

      Query<LocalDateSample> q = pm.newQuery(LocalDateSample.class, filter);
      List<LocalDateSample> results = q.executeList();
      checkQueryResultWithoutOrder(ASSERTION_FAILED, filter, results, expectedResult);
      tx.commit();
    } finally {
      cleanupPM(pm);
    }
  }

  /** */
  @Test
  @Execution(ExecutionMode.CONCURRENT)
  public void testMonthValue() {
    final String filter = "localDate.getMonthValue() == 8";
    PersistenceManager pm = getPMF().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      List<LocalDateSample> expectedResult = new ArrayList<>();
      expectedResult.add((LocalDateSample) pm.getObjectById(oidOfLocalDate2));

      Query<LocalDateSample> q = pm.newQuery(LocalDateSample.class, filter);
      List<LocalDateSample> results = q.executeList();
      checkQueryResultWithoutOrder(ASSERTION_FAILED, filter, results, expectedResult);
      tx.commit();
    } finally {
      cleanupPM(pm);
    }
  }

  /** */
  @Test
  @Execution(ExecutionMode.CONCURRENT)
  public void testYear() {
    final String filter = "localDate.getYear() == 2017";
    PersistenceManager pm = getPMF().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      List<LocalDateSample> expectedResult = new ArrayList<>();
      expectedResult.add((LocalDateSample) pm.getObjectById(oidOfLocalDate1));
      expectedResult.add((LocalDateSample) pm.getObjectById(oidOfLocalDate2));

      Query<LocalDateSample> q = pm.newQuery(LocalDateSample.class, filter);
      List<LocalDateSample> results = q.executeList();
      checkQueryResultWithoutOrder(ASSERTION_FAILED, filter, results, expectedResult);
      tx.commit();
    } finally {
      cleanupPM(pm);
    }
  }

  @BeforeAll
  @Override
  protected void setUp() {
    super.setUp();
  }

  @AfterAll
  @Override
  protected void tearDown() {
    super.tearDown();
  }

  /**
   * @see org.apache.jdo.tck.JDO_Test#localSetUp()
   */
  @Override
  protected void localSetUp() {
    addTearDownClass(LocalDateSample.class);
    insertLocalDateSampleData(getPM());
  }

  /** */
  private void insertLocalDateSampleData(PersistenceManager pm) {
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      LocalDateSample lds1 = new LocalDateSample();
      lds1.setId(1);
      LocalDate localDate1 = LocalDate.of(2017, Month.SEPTEMBER, 12);
      lds1.setLocalDate(localDate1);
      pm.makePersistent(lds1);

      LocalDateSample lds2 = new LocalDateSample();
      lds2.setId(2);
      LocalDate localDate2 = LocalDate.of(2017, Month.AUGUST, 20);
      lds2.setLocalDate(localDate2);
      pm.makePersistent(lds2);

      tx.commit();
      oidOfLocalDate1 = pm.getObjectId(lds1);
      oidOfLocalDate2 = pm.getObjectId(lds2);
      tx = null;
    } finally {
      if ((tx != null) && tx.isActive()) tx.rollback();
    }
  }
}
