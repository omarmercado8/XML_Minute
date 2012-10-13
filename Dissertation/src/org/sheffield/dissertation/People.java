/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

public class People {

/**
 * Get all existing people
 * @return
 */
	public ArrayList getAllPeople() {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/people.xml')//PEOPLE/PERSON  return (data($i/ID), data($i/NAME/FIRST_NAME),data($i/NAME/LAST_NAME))";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("first_name", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 2) {
					data.put("last_name", rs.getItem().getAtomicValue());

					response.add(data.clone());
					data.clear();
					x = 0;
				}
			}

		} catch (Exception ex) {
			System.out.println("getAllPeople " + ex);
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
	 * Get a specific person by ID
	 * @param ID
	 * @return
	 */
	public ArrayList getPerson(String ID) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/people.xml')//PEOPLE/PERSON/ID[text() = '"
					+ ID
					+ "']  return  data($i/ID,$i/NAME/FIRST_NAME,$i/NAME/LAST_NAME,$i/INITIALS, $i/COMPANY,$i/POSITION,$i/CONTACT_INFO/TELEPHONE,$i/CONTACT_INFO/EMAIL,,$i/CONTACT_INFO/MOBILE  )";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int id = 0;
			Node nod = null;
			NamedNodeMap attrs = null;

			while (rs.next()) {
				nod = rs.getNode();
				attrs = nod.getAttributes();
				data.put("id", attrs.getNamedItem("ID").getNodeValue());
				data.put("first_name", attrs.getNamedItem("FIRST_NAME")
						.getNodeValue());
				data.put("last_name", attrs.getNamedItem("LAST_NAME")
						.getNodeValue());
				data.put("initials", attrs.getNamedItem("INITIALS")
						.getNodeValue());
				data.put("company", attrs.getNamedItem("COMPANY")
						.getNodeValue());
				data.put("position", attrs.getNamedItem("POSITION")
						.getNodeValue());
				data.put("telephone", attrs.getNamedItem("TELEPHONE")
						.getNodeValue());
				data.put("email", attrs.getNamedItem("EMAIL").getNodeValue());
				data.put("mobile", attrs.getNamedItem("MOBILE").getNodeValue());
				response.add(response);
				data.clear();
			}

		} catch (Exception ex) {
			System.out.println("getAllCommittees " + ex);
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
	 * Create a new Person
	 * @param info
	 * @return
	 */
	public String createPerson(HashMap info) {
		String response = "";
		String XML = "";
		String XML2 = "";
		XQConnection conn = null;
		try {
			Connection con = new Connection();
			conn = con.getConnection();
			/**
			 * <PEOPLE> 
			 *   <PERSON>"+ 
			 *     <ID></ID>"+ 
			 *     <NAME>"+
			 *       <FIRST_NAME></FIRST_NAME>"+ 
			 *       <LAST_NAME></LAST_NAME>"+
			 *     </NAME>"+ 
			 *     <INITIALS></INITIALS>"+ 
			 *     <COMPANY></COMPANY>"+
			 *     <POSITION></POSITION>"+ 
			 *     <CONTACT_INFO>"+
			 *       <TELEPHONE></TELEPHONE>"+ 
			 *       <EMAIL></EMAIL>"+
			 *       <MOBILE></MOBILE>"+ 
			 *     </CONTACT_INFO>"+ 
			 *   </PERSON>"; 
			 * </PEOPLE>
			 */

			String query = "for $i in doc('omar/people.xml')//PEOPLE  return  data($i/PERSON[last()]/ID)";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int id = 1;

			while (rs.next()) {
				id = Integer.valueOf(rs.getObject().toString());
				id = id + 1;
			}

			XML = "<PERSON>" + "<ID>" + id + "</ID>" + "<NAME>"
					+ "<FIRST_NAME>" + info.get("first_name") + "</FIRST_NAME>"
					+ "<LAST_NAME>" + info.get("last_name") + "</LAST_NAME>"
					+ "</NAME>" + "<INITIALS>" + info.get("initials")
					+ "</INITIALS>" + "<COMPANY>" + info.get("company")
					+ "</COMPANY>" + "<POSITION>" + info.get("position")
					+ "</POSITION>" + "<CONTACT_INFO>" + "<TELEPHONE>"
					+ info.get("telephone") + "</TELEPHONE>" + "<EMAIL>"
					+ info.get("email") + "</EMAIL>" + "<MOBILE>"
					+ info.get("mobile") + "</MOBILE>" + "</CONTACT_INFO>"
					+ "</PERSON>";

			XML2 = "   update insert " + XML
					+ " into  doc('omar/people.xml')/PEOPLE";

			XQExpression expr2 = conn.createExpression();
			expr2 = conn.createExpression();
			expr2.executeCommand(XML2);

		} catch (Exception ex) {
			System.out.println("CreatePerson " + ex);
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
