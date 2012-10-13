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
import org.sheffield.dissertation.Minute;

import com.google.gson.Gson;

/**
 * Servlet implementation class minute
 */
public class minute extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public minute() {
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
		String id = request.getParameter("id");
		String Location = request.getParameter("location");
		String Duration = request.getParameter("duration");
		String Committee = request.getParameter("committee");
		String Present = request.getParameter("present");
		String Absent = request.getParameter("absent");
		String Content = request.getParameter("content");
		String Titles = request.getParameter("titles");
		String Agenda_id = request.getParameter("agenda_id");
		String Chair = request.getParameter("chair");
		String Next_Meeting = request.getParameter("next_meeting");
		String CI = request.getParameter("committee_id");

		HashMap<String, String> info = new HashMap<String, String>();
		Gson json = new Gson();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Minute m = new Minute();
		Committee c = new Committee();

		if (Action.equals("Create")) {// Create a new Minute

			info.put("name", Name);
			info.put("location", Location);
			info.put("duration", Duration);
			info.put("committee", Committee);
			info.put("present", Present);
			info.put("absent", Absent);
			info.put("agenda_id", Agenda_id);
			info.put("content", Content);
			info.put("titles", Titles);
			info.put("chair", Chair);
			m.CreateMinute(info);

			c.UpdateMeeting(Next_Meeting, CI);// Asign the next day the committee have to reunite
		} else if (Action.equals("View")) {// View a minute in PDF format
			String path = request.getSession().getServletContext()
					.getRealPath("/");
			out.write(json.toJson(m.ViewMinute(id, path)));
		}
	}
}
