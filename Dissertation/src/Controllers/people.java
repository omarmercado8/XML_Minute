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
import org.sheffield.dissertation.People;

import com.google.gson.Gson;

/**
 * Servlet implementation class people
 */
public class people extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public people() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String Action = request.getParameter("action");
		String Id = request.getParameter("Id_Person");
		String FirstName = request.getParameter("FIRST_NAME");
		String LastName = request.getParameter("LAST_NAME");
		String Initials = request.getParameter("INITIALS");
		String Company = request.getParameter("COMPANY");
		String Position = request.getParameter("POSITION");
		String Telephone = request.getParameter("TELEPHONE");
		String Email = request.getParameter("EMAIL");
		String Mobile = request.getParameter("MOBILE");

		HashMap info = new HashMap();
		info.put("first_name", FirstName);
		info.put("last_name", LastName);
		info.put("initials", Initials);
		info.put("company", Company);
		info.put("position", Position);
		info.put("telephone", Telephone);
		info.put("email", Email);
		info.put("mobile", Mobile);

		People p = new People();
		Gson json = new Gson();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		if (Action.equals("GetAllPeople")) {// Get all existing people
			out.write(json.toJson(p.getAllPeople()));
		} else if (Action.equals("Create")) {// Create a new person
			out.write(json.toJson(p.createPerson(info)));
		} else if (Action.equals("GetPerson")) {// Get al info of a specific person
			out.write(json.toJson(p.getPerson(Id)));
		}

	}
}
