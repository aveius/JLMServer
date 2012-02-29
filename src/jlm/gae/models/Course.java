package jlm.gae.models;

import com.google.appengine.api.datastore.Entity;

public class Course extends GAEObject {

	public final static String KIND = "Course";

	public Course(String courseName) {
		super(KIND, courseName);
	}

	public Course(Entity e) {
		super(KIND, e);
	}
}