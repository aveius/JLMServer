package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Help extends GAEObject {

	public final static String KIND = "Help";

	public Help(String username, String course, String password, String status) {
		super(KIND);
		put("username", username);
		put("course", course);
		put("status", status);
		put("date", new Date());
	}

	public Help(Entity e) {
		super(e);
	}

	public String getUsername() {
		return (String) get("username");
	}

	public String getCourse() {
		return (String) get("course");
	}

	public String getStatus() {
		return (String) get("status");
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
