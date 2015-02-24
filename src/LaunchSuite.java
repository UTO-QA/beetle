import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.SwingUtilities;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LaunchSuite {

	// QA Environment
	private static final String ENVIRONMENT = "***The link to the QA or Prod Environment goes her***(Basically a parent link)";

	// Global WebDriver
	private static WebDriver driver = new FirefoxDriver();

	// For writing the logs in HTML
	private static PrintWriter writer1;
	private static PrintWriter writer2;

	// Microsoft Excel
	private static HSSFWorkbook workbook;

	// Data-structure to store all the links
	private static HashSet<String> allLinkHash = new HashSet<String>();

	// Strings to be passed
	static String stringToBePassed = null;
	static int flag = 0;

	// Program starts here
	public static void main(String[] args) throws IOException {

		// Graphical user interface
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});

		// Create the log files
		writer1 = new PrintWriter("MyASUSuiteLog.html", "UTF-8");
		writer2 = new PrintWriter("ChangesinMyASU.html", "UTF-8");

		// Create the Excel File
		workbook = new HSSFWorkbook();

		// To check if login button was clicked and start the login process
		int i = 0;
		while (i == 0)
			if (MainFrame.loginFlag == 1) {
				// Login
				final String USERNAME = MainFrame.userString;
				final String PASSWORD = MainFrame.passString;
				Login(USERNAME, PASSWORD);
				i = 1;
			} else {
				// Do Nothing
			}

		final String USERNAME = MainFrame.userString;
		final String PASSWORD = MainFrame.passString;

		// Navigate through all Tabs and capture all the links
		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");
		
		// newer tabs can be added in similar fashion

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		driver.get(ENVIRONMENT + "***links to various tabs***");
		FindAllLinks("***Name of the tab***");

		// Closes the writers
		CloseAllWriters();

		// Finding changes from previous runs
		matchURLs();

		// Mailing the logs
		mailTheLogs(USERNAME, PASSWORD);

	}

	// Login function
	public static void Login(String USERNAME, String PASSWORD) {

		// Code for logging in
		driver.get(ENVIRONMENT + "/myasu/");
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("password")).sendKeys(PASSWORD);
		driver.findElement(By.cssSelector("input.submit")).click();

		// Checking if logged in successfully
		if ((driver.getPageSource().contains("SIGN OUT"))) {
			flag = 1;
		} else {
			flag = 2;
			driver.quit();
		}

		// Updating the log
		writer1.println("<html><body><br><br><h1 align=\"center\">LOGIN TO MyASU!</h1>");

	}

	public static void FindAllLinks(String TabName) throws IOException {

		// Updating the logs
		writer1.println("<br><br><h1 align=\"center\">" + TabName
				+ "</h1><br><br>");

		// Capturing all the links in the page and storing it in a list
		List<WebElement> linkList = driver.findElements(By
				.cssSelector("a[href]"));

		// Displaying in console
		System.out.print("Currently Parsing " + TabName + " --> ");
		System.out.println(linkList.size() + " links captured.");

		// DataStructure to hold hyper-links of all the links captured above
		HashSet<String> hrefHashSet = new HashSet<String>();

		// Adding the hrefs in HashSet
		for (WebElement link : linkList) {
			hrefHashSet.add(link.getAttribute("href").toString() + " "
					+ link.getText());
		}

		// Date and time for log
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		// Initialize row of Excel sheet
		int rownum = 0;
		// Create Different tabs in the excel sheet for different tabs
		HSSFSheet sheet = workbook.createSheet(TabName + " URLs");

		// Store all links in the allLinkHash file created globally
		for (String hrefs : hrefHashSet) {

			String[] internalSplit = hrefs.split(" ");

			if (!allLinkHash.contains(hrefs)) {
				allLinkHash.add(hrefs);
				// Check for all unwanted links
				if ((!internalSplit[0].equals(ENVIRONMENT + "myasu/Signout"))
						&& (!internalSplit[0].equals("http:///"))
						&& (!internalSplit[0]
								.equals(ENVIRONMENT + "myasu/pscs"))
						&& (!internalSplit[0].contains("javascript"))
						&& (!internalSplit[0].contains("@asu.edu"))
						&& (!internalSplit[0].contains("keep"))) {

					try {
						driver.get(internalSplit[0]);

						// Display the visited link
						Row row = sheet.createRow(rownum++);
						row.createCell(0).setCellValue(driver.getTitle());
						row.createCell(1).setCellValue(internalSplit[0]);
						writer1.println("["
								+ sdf.format(Calendar.getInstance().getTime())
								+ "] : " + TabName + " Tab - "
								+ driver.getTitle() + " at " + internalSplit[0]
								+ " has been visited<br/>");
					} catch (Exception ex) {
						System.out.println("Exception occured at "
								+ internalSplit[0]);
						driver.close();
						driver = new FirefoxDriver();
					}
				}

			} else {

				if ((!internalSplit[0].equals(ENVIRONMENT + "myasu/Signout"))
						&& (!internalSplit[0].equals("http:///"))
						&& (!internalSplit[0]
								.equals(ENVIRONMENT + "myasu/pscs"))
						&& (!internalSplit[0].contains("javascript"))
						&& (!internalSplit[0].contains("@asu.edu"))
						&& (!internalSplit[0].contains("keep"))) {

					try {
						driver.get(internalSplit[0]);

						// Display the visited link
						Row row = sheet.createRow(rownum++);
						row.createCell(0).setCellValue(driver.getTitle());
						row.createCell(1).setCellValue(internalSplit[0]);
						writer1.println("["
								+ sdf.format(Calendar.getInstance().getTime())
								+ "] : " + TabName + " Tab - "
								+ driver.getTitle() + " at " + internalSplit[0]
								+ " has been visited<br/>");
					} catch (Exception ex) {
						System.out.println("Exception occured at "
								+ internalSplit[0]);
						driver.close();
						driver = new FirefoxDriver();
					}
				}
			}
		}

	}

	public static void CloseAllWriters() throws IOException {

		// Close the web-driver
		driver.quit();

		// Create the Excel Sheet
		String newFile = FileChooser.newFile;
		String newFileName = FileChooser.newFileName;
		FileOutputStream out = new FileOutputStream(new File(newFile));
		FileOutputStream out2 = new FileOutputStream(new File(newFileName));
		workbook.write(out);
		workbook.write(out2);
		out.close();
		out2.close();

		// Close the HTML log file
		writer1.println("</body></html>");
		writer1.close();
	}

	public static void matchURLs() throws IOException {
		String oldFile = FileChooser.oldFile;
		String newFile = FileChooser.newFile;
		// Displaying in Console
		System.out.println("Finding	the changes...");

		// Writer 2 for writing in the ChangesinMyASU
		writer2.println("<html><body><br><br><h1 align=\"center\">Links that have changed from the previous run</h1><br><br>");

		// Importing file from location
		FileInputStream file1 = new FileInputStream(new File(oldFile));
		FileInputStream file2 = new FileInputStream(new File(newFile));

		// Copying the files to Java Excel Object
		HSSFWorkbook workbook1 = new HSSFWorkbook(file1);
		HSSFWorkbook workbook2 = new HSSFWorkbook(file2);

		// Comparison begins
		for (int sheetCount = 0; sheetCount < 1; sheetCount++) {

			// Comparing the tabs in the sheet
			HSSFSheet sheet1 = workbook1.getSheetAt(sheetCount);
			HSSFSheet sheet2 = workbook2.getSheetAt(sheetCount);

			// Copying the tabs to HashSet
			HashSet<String> sheet1Hash = new HashSet<String>();
			HashSet<String> sheet2Hash = new HashSet<String>();

			// Difference in the two tabs will be stored here
			HashSet<String> differenceHash = new HashSet<String>();

			// Populating HashSet sheet1Hash
			Iterator<Row> rowIterator1 = sheet1.iterator();
			while (rowIterator1.hasNext()) {
				Row row = rowIterator1.next();
				sheet1Hash.add(row.getCell(1).getStringCellValue().toString());
			}

			// Populating HashSet sheet2Hash
			Iterator<Row> rowIterator2 = sheet2.iterator();
			while (rowIterator2.hasNext()) {
				Row row = rowIterator2.next();
				sheet2Hash.add(row.getCell(1).getStringCellValue().toString());
			}

			// Comparing the two HashSets
			Iterator<String> iter1 = sheet1Hash.iterator();
			while (iter1.hasNext()) {
				String s = iter1.next();
				if (sheet2Hash.contains(s)) {
					// So that the remaining links can be displayed as the new
					// added links
					sheet2Hash.remove(s);
				} else {
					differenceHash.add(s);
				}
			}

			writer2.println("<html><body><br><br><h2 align=\"center\">Links that have been added in "
					+ sheet1.getSheetName() + "</h1><br><br>");

			// Display all the newly added links
			Iterator<String> iter2 = sheet2Hash.iterator();
			while (iter2.hasNext()) {
				writer2.println(iter2.next().toString() + "<br/>");
			}

			writer2.println("<html><body><br><br><h2 align=\"center\">Links that have been removed in "
					+ sheet1.getSheetName() + "</h1><br><br>");

			// Display all the removed links
			Iterator<String> iter3 = differenceHash.iterator();
			while (iter3.hasNext()) {
				writer2.println(iter3.next().toString() + "<br/>");
			}
		}
		writer2.println("</body></html>");
		writer2.close();
	}

	public static void mailTheLogs(String USERNAME, String PASSWORD) {
		String host = "smtp.asu.edu";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.user", USERNAME);
		properties.setProperty("mail.password", PASSWORD);
		Session session = Session.getDefaultInstance(properties);
		SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMYYYY");
		String newFleName = FileChooser.newFileName;
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME + "@asu.edu"));
			String Mail1 = MailTo.Mail1;
			String Mail2 = MailTo.Mail2;
			String Mail3 = MailTo.Mail3;
			if (!Mail1.isEmpty()) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(Mail1));
			}
			if (!Mail2.isEmpty()) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(Mail2));
			}
			if (!Mail3.isEmpty()) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(Mail3));
			}
			message.setSubject("My ASU Suite Run Results");
			BodyPart messageBodyPart = new MimeBodyPart();

			String body = "Hello, \n \nMy ASU Suite has run successfully and attached are the Log Files. \n \nThank you!";

			messageBodyPart.setText(body);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(
					"MyASUSuiteLog.html")));
			messageBodyPart.setFileName("MyASUSuiteLog_"
					+ sdf2.format(Calendar.getInstance().getTime()) + ".html");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(
					newFleName)));
			messageBodyPart.setFileName(newFleName);
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(
					"ChangesinMyASU.html")));
			messageBodyPart.setFileName("ChangesinMyASU_"
					+ sdf2.format(Calendar.getInstance().getTime()) + ".html");
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Suite ran successfully! \nResults Mailed!");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
}
