package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 07.04.14
 * Time: 2:07
 * To change this template use File | Settings | File Templates.
 */


public class Serializer {

    private static FileOutputStream fout;
    private static ObjectOutputStream oos;

    public static void serialize(Object obj) throws IOException {
        try {
            fout = new FileOutputStream("D:\\key.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(obj);
        } finally {
            if (oos != null) {
                oos.flush();
                oos.close();
                fout.close();
            }
        }
    }
}