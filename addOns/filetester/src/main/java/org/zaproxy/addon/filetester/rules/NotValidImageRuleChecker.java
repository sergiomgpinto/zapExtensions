package org.zaproxy.addon.filetester.rules;

import org.zaproxy.addon.filetester.reporting.TestReport;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
/**
 * This class will check if an image contains image data or not
 * This class inherits the Rule interface.
 *
 * @see @Rule
 */
public class NotValidImageRuleChecker implements Rule {
private final String filePath;
private final String rule="NotValidImageRuleChecker";

public NotValidImageRuleChecker(String filePath){
     this.filePath=filePath;
}

    /**
     * This method will check if a file contains image data or not
     * @return TestReport containing if it does not contain an image and False otherwise
     */
    @Override
    public TestReport checkRule() throws IOException {
    File file = new File( filePath );
        boolean result = false;
    try {
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        result = ImageIO.read(iis) == null;
    }catch (Exception e){
        return new TestReport(filePath,rule,false,"Error occured when reading file");
    }
    if(result){
        return new TestReport(filePath,rule,true,"Not a Valid Image");
    }else{
        return new TestReport(filePath,rule,false,"Valid Image Detected");
    }
}

}
