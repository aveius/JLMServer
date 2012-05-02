package jlm.gae.models;

import com.google.appengine.api.datastore.Entity;

public class Course extends GAEObject {

	public final static String KIND = "course";

	public Course(String courseName, String password, String teacher_password) {
		super(KIND, courseName);

		this.putData("password", password);
		this.putData("teacher_password", teacher_password);
	}

	public Course(Entity e) {
		super(KIND, e);
	}

	public String getPassword() {
		return (String) data.get("password");
	}

	public String getTeacherPassword() {
		return (String) data.get("teacher_password");
	}
}