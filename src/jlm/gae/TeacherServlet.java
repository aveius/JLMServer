package jlm.gae;


import jlm.gae.models.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class TeacherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String jsonRequest = "";
		BufferedReader br = req.getReader();
		String line;
		boolean success = false;

		while ((line = br.readLine()) != null) {
			jsonRequest += line;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonRequest);
			String action = jsonObject.getString("action");
			
			if (action.equalsIgnoreCase("new")) {
				String course = jsonObject.getString("course");
				String password = jsonObject.getString("password");
				String teacher_password = jsonObject.getString("teacher_password");
				
				Course co = new Course(course, password, teacher_password);

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(success);
				ps.close();
			} else if (action.equalsIgnoreCase("refresh")) {
//		            HashMap<String, Integer> users = new HashMap<String, Integer>();
//		            HashMap<String, Integer> exercises = new HashMap<String, Integer>();
//		            HashMap<String, Integer> users_count = new HashMap<String, Integer>();
//		            HashMap<String, Integer> exercises_count = new HashMap<String, Integer>();
//
//		            DatastoreService datastore = DatastoreServiceFactory
//		                    .getDatastoreService();
//
//		            Query q = new Query(Exercise.KIND);
//
//		            PreparedQuery pq = datastore.prepare(q);
//
//		            for (Entity en : pq.asIterable()) {
//		                Exercise ex = new Exercise(en);
//
//		                if (!users.containsKey(ex.getId())) {
//		                    users.put(ex.getId(), 0);
//		                    users_count.put(ex.getId(), 0);
//		                }
//		                users.put(ex.getId(),
//		                        users.get(ex.getId()) + ex.getPassedTests());
//		                users_count.put(ex.getId(),
//		                        users_count.get(ex.getId()) + ex.getTotalTests());
//
//		                if (!exercises.containsKey(ex.getExoName())) {
//		                    exercises.put(ex.getExoName(), 0);
//		                    exercises_count.put(ex.getExoName(), 0);
//		                }
//		                exercises.put(ex.getExoName(), exercises.get(ex.getExoName())
//		                        + ex.getPassedTests());
//		                exercises_count.put(
//		                        ex.getExoName(),
//		                        exercises_count.get(ex.getExoName())
//		                                + ex.getTotalTests());
//		            }
//
//		            for (String username : users.keySet()) {
//		                users.put(username,
//		                        users.get(username) / users_count.get(username) * 100);
//		            }
//		            for (String exoname : users.keySet()) {
//		                exercises.put(exoname,
//		                        exercises.get(exoname) / exercises_count.get(exoname)
//		                                * 100);
//		            }
//
//		            try {
//		                jsonRequest.put("users", users);
//		                jsonRequest.put("exercises", exercises);
//		                jsonRequest.put("users_count", users_count);
//		                jsonRequest.put("exercises_count", exercises_count);
//		            } catch (JSONException e) {
//		                e.printStackTrace();
//		            }
//
//		            PrintWriter pw = resp.getWriter();
//		            pw.print(jsonRequest);
//		            pw.close();
			} else if (action.equalsIgnoreCase("remove")) {
	            String courseName = req.getParameter("course");

				PrintStream ps = new PrintStream(resp.getOutputStream());
				ps.print(Course.delete(Course.KIND, courseName));
				ps.close();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}