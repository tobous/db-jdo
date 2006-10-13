/*
 * Copyright 2006 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package org.apache.jdo.tck.api.persistencemanager.getobject;

import java.util.Date;

import javax.jdo.Transaction;

import javax.jdo.identity.LongIdentity;

import org.apache.jdo.tck.api.persistencemanager.PersistenceManagerTest;

import org.apache.jdo.tck.pc.company.Company;
import org.apache.jdo.tck.pc.company.ICompany;

import org.apache.jdo.tck.util.BatchTestRunner;

/**
 *<B>Title:</B> Get ObjectidForPersistentInterface
 *<BR>
 *<B>Keywords:</B> identity
 *<BR>
 *<B>Assertion ID:</B> A12.6.6
 *<BR>
 *<B>Assertion Description: </B>
 * For interfaces and classes that use a SingleFieldIdentity as 
 * the object-id class, if the returned instance is subsequently 
 * made persistent, the target class stored in the object-id 
 * instance is the parameter of the newInstance method that created it. 
 */

public class GetObjectIdForPersistentInterface extends PersistenceManagerTest {
    
    /** */
    private static final String ASSERTION_FAILED = 
        "Assertion A12.6.6 (GetObjectId) failed: ";
    
    /**
     * The <code>main</code> is called when the class
     * is directly executed from the command line.
     * @param args The arguments passed to the program.
     */
    public static void main(String[] args) {
        BatchTestRunner.run(GetObjectIdForPersistentInterface.class);
    }

    public void localSetUp() {
        addTearDownClass(ICompany.class);
    }

    /** */
    public void testGetObjectId() {
        if (!runsWithApplicationIdentity()) {
            printNonApplicableIdentityType(
                    "GetObjectIdForPersistentInterface",
                    APPLICATION_IDENTITY);
            return;
        }
        pm = getPM();
        Transaction tx = pm.currentTransaction();
        ICompany icompany = (ICompany)pm.newInstance(ICompany.class);
        icompany.setCompanyid(1001);
        icompany.setName("GooTube");
        icompany.setFounded(new Date());

        tx.begin();
        pm.makePersistent(icompany);
        LongIdentity ioid = (LongIdentity)pm.getObjectId(icompany);
        tx.commit();

        Class icompanyOidClass = ioid.getTargetClass();
        if (icompanyOidClass != icompany.getClass()) 
            appendMessage(ASSERTION_FAILED +
                 " getObjectId(icompany) should return interface class.\n" +
                    "expected: " + icompany.getClass().getName() + "\n" +
                    "actual: " + icompanyOidClass.getName());
        failOnError();

    }
}
