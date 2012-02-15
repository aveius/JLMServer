package jlm.gae;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import jlm.gae.models.Exercise;

@SuppressWarnings("serial")
public class StudentServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(StudentServlet.class
			.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("username");
		String exoName = req.getParameter("exoname");
		String exoLang = req.getParameter("exolang");
		int passedTests = Integer.valueOf(req.getParameter("passedtests"));
		int totalTests = Integer.valueOf(req.getParameter("totaltests"));

		Exercise ex = new Exercise(userName, exoName, exoLang, passedTests,
				totalTests);
		ex.save();
	}
}