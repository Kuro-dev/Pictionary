package org.kurodev.pictionary.logic.net.encoding;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.communication.command.Command;
import org.kurodev.pictionary.logic.net.communication.command.DrawToken;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;
import org.kurodev.pictionary.overlay.util.MessageEncodable;

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
    public void commandTest(String msg) {
        Encodable expected = new Command(msg);
        Encodable actual = new Command("");
        run(expected, actual);
    }

    @Test
    @Parameters({"Kuro", "some Name", "test"})
    public void participantTest(String name) {
        Encodable expected = new Participant(name, 5);
        Encodable actual = new Participant("");
        run(expected, actual);
    }

    @Test
    public void drawTokenTest() {
        Participant p = new Participant("Kuro", 5);
        Encodable expected = new DrawToken(p);
        Encodable actual = new DrawToken(new Participant(""));
        run(expected, actual);
    }

    @Test
    public void messageEncodableTest() {
        Encodable expected = new MessageEncodable("Kuro", "Hello");
        Encodable actual = new MessageEncodable("", "");
        run(expected, actual);
    }
}
