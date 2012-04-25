package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Join extends GAEObject {

	public final static String KIND = "Join";

	public Join(String username) {
		super(KIND, username);
		this.putData("date", new Date());
	}

	public Join(Entity e) {
		super(KIND, e);
	}

	public Date getDate() {
		return (Date) data.get("date");
	}
}
