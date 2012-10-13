/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sheffield.dissertation.Committee;

import com.google.gson.Gson;

/**
 * Servlet implementation class committee
 */
public class committee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public committee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String Action = request.getParameter("action");
		String Name = request.getParameter("name");
		String Frequency = request.getParameter("frequency");
		String Chair = request.getParameter("chair");
		String Members = request.getParameter("members");
		String Id = request.getParameter("id");
		String NM = request.getParameter("nextM");
		String CI = request.getParameter("ci");

		HashMap<String, String> info = new HashMap<String, String>();
		info.put("name", Name);
		info.put("chair", Chair);
		info.put("members", Members);
		info.put("meeting_frequency", Frequency);
		info.put("next_meeting", NM);

		Committee c = new Committee();
		Gson json = new Gson();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (Action.equals("GetAllCommittees")) {// Get all available Committees
			out.write(json.toJson(c.getAllCommittees()));
		} else if (Action.equals("Create")) {// Create a new Committee
			out.write(json.toJson(c.createCommittee(info)));
		} else if (Action.equals("GetCommittee")) {// Get a specific Committee
			out.write(json.toJson(c.getCommittee(Id)));
		} else if (Action.equals("Alert")) {// Get committees that will meet in the next 24 hours
			out.write(json.toJson(c.getDates()));
		} else if (Action.equals("disableCommittee")) {// Disable a specific committee
			c.UpdateMeeting("-99999", CI);
		}

	}
}
