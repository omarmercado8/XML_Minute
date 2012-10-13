/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sheffield.dissertation.Search;

import com.google.gson.Gson;

/**
 * Servlet implementation class search
 */
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public search() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String selection = request.getParameter("selection");
		String keyword = request.getParameter("keyword");

		Gson json = new Gson();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		Search s = new Search();
		if (selection.equals("title")) {// Search by Title
			out.write(json.toJson(s.Title(keyword)));
		} else if (selection.equals("name")) {// Search by Name
			out.write(json.toJson(s.Name(keyword)));
		} else if (selection.equals("content")) {// Search by Content
			out.write(json.toJson(s.Content(keyword)));
		} else if (selection.equals("subject")) {// Search by Subject
			out.write(json.toJson(s.Subject(keyword)));
		}

	}
}
