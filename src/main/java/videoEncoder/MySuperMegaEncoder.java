package videoEncoder;

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
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class MySuperMegaEncoder {
    private static final Path FROM_FILES = Paths.get("D:\\videoFolder");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_encrypt");
    private static final Path NAMES_TXT_FILE = Paths.get("D:\\names.txt");


    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, ClassNotFoundException {
        File fromDirFile = new File(FROM_FILES.toUri());
        String[] names = fromDirFile.list();
        File[] moviesFiles = fromDirFile.listFiles();

        // creation of names.txt file for SimpleMediaPlayer project
        Files.deleteIfExists(NAMES_TXT_FILE);
        Files.createFile(NAMES_TXT_FILE);
        writeToFile(names, NAMES_TXT_FILE);

        Utils.deleteFolder(TO_FILES);
        Files.createDirectory(TO_FILES);
        List<Path> pathList = Utils.createFiles(names, TO_FILES);

        for (File movieFile : moviesFiles) {
            String baseFileName = movieFile.getName();
            InputStream stream = new FileInputStream(movieFile);
            for (Path path : pathList) {
                if (path.toFile().getName().equals(baseFileName)) {
                    OutputStream oStream = new FileOutputStream(path.toFile());
                    AESCryptoUtils.encrypt(stream, oStream);
                    oStream.flush();
                    oStream.close();
                    break;
                }
            }
            stream.close();
        }

    }

    private static void writeToFile(String[] textArr, Path path) throws IOException {
        FileWriter fileWriter = new FileWriter(path.toFile(), true);
        for (int i = 0; i < textArr.length; i++) {
            if (i != textArr.length - 1) {
                fileWriter.write(textArr[i] + "\n");
            } else {
                fileWriter.write(textArr[i]);
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
