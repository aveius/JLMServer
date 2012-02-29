package jlm.gae;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import jlm.gae.models.Course;

public class CourseServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<String> courses = new ArrayList<String>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(Course.KIND);

		PreparedQuery pq = datastore.prepare(q);

		for (Entity en : pq.asIterable()) {
			Course co = new Course(en);
			courses.add(co.getCourseName());
		}

		ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
		oos.writeObject(courses);
		oos.close();
	}
}
