package jlm.gae;

import jlm.gae.models.Exercise;
import jlm.gae.models.Heartbeat;
import jlm.gae.models.Join;
import jlm.gae.models.Leave;
import jlm.gae.models.Switch;

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

		try {
			JSONObject jsonObject = new JSONObject(jsonRequest);
			String action = jsonObject.getString("action");
			String username = jsonObject.getString("username");

			if (action.equalsIgnoreCase("join")) {
				Join j = new Join(username);

				success = j.save();
			} else if (action.equalsIgnoreCase("leave")) {
				Leave l = new Leave(username);

				success = l.save();
			} else if (action.equalsIgnoreCase("heartbeat")) {
				Heartbeat hb = new Heartbeat(username);

				success = hb.save();
			} else if (action.equalsIgnoreCase("switch")) {
				String exoname = jsonObject.getString("exoname");
				String exolang = jsonObject.getString("exolang");
				String course = jsonObject.getString("course");

				Switch sw = new Switch(username, exoname, exolang, course);
				success = sw.save();
			} else if (action.equalsIgnoreCase("execute")) {
				String exoname = jsonObject.getString("exoname");
				String exolang = jsonObject.getString("exolang");
				String course = jsonObject.getString("course");

				int passedtests = Integer.valueOf(jsonObject
						.getString("passedtests"));
				int totaltests = Integer.valueOf(jsonObject
						.getString("totaltests"));
				String source = jsonObject.getString("source");

				Exercise ex = new Exercise(username, exoname, exolang, course,
						passedtests, totaltests, source);
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