package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class payment {

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gbcompany", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPayment(String name, String date, String type, String ammount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into payment(`paymentId`,`paymentName`,`paymentDate`,`paymentType`,`paymentAmmount`)"
					+ "values ( ?,  ?,  ?,  ?,  ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, type);
			preparedStmt.setString(5, ammount);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} catch (Exception e) {
			
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}
	
	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Payment Name</th><th>Payment Date</th><th>Payment Type</th><th>Ammount</th><th>Update</th><th>Remove</th></tr>"; 
			
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = Integer.toString(rs.getInt("paymentId"));
				String paymentName = rs.getString("paymentName");
				String paymentDate = rs.getString("paymentDate");
				String paymentType = rs.getString("paymentType");
				String paymentAmmount = rs.getString("paymentAmmount");
				
				// Add into the html table
				
				
				
				output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='"+ paymentId + "'>" + paymentName + "</td>";
				output += "<td>" + paymentDate + "</td>";
				output += "<td>" + paymentType + "</td>";
				output += "<td>" + paymentAmmount + "</td>";
				// buttons
				
				   output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
	                        + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger' data-paymentid='"
	                        + paymentId + "'>" + "</td></tr>";
				
				
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
		} 
		catch (Exception e) {
			output = "Error while reading the Payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updatePayment(String paymentId, String name, String date, String type, String ammount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET paymentName=?,paymentDate=?,paymentType=?,paymentAmmount=? WHERE paymentId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, ammount);
			preparedStmt.setInt(5, Integer.parseInt(paymentId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Payment.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deletePayment(String paymentId) {
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from payment where paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Payment.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}

}
