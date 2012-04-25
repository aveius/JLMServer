package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Leave extends GAEObject {

	public final static String KIND = "Leave";

	public Leave(String username) {
		super(KIND, username);
		this.putData("date", new Date());
	}

	public Leave(Entity e) {
		super(KIND, e);
	}

	public Date getDate() {
		return (Date) data.get("date");
	}
}
