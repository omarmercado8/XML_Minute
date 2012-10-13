/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Committee {

	/**
	 * Get committee that will have to meet in the next 3 days
	 * -99999 = disabled committee
	 * @return
	 */
	public ArrayList getDates() {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();
			String query = "for $i in doc('omar/committees.xml')//COMMITTEES/COMMITTEE  where $i/NEXT_MEETING != '-99999'  return  (data($i/ID),data($i/NEXT_MEETING))";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;
			String MF = "";
			String ID = "";
			while (rs.next()) {

				if (x == 0) {
					ID = rs.getItem().getAtomicValue();
					data.put("id", ID);
					x++;
				}

				else if (x == 1) {

					MF = rs.getItem().getAtomicValue();
					Date date;
					date = dateFormat1.parse(MF);
					c.setTime((date));
					c.add(Calendar.DATE, 1);

					int days = (int) ((c.getTimeInMillis() - date.getTime()) / (1000 * 60 * 60 * 24));

					if (days > 0 && days < 2) {
						data.put("next_meeting", MF);
						response.add(data.clone());
						data.clear();
						x = 0;

						Committee com = new Committee();
						com.Remainder(ID);

					} else {
						data.clear();
						x = 0;
					}

				}
			}

		} catch (Exception ex) {
			System.out.println("getDates " + ex);
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
	 * Send an email reminder to committees that will meet soon
	 * @param id
	 */
	public void Remainder(String id) {

		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();
			String query = "for $i in doc('omar/committees.xml')//COMMITTEES/COMMITTEE[ID/text() = '"
					+ id + "']  return (data($i/MEMBERS/PERSON_ID))";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			while (rs.next()) {
				String person_id = rs.getItem().getAtomicValue();

				String query2 = "for $i in doc('omar/people.xml')//PEOPLE/PERSON[ID/text() = '"
						+ person_id
						+ "']  return (data($i/CONTACT_INFO/EMAIL))";
				XQPreparedExpression expr2 = conn.prepareExpression(query2);
				XQResultSequence rs2 = expr2.executeQuery();

				emailSender e = new emailSender();

				while (rs2.next()) {
					String emailAddress = rs2.getItem().getAtomicValue();
					e.sendEmail(emailAddress, "Remainder",
							"Remainder of next meeting");
				}
			}

		} catch (Exception ex) {
			System.out.println("Remainder " + ex);
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
	}

	/**
	 *  Update the meeting date of the committee to the next time period according to the committee periodicity
	 * @param date
	 * @param id
	 * @return
	 */
	public String UpdateMeeting(String date, String id) {

		HashMap data = new HashMap();
		String response = "OK";
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();
			String query = "update replace doc('omar/committees.xml')//COMMITTEES/COMMITTEE[ID/text() = '"
					+ id
					+ "']/NEXT_MEETING  with <NEXT_MEETING>"
					+ date
					+ "</NEXT_MEETING>";
			XQExpression expr3 = conn.createExpression();
			expr3.executeCommand(query);

		} catch (Exception ex) {
			System.out.println("UpdateMeeting " + ex);
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
	 * Get all existing committees
	 * -99999 = disabled committees
	 * @return
	 */
	public ArrayList getAllCommittees() {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();
			String query = "for $i in doc('omar/committees.xml')//COMMITTEES/COMMITTEE  where $i/NEXT_MEETING != '-99999'  return  (data($i/ID),data($i/NAME))";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;

			while (rs.next()) {
				if (x == 0) {
					data.put("id", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("name", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}
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

	static DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
	static Calendar c = Calendar.getInstance();

	/**
	 * Get a specific committee by ID
	 * @param ID
	 * @return
	 */
	public ArrayList getCommittee(String ID) {

		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		XQConnection conn = null;
		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query3 = "for $i in doc('omar/committees.xml')//COMMITTEES/COMMITTEE  where $i/ID[text() = '"
					+ ID
					+ "']  return  (data($i/NAME),data($i/CHAIR),data($i/MEETING_FREQUENCY),data($i/NEXT_MEETING))";
			XQPreparedExpression expr3 = conn.prepareExpression(query3);
			XQResultSequence rs3 = expr3.executeQuery();

			int x = 0;
			String chair_id = "";
			String MF = "";
			while (rs3.next()) {
				data.clear();
				if (x == 0) {
					data.put("title", rs3.getItem().getAtomicValue());
					x++;
					response.add(data.clone());
				} else if (x == 1) {
					data.put("chair", rs3.getItem().getAtomicValue());
					x++;
					response.add(data.clone());
				} else if (x == 2) {
					MF = rs3.getItem().getAtomicValue();
					data.put("meeting_frequency", MF);
					x++;
					response.add(data.clone());
				} else if (x == 3) {
					int num = 0;
					String da = rs3.getItem().getAtomicValue();
					data.put("next_meeting", da);
					x++;
					response.add(data.clone());
				}
			}

			String query = "for $i in doc('omar/committees.xml')//COMMITTEES/COMMITTEE  where $i/ID[text() = '"
					+ ID + "']  return  data($i/MEMBERS/PERSON_ID)";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			x = 0;
			String member = "";

			while (rs.next()) {

				member = rs.getItem().getAtomicValue();

				String query2 = "for $i in doc('omar/people.xml')//PEOPLE/PERSON       where $i/ID[text() = '"
						+ member
						+ "']  return  (data($i/ID),data($i/NAME/FIRST_NAME),data($i/NAME/LAST_NAME),data($i/INITIALS))";
				XQPreparedExpression expr2 = conn.prepareExpression(query2);
				XQResultSequence rs2 = expr2.executeQuery();

				int id = 0;

				while (rs2.next()) {
					if (x == 0) {
						data.put("id", rs2.getItem().getAtomicValue());
						x++;
					} else if (x == 1) {
						data.put("first_name", rs2.getItem().getAtomicValue());
						x++;
					} else if (x == 2) {
						data.put("last_name", rs2.getItem().getAtomicValue());
						x++;
					} else if (x == 3) {
						data.put("initials", rs2.getItem().getAtomicValue());
						response.add(data.clone());
						data.clear();
						x = 0;
					}
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
	 * Create a new Committee
	 * @param info
	 * @return
	 */
	public String createCommittee(HashMap info) {
		String response = "";
		String XML = "";
		String XML2 = "";
		XQConnection conn = null;
		try {
			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <COMMITTEE> 
			 *   <ID></ID> 
			 *   <NAME></NAME> 
			 *   <CHAIR></CHAIR> 
			 *   <MEMBERS>
			 *     <PERSON_ID></PERSON_ID> 
			 *   </MEMBERS>
			 *   <MEETING_FREQUENCY></MEETING_FREQUENCY>
			 *   <NEXT_MEETING></NEXT_MEETING> 
			 * </COMMITTEE>
			 */
			String query = "for $i in doc('omar/committees.xml')//COMMITTEES  return  data($i/COMMITTEE[last()]/ID)";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();
			int id = 1;

			while (rs.next()) {
				id = Integer.valueOf(rs.getObject().toString());
				id = id + 1;
			}

			String members[] = info.get("members").toString().split(",");

			XML = "<COMMITTEE>" + "<ID>" + id + "</ID>" + "<NAME>"
					+ info.get("name") + "</NAME>" + "<CHAIR>"
					+ info.get("chair") + "</CHAIR>" + "<MEMBERS>";
			int size = members.length;

			for (int x = 0; x < size; x++) {
				XML = XML + "<PERSON_ID>" + members[x] + "</PERSON_ID>";
			}

			XML = XML + "</MEMBERS>" + "<MEETING_FREQUENCY>"
					+ info.get("meeting_frequency") + "</MEETING_FREQUENCY>"
					+ "<NEXT_MEETING>" + info.get("next_meeting")
					+ "</NEXT_MEETING>" + "</COMMITTEE>";

			XML2 = "   update insert " + XML
					+ " into  doc('omar/committees.xml')/COMMITTEES";

			XQExpression expr2 = conn.createExpression();
			expr2 = conn.createExpression();
			expr2.executeCommand(XML2);

		} catch (Exception ex) {
			System.out.println("createCommittee " + ex);
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
