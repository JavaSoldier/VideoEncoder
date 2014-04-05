package videoEncoder;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 01.04.14
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final Path FROM_FILES = Paths.get("D:\\videoFolder");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_encrypt");

    private static BASE64Encoder enc = new BASE64Encoder();

    public static void main(String[] args) throws IOException {
        File fromDirFile = new File(FROM_FILES.toUri());
        String[] names = fromDirFile.list();
        File[] moviesFiles = fromDirFile.listFiles();

        deleteFolder(TO_FILES);
        Files.createDirectory(TO_FILES);
        List<Path> pathList = createFiles(names);


        for (File movieFile : moviesFiles) {
            String baseFileName = movieFile.getName();
            StringBuilder encryptMovie = new StringBuilder();
            InputStream stream = new FileInputStream(movieFile);
            byte buf[] = new byte[1024];
            String encryptMoviePart = new String();
            while ((stream.read(buf)) > 0) {
                encryptMoviePart = base64encode(buf);
                encryptMovie.append(encryptMoviePart.trim());
            }
            stream.close();

            for (Path path : pathList) {
                if (path.toFile().getName().equals(baseFileName)) {
                    writeToFile(encryptMovie.toString(), path);
                    break;
                }
            }
        }
    }

    private static void writeToFile(String encryptMovie, Path paths) throws IOException {
        FileWriter fileWriter = new FileWriter(paths.toFile());
        fileWriter.write(encryptMovie);
        fileWriter.flush();
        fileWriter.close();
    }

    private static List<Path> createFiles(String[] fileNames) throws IOException {
        List<Path> pathList = new LinkedList<>();
        for (String name : fileNames) {
            String pathToNewFile = TO_FILES.toString() + "\\" + name;
            pathList.add(Files.createFile(Paths.get(pathToNewFile)));
        }
        return pathList;
    }

    private static void deleteFolder(Path path) throws IOException {
        if (Files.exists(path)) {
            File f = new File(path.toUri());
            String[] names = f.list();
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            Files.deleteIfExists(path);
        }
    }

    private static String base64encode(byte[] bytes) throws IOException {
        return enc.encode(bytes);
    }
}
