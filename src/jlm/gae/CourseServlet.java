package jlm.gae;

import com.google.appengine.api.datastore.*;

import jlm.gae.models.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CourseServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String jsonRequest = "";
		BufferedReader br = req.getReader();
		String line;

		while ((line = br.readLine()) != null) {
			jsonRequest += line;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonRequest);
			String action = jsonObject.getString("action");
			
			if (action.equalsIgnoreCase("allids")) {
				
				JSONArray jsonArray = new JSONArray();

				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();

				Query q = new Query(Course.KIND);

				PreparedQuery pq = datastore.prepare(q);

				jsonArray.put("test");
				for (Entity en : pq.asIterable()) {
					Course co = new Course(en);
					jsonArray.put(co.getId());
				}

				System.out.println(jsonArray);
				PrintWriter pw = resp.getWriter();
				pw.print(jsonArray);
				pw.close();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}