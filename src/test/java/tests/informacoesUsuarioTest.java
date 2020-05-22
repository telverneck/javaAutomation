package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Generator;
import support.Screenshot;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "informacoesUsuarioTest.csv")

public class informacoesUsuarioTest {
    private WebDriver browser;
    private String base_url;
    String buttonAddMoreData = "//button[@data-target='addmoredata']";


    @Rule
    public TestName test = new TestName();

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\drivers\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        base_url = "http://www.juliodelima.com.br/";

        browser.get(base_url + "taskit");

        String buttonSignIn = "//a[contains(text(), \"Sign\")]";
        String tabMoreData = "//a[@href='#moredata']";

        WebElement formSignInBox = browser.findElement(By.id("signinbox"));

        browser.findElement(By.xpath(buttonSignIn)).click();
        formSignInBox.findElement(By.name("login")).sendKeys("julio0001");
        formSignInBox.findElement(By.name("password")).sendKeys("123456");
        formSignInBox.findElement(By.linkText("SIGN IN")).click();

        WebElement me = browser.findElement(By.className("me"));

        //String textoMe = me.getText();
        //assertEquals("Hi, Julio", textoMe);

        me.click();
        browser.findElement(By.xpath(tabMoreData)).click();
    }

    @Test
    public void testAddUserInfo(@Param(name="tipo")String tipo, @Param(name="contato")String contato,
                                @Param(name="mensagem")String mensagem) {

        browser.findElement(By.xpath(buttonAddMoreData)).click();

        WebElement formAddMoreData = browser.findElement(By.id("addmoredata"));

        WebElement comboType = formAddMoreData.findElement(By.name("type"));
        new Select(comboType).selectByVisibleText(tipo);

        formAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        formAddMoreData.findElement(By.linkText("SAVE")).click();

        WebElement popupMensagem = browser.findElement(By.id("toast-container"));
        String popText = popupMensagem.getText();

        assertEquals(mensagem,popText);

    }

    //@Test
    public void removerContatoUsuario(){
    // //span[text()='559299999999']/following-sibling::a

    browser.findElement(By.xpath("//span[text()='559299999999']/following-sibling::a")).click();

    browser.switchTo().alert().accept();

    WebElement popupMensagem = browser.findElement(By.id("toast-container"));
    String popText = popupMensagem.getText();

    assertEquals("Rest in peace, dear phone!",popText);

    String screenshotFile = "c:/Selenium/" + Generator.dateTimeToFile() + test.getMethodName() + ".png";
    Screenshot.takeShot(browser, screenshotFile );

    WebDriverWait wait = new WebDriverWait(browser, 10);
    wait.until(ExpectedConditions.stalenessOf(popupMensagem));

    browser.findElement(By.linkText("Logout")).click();
    }


    @After
    public void tearDown() {
        browser.quit();

    }

}
