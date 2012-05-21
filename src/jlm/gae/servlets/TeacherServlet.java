package jlm.gae.servlets;

import com.google.appengine.api.datastore.*;
import jlm.gae.data.ExerciseData;
import jlm.gae.data.UserData;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
public class TeacherServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String jsonRequest = "";
		BufferedReader br = req.getReader();
		String line;
		boolean password_ok = false;
		Answer answer = Answer.ALL_IS_FINE;

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
			String password = (String) jsonObject.get("password");

			Course co = new Course(course, password, teacher_password);

			answer = co.save();
		} else if (password_ok) {
			if (action.equalsIgnoreCase("refresh")) {
				GregorianCalendar last2Hours = new GregorianCalendar();
				last2Hours.setTimeInMillis(last2Hours.getTimeInMillis() - 2 * 60 * 60 * 1000);
				Map<String,UserData> map = new HashMap<String,UserData>();

				// Recupère les utilisateurs avec les Join
				q = new Query(Join.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, last2Hours);
				pq = datastore.prepare(q);
				Iterator<Entity> iten = pq.asIterator();
				if (iten.hasNext()) {
					Join j = new Join(pq.asIterator().next());
					if (map.containsKey(j.getUsername())) {
						UserData u = new UserData();
						u.setUsername(j.getUsername());
						map.put(j.getUsername(),u);
					}
				}
				
				// foreach user
				for (String username : map.keySet()) {
					// get all data since last leave
					q = new Query(Leave.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL, username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					if (iten.hasNext()) {
						Leave l = new Leave(pq.asIterator().next());
						UserData u = map.get(username);
						if (u.getLastJoin().before(l.getDate())) {
							if (u.getLastLeave() == null || u.getLastLeave() != null && u.getLastLeave().before(l.getDate())) {
								u.setLastLeave(l.getDate());
							}
						}
					}
					
					// get the last heartbeat
					q = new Query(Heartbeat.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL, username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					if (iten.hasNext()) {
						Leave l = new Leave(pq.asIterator().next());
						UserData u = map.get(username);
						if (u.getLastJoin().before(l.getDate())) {
							if (u.getLastLeave() == null || u.getLastLeave() != null && u.getLastLeave().before(l.getDate())) {
								u.setLastLeave(l.getDate());
							}
						}
					}
					
					// get all exercises
					q = new Query(Exercise.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL, username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					if (iten.hasNext()) {
						Exercise e = new Exercise(pq.asIterator().next());
						UserData u = map.get(username);
						
						ExerciseData ue = new ExerciseData();
						ue.setName(e.getExoName());
						ue.setLang(e.getExoLang());
						ue.setPassedTests(e.getPassedTests());
						ue.setTotalTests(e.getTotalTests());
						// ue.setSource(e.getSource());
						ue.setDate(e.getDate());

						u.getExercises().add(ue);
					}
				}
				
				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(map));
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("remove")) {
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
			}
		} else {
			answer = Answer.WRONG_PASSWORD;
		}
		
		PrintStream ps = new PrintStream(resp.getOutputStream());
		ps.print(answer.ordinal());
		ps.close();
	}
}