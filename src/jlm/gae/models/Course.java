package jlm.gae.models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Course extends GAEObject {

	public final static String KIND = "course";

	public Course(String course, String password, String teacher_password) {
		super(KIND);
		put("course", course);
		put("password", password);
		put("teacher_password", teacher_password);
	}

	public Course(Entity e) {
		super(e);
	}

	public String getCourse() {
		return (String) get("course");
	}

	public String getPassword() {
		return (String) get("password");
	}

	public String getTeacherPassword() {
		return (String) get("teacher_password");
	}

	@Override
	public Answer save() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(KIND);
		q.addFilter("course", Query.FilterOperator.EQUAL, getCourse());
		PreparedQuery pq = datastore.prepare(q);
		if (pq.asIterator().hasNext()) {
			return Answer.COURSE_NAME_ALREADY_USED;
		}
		
		datastore.put(entity);
		return Answer.ALL_IS_FINE;
	}
}