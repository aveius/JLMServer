package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Exercise extends GAEObject {

	public final static String KIND = "exercise";

	public Exercise(String username, String exoname, String exolang,
			String course, int passedtests, int totaltests, String source) {
		super(KIND, username);

		this.putData("exoname", exoname);
		this.putData("exolang", exolang);
		this.putData("course", course);
		this.putData("passedtests", passedtests);
		this.putData("totaltests", totaltests);
		this.putData("source", source);
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

	public String getCourse() {
		return (String) data.get("course");
	}

	public Integer getPassedTests() {
		return (Integer) data.get("passedtests");
	}

	public Integer getTotalTests() {
		return (Integer) data.get("totaltests");
	}

	public String getSource() {
		return (String) data.get("source");
	}

	public Date getDate() {
		return (Date) data.get("date");
	}
}