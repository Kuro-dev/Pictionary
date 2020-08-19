import org.junit.Test;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;

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
        byte[] code = pix.encode();
        Pixel pix1 = new Pixel(code);
        assertEquals(pix, pix1);
    }
}
