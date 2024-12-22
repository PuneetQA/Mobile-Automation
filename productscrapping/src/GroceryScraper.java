import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GroceryScraper {

    public static void main(String[] args) {
        // Ensure ChromeDriver is added to the system PATH
        // Uncomment the line below if ChromeDriver is not set in the PATH
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        // Initialize WebDriver
        ChromeDriver driver = new ChromeDriver();

        // Navigate to the BigBasket homepage
        driver.get("https://www.bigbasket.com/");

        // Create an Excel workbook and worksheetw
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Define column headers for the Excel sheet
        String[] headers = {
            "Product Code", "Product Name", "MRP", "Brand Name", "Variant", "Pack Size",
            "HSN Code / SKU", "GST Percentage", "Product Category", "Product Subcategory",
            "Barcode", "Pack Quantity", "Weight", "Unit", "Is Returnable", "Return Window",
            "Is Vegetarian", "Manufacturer Name", "Manufactured Date", "Nutritional Info",
            "Additive Info", "Instructions", "Is Cancellable", "Description", "Images",
            "Manufacturer Address", "FSSAI Number", "Country of Origin", "Best Before"
        };

        // Add headers to the first row of the Excel sheet
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        try {
            // Locate product categories on the webpage
            List<WebElement> categories = driver.findElements(By.cssSelector(".category-name"));
            int rowIndex = 1; // Start from the second row for product data

            // Iterate through each category
            for (WebElement category : categories) {
                category.click(); // Navigate to the category page

                // Add a wait for the page to load completely
                Thread.sleep(2000);

                // Locate products within the category
                List<WebElement> products = driver.findElements(By.className("break-words h-10 w-full"));

                // Iterate through each product and extract details
                for (WebElement product : products) {
                    Row row = sheet.createRow(rowIndex++);

                    // Extract product details using appropriate locators
                    String productCode = product.getAttribute("data-product-code"); // Adjust locator
                    String name = product.findElement(By.className("product-name")).getText();
                    String price = product.findElement(By.className("product-price")).getText();
                    String brandName = product.findElement(By.className("brand-name")).getText(); // Adjust locator
                    String variant = product.findElement(By.className("variant")).getText(); // Adjust locator
                    String packSize = product.findElement(By.className("pack-size")).getText(); // Adjust locator
                    String hsnCode = product.getAttribute("data-hsn-code"); // Adjust locator
                    String gstPercentage = product.getAttribute("data-gst"); // Adjust locator
                    String categoryName = driver.findElement(By.cssSelector(".category-name")).getText();
                    String subcategoryName = ""; // Extract subcategory if available
                    String barcode = product.findElement(By.className("barcode")).getText(); // Adjust locator
                    String packQty = product.findElement(By.className("pack-qty")).getText(); // Adjust locator
                    String weight = product.findElement(By.className("weight")).getText(); // Adjust locator
                    String unit = product.findElement(By.className("unit")).getText(); // Adjust locator
                    String isReturnable = product.findElement(By.className("returnable")).getText(); // Adjust locator
                    String returnWindow = product.findElement(By.className("return-window")).getText(); // Adjust locator
                    String isVegetarian = product.findElement(By.className("vegetarian")).getText(); // Adjust locator
                    String manufacturerName = product.findElement(By.className("manufacturer-name")).getText(); // Adjust locator
                    String manufacturedDate = product.findElement(By.className("manufactured-date")).getText(); // Adjust locator
                    String nutritionalInfo = product.findElement(By.className("nutritional-info")).getText(); // Adjust locator
                    String additiveInfo = product.findElement(By.className("additive-info")).getText(); // Adjust locator
                    String instructions = product.findElement(By.className("instructions")).getText(); // Adjust locator
                    String isCancellable = product.findElement(By.className("cancellable")).getText(); // Adjust locator
                    String description = product.findElement(By.className("description")).getText();
                    String images = product.findElement(By.tagName("img")).getAttribute("src");
                    String manufacturerAddress = product.findElement(By.className("manufacturer-address")).getText(); // Adjust locator
                    String fssaiNumber = product.findElement(By.className("fssai-number")).getText(); // Adjust locator
                    String countryOfOrigin = product.findElement(By.className("country-of-origin")).getText(); // Adjust locator
                    String bestBefore = product.findElement(By.className("best-before")).getText(); // Adjust locator

                    // Store all extracted details in an array
                    String[] details = {
                        productCode, name, price, brandName, variant, packSize, hsnCode, gstPercentage,
                        categoryName, subcategoryName, barcode, packQty, weight, unit, isReturnable, returnWindow,
                        isVegetarian, manufacturerName, manufacturedDate, nutritionalInfo, additiveInfo,
                        instructions, isCancellable, description, images, manufacturerAddress, fssaiNumber,
                        countryOfOrigin, bestBefore
                    };

                    // Write details to the Excel row
                    for (int i = 0; i < details.length; i++) {
                        row.createCell(i).setCellValue(details[i] != null ? details[i] : "N/A");
                    }
                }
            }

            // Save the Excel file
            FileOutputStream fileOut = new FileOutputStream("FMCG_Products_Categories.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Data successfully saved to FMCG_Products_Categories.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser to free resources
            driver.quit();
        }
    }
}
