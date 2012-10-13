/*
 * Author : Omar Manuel Mercado Casillas
 * Date: 06/08/2012
 * Project: An XML Minute Database
 */
package org.sheffield.dissertation;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;

import net.xqj.exist.ExistXQDataSource;

public class Connection {

	private String msgerr = "Error  " + getClass();

	XQConnection getConnection() {

		XQConnection conn = null;
		try {
			XQDataSource xqs = new ExistXQDataSource();
			xqs.setProperty("serverName", "ip");
			xqs.setProperty("port", "8080");
			conn = xqs.getConnection("user", "password");

		} catch (Exception ex) {
			System.err.println(msgerr + "  :  " + " getConnection : " + ex);
		}
		return conn;
	}

}
