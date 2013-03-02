/*
 * @(#)VM_TRUNCATABLE.java	1.10 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.omg.CORBA;

/** Defines the code used to represent a truncatable value type in
* a typecode. A value type is truncatable if it inherits "safely"
* from another value type, which means it can be cast to a more
* general inherited type.
* This is one of the possible results of the <code>type_modifier</code>
* method on the <code>TypeCode</code> interface.
* @see org.omg.CORBA.TypeCode
* @version 1.10 11/17/05
*/
public interface VM_TRUNCATABLE {
    /** The value representing a truncatable value type in
    * a typecode.
    */
    final short value = (short) (3L);
}
