package workspace;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DailySending150k {
			
			int testNum=0;
		    
			ArrayList<String> fromList = new ArrayList<String>();
			ArrayList<String> toList = new ArrayList<String>();
			WebDriver driver;

			public static void main(String[] args) throws Exception
			{
				DailySending150k objCube=new DailySending150k();
				objCube.readExcel();
				objCube.runScript();
				System.exit(0);
			}	
			
			public void readExcel() throws Exception
			{
				try {

				File src = new File("/Users/puneetjhansal/Downloads/DailyMail-1.xlsx");
				FileInputStream fis = new FileInputStream(src);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet sheet1 = wb.getSheetAt(0);
				int rowCount = sheet1.getLastRowNum();
				
				for (int i = 1; i <= rowCount; i++) 
				{
					String value1 = sheet1.getRow(i).getCell(0).getStringCellValue();
					if (value1.equalsIgnoreCase("True"))
					{
						fromList.add(sheet1.getRow(i).getCell(1).getStringCellValue());
						toList.add(sheet1.getRow(i).getCell(2).getStringCellValue());
					}

				}
				
				wb.close();
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			
			@SuppressWarnings("deprecation")
			public void runScript() throws Exception
			{
				
				for (int i = 0; i < fromList.size(); i++) 
				{
					
				//System.out.println(conList.get(0));
				driver=new FirefoxDriver();
				driver.get("https://webmail.gandi.net/");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						
				//Enter Email id
				String sendEmail=fromList.get(i);
				System.out.println(sendEmail);
				driver.findElement(By.xpath(".//*[@id='rcmloginuser']")).sendKeys(sendEmail);
						
				//Enter Password
				driver.findElement(By.xpath(".//*[@id='rcmloginpwd']")).sendKeys("qF3FC$skuQ2B5d");
				driver.findElement(By.xpath(".//*[@id='rcmloginsubmit']")).click();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(4000);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
				
				//Click on Compose button
				driver.findElement(By.xpath("//*[@class='button compose']")).click();
				String toEmail=toList.get(i);
				System.out.println(toEmail);
				
				Thread.sleep(4000);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath(".//*[@id='_to']")).click();
				driver.findElement(By.xpath(".//*[@id='_to']")).sendKeys(toEmail);
				driver.findElement(By.xpath(".//*[@id='compose-subject']")).sendKeys("Vos e-mails ciblÈs");
				driver.findElement(By.xpath(".//*[@id='composebody']")).sendKeys("Bonjour,\n\nJe me prÈsente Henri PERPIN. Je vous propose des solutions sur-mesure d'e-mailing ciblÈs.\n\nL'e-mailing est le dernier media rentable. J'en sais quelque chose. Nous vous proposons 10 000 mails envoyÈs pour seulement 199 Ä.\n\nTestez le : appelez-moi au 0664922233\n\nCordialement\n\nHenri PEPIN\nDirecteur de sociÈtÈs de marketing\nPort 0664922233\n");
				
				//Send mail
				driver.findElement(By.xpath(".//*[@class='button send']")).click();
				
				Thread.sleep(1500);
				driver.quit();
				}
			}
		
	}

		
