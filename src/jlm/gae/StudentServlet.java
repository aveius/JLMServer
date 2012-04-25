package jlm.gae;

import jlm.gae.models.Exercise;
import jlm.gae.models.Heartbeat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

@SuppressWarnings("serial")
public class StudentServlet extends HttpServlet {

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

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonRequest);
			String action = jsonObject.getString("action");
			String userName = jsonObject.getString("username");

			if (action.equalsIgnoreCase("heartbeat")) {
				Heartbeat hb = new Heartbeat(userName);
				success = hb.save();
			} else if (action.equalsIgnoreCase("switch")) {
				
			} else if (action.equalsIgnoreCase("executed")) {
				String exoName = jsonObject.getString("exoname");
				String exoLang = jsonObject.getString("exolang");
				String courseName = jsonObject.getString("course");

				int passedTests = Integer.valueOf(jsonObject
						.getString("passedtests"));
				int totalTests = Integer.valueOf(jsonObject
						.getString("totaltests"));

				Exercise ex = new Exercise(userName, exoName, exoLang,
						courseName, passedTests, totalTests);

				success = ex.save();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		PrintStream ps = new PrintStream(resp.getOutputStream());
		ps.print(success);
		ps.close();
	}
}