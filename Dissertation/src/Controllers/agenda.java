/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sheffield.dissertation.Agenda;
import org.sheffield.dissertation.Minute;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class agenda
 */
public class agenda extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public agenda() {
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
		String Committee = request.getParameter("committee");
		String Content = request.getParameter("content");
		String Id = request.getParameter("id");

		HashMap<String, String> info = new HashMap<String, String>();
		info.put("name", Name);
		info.put("committee", Committee);
		info.put("content", Content);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson json = new Gson();

		if (Action.equals("GetCleanAgendas")) {// Get all agendas that does not belong to a minute yet
			Agenda a = new Agenda();
			out.write(json.toJson(a.GetCleanAgendas()));
		} else if (Action.equals("Create")) {//Create a new Agenda
			Agenda a = new Agenda();
			out.write(json.toJson(a.createAgenda(info)));
		} else if (Action.equals("GetAgendaInfo")) {// Gett all the content for a specific Agenda
			Agenda a = new Agenda();
			out.write(json.toJson(a.GetAgendaInfo(Id)));
		}

	}

}
