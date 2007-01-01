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

package org.apache.jdo.impl.jdoql.tree;

import org.apache.jdo.impl.jdoql.jdoqlc.JDOQLTokenTypes;
import org.apache.jdo.jdoql.tree.BooleanLiteralExpression;
import org.apache.jdo.jdoql.tree.NodeVisitor;


/**
 * This node represents a boolean literal such as <code>true</code> or
 * <code>false</code>. It does not have any children.
 *
 * @author Michael Watzek
 */
public final class BooleanLiteralExpr 
    extends ConstantExpr implements BooleanLiteralExpression
{
    /**
     * The noarg constructor is needed for ANTLR support and deserialization.
     * The caller must make sure to set the ANTLR tree structure himself
     * or, call <code>setChildren</code> optionally.
     */
    public BooleanLiteralExpr()
    {}

    /**
     * The noarg constructor is needed for ANTLR support.
     * The caller must make sure to set the ANTLR tree structure himself
     * or, call <code>setChildren</code> optionally.
     */
    public BooleanLiteralExpr(antlr.Token token)
    {   initialize( token );
    }

    /**
     * This constructor is called by the query tree instance.
     * It delegates to the super class constructor.
     * @param b the boolean value
     */
    BooleanLiteralExpr(Boolean b)
    {   super( JDOQLTokenTypes.BOOLEAN_LITERAL, b.toString(), b ); //NOI18N
    }

    /**
     * This constructor is called by the query tree instance.
     * It delegates to the super class constructor.
     * @param b the boolean value
     */
    BooleanLiteralExpr(boolean b)
    {   this( new Boolean(b) );
    }

    /**
     * Returns the boolean value represented by this expression.
     * @return the boolean value
     */
    public boolean getBoolean()
    {   return ((Boolean)this.value).booleanValue();
    }

    /**
     * Delegates to the argument <code>visitor</code>.
     * @param visitor the node visitor
     */
    public void arrive(NodeVisitor visitor)
    {   visitor.arrive( this );
    }

    /**
     * Delegates to the argument <code>visitor</code>.
     * @param visitor the node visitor
     * @param results the result array
     * @return the object returned by the visitor instance
     */
    public Object leave(NodeVisitor visitor, Object[] results)
    {   return visitor.leave( this, results );
    }
}
