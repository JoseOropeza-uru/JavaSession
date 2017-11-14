package jaom.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class Session
 */
@WebServlet("/Session")
public class Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();	
		if(session.isNew()) {
			json.put("status", 403).put("res", "session not started").put("value", "");
			session.invalidate();
		}
		else {
			json.put("status", 200).put("res", "session started").put("session", session.getAttribute("session"));
		}
		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(reqBody);
		System.out.println(reqBody.get("param"));
		JSONObject json = new JSONObject();	
		if(session.isNew()) {
			json.put("status", 200).put("res", "session stored");
			storeValue(reqBody.getString("param"), session);
		}
		else {
			json.put("status", 200).put("res", "value changed");
			storeValue(reqBody.getString("param"), session);
		}
			out.println(json.toString());
	}
	
	private void storeValue(String value,HttpSession session) {
		if(value ==null) {
			session.setAttribute("session", "");
		}
		else {
			session.setAttribute("session", value);
		}
	}
}
