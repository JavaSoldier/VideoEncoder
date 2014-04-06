package videoDecoder;

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
public class Decoder {
    private static final Path FROM_FILES = Paths.get("D:\\videoFolder_encrypt");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_decode");

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
//            Scanner scanner = new Scanner(stream);
//            StringBuilder encryptMovie = new StringBuilder();
//            int i = 0;
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                encryptMovie.append(line);
//                System.out.println(i++ + " --> " + line.length());
//            }
//            System.out.println(encryptMovie.length());
//            byte[] bytes = Utils.base64decode(encryptMovie.toString());
//            for (Path path : pathList) {
//                if (path.toFile().getName().equals(baseFileName)) {
//                    Utils.writeToFile(bytes, path);
//                    break;
//                }
//            }
//            scanner.close();


            byte buf[] = new byte[(int) movieFile.length() / 10];
            while ((stream.read(buf)) > 0) {
                byte[] decodeBuf = Utils.base64decode(new String(buf));
                for (Path path : pathList) {
                    if (path.toFile().getName().equals(baseFileName)) {
                        Utils.writeToFile(decodeBuf, path);
                        break;
                    }
                }
            }
            stream.close();
        }
    }
}
