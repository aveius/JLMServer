package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Heartbeat extends GAEObject {

	public final static String KIND = "heartbeat";

	public Heartbeat(String username) {
		super(KIND, username);
		this.putData("date", new Date());
	}

	public Heartbeat(Entity e) {
		super(KIND, e);
	}

	public Date getDate() {
		return (Date) data.get("date");
	}
}
