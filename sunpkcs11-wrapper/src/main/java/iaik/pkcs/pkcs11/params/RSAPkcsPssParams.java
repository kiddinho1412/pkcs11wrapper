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

package iaik.pkcs.pkcs11.params;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Util;
import sun.security.pkcs11.wrapper.CK_RSA_PKCS_PSS_PARAMS;

/**
 * This class encapsulates parameters for the Mechanism.RSA_PKCS_PSS.
 *
 * @author Karl Scheibelhofer
 * @version 1.0
 * @invariants
 */
@SuppressWarnings("restriction")
// CHECKSTYLE:SKIP
public class RSAPkcsPssParams extends RSAPkcsParams {

  /**
   * The length of the salt value in octets.
   */
  protected long saltLength;

  /**
   * Create a new RSAPkcsOaepParameters object with the given attributes.
   *
   * @param hashAlg
   *          The message digest algorithm used to calculate the digest of the
   *          encoding parameter.
   * @param mgf
   *          The mask to apply to the encoded block. One of the constants
   *          defined in the MessageGenerationFunctionType interface.
   * @param saltLength
   *          The length of the salt value in octets.
   * @preconditions (hashAlg <> null)
   *                and (mgf == MessageGenerationFunctionType.Sha1)
   * @postconditions
   */
  public RSAPkcsPssParams(Mechanism hashAlg, long mgf, long saltLength) {
    super(hashAlg, mgf);
    this.saltLength = saltLength;
  }

  /**
   * Get this parameters object as an object of the CK_RSA_PKCS_PSS_PARAMS
   * class.
   *
   * @return This object as a CK_RSA_PKCS_PSS_PARAMS object.
   * @preconditions
   * @postconditions (result <> null)
   */
  @Override
  public Object getPKCS11ParamsObject() {
    CK_RSA_PKCS_PSS_PARAMS params = new CK_RSA_PKCS_PSS_PARAMS();

    params.hashAlg = hashAlg.getMechanismCode();
    params.mgf = mgf;
    params.sLen = saltLength;

    return params;
  }

  /**
   * Get the length of the salt value in octets.
   *
   * @return The length of the salt value in octets.
   * @preconditions
   * @postconditions
   */
  public long getSaltLength() {
    return saltLength;
  }

  /**
   * Set the length of the salt value in octets.
   *
   * @param saltLength
   *          The length of the salt value in octets.
   * @preconditions
   * @postconditions
   */
  public void setSaltLength(long saltLength) {
    this.saltLength = saltLength;
  }

  /**
   * Returns the string representation of this object. Do not parse data from
   * this string, it is for debugging only.
   *
   * @return A string representation of this object.
   */
  @Override
  public String toString() {
    return Util.concat(super.toString(),
        "\n  Salt Length (octets, dec): ", Long.toString(saltLength));
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
    } else if (!(otherObject instanceof RSAPkcsPssParams)) {
      return false;
    }

    RSAPkcsPssParams other = (RSAPkcsPssParams) otherObject;
    return super.equals(other) && (this.saltLength == other.saltLength);
  }

  /**
   * The overriding of this method should ensure that the objects of this
   * class work correctly in a hashtable.
   *
   * @return The hash code of this object.
   * @preconditions
   * @postconditions
   */
  @Override
  public int hashCode() {
    return super.hashCode() ^ ((int) saltLength);
  }

}
