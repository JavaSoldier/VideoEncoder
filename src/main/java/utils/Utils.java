package utils;

import java.io.File;
import java.io.IOException;
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

    public static List<Path> createFiles(String[] fileNames, Path toFiles) throws IOException {
        List<Path> pathList = new LinkedList<Path>();
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
}
