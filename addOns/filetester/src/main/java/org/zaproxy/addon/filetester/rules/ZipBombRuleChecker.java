package org.zaproxy.addon.filetester.rules;

import org.zaproxy.addon.filetester.utils.FileUtils;
import org.zaproxy.addon.filetester.reporting.TestReport;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * This class checks if the downloaded zip file is a zip bomb
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class ZipBombRuleChecker implements Rule{
    private String rule = "ZipBomb";
    private String filePath;
    public ZipBombRuleChecker(String filePath){
        this.filePath=filePath;

    }
    /**
     * This method checks if the downloaded zip file is a zip bomb
     *
     * @return TestReport containing if the zip file is a zip bomb
     */
    @Override
    public TestReport checkRule() {
        if (!filePath.endsWith(".zip")) {
            System.out.println("Not a zip file");
            return new TestReport(filePath,  rule,  false, "Not a zip file.");
        }
        try {
            return unzip(filePath);
        } catch (Exception e) {
            return new TestReport(filePath,  rule, false,"Error occured when reading file");
        }
        //info = "Zip file passed the test.";
        //result = false;
       // return new TestReport(filePath,  rule,  result, info);
    }




    static final int BUFFER = 512;
    static final long TOOBIG = 0x6400000; // Max size of unzipped data, 100MB
    static final int TOOMANY = 1024;      // Max number of files
// ...

    /**
     * This method unzips the file contents to check it's size and the amount of files inside the zip.
     *
     * @param filename = the path of the file you want to unzip
     * @return true if and only if the zip file has malicious content. I.E. more than 1024 files, files larger than 100MB or files containing a malicious path as filename.
     */
    private final TestReport unzip(String filename) throws java.io.IOException {
        String ZIP_RESOURCES_PATH  = "/zap-extensions/addOns/filetester/src/main/resources/org/zaproxy/addon/filetester/resources/zip_files/extracted_zip_files/";
        String ABSOLUTE_USER_DIR_PATH = System.getProperty("user.dir");
        String ZAP_EXTENSIONS = "zap-extensions";
        String ZA_PROXY = "zaproxy";
        String zapExtPath;

        if (ABSOLUTE_USER_DIR_PATH.contains(ZAP_EXTENSIONS)){
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZAP_EXTENSIONS));
        }
        else{
            zapExtPath = ABSOLUTE_USER_DIR_PATH.substring(0,ABSOLUTE_USER_DIR_PATH
                    .indexOf(ZA_PROXY));
        }
        String intended_dir = null;
        try {
            intended_dir = zapExtPath + ZIP_RESOURCES_PATH;
        } catch (Exception e) {
//            e.printStackTrace();
        }


        FileInputStream fis = new FileInputStream(filename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        int entries = 0;
        long total = 0;
        try {
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                // Write the files to the disk, but ensure that the filename is valid,
                // and that the file is not insanely big
                String name = FileUtils.validateFilename(intended_dir +entry.getName(), intended_dir);
                if (name == null) {
                    return new TestReport(filePath,  rule,  true,  "File has a malicious path in its name.");
                }
                if (entry.isDirectory()) {
                    System.out.println("Creating directory " + name);
                    new File(name).mkdir();
                    continue;
                }
                FileOutputStream fos = new FileOutputStream(name);
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                while (total + BUFFER <= TOOBIG && (count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                    total += count;
                }
                dest.flush();
                dest.close();
                zis.closeEntry();
                entries++;
                if (entries > TOOMANY) {
                    System.out.println("Too many files to unzip.");
                    return new TestReport(filePath,  rule,   true, "Zip file is a zip bomb, it has too many files inside.");
                }
                if (total + BUFFER > TOOBIG) {
                    System.out.println("File being unzipped is too big.");
                    return new TestReport(filePath,  rule,  true, "Zip file is a zip bomb, it is too big.");
                }
            }
        } catch (Exception e) {
            System.out.println("caught an error");
//            e.printStackTrace();
        } finally {
            zis.close();
        }
        return new TestReport(filePath,  rule,  false, "Zip file passed the test.");
    }
}
