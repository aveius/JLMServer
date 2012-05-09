package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Switch extends GAEObject {

	public final static String KIND = "Switch";

	public Switch(String username, String course, String password,
			String exoname, String exolang) {
		super(KIND);
		put("username", username);
		put("course", course);
		put("password", password);
		put("exoname", exoname);
		put("exolang", exolang);
		put("date", new Date());
	}

	public Switch(Entity e) {
		super(e);
	}

	public String getUsername() {
		return (String) get("username");
	}

	public String getCourse() {
		return (String) get("course");
	}

	public String getPassword() {
		return (String) get("password");
	}

	public String getExoName() {
		return (String) get("exoname");
	}

	public String getExoLang() {
		return (String) get("exolang");
	}

	public Date getDate() {
		return (Date) get("date");
	}

	@Override
	public Answer save() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(entity);
		return Answer.ALL_IS_FINE;
	}
}