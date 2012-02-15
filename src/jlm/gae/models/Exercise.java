package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Exercise extends GAEObject {

	public final static String KIND = "Exercise";

	private String userName;
	private String exoName;
	private String exoLang;
	private String courseName;
	private int passedTests;
	private int totalTests;
	private Date date;

	public Exercise(String userName, String exoName, String exoLang,
			String courseName, int passedTests, int totalTests) {
		super(KIND);
		createKey(exoName);

		this.userName = userName;
		this.exoName = exoName;
		this.exoLang = exoLang;
		this.courseName = courseName;
		this.passedTests = passedTests;
		this.totalTests = totalTests;
		date = new Date();
	}

	public Exercise(Entity e) {
		super(KIND);
		key = e.getKey();
		userName = (String) e.getProperty("username");
		exoName = (String) e.getProperty("exoname");
		exoLang = (String) e.getProperty("exolang");
		courseName = (String) e.getProperty("coursename");
		passedTests = (Integer) e.getProperty("passedtests");
		totalTests = (Integer) e.getProperty("totaltests");
		date = (Date) e.getProperty("date");
	}

	public void save() {
		Entity en = new Entity(KIND, key);
		en.setProperty("username", userName);
		en.setProperty("exoname", exoName);
		en.setProperty("exolang", exoLang);
		en.setProperty("coursename", courseName);
		en.setProperty("passedtests", passedTests);
		en.setProperty("totaltests", totalTests);
		en.setProperty("date", date);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(en);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExoName() {
		return exoName;
	}

	public void setExoName(String exoName) {
		this.exoName = exoName;
	}

	public String getExoLang() {
		return exoLang;
	}

	public void setExoLang(String exoLang) {
		this.exoLang = exoLang;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getPassedTests() {
		return passedTests;
	}

	public void setPassedTests(int passedTests) {
		this.passedTests = passedTests;
	}

	public int getTotalTests() {
		return totalTests;
	}

	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
