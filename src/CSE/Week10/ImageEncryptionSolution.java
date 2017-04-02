package CSE.Week10;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

/**
 * Created by HanWei on 31/3/2017.
 */
public class ImageEncryptionSolution {


    public static void main(String[] args) throws Exception {
        int image_width;
        int image_length;
        // Read image file and save pixel value into int[][] imageArray
        BufferedImage img = ImageIO.read(new File("src\\CSE\\Week10\\globe.bmp")); // pass the image globe.bmp as first command-line argument.
        image_width = img.getWidth();
        image_length = img.getHeight();
        // byte[][] imageArray = new byte[image_width][image_length];
        int[][] imageArray = new int[image_width][image_length];
        for (int idx = 0; idx < image_width; idx++) {
            for (int idy = 0; idy < image_length; idy++) {
                int color = img.getRGB(idx, idy);
                imageArray[idx][idy] = color;
            }
        }
        // Generate secret key using DES algorithm
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey key = keyGenerator.generateKey();

        // Create cipher object, initialize the ciphers with the given key, choose encryption algorithm/mode/padding,
        Cipher cbcCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        Cipher ecbCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cbcCipher.init(Cipher.ENCRYPT_MODE, key);
        ecbCipher.init(Cipher.ENCRYPT_MODE, key);
        // You need to try both ECB and CBC mode, use PKCS5Padding padding method


        // Define output BufferedImage, set size and format
        BufferedImage cbcOutImage = new BufferedImage(image_width, image_length, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage ecbOutImage = new BufferedImage(image_width, image_length, BufferedImage.TYPE_3BYTE_BGR);

        for (int idx = 0; idx < image_width; idx++) {
            // convert each column int[] into a byte[] (each_width_pixel)
            byte[] each_width_pixel = new byte[4 * image_length];
            for (int idy = 0; idy < image_length; idy++) {
                ByteBuffer dbuf = ByteBuffer.allocate(4);
                dbuf.putInt(imageArray[idx][idy]);
                byte[] bytes = dbuf.array();
                System.arraycopy(bytes, 0, each_width_pixel, idy * 4, 4);
            }

            // Encrypt each column or row bytes
            byte[] cbcEncryptedByte = cbcCipher.doFinal(each_width_pixel);
            byte[] ecbEncryptedByte = ecbCipher.doFinal(each_width_pixel);

            // Convert the encrypted byte[] back into int[] and write to outImage (use setRGB)

            for (int idy = 0; idy < image_length; idy++) {
                ByteBuffer cbcBuffer = ByteBuffer.allocate(4);
                ByteBuffer ecbBuffer = ByteBuffer.allocate(4);
                cbcBuffer.put(cbcEncryptedByte, 4 * idy, 4);
                ecbBuffer.put(ecbEncryptedByte, 4 * idy, 4);
                cbcOutImage.setRGB(idx, idy, cbcBuffer.getInt(0));
                ecbOutImage.setRGB(idx, idy, ecbBuffer.getInt(0));
            }
        }
        //write outImage into file
        ImageIO.write(cbcOutImage, "BMP", new File("cbc.bmp"));//for the CBC mode output
        ImageIO.write(ecbOutImage, "BMP", new File("ecb.bmp"));//for the ECB mode output

        /*
        Questions
        1. The shape of the image is retained, while the colour has changed. It is easy to see what image
        was encrypted albeit in a different colour.

        2. You can still make out the outline of the image encrypted by ECB because each block of plaintext
        bytes are encrypted separately. Thus, when an identically-coloured pixel is encountered, the encrypted
        byte arrays are exactly the same. This results in ECB being unable to hide patterns well.

        3. In CBC mode, the pattern that emerges is pseudo-random and no outline nor colour from the original
        picture is observable from the encrypted picture. This is because in CBC, each block of plaintext is
        XOR-ed with the previous block of ciphertext before being encrypted, resulting in a random-looking image.
         */
    }
}

