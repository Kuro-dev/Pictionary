package org.kurodev.pictionary.logic.net.encoding;

import org.junit.Test;
import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author kuro
 **/
public class EncodableTest {
    private static void run(Encodable e, Encodable e2) {
        assertNotEquals(e, e2);
        e2.decode(e.encode());
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
}
