package jlm.gae.servlets;

import com.google.appengine.api.datastore.*;
import jlm.gae.models.*;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

@SuppressWarnings("serial")
public class StudentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String jsonRequest = "";
		BufferedReader br = req.getReader();
		String line;
		Answer answer = Answer.ALL_IS_FINE;
		boolean password_ok = false;

		while ((line = br.readLine()) != null) {
			jsonRequest += line;
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonRequest);
		String action = (String) jsonObject.get("action");
		String username = (String) jsonObject.get("username");
		String course = (String) jsonObject.get("course");
		String password = (String) jsonObject.get("password");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query(Course.KIND);
		q.addFilter("course", Query.FilterOperator.EQUAL, course);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iten = pq.asIterator();
		
		boolean found = false;
		while (iten.hasNext()) {
			Course co = new Course(iten.next());
			if (co.getCourse().equalsIgnoreCase(course)) {
				found = true;
				if (co.getPassword().equalsIgnoreCase(password)) {
					password_ok = true;
				}
			}
		}

		if (!found) {
			answer = Answer.DATA_NOT_IN_DATABASE;
		}
		
		if (password_ok) {
			if (action.equalsIgnoreCase("join")) {
				Join j = new Join(username, course, password);

				answer = j.save();
			} else if (action.equalsIgnoreCase("leave")) {
				Leave l = new Leave(username, course, password);

				answer = l.save();
			} else if (action.equalsIgnoreCase("heartbeat")) {
				Heartbeat hb = new Heartbeat(username, course, password);

				answer = hb.save();
			} else if (action.equalsIgnoreCase("switch")) {
				String exoname = (String) jsonObject.get("exoname");
				String exolang = (String) jsonObject.get("exolang");

				Switch sw = new Switch(username, course, password, exoname,
						exolang);
				answer = sw.save();
			} else if (action.equalsIgnoreCase("execute")) {
				String exoname = (String) jsonObject.get("exoname");
				String exolang = (String) jsonObject.get("exolang");

				int passedtests = Integer.valueOf((String) jsonObject
                        .get("passedtests"));
				int totaltests = Integer.valueOf((String) jsonObject
						.get("totaltests"));
				String source = (String) jsonObject.get("source");

				Exercise ex = new Exercise(username, exoname, exolang, course,
						passedtests, totaltests, source);
				answer = ex.save();
			} else if (action.equalsIgnoreCase("help")) {
				System.out.println((String) jsonObject.get("status"));
				boolean status = Boolean.parseBoolean((String) jsonObject.get("status"));

				Help he = new Help(username, course, password, status);
				answer = he.save();
			}
		} else {
			answer = Answer.WRONG_PASSWORD;
		}

		PrintStream ps = new PrintStream(resp.getOutputStream());
		ps.print(answer.ordinal());
		ps.close();
	}
}