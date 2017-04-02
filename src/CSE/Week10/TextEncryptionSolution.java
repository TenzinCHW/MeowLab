package CSE.Week10;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;


public class TextEncryptionSolution {
    public static void main(String[] args) throws Exception {
        String dataSmall = "";
        String dataBig = "";
        String line;
//        BufferedReader bufferedReader = new BufferedReader( new FileReader(args[0])); //args[0] is the file you are going to encrypt.
        BufferedReader smallBufferedReader = new BufferedReader(new FileReader("src\\CSE\\Week10\\TextFiles\\smallFile.txt"));
        BufferedReader largeBufferedReader = new BufferedReader(new FileReader("src\\CSE\\Week10\\TextFiles\\largeFile.txt"));
        while ((line = smallBufferedReader.readLine()) != null) {
            dataSmall = dataSmall + "\n" + line;
        }
        while ((line = largeBufferedReader.readLine()) != null) {
            dataBig += "\n" + line;
        }

        // Print to screen contents of the file
        System.out.println(dataSmall);
        System.out.println(dataBig);

        // Generate secret key using DES algorithm
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey key = keyGenerator.generateKey();

        // Create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, key);

        // Do encryption, by calling method Cipher.doFinal().
        byte[] encryptedDataSmall = desCipher.doFinal(dataSmall.getBytes());
        byte[] encryptedDataBig = desCipher.doFinal(dataBig.getBytes());

        // Print the length of output encrypted byte[], compare the length of file smallSize.txt and largeSize.txt
        System.out.println("Small file encrypted size: " + encryptedDataSmall.length);
        System.out.println("Large file encrypted size: " + encryptedDataBig.length);
        System.out.println(new String(encryptedDataSmall));
        System.out.println(new String(encryptedDataBig));

        // Do format conversion. Turn the encrypted byte[] format into base64format String using DatatypeConverter
        String encryptedStringSmall = DatatypeConverter.printBase64Binary(encryptedDataSmall);
        String encryptedStringBig = DatatypeConverter.printBase64Binary(encryptedDataBig);

        // Print the encrypted message (in base64format String format)
        System.out.println("Small file encrypted:");
        System.out.println(encryptedStringSmall);
        System.out.println("Large file encrypted:");
        System.out.println(encryptedStringBig);

        // Create cipher object, initialize the ciphers with the given key, choose decryption mode as DES
        Cipher desDecryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desDecryptCipher.init(Cipher.DECRYPT_MODE, key);

        // Do decryption, by calling method Cipher.doFinal().
        byte[] decryptedDataSmall = desDecryptCipher.doFinal(encryptedDataSmall);
        byte[] decryptedDataBig = desDecryptCipher.doFinal(encryptedDataBig);

        // Do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"
        String decryptedStringSmall = new String(decryptedDataSmall);
        String decryptedStringBig = new String(decryptedDataBig);

        // Print the decrypted String text and compare it with original text
        System.out.println("Decrypted small file: " + decryptedStringSmall);
        System.out.println("Decrypted big file: " + decryptedStringBig);
    }
}