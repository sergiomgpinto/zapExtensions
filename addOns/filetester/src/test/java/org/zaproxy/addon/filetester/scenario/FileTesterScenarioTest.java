package org.zaproxy.addon.filetester.scenario;

import org.junit.jupiter.api.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.filetester.FileTesterProxyListener;
import org.zaproxy.addon.filetester.parser.GoogleDriveParser;
import org.zaproxy.addon.filetester.parser.Parser;
import org.zaproxy.addon.filetester.utils.ResourceLoader;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTesterScenarioTest {
    String downloads = System.getProperty("user.home") + "/Downloads/";
    String tempDir = ResourceLoader.getResourcesPath() + "temp/";
    String unsafeDir = ResourceLoader.getResourcesPath() + "unsafe/";



    /**
     * This method test the following requirement:
     * JPEG and PNG pictures should be scanned to see if they contain valid image data, or
     * some other type of data
     * In this case a valid image is downloaded
     */
    @Test
    void ValidImageScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("images/brain.jpg");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("images/brainjpgresponseheader.txt");
        msg.setResponseHeader(responseheader);
        proxy.onHttpResponseReceive(msg);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        // check that file is in the downloads, not in the temp dir anymore and not in the unsafe dir
        assertTrue(Files.exists(Path.of(downloads + fileName)));
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertFalse(Files.exists(Path.of(unsafeDir + fileName)));
    }



    /**
     * This method test the following requirement:
     * JPEG pictures should be scanned for EXIF metadata, and the data should be listed in
     * the results of the scan
     * In this case a valid JPG image is downloaded
     */
    @Test
    void ValidExifMetaDataScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("images/brain.jpg");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("images/brainjpgresponseheader.txt");
        msg.setResponseHeader(responseheader);
        proxy.onHttpResponseReceive(msg);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        assertTrue(Files.exists(Path.of(downloads + fileName)));
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertFalse(Files.exists(Path.of(unsafeDir + fileName)));
    }



    /**
     * This method test the following requirement:
     * ZIP files should be scanned to see if they are password-protected
     * In this case a not password protected ZIP file is downloaded
     */
    @Test
    void NotPassWordProtectedZIPScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("zip_files/normal_zip.zip");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("zip_files/normalzipresponseheader.txt");
        msg.setResponseHeader(responseheader);
        proxy.onHttpResponseReceive(msg);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        assertTrue(Files.exists(Path.of(downloads + fileName)));
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertFalse(Files.exists(Path.of(unsafeDir + fileName)));
    }

    /**
     * This method test the following requirement:
     * ZIP files should be scanned to see if they are password-protected
     * In this case a password protected ZIP file is downloaded
     */
    @Test
    void PassWordProtectedZIPScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("zip_files/pwd_protected_zip.zip");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("zip_files/pwd_protected_zipresponseheader.txt");
        msg.setResponseHeader(responseheader);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        System.out.println("filename in test = " + fileName);
        proxy.onHttpResponseReceive(msg);
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertTrue(Files.exists(Path.of(unsafeDir + fileName)));
        assertFalse(Files.exists(Path.of(downloads + fileName)));

    }

    /**
     * This method test the following requirement:
     * ZIP files should be scanned to see if they are a ZIP-bomb
     * In this case a ZIP file that is not a ZIP bomb is downloaded
     */
    @Test
    void NotZIPBombScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("zip_files/normal_zip.zip");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("zip_files/normalzipresponseheader.txt");
        msg.setResponseHeader(responseheader);
        proxy.onHttpResponseReceive(msg);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        assertTrue(Files.exists(Path.of(downloads + fileName)));
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertFalse(Files.exists(Path.of(unsafeDir + fileName)));
    }



    /**
     * This method test the following requirement:
     * EXE files should be automatically run through VirusTotal, and the result should include
     * an overview of the test results
     * In this case a safe .exe file is downloaded
     */
    @Test
    void SafeExeScenarioTest() throws HttpMalformedHeaderException {
        FileTesterProxyListener proxy = new FileTesterProxyListener();
        HttpMessage msg = new HttpMessage();
        byte[] responsebody = ResourceLoader.loadBytes("virustotal/full.exe");
        msg.setResponseBody(responsebody);
        String responseheader = ResourceLoader.loadString("virustotal/fullexeresponseheader.txt");
        msg.setResponseHeader(responseheader);
        proxy.onHttpResponseReceive(msg);
        Parser googleDriveParser = new GoogleDriveParser();
        String fileName = googleDriveParser.getFileName(msg);
        assertFalse(Files.exists(Path.of(tempDir + fileName)));
        assertFalse(Files.exists(Path.of(unsafeDir + fileName)));
        assertTrue(Files.exists(Path.of(downloads + fileName)));

    }


}
