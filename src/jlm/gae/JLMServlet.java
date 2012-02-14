package jlm.gae;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class JLMServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(JLMServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String exoName = req.getParameter("exoname");
        String exoLang = req.getParameter("exolang");
        String exoSuccess = req.getParameter("exosuccess");

        Exercise ex = new Exercise(userName, exoName, exoLang, Boolean.valueOf(exoSuccess).booleanValue());
        ex.save();
    }

}
