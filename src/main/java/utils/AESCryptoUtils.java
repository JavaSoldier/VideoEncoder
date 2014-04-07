package utils;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 07.04.14
 * Time: 1:39
 * To change this template use File | Settings | File Templates.
 */

public class AESCryptoUtils {

    public static void encrypt(InputStream is, OutputStream os) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, ClassNotFoundException {


        Path path = Paths.get("D:\\key.ser");
        //Files.deleteIfExists(path);
        if (!Files.exists(path)) {
            SecretKey key = KeyGenerator.getInstance("AES").generateKey();
            Files.createFile(path);
            Serializer.serialize(key);
        }
        //get Cipher instance and initiate in encrypt mode
        Cipher encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, Deserializer.deserializeObject());

        //create CipherOutputStream to encrypt the data using encryptCipher
        os = new CipherOutputStream(os, encryptCipher);
        writeData(is, os);
    }


    public static void decrypt(InputStream is, OutputStream os) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, ClassNotFoundException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKey key = Deserializer.deserializeObject();

        //get Cipher instance and initiate in decrypt mode
        Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        //create CipherOutputStream to decrypt the data using decryptCipher
        is = new CipherInputStream(is, decryptCipher);
        writeData(is, os);
    }

    //utility method to read data from input stream and write to output stream
    private static void writeData(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int numRead;

        //read and write operation
        while ((numRead = is.read(buf)) >= 0) {
            os.write(buf, 0, numRead);
        }
        os.close();
        is.close();
    }

}