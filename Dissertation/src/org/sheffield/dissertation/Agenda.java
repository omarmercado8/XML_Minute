/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

public class Agenda {

	/**
	 * Create a new Agenda
	 * @param info
	 * @return
	 */
	public String createAgenda(HashMap info) {
		String response = "";
		String XML = "";
		String XML2 = "";
		XQConnection conn = null;
		try {
			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <AGENDA>
			 *   <ID></ID> 
			 *   <TITLE></TITLE> 
			 *   <COMMITTEE_ID></COMMITTEE_ID>
			 *   <CONTENT> 
			 *     <SUBJECT></SUBJECT> 
			 *   </CONTENT> 
			 * </AGENDA>
			 */
			String query = "for $i in doc('omar/agendas.xml')//AGENDAS  return  data($i/AGENDA[last()]/ID)";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();
			int id = 1;

			while (rs.next()) {
				id = Integer.valueOf(rs.getObject().toString());
				id = id + 1;
			}

			String content[] = info.get("content").toString().split(",");

			XML = "<AGENDA>" + "<ID>" + id + "</ID>" + "<TITLE>"
					+ info.get("name") + "</TITLE>" + "<COMMITTEE_ID>"
					+ info.get("committee") + "</COMMITTEE_ID>" + "<CONTENT>";
			int size = content.length;

			for (int x = 0; x < size; x++) {
				XML = XML + "<SUBJECT>" + "<TITLE>" + content[x] + "</TITLE>"
						+ "</SUBJECT>";
			}
			XML = XML + "</CONTENT>" + "<MINUTE_ID>0</MINUTE_ID>" + "</AGENDA>";

			XML2 = "   update insert " + XML
					+ " into  doc('omar/agendas.xml')/AGENDAS";

			XQExpression expr2 = conn.createExpression();
			expr2 = conn.createExpression();
			expr2.executeCommand(XML2);

		} catch (Exception ex) {
			System.out.println("createAgenda " + ex);
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
	 * Get al agendas that does not belong to a minute, they will have a 0 in the minute_id tag
	 * @return response
	 */
	public ArrayList GetCleanAgendas() {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <AGENDAS>
			 *   <AGENDA>
			 *     <ID></ID>
			 *     <TITLE></TITLE>
			 *     <COMMITTEE_ID></COMMITTEE_ID> 
			 *     <CONTENT></CONTENT>
			 *     <MINUTE_ID></MINUTE_ID> 
			 *   </AGENDA>
			 * </AGENDAS>
			 */

			String query = "for $i in doc('omar/agendas.xml')//AGENDAS/AGENDA  where $i/MINUTE_ID[text() = '0']  return  (data($i/ID),data($i/TITLE))";
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
			System.out.println("getCommittee " + ex);
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
	 * Get all info of a particular Agenda by ID
	 * @param Id
	 * @return
	 */
	public ArrayList GetAgendaInfo(String Id) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		ArrayList title = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <AGENDAS>
			 *   <AGENDA>
			 *     <ID></ID>
			 *     <TITLE></TITLE>
			 *     <COMMITTEE_ID></COMMITTEE_ID> 
			 *     <CONTENT></CONTENT>
			 *     <MINUTE_ID></MINUTE_ID> 
			 *   </AGENDA> 
			 * </AGENDAS>
			 */

			String query = "for $i in doc('omar/agendas.xml')//AGENDAS/AGENDA  where $i/ID[text() = '"
					+ Id
					+ "']  "
					+ " return  (data($i/ID),data($i/TITLE),data($i/COMMITTEE_ID))";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {

				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("title", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 2) {
					data.put("committe_id", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}
			}

			String query2 = "for $i in doc('omar/agendas.xml')//AGENDAS/AGENDA  where $i/ID[text() = '"
					+ Id + "']  " + " return  data($i/CONTENT/SUBJECT/TITLE)";
			XQPreparedExpression expr2 = conn.prepareExpression(query2);
			XQResultSequence rs2 = expr2.executeQuery();

			x = 0;
			while (rs2.next()) {
				title.add(rs2.getItem().getAtomicValue());
			}

			if (title.size() > 0) {
				data.put("titles", title);
				response.add(data.clone());
			}

		} catch (Exception ex) {
			System.out.println("GetAgendaInfo " + ex);
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
