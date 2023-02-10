package org.zaproxy.addon.filetester.rules;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.zaproxy.addon.filetester.utils.FileUtils;
import org.zaproxy.addon.filetester.reporting.TestReport;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.util.Iterator;


/**
 * This class will check if an image contain metadata or not
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class ExifMetadataRuleChecker implements Rule {
    private final String filePath;
    private final String rule="ExifMetadataRuleChecker";

    public ExifMetadataRuleChecker(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method will check if a file contains Exif metadata or not
     *
     * @return TestReport containing metadata
     */
    @Override
    public TestReport checkRule() {
        String result;
        try {
            result = readAndDisplayMetadata(this.filePath);
        } catch (Exception e) {
            return new TestReport(filePath, rule, false, "Error occured when reading file");
        }
        if (result.length() == 0) {
            return new TestReport(filePath, rule, false, "No Meta Found");

        } else {

            return new TestReport(filePath, rule, true, result);
        }
    }
    /**
     *
     * This method will read the Metadata inside a file
     * @param fileName
     * @return String containing metadata inside the file in XML format
     */
    public static String readAndDisplayMetadata( String fileName ) {
        StringBuilder builder=new StringBuilder();
        try {

            File file = new File( fileName );
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();

                for (String name : names) {
                    String metadataTree=displayMetadata(metadata.getAsTree(name));
                    builder.append(metadataTree);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * This method will display the metadata starting from the root
     * @param root
     * @return String containing metadata inside the file in XML format
     */
    private static String displayMetadata(Node root) {
        StringBuilder builder = new StringBuilder();
        return displayMetadata(root, 0,builder);
    }

    /**
     * Add indentation to the string
     * @param level
     * @param builder
     */
    private static void indent(int level,StringBuilder builder) {
        builder.append("    ".repeat(Math.max(0, level)));
    }

    /**
     * This method will traverse the nodes inside the XML tree
     * and write them to a string
     * @param node
     * @param level
     * @param builder
     * @return String containing metadata inside the file in XML format
     */
    private static String displayMetadata(Node node, int level, StringBuilder builder) {
        // print open tag of element
        builder.append("\n");
        indent(level,builder);
        builder.append("<").append(node.getNodeName()).append("\n");
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                builder.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            builder.append("/>").append("\n");
            return builder.toString();
        }

        // children, so close current tag
        builder.append(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1,builder);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level,builder);
        builder.append("</").append(node.getNodeName()).append(">").append("\n");
        return builder.toString();
    }


}
