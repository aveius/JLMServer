package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Heartbeat extends GAEObject {

	public final static String KIND = "Heartbeat";

	public Heartbeat(String username, String course, String password) {
		super(KIND);
		put("username", username);
		put("course", course);
		put("date", new Date());
	}

	public Heartbeat(Entity e) {
		super(e);
	}

	public String getUsername() {
		return (String) get("username");
	}

	public String getCourse() {
		return (String) get("course");
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
