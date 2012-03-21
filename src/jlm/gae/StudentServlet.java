package jlm.gae;

import jlm.gae.json.JSONException;
import jlm.gae.json.JSONObject;
import jlm.gae.models.Exercise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            String userName = jsonObject.getString("username");
            String exoName = jsonObject.getString("exoname");
            String exoLang = jsonObject.getString("exolang");
            String courseName = jsonObject.getString("course");

            int passedTests = Integer.valueOf(jsonObject.getString("passedtests"));
            int totalTests = Integer.valueOf(jsonObject.getString("totaltests"));

            Exercise ex = new Exercise(userName, exoName, exoLang, courseName,
                    passedTests, totalTests);

            success = ex.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PrintStream ps = new PrintStream(resp.getOutputStream());
        ps.print(success);
        ps.close();
    }
}