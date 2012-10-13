/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

public class Delete {

	/**
	 * @param id
	 * @return
	 */
	public String deletePerson(String id) {

		String response = "";
		XQConnection conn = null;

		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/people.xml')/PEOPLE  return  data($i/PERSON/"
					+ id + ")";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

		} catch (Exception ex) {
			System.out.println("deletePerson " + ex);
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
	 * 
	 * @param id
	 * @return
	 */
	public String deleteCommittee(String id) {

		String response = "";
		XQConnection conn = null;

		try {

			Connection con = new Connection();
			conn = con.getConnection();

			String query = "for $i in doc('omar/committees.xml')/COMMITTEES  return  data($i/COMMITTEE/"
					+ id + ")";
			XQPreparedExpression expr = conn.prepareExpression(query);
			XQResultSequence rs = expr.executeQuery();

		} catch (Exception ex) {
			System.out.println("deleteCommittee " + ex);
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
