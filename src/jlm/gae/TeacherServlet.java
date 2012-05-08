package jlm.gae;

import jlm.gae.models.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

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

		while ((line = br.readLine()) != null) {
			jsonRequest += line;
		}

		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonRequest);
		String action = (String) jsonObject.get("action");

		if (action.equalsIgnoreCase("new")) {
			String course = (String) jsonObject.get("course");
			String password = (String) jsonObject.get("password");
			String teacher_password = (String) jsonObject
					.get("teacher_password");

			Course co = new Course(course, password, teacher_password);

			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(co.save());
			ps.close();
		} else if (action.equalsIgnoreCase("refresh")) {
			// TODO
		} else if (action.equalsIgnoreCase("remove")) {
			String courseName = req.getParameter("course");

			PrintStream ps = new PrintStream(resp.getOutputStream());
			ps.print(Course.delete(Course.KIND, courseName));
			ps.close();
		}
	}
}