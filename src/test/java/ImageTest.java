import org.junit.Test;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;
import org.kurodev.pictionary.logic.util.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * @author kuro
 **/
public class ImageTest {
    @Test
    public void testImageCreation() {
        int width = 20, height = 20, x = 1, y = 4, argb = 0;
        Image img = new Image(width, height);
        img.write(x, y, argb);
        int[] image = img.getImage();
        assertEquals(argb, image[24]);
    }

    @Test
    public void pixelEncodingWorks() {
        Pixel pix = new Pixel(2, 5, 241515);
        EasyByteWriter writer = new EasyByteWriter();
        pix.encode(writer);
        Pixel pix1 = new Pixel();
        pix1.decode(writer.toByteArray());
        assertEquals(pix, pix1);
    }

    @Test
    public void byteUtilTest() {
        int x = 50;
        byte[] x2 = ByteUtils.intToByte(x);
        assertEquals(x, ByteUtils.byteToInt(x2));
    }
}
