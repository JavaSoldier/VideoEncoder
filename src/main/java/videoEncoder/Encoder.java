package videoEncoder;

import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 06.04.14
 * Time: 1:28
 * To change this template use File | Settings | File Templates.
 */
public class Encoder {
    private static final Path FROM_FILES = Paths.get("D:\\videoFolder");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_encrypt");


    public static void main(String[] args) throws IOException {
        File fromDirFile = new File(FROM_FILES.toUri());
        String[] names = fromDirFile.list();
        File[] moviesFiles = fromDirFile.listFiles();

        Utils.deleteFolder(TO_FILES);
        Files.createDirectory(TO_FILES);
        List<Path> pathList = Utils.createFiles(names, TO_FILES);

        for (File movieFile : moviesFiles) {
            String baseFileName = movieFile.getName();
            InputStream stream = new FileInputStream(movieFile);
            byte buf[] = new byte[(int) movieFile.length() / 10];
            while ((stream.read(buf)) > 0) {
                String encryptMoviePart = Utils.base64encode(buf);
                for (Path path : pathList) {
                    if (path.toFile().getName().equals(baseFileName)) {
                        Utils.writeToFile(encryptMoviePart.trim(), path);
                        break;
                    }
                }
            }
            stream.close();
        }
    }
}
