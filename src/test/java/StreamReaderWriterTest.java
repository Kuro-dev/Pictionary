import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.communication.command.tokens.SelectWordToken;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.StreamReader;
import org.kurodev.pictionary.logic.net.encoding.stream.StreamWriter;
import org.kurodev.pictionary.overlay.util.MessageEncodable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author kuro
 **/
@RunWith(JUnitParamsRunner.class)
public class StreamReaderWriterTest {

    public void testStreamRead(Encodable e) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamWriter writer = new StreamWriter(bos);
        writer.write(e);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        assertEquals(bytes.length, bis.available());
        StreamReader reader = new StreamReader(bis, null);
        Encodable copy = reader.interpret(reader.read());
        assertEquals(e, copy);
    }

    @Test
    public void canReadAndWritePixelsFromStreams() throws IOException {
        Pixel pix = new Pixel(5, 6, 7);
        testStreamRead(pix);
    }

    @Test
    public void canReadAndWriteParticipantFromStreams() throws IOException {
        Encodable en = new Participant("name", 0);
        testStreamRead(en);
    }

    @Test
    @Parameters({"1,2", "3,5", "5,8", "2,15", "10,6", "15,12", "15,15", "10,10", "100,2", "1,1000", "245,1633"})
    public void testImageStreamReadWrite(int width, int height) throws IOException {
        Image image = new Image(width, height);
        testStreamRead(image);
    }

    @Test
    public void testPictionaryStreamReadWrite() throws IOException {
        Pictionary pic = new Pictionary("Hello!");
        testStreamRead(pic);
    }

    @Test
    public void testImageTransmitPixels() throws IOException {
        Image image = new Image(4, 3);
        image.write(3, 2, 225);
        assertTrue(image.isTransparent(0, 0));
        assertFalse(image.isTransparent(3, 2));
        testStreamRead(image);
    }

    @Test
    public void messageEncodableTest() throws IOException {
        Encodable expected = new MessageEncodable("Kuro", "Hello");
        testStreamRead(expected);
    }

    @Test
    public void messageSelectWordToken() throws IOException {
        Encodable expected = new SelectWordToken("Kuro", "Hello", "Hey");
        testStreamRead(expected);
    }

}
