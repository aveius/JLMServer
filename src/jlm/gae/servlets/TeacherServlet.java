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
import java.util.ArrayList;
import java.util.Date;
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
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iten = pq.asIterator();

		boolean founded = false;
		while (iten.hasNext()) {
			Course co = new Course(iten.next());
			if (co.getCourse().equalsIgnoreCase(course)) {
				founded = true;
				if (co.getTeacherPassword().equalsIgnoreCase(teacher_password)) {
					password_ok = true;
				}
			}
		}

		if (!founded) {
			answer = Answer.DATA_NOT_IN_DATABASE;
		}

		if (action.equalsIgnoreCase("new")) {
			String password = (String) jsonObject.get("password");

			Course co = new Course(course, password, teacher_password);

			answer = co.save();
		} else if (password_ok) {
			Date last2Hours = new Date();
            // TODO change 5hours to 2 hours
			last2Hours.setTime(last2Hours.getTime() - 5 * 24 * 60 * 60
					* 1000);
			if (action.equalsIgnoreCase("refresh")) {
				Map<String, UserData> map = new HashMap<String, UserData>();

				// Get users with Join events
				q = new Query(Join.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,
						last2Hours);
				pq = datastore.prepare(q);
				iten = pq.asIterator();
				while (iten.hasNext()) {
					Join j = new Join(iten.next());
					if (!map.containsKey(j.getUsername())) {
						UserData u = new UserData();
						u.setUsername(j.getUsername());
						u.setLastJoin(j.getDate());
						map.put(j.getUsername(), u);
					}
				}

				// foreach user
				for (String username : map.keySet()) {
					// get all data since last leave
					q = new Query(Leave.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL,
							username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date",
							Query.FilterOperator.GREATER_THAN_OR_EQUAL,
							last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					while (iten.hasNext()) {
						Leave l = new Leave(iten.next());
						UserData u = map.get(username);
						if (u.getLastJoin().before(l.getDate())) {
							if (u.getLastLeave() == null
									|| u.getLastLeave() != null
									&& u.getLastLeave().before(l.getDate())) {
								u.setLastLeave(l.getDate());
							}
						}
					}

					// get the last heartbeat
					q = new Query(Heartbeat.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL,
							username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date",
							Query.FilterOperator.GREATER_THAN_OR_EQUAL,
							last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					while (iten.hasNext()) {
						Heartbeat h = new Heartbeat(iten.next());
						UserData u = map.get(username);
						if (u.getLastJoin().before(h.getDate())) {
							if (u.getLastHeartbeat() == null
									|| u.getLastHeartbeat() != null
									&& u.getLastHeartbeat().before(h.getDate())) {
								u.setLastHeartbeat(h.getDate());
							}
						}
					}

					// get all exercises
					q = new Query(Exercise.KIND);
					q.addFilter("username", Query.FilterOperator.EQUAL,
							username);
					q.addFilter("course", Query.FilterOperator.EQUAL, course);
					q.addFilter("date",
							Query.FilterOperator.GREATER_THAN_OR_EQUAL,
							last2Hours);
					pq = datastore.prepare(q);
					iten = pq.asIterator();
					while (iten.hasNext()) {
						Exercise e = new Exercise(iten.next());
						UserData u = map.get(username);

						ExerciseData ue = new ExerciseData();
						ue.setName(e.getExoName());
						ue.setLang(e.getExoLang());
						ue.setPassedTests((int) e.getPassedTests());
						ue.setTotalTests((int) e.getTotalTests());
						// ue.setSource(e.getSource());
						ue.setDate(e.getDate());

						u.getExercises().add(ue);
					}
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(map));
				System.out.println(map);
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("needinghelp")) {
				ArrayList<String> list = new ArrayList<String>();

				// Get users with Help events
				q = new Query(Help.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("status", Query.FilterOperator.EQUAL, true);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,
						last2Hours);
				pq = datastore.prepare(q);
				iten = pq.asIterator();
				while (iten.hasNext()) {
					Help h = new Help(iten.next());
					if (!list.contains(h.getUsername())) {
						list.add(h.getUsername());
					}
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(list));
				System.out.println(list);
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("ugly")) {
				ArrayList<String> list = new ArrayList<String>();

				// Get users with Join events
				q = new Query(Join.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,
						last2Hours);
				pq = datastore.prepare(q);
				iten = pq.asIterator();
				while (iten.hasNext()) {
					Join j = new Join(iten.next());
					String username = j.getUsername();
					if (!list.contains(username)) {
						// Get all exercises for the student
						q = new Query(Exercise.KIND);
						q.addFilter("username", Query.FilterOperator.EQUAL,
								username);
						q.addFilter("course", Query.FilterOperator.EQUAL, course);
						q.addFilter("date",
								Query.FilterOperator.GREATER_THAN_OR_EQUAL,
								last2Hours);
						pq = datastore.prepare(q);
						Iterator<Entity> iten2 = pq.asIterator();
						if (!iten2.hasNext()) {
							list.add(username);
						}
					}
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(list));
				System.out.println(list);
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("bad")) {
				ArrayList<String> list = new ArrayList<String>();

				// Get users with Join events
				q = new Query(Join.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,
						last2Hours);
				pq = datastore.prepare(q);
				iten = pq.asIterator();
				while (iten.hasNext()) {
					Join j = new Join(iten.next());
					String username = j.getUsername();
					int passed = 0;
					int total = 0;
					if (!list.contains(username)) {
						// Get all exercises for the student
						q = new Query(Exercise.KIND);
						q.addFilter("username", Query.FilterOperator.EQUAL,
								username);
						q.addFilter("course", Query.FilterOperator.EQUAL, course);
						q.addFilter("date",
								Query.FilterOperator.GREATER_THAN_OR_EQUAL,
								last2Hours);
						pq = datastore.prepare(q);
						Iterator<Entity> iten2 = pq.asIterator();
						while (iten2.hasNext()) {
							Exercise e = new Exercise(iten2.next());
							passed += e.getPassedTests();
							total += e.getTotalTests();
						}
					}
					if (passed <= total / 100 * 5) {
						list.add(username);
					}
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(list));
				System.out.println(list);
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("good")) {
				ArrayList<String> list = new ArrayList<String>();

				// Get users with Join events
				q = new Query(Join.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				q.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,
						last2Hours);
				pq = datastore.prepare(q);
				iten = pq.asIterator();
				while (iten.hasNext()) {
					Join j = new Join(iten.next());
					String username = j.getUsername();
					int passed = 0;
					int total = 0;
					if (!list.contains(username)) {
						// Get all exercises for the student
						q = new Query(Exercise.KIND);
						q.addFilter("username", Query.FilterOperator.EQUAL,
								username);
						q.addFilter("course", Query.FilterOperator.EQUAL, course);
						q.addFilter("date",
								Query.FilterOperator.GREATER_THAN_OR_EQUAL,
								last2Hours);
						pq = datastore.prepare(q);
						Iterator<Entity> iten2 = pq.asIterator();
						while (iten2.hasNext()) {
							Exercise e = new Exercise(iten2.next());
							passed += e.getPassedTests();
							total += e.getTotalTests();
						}
					}
					if (passed >= total / 100 * 90) {
						list.add(username);
					}
				}

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(JSONValue.toJSONString(list));
				System.out.println(list);
				ps.close();
				return;
			} else if (action.equalsIgnoreCase("remove")) {
				q = new Query(Course.KIND);
				q.addFilter("course", Query.FilterOperator.EQUAL, course);
				pq = datastore.prepare(q);
				Course co = null;
				iten = pq.asIterator();
				if (iten.hasNext()) {
					co = new Course(pq.asIterator().next());
				}

				if (co != null) {
					answer = co.delete();
				}
			}
		} else {
			answer = Answer.WRONG_TEACHER_PASSWORD;
		}

		PrintStream ps = new PrintStream(resp.getOutputStream());
		ps.print(answer.ordinal());
		ps.close();
	}
}
