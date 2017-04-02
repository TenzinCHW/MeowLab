package CSE.Week10;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;


public class DigitalSignatureSolution {

    public static void main(String[] args) throws Exception {
        //Read the text file and save to String data
        String smallData = "";
        String bigData = "";
        String line;
        // BufferedReader bufferedReader = new BufferedReader( new FileReader(args[0]));
        BufferedReader smallBufferedReader = new BufferedReader(new FileReader("src\\CSE\\Week10\\TextFiles\\smallFile.txt"));
        BufferedReader bigBufferedReader = new BufferedReader(new FileReader("src\\CSE\\Week10\\TextFiles\\largeFile.txt"));
        while ((line = smallBufferedReader.readLine()) != null) smallData = smallData + "\n" + line;
        while ((line = bigBufferedReader.readLine()) != null) bigData = bigData + "\n" + line;


        // Generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        // Calculate message digest, using MD5 hash function
        MessageDigest smallMD5 = MessageDigest.getInstance("MD5");
        MessageDigest bigMD5 = MessageDigest.getInstance("MD5");
        smallMD5.update(smallData.getBytes());
        bigMD5.update(bigData.getBytes());

        // Print the length of output digest byte[], compare the length of file smallSize.txt and largeSize.txt
        System.out.println("Small text size: " + smallMD5.digest().length);
        System.out.println("Large text size: " + bigMD5.digest().length);

        // Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);

        // Encrypt digest message
        byte[] smallEncrypted = rsaCipher.doFinal(smallMD5.digest());
        byte[] bigEncrypted = rsaCipher.doFinal(bigMD5.digest());

        System.out.println("Signed small digest size: " + smallEncrypted.length);
        System.out.println("Signed big digest size: " + bigEncrypted.length);

        // Print the encrypted message (in base64format String using DatatypeConverter)
        System.out.println("Small file encrypted digest:\n" + DatatypeConverter.printBase64Binary(smallEncrypted));
        System.out.println("Large file encrypted digest:\n" + DatatypeConverter.printBase64Binary(bigEncrypted));

        // Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.
        Cipher rsaDecrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaDecrypt.init(Cipher.DECRYPT_MODE, publicKey);

        // Decrypt message
        byte[] smallDecrypted = rsaDecrypt.doFinal(smallEncrypted);
        byte[] bigDecrypted = rsaDecrypt.doFinal(bigEncrypted);

        // Print the decrypted message (in base64format String using DatatypeConverter), compare with origin digest
        String smallDigestDecrypted = DatatypeConverter.printBase64Binary(smallDecrypted);
        String bigDigestDecrypted = DatatypeConverter.printBase64Binary(bigDecrypted);
        System.out.println("Small file decrypted digest:\n" + smallDigestDecrypted);
        System.out.println("Large file decrypted digest:\n" + bigDigestDecrypted);
        System.out.println("Small file stayed the same: " + smallDigestDecrypted.equals(DatatypeConverter.printBase64Binary(smallMD5.digest())));
        System.out.println("Large file stayed the same: " + bigDigestDecrypted.equals(DatatypeConverter.printBase64Binary(bigMD5.digest())));

        /*
        Questions
        1. Size: 16. Same size.
        2. Larger file does not give larger digest, but the same-sized signed digest as the small file.
        This is because the signature generated is dependent only on the signing algorithm (MD5) and not
        the encryption algorithm.
         */
    }
}