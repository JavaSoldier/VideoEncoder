package videoDecoder;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 05.04.14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final Path FROM_FILES = Paths.get("D:\\videoFolder_encrypt");
    private static final Path TO_FILES = Paths.get("D:\\videoFolder_decode");
    private static BASE64Decoder dec = new BASE64Decoder();

    public static void main(String[] args) throws IOException {
        File fromDirFile = new File(FROM_FILES.toUri());
        String[] names = fromDirFile.list();
        File[] moviesFiles = fromDirFile.listFiles();

        deleteFolder(TO_FILES);
        Files.createDirectory(TO_FILES);
        List<Path> pathList = createFiles(names);

        for (File movieFile : moviesFiles) {
            String baseFileName = movieFile.getName();
            InputStream stream = new FileInputStream(movieFile);
            Scanner scanner = new Scanner(stream);
            StringBuilder encryptMovie = new StringBuilder();
            while (scanner.hasNextLine()) {
                encryptMovie.append(scanner.nextLine());
            }
            System.out.println(encryptMovie.length());
            byte[] bytes = base64decode(encryptMovie.toString());
            for (Path path : pathList) {
                if (path.toFile().getName().equals(baseFileName)) {
                    writeToFile(bytes, path);
                    break;
                }
            }
            scanner.close();
            stream.close();
        }
    }

    private static void writeToFile(byte[] bytes, Path paths) throws IOException {
        OutputStream stream = new FileOutputStream(paths.toFile());
        stream.write(bytes);
        stream.flush();
        stream.close();
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
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            Files.deleteIfExists(path);
        }
    }

    private static byte[] base64decode(String base64str) throws IOException {
        return dec.decodeBuffer(base64str);
    }
}
