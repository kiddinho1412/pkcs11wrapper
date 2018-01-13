// Copyright (c) 2002 Graz University of Technology. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// 3. The end-user documentation included with the redistribution, if any, must
//    include the following acknowledgment:
//
//    "This product includes software developed by IAIK of Graz University of
//     Technology."
//
//    Alternately, this acknowledgment may appear in the software itself, if and
//    wherever such third-party acknowledgments normally appear.
//
// 4. The names "Graz University of Technology" and "IAIK of Graz University of
//    Technology" must not be used to endorse or promote products derived from
//    this software without prior written permission.
//
// 5. Products derived from this software may not be called "IAIK PKCS Wrapper",
//    nor may "IAIK" appear in their name, without prior written permission of
//    Graz University of Technology.
//
// THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE LICENSOR BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
// OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
// ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package iaik.pkcs.pkcs11.objects;

import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.Util;

/**
 * Objects of this class represent secret keys as specified by PKCS#11
 * v2.11.
 *
 * @author Karl Scheibelhofer
 * @author Lijun Liao
 */
// CHECKSTYLE:SKIP
public class ValuedSecretKey extends SecretKey {

    /**
     * The value attribute of this secret key.
     */
    protected ByteArrayAttribute value;

    /**
     * The length of this secret key in bytes.
     */
    protected LongAttribute valueLen;

    /**
     * Default Constructor.
     *
     * @param keyType
     *          The type of the key.
     *
     * @preconditions
     * @postconditions
     */
    public ValuedSecretKey(long keyType) {
        this.keyType.setLongValue(keyType);
    }

    /**
     * Called by getInstance to create an instance of a PKCS#11 secret key.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @param objectHandle
     *          The object handle as given from the PKCS#111 module.
     * @param keyType
     *          The type of the key.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions
     */
    protected ValuedSecretKey(Session session, long objectHandle, long keyType)
        throws TokenException {
        super(session, objectHandle);
        this.keyType.setLongValue(keyType);
    }

    /**
     * The getInstance method of the SecretKey class uses this method to create
     * an instance of a PKCS#11 AES secret key.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @param objectHandle
     *          The object handle as given from the PKCS#111 module.
     * @param keyType
     *          The type of the key.
     * @return The object representing the PKCS#11 object.
     *         The returned object can be casted to the according sub-class.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions (result <> null)
     */
    public static PKCS11Object getInstance(Session session, long objectHandle,
            long keyType)
        throws TokenException {
        return new ValuedSecretKey(session, objectHandle, keyType);
    }

    /**
     * Put all attributes of the given object into the attributes table of this
     * object. This method is only static to be able to access invoke the
     * implementation of this method for each class separately.
     *
     * @param object
     *          The object to handle.
     * @preconditions (object <> null)
     * @postconditions
     */
    protected static void putAttributesInTable(ValuedSecretKey object) {
        Util.requireNonNull("object", object);
        object.attributeTable.put(Attribute.VALUE, object.value);
        object.attributeTable.put(Attribute.VALUE_LEN, object.valueLen);
    }

    /**
     * Allocates the attribute objects for this class and adds them to the
     * attribute table.
     *
     * @preconditions
     * @postconditions
     */
    @Override
    protected void allocateAttributes() {
        super.allocateAttributes();

        value = new ByteArrayAttribute(Attribute.VALUE);
        valueLen = new LongAttribute(Attribute.VALUE_LEN);

        putAttributesInTable(this);
    }

    /**
     * Compares all member variables of this object with the other object.
     * Returns only true, if all are equal in both objects.
     *
     * @param otherObject
     *          The other object to compare to.
     * @return True, if other is an instance of this class and all member
     *         variables of both objects are equal. False, otherwise.
     * @preconditions
     * @postconditions
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        } else if (!(otherObject instanceof ValuedSecretKey)) {
            return false;
        }

        ValuedSecretKey other = (ValuedSecretKey) otherObject;
        return super.equals(other)
                && this.value.equals(other.value)
                && this.valueLen.equals(other.valueLen);
    }

    /**
     * Gets the value attribute of this AES key.
     *
     * @return The value attribute.
     * @preconditions
     * @postconditions (result <> null)
     */
    public ByteArrayAttribute getValue() {
        return value;
    }

    /**
     * Gets the value length attribute of this AES key (in bytes).
     *
     * @return The value attribute.
     * @preconditions
     * @postconditions (result <> null)
     */
    public LongAttribute getValueLen() {
        return valueLen;
    }

    /**
     * Read the values of the attributes of this object from the token.
     *
     * @param session
     *          The session to use for reading attributes. This session must
     *          have the appropriate rights; i.e. it must be a user-session, if
     *          it is a private object.
     * @exception TokenException
     *              If getting the attributes failed.
     * @preconditions (session <> null)
     * @postconditions
     */
    @Override
    public void readAttributes(Session session)
        throws TokenException {
        super.readAttributes(session);

        PKCS11Object.getAttributeValue(session, objectHandle, value);
        PKCS11Object.getAttributeValue(session, objectHandle, valueLen);
    }

    /**
     * Returns a string representation of the current object. The
     * output is only for debugging purposes and should not be used for other
     * purposes.
     *
     * @return A string presentation of this object for debugging output.
     * @preconditions
     * @postconditions (result <> null)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n  Value (hex): ").append(value);
        if (valueLen.isPresent()) {
            sb.append("\n  Value Length (dec): ").append(valueLen.toString(10));
        }
        return sb.toString();
    }

}