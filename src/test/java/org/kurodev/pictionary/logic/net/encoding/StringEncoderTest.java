package org.kurodev.pictionary.logic.net.encoding;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kurodev.pictionary.logic.net.encoding.stream.StringEncoder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class StringEncoderTest {
    private static final byte delimiter = 0x2;

    @Test
    public void encode() {
        byte[] b = new byte[]{0x30, delimiter};
        byte[] encoded = StringEncoder.encode("0");
        assertArrayEquals(encoded, b);
    }

    @Test
    public void decode() {
        String[] test = new String[]{"test", "test2"};
        byte[] b = StringEncoder.encode(test);
        String[] decode = StringEncoder.decode(b);
        assertArrayEquals(decode, test);
    }

    @Test
    public void decodeNextString() {
        byte[] b = new byte[]{0x30};
        assertEquals(StringEncoder.decodeNextString(b), "0");
    }

    @Test
    @Parameters({"srahsro,atiohsaot,atksatnst,astaotp",
            "as9rasg aas,asga",
            "apahrpasjrara",
            "Hello World, Hello, World",
            "ÄÖÜäääääää#üsat+r5a#ry+fl,469346gäÄÖÜsd#s,463Ö7#gÄd#grÜd"})
    public void testGarbageInputs(String... args) {
        byte[] encoded = StringEncoder.encode(args);
        String[] decoded = StringEncoder.decode(encoded);
        assertArrayEquals(args, decoded);
    }
}