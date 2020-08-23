package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

/**
 * @author kuro
 **/
public interface Encodable {

    /**
     * Decodes the byte code result from the Encode method.
     *
     * @param data The bytes which are the direct result of a similar objects encode method.
     * @apiNote For this to work as expected it is strongly advised to only use bytecode from a same class instance
     */
    void decode(EasyByteReader data);

    default void decode(byte[] bytes) {
        decode(new EasyByteReader(bytes));
    }

    /**
     * Encodes the object into a byte representation of itself. Use {@link #decode(EasyByteReader)} to rebuild the object.
     * many such objects have a constructor which will accept the bytes.
     *
     * @param out
     */
    void encode(EasyByteWriter out);
}
