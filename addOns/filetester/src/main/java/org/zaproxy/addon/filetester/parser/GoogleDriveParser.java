package org.zaproxy.addon.filetester.parser;

import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpResponseHeader;
import org.zaproxy.addon.filetester.files.UnknownFile;
import org.zaproxy.addon.filetester.files.JPEGFile;
import org.zaproxy.addon.filetester.files.PNGFile;
import org.zaproxy.addon.filetester.files.ZipFile;
import org.zaproxy.addon.filetester.files.FileType;
import org.zaproxy.addon.filetester.files.ExeFile;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the parser interface
 * for the OneDrive website.
 *
 * @see Parser
 */
public class GoogleDriveParser implements Parser {

    /**
     * Checks if msg is a suspicious download.
     *
     * @param msg
     * @return the file-extension (null if not suspicious)
     */
    @Override
    public FileType getSuspiciousFileType(HttpMessage msg) {

        HttpResponseHeader header = msg.getResponseHeader();
        FileType result;

        String body = msg.getResponseBody().toString();
        if (body == null || body.isEmpty() || body.isBlank()) {
            return null;
        }
        if (header == null)
            return null;

        // indicates download instead of preview or in-browser use, also ignore the file response.bin
        if (!header.getHeadersAsString().contains("Content-Disposition: attachment") || header.getHeadersAsString().contains("filename=\"response.bin\""))
            return null;

        if (header.hasContentType("application/octet-stream") || header.hasContentType("application/x-ms-dos-executable") ||header.hasContentType("application/x-msdownload"))
            result = new ExeFile();
        else if (header.hasContentType("application/zip") || header.hasContentType("application/x-zip-compressed") || header.hasContentType("application/x-zip"))
            result = new ZipFile();
        else if (header.hasContentType("image/jpeg"))
            result = new JPEGFile();
        else if (header.hasContentType("image/png"))
            result = new PNGFile();
        else
            result = new UnknownFile();

        return result;
    }

    /**
     * Returns the filename to use.
     *
     * @param msg - the http response recieved
     * @return the filename if found or a randomly generated name if not
     */
    @Override
    public String getFileName(HttpMessage msg) {
        Random rand = new Random();
        byte[] nameData = new byte[10];
        rand.nextBytes(nameData);
        String filename = new String(nameData);

        Pattern p = Pattern.compile("filename=\"[^;\r\n]*\"");
        Matcher m = p.matcher(msg.getResponseHeader().getHeadersAsString());
        if (m.find()) {
            filename = m.group();
            filename = filename.substring(10, filename.length() - 1);
        }
        return filename;
    }

}
