package connect;


import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class Base {
	static WebDriver driver;
	static String baseUrl;
	static File src;
	static FileInputStream inputStream;
	static FileOutputStream outputStream;
	static XSSFWorkbook wb;
	static XSSFSheet sh1;
	static Set<String> stringSet = new HashSet<String>();
  @BeforeClass
	public void setUp() throws IOException {
	// Setting my baseUrl
			baseUrl = ("https://connect.thrivecincinnati.com/?signin");

	// Setting the path to Chrome Driver
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\automation\\Desktop\\JAVA\\chromedriver.exe");

	// Opening Chrome and maximizing the window
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			
			src = new File("C:\\Users\\automation\\Desktop\\JAVA\\BrokenLinkTestResults.xlsx");
			inputStream = new FileInputStream(src);
			wb = new XSSFWorkbook(inputStream);
			sh1 = wb.getSheetAt(0);
			outputStream = new FileOutputStream("C:\\Users\\automation\\Desktop\\JAVA\\BrokenLinkTestResults.xlsx");

		}
	@AfterTest
	public void afterTest() throws IOException {

		wb.write(outputStream);

		// Closes file
		outputStream.close();

		// Close workbook
		wb.close();
		driver.quit();
	}

}


