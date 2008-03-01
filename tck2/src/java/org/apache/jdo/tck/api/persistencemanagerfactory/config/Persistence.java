/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package org.apache.jdo.tck.api.persistencemanagerfactory.config;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import org.apache.jdo.tck.JDO_Test;
import org.apache.jdo.tck.pc.mylib.PCPoint;
import org.apache.jdo.tck.util.BatchTestRunner;

/**
 *<B>Title:</B>Close of PersistenceManagerFactory  
 *<BR/>
 *<B>Keywords:</B> persistencemanagerfactory, jdoconfig.xml
 *<BR/>
 *<B>Assertion IDs:</B> A8.7-1
 *<BR/>
 *<B>Assertion Description: </B>
 * The application provides a resource named META-INF/persistence.xml and
 * optionally META-INF/services/javax.persistence.EntityManagerFactory which 
 * contain configuration information
 */
public class Persistence extends JDO_Test {

    /** Creates a new instance of Jdoconfig */
    public Persistence() {
    }
    /** */
    private static final String ASSERTION_FAILED =
            "Assertion A11.1.2-1 failed: ";
    private static final String ANONYMOUS_PMF_NAME = "";
    // Do not use superclass pmf, pm
    private PersistenceManagerFactory pmf = null;
    private PersistenceManager pm = null;

    /**
     * The <code>main</code> is called when the class
     * is directly executed from the command line.
     * @param args The arguments passed to the program.
     */
    public static void main(String[] args) {
        BatchTestRunner.run(Persistence.class);
    }

    /** */
    public void testGetPMFfromEMFNamed() {
        String name = identitytype + "0";
        pmf = JDOHelper.getPersistenceManagerFactory(name);
        assertEquals("Incorrect value for RestoreValues",
                false, pmf.getRestoreValues());
        runTest(name);
    }

    /** */
    public void testGetPMFfromEMFNamedSpaces() {
        String name = identitytype + "0";
        pmf = JDOHelper.getPersistenceManagerFactory(" \t" + name + " \n");
        assertEquals("Incorrect value for RestoreValues",
                false, pmf.getRestoreValues());
        runTest(name);
    }

    /** */
    public void runTest(String name) {
        String actualPUName = pmf.getPersistenceUnitName();
        assertEquals("Incorrect PersistenceUnitName", name, actualPUName);

        // check pmf.isClosed() before and after pmf.close()
        if (pmf.isClosed()) {
            fail(ASSERTION_FAILED,
                    "PMF.isClosed() returned true on an open pmf");
        }
        makePersistent();

        pmf.close();

        if (!pmf.isClosed()) {
            fail(ASSERTION_FAILED,
                    "PMF.isClosed() returned false on a closed pmf");
        }
        // have next invocation of getPMF() get a new pmf
        pmf = null;
    }

    protected void makePersistent() {
        addTearDownClass(PCPoint.class);
        pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        tx.begin();
        PCPoint comp = new PCPoint(1, 2);
        pm.makePersistent(comp);
        tx.commit();
    }
}
