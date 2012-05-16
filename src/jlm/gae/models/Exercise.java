package jlm.gae.models;

import java.util.GregorianCalendar;

import com.google.appengine.api.datastore.*;

public class Exercise extends GAEObject {

	public final static String KIND = "exercise";

	public Exercise(String username, String exoname, String exolang,
			String course, int passedtests, int totaltests, String source) {
		super(KIND);
		put("username", username);
		put("exoname", exoname);
		put("exolang", exolang);
		put("course", course);
		put("passedtests", passedtests);
		put("totaltests", totaltests);
		put("source", source);
		put("date", new GregorianCalendar());
	}

	public Exercise(Entity e) {
		super(e);
	}

	public String getUsername() {
		return (String) get("username");
	}

	public String getExoName() {
		return (String) get("exoname");
	}

	public String getExoLang() {
		return (String) get("exolang");
	}

	public String getCourse() {
		return (String) get("course");
	}

	public Integer getPassedTests() {
		return (Integer) get("passedtests");
	}

	public Integer getTotalTests() {
		return (Integer) get("totaltests");
	}

	public String getSource() {
		return (String) get("source");
	}

	public GregorianCalendar getDate() {
		return (GregorianCalendar) get("date");
	}

	@Override
	public Answer save() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(entity);
		return Answer.ALL_IS_FINE;
	}
}