package org.kurodev.pictionary.logic.net.encoding;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;
import org.kurodev.pictionary.logic.net.stream.Message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author kuro
 **/
@RunWith(JUnitParamsRunner.class)
public class EncodableTest {
    private static void run(Encodable e, Encodable e2) {
        assertNotEquals(e, e2);
        EasyByteWriter writer = new EasyByteWriter();
        e.encode(writer);
        e2.decode(writer.toByteArray());
        assertEquals(e, e2);
    }

    @Test
    public void pixelTest() {
        Encodable pix = new Pixel(5, 2, 5135236);
        Encodable pix2 = new Pixel();
        run(pix, pix2);
    }

    @Test
    public void imageTest() {
        Encodable i = new Image(2, 50);
        Encodable img = new Image();
        run(i, img);
    }

    @Test
    public void pictionaryTest() {
        Encodable expected = new Pictionary("Hello World");
        Encodable actual = new Pictionary();
        run(expected, actual);
    }

    @Test
    @Parameters({"hello world", "test\n\n\n\n\n\ntest", "test\r\r\r\r\r\n\n", "__.d^1^# a^@@}\\=?"})
    public void messageTest(String msg) {
        Encodable expected = new Message(msg);
        Encodable actual = new Message("");
        run(expected, actual);
    }
}
