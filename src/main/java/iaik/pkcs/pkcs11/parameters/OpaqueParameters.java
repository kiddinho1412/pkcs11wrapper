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

package iaik.pkcs.pkcs11.parameters;

import iaik.pkcs.pkcs11.TokenRuntimeException;
import iaik.pkcs.pkcs11.Util;
import iaik.pkcs.pkcs11.wrapper.Constants;
import iaik.pkcs.pkcs11.wrapper.Functions;

/**
 * This class encapsulates parameters byte arrays.
 *
 * @author 
 * @version 1.0
 * @invariants (publicValue_ <> null)
 */
public class OpaqueParameters implements Parameters {

    protected byte[] bytes_;

    public OpaqueParameters(byte[] bytes) {
        bytes_ = bytes;
    }

    @Override
    public java.lang.Object clone() {
        OpaqueParameters clone;

        try {
            clone = (OpaqueParameters) super.clone();

            clone.bytes_ = (byte[]) this.bytes_.clone();
        } catch (CloneNotSupportedException ex) {
            // this must not happen, because this class is cloneable
            throw new TokenRuntimeException(
                    "An unexpected clone exception occurred.", ex);
        }

        return clone;
    }

    /**
     * Get this parameters object as a byte array.
     *
     * @return This object as a byte array.
     * @preconditions
     * @postconditions (result <> null)
     */
    @Override
    public Object getPKCS11ParamsObject() {
        return bytes_;
    }

    /**
     * Get the public value of the other party in the key agreement protocol.
     *
     * @return The public value of the other party in the key agreement
     *         protocol.
     * @preconditions
     * @postconditions (result <> null)
     */
    public byte[] getBytes() {
        return bytes_;
    }

    public void setIDA(byte[] bytes) {
        bytes_ = Util.requireNonNull("bytes_", bytes);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(Constants.INDENT);
        buffer.append("Bytes (hex): ");
        buffer.append(Functions.toHexString(bytes_));

        return buffer.toString();
    }

    @Override
    public boolean equals(java.lang.Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (!(otherObject instanceof OpaqueParameters)) {
            return false;
        }

        OpaqueParameters other = (OpaqueParameters) otherObject;
        return Functions.equals(this.bytes_, other.bytes_);
    }

    @Override
    public int hashCode() {
        return Functions.hashCode(bytes_);
    }

}