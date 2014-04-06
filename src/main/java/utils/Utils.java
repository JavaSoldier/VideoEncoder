package utils;

import sun.misc.BASE64Decoder;
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
 * Date: 06.04.14
 * Time: 1:29
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();


    public static void writeToFile(String encryptMovie, Path paths) throws IOException {
        FileWriter fileWriter = new FileWriter(paths.toFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(encryptMovie);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void writeToFile(byte[] bytes, Path paths) throws IOException {
        OutputStream stream = new FileOutputStream(paths.toFile(), true);
        stream.write(bytes);
        stream.flush();
        stream.close();
    }

    public static List<Path> createFiles(String[] fileNames, Path toFiles) throws IOException {
        List<Path> pathList = new LinkedList<>();
        for (String name : fileNames) {
            String pathToNewFile = toFiles.toString() + "\\" + name;
            pathList.add(Files.createFile(Paths.get(pathToNewFile)));
        }
        return pathList;
    }

    public static void deleteFolder(Path path) throws IOException {
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

    public static String base64encode(byte[] bytes) throws IOException {
        return enc.encode(bytes);
    }

    public static byte[] base64decode(String base64str) throws IOException {
        return dec.decodeBuffer(base64str);
    }
}
