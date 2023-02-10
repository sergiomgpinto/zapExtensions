package org.zaproxy.addon.filetester;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.filetester.extensionapp.FileTesterPopupMenuUI;
import org.zaproxy.addon.filetester.parser.GoogleDriveParser;
import org.zaproxy.addon.filetester.parser.Parser;
import org.zaproxy.addon.filetester.reporting.ReportManager;
import org.zaproxy.addon.filetester.reporting.TestReport;
import org.zaproxy.addon.filetester.files.FileType;
import org.zaproxy.addon.filetester.utils.JSONLoader;
import org.zaproxy.addon.filetester.utils.ResourceLoader;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * This class implements the logic to handle a response
 * to a request of a file download.
 */
public class FileTesterManager {

    private final ReportManager reportManager = new ReportManager();
    private final FileTests fileTests = new FileTests();

    /**
     * Main method of the extension.
     *
     * @param msg - The response.
     * @return true if response should be forwarded to the client, false otherwise
     */
    public boolean scanFileDownload(HttpMessage msg) {

        if (!isFileTesterExtensionEnabled()) {
            return true;
        }

        Parser parser = new GoogleDriveParser();

        // Parse the response to obtain the file data and the file type.
        FileType fileType = parser.getSuspiciousFileType(msg);
        if (fileType == null) {
            return true;    // no suspicious download found
        }

        String filename = parser.getFileName(msg);
        if (filename == null) {
            System.out.println("error: couldn't find filename");
        }

        String tempRelativePath = "temp/" + filename;
        ResourceLoader.saveBytes(tempRelativePath, msg.getResponseBody().getBytes());

        List<TestReport> testReports= fileTests.performTests(fileType,ResourceLoader.getResourcesPath() + tempRelativePath);
        // Perform tests on temp file and move it to the appropriate folder.
        boolean isNotSafe = reportManager.addReportBatch(testReports);
        moveFile(filename, isNotSafe);

        if (isNotSafe) {
            showPopUp(filename);
            try {
                msg.setResponseHeader(msg.getRequestHeader().getVersion() + " 403 Forbidden");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * This method is responsible for showing the popup when an unsafe
     * file is encountered.
     *
     * @param filename - The name of the file.
     */
    private void showPopUp(String filename) {
        new FileTesterPopupMenuUI(filename);
    }


    /**
     * This method moves the temporary file to the user's Downloads dir
     * if it's safe, otherwise it is moved to the resources' unsafe dir.
     *
     * @param fileName - The path of the temporary file.
     * @param isNotSafe - Whether the file is safe or not.
     */
    private static void moveFile(String fileName, boolean isNotSafe) {
        String userHome = System.getProperty("user.home");
        Path sourceTempFilePath = Path.of(ResourceLoader.getResourcesPath() + "temp/" + fileName);
        Path targetTempFilePath;

        if (isNotSafe) {
            targetTempFilePath = Path.of(ResourceLoader.getResourcesPath() + "unsafe/" + fileName);
        } else {
            targetTempFilePath = Path.of(userHome + "/Downloads/" + fileName);
        }

        CopyOption[] options = { StandardCopyOption.REPLACE_EXISTING };
        try {
            Files.move(sourceTempFilePath, targetTempFilePath, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method retrieves from the database if this
     * extension is enabled or not.
     *
     * @return true if it is else false.
     */
    private boolean isFileTesterExtensionEnabled() {
        return ResourceLoader.loadString(
                JSONLoader.getLabel("EnableFileTesterExtension")).equals("true");
    }
}

