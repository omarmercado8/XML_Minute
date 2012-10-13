/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Search {

	/**
	 * Search by Title
	 * @param keyword
	 * @return
	 */
	public ArrayList Title(String keyword) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/minutes.xml')//MINUTES/MINUTE where $i/TITLE[contains(.,'"
					+ keyword + "')]  return (data($i/ID),data($i/TITLE)) ";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("title", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}

			}

		} catch (Exception ex) {
			System.out.println("Search title " + ex);
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		} finally {
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		}
		return response;
	}

	/**
	 * Search by Name
	 * @param keyword
	 * @return
	 */
	public ArrayList Name(String keyword) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/minutes.xml')//MINUTES/MINUTE where $i/MEMBERS/PRESENT/PERSON/FIRST_NAME[contains(.,'"
					+ keyword
					+ "')] or $i/MEMBERS/PRESENT/PERSON/LAST_NAME[contains(.,'"
					+ keyword + "')]  return (data($i/ID),data($i/TITLE)) ";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("title", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}

			}

		} catch (Exception ex) {
			System.out.println("Search Name " + ex);
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		} finally {
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		}
		return response;
	}

	/**
	 * Search by Content
	 * @param keyword
	 * @return
	 */
	public ArrayList Content(String keyword) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/minutes.xml')//MINUTES/MINUTE where $i/CONTENT/SUBJECT/BODY[contains(.,'"
					+ keyword + "')]  return (data($i/ID),data($i/TITLE)) ";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("title", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}

			}

		} catch (Exception ex) {
			System.out.println("Search Content " + ex);
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		} finally {
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		}
		return response;
	}

	/**
	 * Search by Subject
	 * @param keyword
	 * @return
	 */
	public ArrayList Subject(String keyword) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/minutes.xml')//MINUTES/MINUTE where $i/CONTENT/SUBJECT/TITLE[contains(.,'"
					+ keyword + "')]  return (data($i/ID),data($i/TITLE)) ";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("title", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}

			}

		} catch (Exception ex) {
			System.out.println("Subject title " + ex);
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		} finally {
			try {
				conn.close();
			} catch (Exception ex2) {
			}
		}
		return response;
	}

}
