package connect;

import org.testng.annotations.Test;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class WikiLinks extends Base {

	@Test
	public static void findingAllLinks() throws InterruptedException, IOException {
// Creating a new set of Cookies
		CookieHandler.setDefault(new CookieManager());

// navigating to connect.thrivecincinnati.com
		driver.get(baseUrl);
		Thread.sleep(5000);

// Finding user name field and entering email
		driver.findElement(By.id("default")).sendKeys(connect.Util.USERNAME);

// Finding password field and entering password
		driver.findElement(By.id("pass_unencrypted")).sendKeys(connect.Util.PASSWORD);

// Clicking Submit button
		driver.findElement(By.id("signin-submit-button")).click();
		Thread.sleep(5000);
		
// Navigate to Community wiki page
		driver.navigate().to("https://connect.thrivecincinnati.com/community_building/community_wiki");
		List<WebElement> communityWiki = driver.findElements(By.xpath("//div[@id='ig-leafpreview']//a"));
		printUrlToExcel(communityWiki, sh1);
		
// Navigate to Knowledge tank wiki page
		driver.navigate().to("https://connect.thrivecincinnati.com/skill_building/knowledge_tank");
		List<WebElement> knowledgeTank = driver.findElements(By.xpath("//div[@id='ig-leafpreview']//a"));
		printUrlToExcel(knowledgeTank, sh1);

// Navigate to Open Learning wiki
		driver.navigate().to("https://connect.thrivecincinnati.com/skill_building/open_learning_wiki");
		List<WebElement> openLearning  = driver.findElements(By.xpath("//div[@id='ig-leafpreview']//a"));
		printUrlToExcel(openLearning, sh1);
		loopThroughExcel();
	}
public static void printUrlToExcel(List<WebElement> pageLinks, XSSFSheet sh1) throws IOException {
		
		for (int i = 0; i < pageLinks.size(); i++) {
			
			WebElement ele = pageLinks.get(i);

// Getting the href of the tag and turning it into a string
			String url = ele.getAttribute("href");
			
// Preventing duplicate links being added to list
			if(i >= 1 && stringSet.contains(url) == true) {
				System.out.println("Didnt print duplicate - " + url);
				continue;
			}
// URL filter criteria
			 if (url == null || !url.contains("connect.thrive") || url.contains("#") || url.contains("logout") || url.contains("add.") || url.contains("my.subscriptions")
			|| url.contains("calendarevent") ||  url.contains(".profile") || url.contains("mailto") || url.contains("download") ||  url.contains(".card")
			|| url.contains("thrivepictures") || url.contains("pto") || url.contains("action=") || url.contains("skill_building_files") || url.contains("my.discussions"))
			{
			System.out.println("skipping");
				continue;

// If 'url' passes filters
			} else {

// Setting the value of newRow to last row number + 1, so loop doesn't continually set cell value to same cell
				int newRow = (sh1.getLastRowNum() + 1);

// Creating a 'row' at 'newRow'
				Row row = sh1.createRow(newRow);

// Creating a cell at '0' in 'row'
				Cell originUrl = row.createCell(0);
				
// Creating a cell at '1' in a row
				Cell cell = row.createCell(1);

// Setting value of the cell with the 'originUrl'
				originUrl.setCellValue(driver.getCurrentUrl());
				
// Setting value of the cell with the 'URL'
				cell.setCellValue(url);
				System.out.println(url);
// Adding url to list hashSet
				stringSet.add(url);
			}	
			}
			
	}
		public static void loopThroughExcel() throws IOException {

			for (int row = 1; row < sh1.getLastRowNum() + 1; row++) {
				String url = (sh1.getRow(row).getCell(1)).toString();
				Row newRow = sh1.getRow(row);
				System.out.println("navigating to " + url);
// Navigating to url
				driver.navigate().to(url);
				
// Writes to excel file that link is bad			
				if (ifErrorPage(url) == true) {
					Cell badLink = newRow.createCell(2);
					badLink.setCellValue("Bad Link");
				} else {
// Writes to excel file that link is good	
					Cell goodLink = newRow.createCell(2);
					goodLink.setCellValue("OK");
					
					List<WebElement> linksToExcel = driver.findElements(By.xpath("//div[@id='ig-leafpreview']//a"));
					if(linksToExcel.size() == 0) {
						continue;
					}else {
					System.out.println("*********************** Printing new set of URL's from " + driver.getCurrentUrl() +" to Excel *********************");
					printUrlToExcel(linksToExcel, sh1);
					
					}
				}
			}
				}
				public static boolean ifErrorPage(String url) {
					
					List<WebElement> error = (driver.findElements(By.id("errorpage-message")));
					if (error.size() > 0) {
						System.out.println("BADDDDDDD");
						return true;
					} else {
						return false;
					}
			
		
				}
	}
				

