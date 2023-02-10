package org.zaproxy.addon.filetester.parser;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.filetester.files.FileType;

/**
 * This class implements the interface
 * which allows to abstract several
 * websites to download
 * files from.
 */
public interface Parser {

    /**
     * Checks if msg is a suspicious download.
     * @param msg
     * @return the file-extension (null if not suspicious)
     */
    FileType getSuspiciousFileType(HttpMessage msg);

    /**
     * Returns the filename to use.
     * @param
     * @return
     */
    String getFileName(HttpMessage msg);
}
