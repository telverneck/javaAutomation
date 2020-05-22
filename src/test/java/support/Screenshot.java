package support;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Screenshot {
    public static void takeShot(WebDriver browser, String file){
        File screenshot = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(screenshot, new File(file));

        }catch (Exception e) {
            System.out.println("Error while trying to copy the file" + e.getMessage());
        }
    }
}
