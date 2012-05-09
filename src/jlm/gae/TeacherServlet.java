package jlm.gae;

import jlm.gae.models.Course;
import jlm.gae.models.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

@SuppressWarnings("serial")
public class TeacherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String jsonRequest = "";
		BufferedReader br = req.getReader();
		String line;
		boolean password_ok = false;

		while ((line = br.readLine()) != null) {
			jsonRequest += line;
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonRequest);
		String action = (String) jsonObject.get("action");
		String course = (String) jsonObject.get("course");
		String teacher_password = (String) jsonObject.get("teacher_password");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query(Course.KIND);
		q.addFilter("course", Query.FilterOperator.EQUAL, course);
		q.addFilter("teacher_password", Query.FilterOperator.EQUAL,
				teacher_password);
		PreparedQuery pq = datastore.prepare(q);
		if (pq.asIterator().hasNext()) {
			password_ok = true;
		}

		if (action.equalsIgnoreCase("new")) {
			Answer answer = Answer.ALL_IS_FINE;

			String password = (String) jsonObject.get("password");

			Course co = new Course(course, password, teacher_password);

			answer = co.save();
			
			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(answer);
			ps.close();
		} else if (password_ok) {
			if (action.equalsIgnoreCase("refresh")) {
				// TODO
			} else if (action.equalsIgnoreCase("remove")) {
				Answer answer = Answer.ALL_IS_FINE;

				q = new Query(Course.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				pq = datastore.prepare(q);
				Course co = null;
				Iterator<Entity> iten = pq.asIterator();
				if (iten.hasNext()) {
					co = new Course(pq.asIterator().next());
				}

				if (co != null) {
					answer = co.delete();
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(answer);
				ps.close();
			}
		} else {
			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(Answer.WRONG_PASSWORD);
			ps.close();
		}
	}
}