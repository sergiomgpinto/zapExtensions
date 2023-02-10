package org.zaproxy.addon.filetester.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class FileUtils {

    /**
     * This method checks if the file does not have a malicious path as it's filename, and does indeed go to the intended directory
     *
     * @param filename = the name of the file
     * @param intendedDir = the path of the intended directory to extract
     * @return null if and only if the filename has a different path than the intended directory, else returns the canonical path of the file
     */
    public static String validateFilename(String filename, String intendedDir) throws java.io.IOException {
        File f = new File(filename);
        String canonicalPath = f.getCanonicalPath();
        File iD = new File(intendedDir);
        String canonicalID = iD.getCanonicalPath();



        if (canonicalPath.startsWith(canonicalID)) {
            return canonicalPath;
        } else {
            System.out.println("FileType is outside extraction target directory.");
            return null;
        }
    }

    /**
     * This method moves the temporary file to the Downloads dir
     * if its safe else it is moved to the Unsafe dir.
     *
     * @param filePath - The path of the temporary file.
     * @param fileCounter - The id of the file used for the file name.
     * @param isNotSafe - Whether the file is safe or not.
     */
    public static void moveFile(String filePath, int fileCounter, boolean isNotSafe) {

        String absoluteUserDirPath = System.getProperty("user.home");

        String fileExtension = filePath.substring(filePath.lastIndexOf("."));
        String newFileName = "File" + fileCounter + fileExtension;
        Path sourceTempFilePath = Path.of(filePath);
        Path targetTempFilePath;

        if (isNotSafe) {
            targetTempFilePath = Path.of(absoluteUserDirPath + "/Unsafe/" + newFileName);
        } else {
            targetTempFilePath = Path.of(absoluteUserDirPath + "/Downloads/" + newFileName);
        }

        try {
            Files.move(sourceTempFilePath,targetTempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
