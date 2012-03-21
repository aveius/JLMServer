package jlm.gae;

import com.google.appengine.api.datastore.*;
import jlm.gae.json.JSONArray;
import jlm.gae.models.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CourseServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        JSONArray jsonArray = new JSONArray();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query(Course.KIND);

		PreparedQuery pq = datastore.prepare(q);

		for (Entity en : pq.asIterable()) {
			Course co = new Course(en);
            jsonArray.put(co.getId());
		}

        PrintWriter pw = resp.getWriter();
        pw.print(jsonArray.toString());
        pw.close();
	}
}