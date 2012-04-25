package jlm.gae.models;

import java.util.Date;

import com.google.appengine.api.datastore.*;

public class Switch extends GAEObject {

	public final static String KIND = "Switch";

	public Switch(String username, String exoname, String exolang,
			String course) {
		super(KIND, username);
		this.putData("exoname", exoname);
		this.putData("exolang", exolang);
		this.putData("course", course);
		this.putData("date", new Date());
	}

	public Switch(Entity e) {
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

	public Date getDate() {
		return (Date) data.get("date");
	}
}