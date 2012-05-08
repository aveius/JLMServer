package jlm.gae;

import com.google.appengine.api.datastore.*;

import jlm.gae.models.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

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
			JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonRequest);
			String action = (String) jsonObject.get("action");
						
			if (action.equalsIgnoreCase("allids")) {
				
				JSONArray jsonArray = new JSONArray();

				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();

				Query q = new Query(Course.KIND);

				PreparedQuery pq = datastore.prepare(q);

				jsonArray.add("test");
				for (Entity en : pq.asIterable()) {
					Course co = new Course(en);
					jsonArray.add(co.getId());
				}

				System.out.println(jsonArray);
				PrintWriter pw = resp.getWriter();
				pw.print(jsonArray);
				pw.close();
			}
	}
}