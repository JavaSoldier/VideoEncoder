package utils;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 07.04.14
 * Time: 2:14
 * To change this template use File | Settings | File Templates.
 */


public class Deserializer {
    private static FileInputStream fin;
    private static ObjectInputStream ois;

    public static SecretKey deserializeObject() throws IOException, ClassNotFoundException {
        SecretKey key;
        try {
            fin = new FileInputStream("D:\\key.ser");
            ois = new ObjectInputStream(fin);
            key = (SecretKey) ois.readObject();

            return key;
        } finally {
            if (ois != null) {
                ois.close();
                fin.close();
            }
        }
    }
}