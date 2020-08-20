package org.kurodev.pictionary.logic.net.encoding;

/**
 * @author kuro
 **/
public interface Encodable {
    default String delimiter() {
        return new String(new char[]{(char) 0x6, (char) 0x10, (char) 0x15});
    }

    /**
     * Decodes the byte code result from the Encode method.
     *
     * @param bytes The bytes which are the direct result of a similar objects encode method.
     * @apiNote For this to work as expected it is strongly advised to only use bytecode from a same class instance
     */
    void decode(byte[] bytes);

    /**
     * Encodes the object into a byte representation of itself. Use {@link #decode(byte[])} to rebuild the object.
     * many such objects have a constructor which will accept the bytes.
     *
     * @return bytecode representation of the object.
     * @see org.kurodev.pictionary.logic.img.Image#Image(byte[])
     * @see org.kurodev.pictionary.logic.img.Pixel#Pixel(byte[])
     */
    byte[] encode();
}
