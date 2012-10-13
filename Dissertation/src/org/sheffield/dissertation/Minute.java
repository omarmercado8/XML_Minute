/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Minute {

	static DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
	static Calendar c = Calendar.getInstance();

	/**
	 * Create a new Minute from an existing Agenda, re schedule next committee meeting and change minute_id in the agenda to 0
	 * @param info
	 * @return
	 */
	public String CreateMinute(HashMap info) {
	
		String response = "";
		String XML = "";
		String XML2 = "";
		XQConnection conn = null;
		try {
			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <MINUTES> 
			 *   <MINUTE> 
			 *     <ID></ID> 
			 *     <TITLE></TITLE>
			 *     <COMMITTEE></COMMITTEE> 
			 *     <LOCATION></LOCATION>
			 *     <DURATION></DURATION> 
			 *     <CHAIR></CHAIR> 
			 *     <MEMBERS> 
			 *       <PRESENT>
			 *         <PERSON> 
			 *           <FIRST_NAME></FIRST_NAME> 
			 *           <LAST_NAME></LAST_NAME>
			 *           <INITIALS></INITIALS> 
			 *         </PERSON> 
			 *       </PRESENT> 
			 *       <ABSENT> 
			 *         <PERSON>
			 *           <FIRST_NAME></FIRST_NAME> 
			 *           <LAST_NAME></LAST_NAME>
			 *           <INITIALS></INITIALS> 
			 *         </PERSON> 
			 *       </ABSENT>
			 *     </MEMBERS> 
			 *     <CONTENT> 
			 *       <SUBJECT>
			 *         <TITLE></TITLE> 
			 *         <BODY></BODY> 
			 *       </SUBJECT> 
			 *     </CONTENT>      
			 *   </MINUTE> 
			 * </MINUTES>
			 */
			String query = "for $i in doc('omar/minutes.xml')//MINUTES  return  data($i/MINUTE[last()]/ID)";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();
			int id = 1;

			while (rs.next()) {
				id = Integer.valueOf(rs.getObject().toString());
				id = id + 1;
			}

			String present[] = info.get("present").toString().split(",");

			String absent[] = info.get("absent").toString().trim().split(",");


			String titles[] = info.get("titles").toString().split(",");

			String content[] = info.get("content").toString().split(",");

			int size_present = present.length;

			if (present[0].length() < 1) {
				size_present = 0;
			}

			int size_absent = absent.length;

			if (absent[0].length() < 1) {
				size_absent = 0;
			}

			int size_titles = titles.length;

			String person_id = "";

			HashMap data = new HashMap();

			XML = "<MINUTE>" + "<ID>" + id + "</ID>" + "<TITLE>"
					+ info.get("name") + "</TITLE>" + "<COMMITTEE>"
					+ info.get("committee") + "</COMMITTEE>" + "<LOCATION>"
					+ info.get("location") + "</LOCATION>" + "<DURATION>"
					+ info.get("duration") + "</DURATION>" + "<CHAIR>"
					+ info.get("chair") + "</CHAIR>" + "<MEMBERS>"
					+ "<PRESENT>";
			for (int x = 0; x < size_present; x++) {
				person_id = present[x];

				String query3 = "for $i in doc('omar/people.xml')//PEOPLE/PERSON       where $i/ID[text() = '"
						+ person_id
						+ "']  return  (data($i/NAME/FIRST_NAME),data($i/NAME/LAST_NAME),data($i/INITIALS))";
				XQPreparedExpression expr3 = conn.prepareExpression(query3);
				XQResultSequence rs3 = expr3.executeQuery();
				int x3 = 0;
				data.clear();
				while (rs3.next()) {
					if (x3 == 0) {
						data.put("first_name", rs3.getItem().getAtomicValue());
						x3++;
					} else if (x3 == 1) {
						data.put("last_name", rs3.getItem().getAtomicValue());
						x3++;
					} else if (x3 == 2) {
						data.put("initials", rs3.getItem().getAtomicValue());
						x3++;
					}
				}

				XML = XML + "<PERSON>" + "<FIRST_NAME>"
						+ data.get("first_name") + "</FIRST_NAME>"
						+ "<LAST_NAME>" + data.get("last_name")
						+ "</LAST_NAME>" + "<INITIALS>" + data.get("initials")
						+ "</INITIALS>" + "</PERSON>";
			}
			XML = XML + "</PRESENT>" + "<ABSENT>";
			for (int x = 0; x < size_absent; x++) {
				person_id = absent[x];

				String query4 = "for $i in doc('omar/people.xml')//PEOPLE/PERSON       where $i/ID[text() = '"
						+ person_id
						+ "']  return  (data($i/NAME/FIRST_NAME),data($i/NAME/LAST_NAME),data($i/INITIALS))";
				XQPreparedExpression expr4 = conn.prepareExpression(query4);
				XQResultSequence rs4 = expr4.executeQuery();
				int x4 = 0;
				data.clear();
				while (rs4.next()) {
					if (x4 == 0) {
						data.put("first_name", rs4.getItem().getAtomicValue());
						x4++;
					} else if (x4 == 1) {
						data.put("last_name", rs4.getItem().getAtomicValue());
						x4++;
					} else if (x4 == 2) {
						data.put("initials", rs4.getItem().getAtomicValue());
						x4++;
					}
				}
				XML = XML + "<PERSON>" + "<FIRST_NAME>"
						+ data.get("first_name") + "</FIRST_NAME>"
						+ "<LAST_NAME>" + data.get("last_name")
						+ "</LAST_NAME>" + "<INITIALS>" + data.get("initials")
						+ "</INITIALS>" + "</PERSON>";
			}
			XML = XML + "</ABSENT>" + "</MEMBERS>" + "<CONTENT>";

			for (int x = 0; x < size_titles; x++) {

				XML = XML + "<SUBJECT>" +

				"<TITLE>" + titles[x] + "</TITLE>" +

				"<BODY>" + content[x] + "</BODY>" + "</SUBJECT>";
			}
			XML = XML + "</CONTENT>" + "</MINUTE>";

			XML2 = "update insert " + XML
					+ " into  doc('omar/minutes.xml')/MINUTES";

			XQExpression expr2 = conn.createExpression();
			expr2 = conn.createExpression();
			expr2.executeCommand(XML2);

			/**
			 * <AGENDAS> 
			 *   <AGENDA> 
			 *     <ID></ID> 
			 *     <TITLE></TITLE> 
			 *     <CONTENT></CONTENT>
			 *     <MINUTE_ID></MINUTE_ID> 
			 *   </AGENDA> 
			 * </AGENDAS>
			 */

			String XML3 = "update replace doc('omar/agendas.xml')//AGENDAS/AGENDA[ID/text() = '"
					+ info.get("agenda_id")
					+ "']/MINUTE_ID  with <MINUTE_ID>"
					+ id + "</MINUTE_ID>";

			XQExpression expr3 = conn.createExpression();
			expr3 = conn.createExpression();
			expr3.executeCommand(XML3);

		} catch (Exception ex) {
			System.out.println("CreateMinute " + ex);
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
	 * View a specific minute in PDF format
	 * @param id
	 * @param path
	 * @return
	 */
	public ArrayList ViewMinute(String id, String path) {
		HashMap data = new HashMap();
		ArrayList response = new ArrayList();
		ArrayList content = new ArrayList();
		ArrayList present = new ArrayList();
		ArrayList absent = new ArrayList();

		String XML = "";
		String XML2 = "";
		XQConnection conn = null;

		try {
			Connection con = new Connection();
			conn = con.getConnection();

			/**
			 * <MINUTES> 
			 *   <MINUTE> 
			 *     <ID></ID> 
			 *     <TITLE></TITLE>
			 *     <COMMITTEE></COMMITTEE> 
			 *     <LOCATION></LOCATION>
			 *     <DURATION></DURATION> 
			 *     <MEMBERS> 
			 *       <PRESENT> 
			 *         <PERSON>
			 *           <FIRST_NAME></FIRST_NAME> 
			 *           <LAST_NAME></LAST_NAME>
			 *           <INITIALS></INITIALS> 
			 *         </PERSON> 
			 *       </PRESENT> 
			 *       <ABSENT> 
			 *         <PERSON>
			 *           <FIRST_NAME></FIRST_NAME> 
			 *           <LAST_NAME></LAST_NAME>
			 *           <INITIALS></INITIALS> 
			 *         </PERSON> 
			 *       </ABSENT> 
			 *     </MEMBERS>
			 *     <CONTENT> 
			 *       <SUBJECT>
			 *         <TITLE></TITLE> 
			 *         <BODY></BODY> 
			 *       </SUBJECT> 
			 *     </CONTENT> 
			 *   </MINUTE> 
			 * </MINUTES>
			 */
			String query = "for $i in doc('omar/minutes.xml')/MINUTES/MINUTE where $i/ID[text() ='"
					+ id
					+ "'] return ( "
					+ " data($i/TITLE),data($i/COMMITTEE),data($i/LOCATION),data($i/CHAIR),data($i/DURATION) )";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

			int x = 0;
			while (rs.next()) {

				if (x == 0) {
					data.put("title", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("committee", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 2) {
					data.put("location", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 3) {
					data.put("chair", rs.getItem().getAtomicValue());
					x++;
				} else if (x == 4) {
					data.put("duration", rs.getItem().getAtomicValue());
					response.add(data.clone());
					data.clear();
					x = 0;
				}

			}

			data.clear();

			String query2 = "for $i in doc('omar/minutes.xml')/MINUTES/MINUTE where $i/ID[text() ='"
					+ id
					+ "'] return ( "
					+ "  data($i/MEMBERS/PRESENT/PERSON/node()))";
			XQPreparedExpression expr2 = conn.prepareExpression(query2);
			XQResultSequence rs2 = expr2.executeQuery();

			x = 0;
			while (rs2.next()) {

				if (x == 0) {
					data.put("first_name", rs2.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("last_name", rs2.getItem().getAtomicValue());
					x++;
				} else if (x == 2) {
					data.put("initials", rs2.getItem().getAtomicValue());
					present.add(data.clone());
					data.clear();
					x = 0;
				}
			}
			data.put("present", present.clone());
			response.add(data.clone());
			data.clear();

			String query3 = "for $i in doc('omar/minutes.xml')/MINUTES/MINUTE where $i/ID[text() ='"
					+ id
					+ "'] return ( "
					+ " data($i/MEMBERS/ABSENT/PERSON/node()) )";
			XQPreparedExpression expr3 = conn.prepareExpression(query3);
			XQResultSequence rs3 = expr3.executeQuery();

			x = 0;
			while (rs3.next()) {

				if (x == 0) {
					data.put("first_name", rs3.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("last_name", rs3.getItem().getAtomicValue());
					x++;
				} else if (x == 2) {
					data.put("initials", rs3.getItem().getAtomicValue());
					absent.add(data.clone());
					data.clear();
					x = 0;
				}
			}
			data.put("absent", absent.clone());
			response.add(data.clone());

			String query4 = "for $i in doc('omar/minutes.xml')/MINUTES/MINUTE where $i/ID[text() ='"
					+ id + "'] return ( " + " data($i/CONTENT/SUBJECT/node()))";
			XQPreparedExpression expr4 = conn.prepareExpression(query4);
			XQResultSequence rs4 = expr4.executeQuery();

			x = 0;
			data.clear();
			while (rs4.next()) {

				if (x == 0) {
					data.put("title", rs4.getItem().getAtomicValue());
					x++;
				} else if (x == 1) {
					data.put("body", rs4.getItem().getAtomicValue());
					content.add(data.clone());
					data.clear();
					x = 0;
				}
			}
			data.put("content", content.clone());
			response.add(data.clone());

			// [{chair=OMAR3 CASILLAS3 OC3, title=Agenda 1, duration=1hr,
			// committee=COMITTEE 1, location=sheff uni #2},
			// {present=[{first_name=OMAR, initials=OC, last_name=CASILLAS},
			// {first_name=OMAR3, initials=OC3, last_name=CASILLAS3}]},
			// {absent=[{first_name=OMAR2, initials=OC2, last_name=CASILLAS2}]},
			// {content=[{body=wb, title=WELCOME}, {body=ab, title=apologies},
			// {body=ob b, title=other bussiness}]}]

			toPdf(response, path);

		} catch (Exception ex) {
			System.out.println("CreateMinute " + ex);
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
	 * From the minute info Using iText create a PDF from this data
	 * @param response
	 * @param path
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void toPdf(ArrayList response, String path)
			throws DocumentException, IOException {

		path = "PATH";
		path = path + "minute.pdf";

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();


		HashMap first = new HashMap();
		first = (HashMap) response.get(0);


		first.get("chair");
		first.get("title");
		first.get("duration");
		first.get("committee");
		first.get("location");


		document.add(new Paragraph("TITLE : " + first.get("title")));
		document.add(new Paragraph("COMMITTEE : " + first.get("committee")));
		document.add(new Paragraph("CHAIR : " + first.get("chair")));
		document.add(new Paragraph("DURATION : " + first.get("duration")));
		document.add(new Paragraph("LOCATION : " + first.get("location")));


		HashMap present = new HashMap();
		HashMap absent = new HashMap();
		HashMap content = new HashMap();

		HashMap person = new HashMap();

		ArrayList presentArr = new ArrayList();
		ArrayList absentArr = new ArrayList();
		ArrayList contentArr = new ArrayList();

		present = (HashMap) response.get(1);
		presentArr = (ArrayList) present.get("present");

		for (int x = 0; x < presentArr.size(); x++) {

			person = (HashMap) presentArr.get(x);

			document.add(new Paragraph("PRESENT : " + person.get("first_name")
					+ "   " + person.get("last_name") + "   "
					+ person.get("initials")));

		}


		absent = (HashMap) response.get(2);
		absentArr = (ArrayList) absent.get("absent");

		for (int x = 0; x < absentArr.size(); x++) {

			person = (HashMap) absentArr.get(x);
			document.add(new Paragraph("ABSENT : " + person.get("first_name")
					+ "   " + person.get("last_name") + "   "
					+ person.get("initials")));

		}
		content = (HashMap) response.get(3);
		contentArr = (ArrayList) content.get("content");

		document.add(new Paragraph("Content"));
		
		for (int x = 0; x < contentArr.size(); x++) {

			person = (HashMap) contentArr.get(x);
			document.add(new Paragraph(x + 1 + "  " + person.get("title")));
			document.add(new Paragraph("    " + person.get("body")));
		}

		document.close();
	}

}
