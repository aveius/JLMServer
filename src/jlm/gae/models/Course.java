package jlm.gae.models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Course extends GAEObject {

	public final static String KIND = "Course";

	private String courseName;

	public Course(String courseName) {
		super(KIND);
		createKey(courseName);

		this.courseName = courseName;
	}

	public Course(Entity e) {
		super(KIND);
		key = e.getKey();
		courseName = (String) e.getProperty("coursename");
	}

	public void save() {
		Entity en = new Entity(KIND, key);
		en.setProperty("coursename", courseName);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(en);
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
