package org.kurodev.pictionary.logic.net.encoding;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class StringEncoderTest {
    private static final byte delimiter = 0x2;
    private static final StringEncoder ENCODER = new StringEncoder();

    @Test
    public void encode() {
        byte[] b = new byte[]{delimiter, 0x30, delimiter};
        byte[] encoded = ENCODER.encode("0");
        assertArrayEquals(encoded, b);
    }

    @Test
    public void decode() {
        String[] test = new String[]{"test", "test2"};
        byte[] b = ENCODER.encode(test);
        String[] decode = ENCODER.decode(b);
        assertArrayEquals(decode, test);
    }

    @Test
    public void decodeNextString() {
        byte[] b = new byte[]{0x30};
        assertEquals(ENCODER.decodeNextString(b), "0");
    }

    @Test
    @Parameters({"srahsro,atiohsaot,atksatnst,astaotp",
            "as9rasg aas,asga",
            "apahrpasjrara",
            "Hello World, Hello, World"})
    public void testGarbageInputs(String... args) {
        byte[] encoded = ENCODER.encode(args);
        String[] decoded = ENCODER.decode(encoded);
        assertArrayEquals(args, decoded);
    }
}