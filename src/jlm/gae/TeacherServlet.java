package jlm.gae;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import jlm.gae.models.Course;
import jlm.gae.models.Exercise;

public class TeacherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("new")) {
			String course = req.getParameter("course");

			Course co = new Course(course);
			boolean b = co.save();

			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(b);
			ps.close();
		} else if (action.equals("refresh")) {
			HashMap<String, Integer> users = new HashMap<String, Integer>();
			HashMap<String, Integer> exercises = new HashMap<String, Integer>();
			HashMap<String, Integer> users_count = new HashMap<String, Integer>();
			HashMap<String, Integer> exercises_count = new HashMap<String, Integer>();

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query q = new Query(Exercise.KIND);

			PreparedQuery pq = datastore.prepare(q);

			for (Entity en : pq.asIterable()) {
				Exercise ex = new Exercise(en);

				if (!users.containsKey(ex.getId())) {
					users.put(ex.getId(), 0);
					users_count.put(ex.getId(), 0);
				}
				users.put(ex.getId(),
						users.get(ex.getId()) + ex.getPassedTests());
				users_count.put(ex.getId(),
						users_count.get(ex.getId()) + ex.getTotalTests());

				if (!exercises.containsKey(ex.getExoName())) {
					exercises.put(ex.getExoName(), 0);
					exercises_count.put(ex.getExoName(), 0);
				}
				exercises.put(ex.getExoName(), exercises.get(ex.getExoName())
						+ ex.getPassedTests());
				exercises_count.put(
						ex.getExoName(),
						exercises_count.get(ex.getExoName())
								+ ex.getTotalTests());
			}

			for (String username : users.keySet()) {
				users.put(username,
						users.get(username) / users_count.get(username) * 100);
			}
			for (String exoname : users.keySet()) {
				exercises.put(exoname,
						exercises.get(exoname) / exercises_count.get(exoname)
								* 100);
			}

			ObjectOutputStream oos = new ObjectOutputStream(
					resp.getOutputStream());
			oos.writeObject(users);
			oos.writeObject(exercises);
			oos.close();
		} else if (action.equals("remove")) {
			String courseName = req.getParameter("course");

			boolean b = Course.delete(Course.KIND, courseName);
			
			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(b);
			ps.close();
		}
	}
}