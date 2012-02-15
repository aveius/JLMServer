package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Exercise extends GAEObject {

	public final static String KIND = "Exercise";

	private String userName;
	private String exoName;
	private String exoLang;
	private int passedTests;
	private int totalTests;
	private Date date;

	public Exercise(String userName, String exoName, String exoLang,
			int passedTests, int totalTests) {
		super(KIND);
		this.userName = userName;
		this.exoName = exoName;
		this.exoLang = exoLang;
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
		passedTests = (Integer) e.getProperty("passedtests");
		totalTests = (Integer) e.getProperty("totaltests");
		date = (Date) e.getProperty("date");
	}

	public void save() {
		Entity exercise = new Entity(KIND, key);
		exercise.setProperty("username", userName);
		exercise.setProperty("exoname", exoName);
		exercise.setProperty("exolang", exoLang);
		exercise.setProperty("passedtests", passedTests);
		exercise.setProperty("totaltests", totalTests);
		exercise.setProperty("date", date);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(exercise);
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
