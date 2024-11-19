package hi;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class actions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://www.amazon.com/");
		// actions class for mouse actions
		Actions a = new Actions(driver);
		WebElement move = driver.findElement(By.id("nav-link-accountList-nav-line-1"));

		a.moveToElement(driver.findElement(By.id("twotabsearchtextbox"))).click().keyDown(Keys.SHIFT).sendKeys("hello").doubleClick()
				.build().perform();

	
		// move the hover to the specific option

		a.moveToElement(move).contextClick().build().perform();

	}
}
