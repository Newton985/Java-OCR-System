package sample;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

/**
 * TODO: write you class description here
 *
 * @author newtonkarani98@gmail.com
 */

public class TesseractHelper {

    public static String
    processImg(BufferedImage ipimage,
               float scaleFactor,
               float offset)
            throws IOException, TesseractException
    {
        // Making an empty image buffer
        // to store image later
        // ipimage is an image buffer
        // of input image
        BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType());
        // creating a 2D platform
        // on the buffer image
        // for drawing the new image
        Graphics2D graphic = opimage.createGraphics();

        // drawing new image starting from 0 0
        // of size 1050 x 1024 (zoomed images)
        // null is the ImageObserver class object
        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphic.dispose();

        // rescale OP object
        // for gray scaling images
        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);

        // performing scaling
        // and writing on a .png file
        BufferedImage fopimage = rescale.filter(opimage, null);
        ImageIO.write(fopimage, "jpg",
                        new File("output.png"));

        // Instantiating the Tesseract class
        // which is used to perform OCR
        Tesseract it = new Tesseract();
        it.setDatapath("/root/Tess4J/tessdata");

        // doing OCR on the image
        // and storing result in string str
        String str = it.doOCR(fopimage);
        return str;
    }
}
