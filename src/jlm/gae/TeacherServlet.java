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

import jlm.gae.models.Exercise;

public class TeacherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("data")) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			
			Query q = new Query("Exercise");

			PreparedQuery pq = datastore.prepare(q);

			for (Entity en : pq.asIterable()) {
				Exercise ex = new Exercise(en);
			}
		} else if (action.equals("reset")) {
			
		}
	}
}
