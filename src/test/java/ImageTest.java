import org.junit.Assert;
import org.junit.Test;
import org.kurodev.pictionary.logic.img.Image;

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
        Assert.assertEquals(argb, image[24]);
    }
}
