/*
 * @(#)UnknownTypeException.java	1.3 06/07/31
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.lang.model.type;

/**
 * Indicates that an unknown kind of type was encountered.  This can
 * occur if the language evolves and new kinds of types are added to
 * the {@code TypeMirror} hierarchy.  May be thrown by a {@linkplain
 * TypeVisitor type visitor} to indicate that the visitor was created
 * for a prior version of the language.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version 1.3 06/07/31
 * @see TypeVisitor#visitUnknown
 * @since 1.6
 */
public class UnknownTypeException extends RuntimeException {

    private static final long serialVersionUID = 269L;

    private transient TypeMirror type;
    private transient Object parameter;

    /**
     * Creates a new {@code UnknownTypeException}.The {@code p}
     * parameter may be used to pass in an additional argument with
     * information about the context in which the unknown type was
     * encountered; for example, the visit methods of {@link
     * TypeVisitor} may pass in their additional parameter.
     *
     * @param t the unknown type, may be {@code null}
     * @param p an additional parameter, may be {@code null}
     */
    public UnknownTypeException(TypeMirror t, Object p) {
	super("Unknown type: " + t);
	type = t;
	this.parameter = p;
    }

    /**
     * Returns the unknown type.
     * The value may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * @return the unknown type, or {@code null} if unavailable
     */
    public TypeMirror getUnknownType() {
	return type;
    }

    /**
     * Returns the additional argument.
     *
     * @return the additional argument
     */
    public Object getArgument() {
	return parameter;
    }
}
