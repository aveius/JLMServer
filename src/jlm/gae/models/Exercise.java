package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Exercise extends GAEObject {

	public final static String KIND = "Exercise";

	public Exercise(String userName, String exoName, String exoLang,
			String courseName, int passedTests, int totalTests) {
		super(KIND, userName);

		this.putData("exoname", exoName);
		this.putData("exolang", exoLang);
		this.putData("coursename", courseName);
		this.putData("passedtests", passedTests);
		this.putData("totaltests", totalTests);
		this.putData("date", new Date());
	}

	public Exercise(Entity e) {
		super(KIND, e);
	}

	public String getExoName() {
		return (String) data.get("exoname");
	}

	public String getExoLang() {
		return (String) data.get("exolang");
	}

	public String getCourseName() {
		return (String) data.get("coursename");
	}

	public Integer getPassedTests() {
		return (Integer) data.get("passedtests");
	}

	public Integer getTotalTests() {
		return (Integer) data.get("totaltests");
	}

	public Date getDate() {
		return (Date) data.get("date");
	}
}