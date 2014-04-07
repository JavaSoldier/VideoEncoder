package videoDecoder;

import utils.AESCryptoUtils;
import utils.Utils;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 06.04.14
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */
public class MySuperDuperDecoder {

    private static final Path FROM_FILES = Paths.get("D:\\videoFolder_encrypt");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_decode");

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        File fromDirFile = new File(FROM_FILES.toUri());
        String[] names = fromDirFile.list();
        File[] moviesFiles = fromDirFile.listFiles();

        Utils.deleteFolder(TO_FILES);
        Files.createDirectory(TO_FILES);
        List<Path> pathList = Utils.createFiles(names, TO_FILES);

        for (File movieFile : moviesFiles) {
            String baseFileName = movieFile.getName();
            InputStream stream = new FileInputStream(movieFile);
            for (Path path : pathList) {
                if (path.toFile().getName().equals(baseFileName)) {
                    OutputStream oStream = new FileOutputStream(path.toFile());
                    //Utils.myMadDecryption(stream, oStream);
                    AESCryptoUtils.decrypt(stream, oStream);
                    oStream.flush();
                    oStream.close();
                    break;
                }
            }
            stream.close();
        }
    }
}
