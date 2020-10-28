
package mobile.operator;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Scanner;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

public class Features {

	Scanner sc = new Scanner(System.in);

	void messageSent(Connection con) {
		try {
			System.out.println("Enter Number whose send messages you want to print");
			String number = sc.next();

			CallableStatement smt = con.prepareCall("{call GET_MESSAGE_FROM_A_NUMBER(?,?)}");
			smt.setString(1, number);
			smt.registerOutParameter(2, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(2);

			System.out.println("PHONE_NUMBER MESSAGE");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2));
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	void messageReceived(Connection con) {

		try {
			System.out.println("Enter Number whose received messages you want to print");
			String number = sc.next();

			CallableStatement smt = con.prepareCall("{call messages_received(?,?)}");
			smt.setString(1, number);
			smt.registerOutParameter(2, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(2);

			System.out.println("PHONE_NUMBER MESSAGE");
			while (rs.next())
				System.out.println(rs.getString("msg_to") + " " + rs.getString("message"));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	void messageBetweenTwoNumbers(Connection con) {
		try {
			System.out.println("Enter send phone number");
			String sendNumber = sc.next();
			System.out.println("Enter receiving phone number");
			String receiveNumber = sc.next();

			CallableStatement smt = con.prepareCall("{call messages_from_to(?,?,?)}");
			smt.setString(1, sendNumber);
			smt.setString(2, receiveNumber);
			smt.registerOutParameter(3, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(3);

			System.out.println("SEND_NUMBER RECEIVED_NUMBER MESSAGE");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	void messageReceivedFromPunjab(Connection con) {

		try {
			System.out.println("Enter Region");
			String region = sc.next();

			CallableStatement smt = con.prepareCall("{call GET_MESSAGE_FROM_STATE(?,?)}");
			smt.setString(1, region);
			smt.registerOutParameter(2, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(2);

			System.out.println("PHONE_NUMBER REGION MESSAGE");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	void messageReceivedFromAirtelPunjab(Connection con) {

		try {
			System.out.println("Enter Region");
			String region = sc.next();
			System.out.println("Enter Operator");
			String operator = sc.next();

			CallableStatement smt = con.prepareCall("{call get_message(?,?,?)}");
			smt.setString(1, region);
			smt.setString(2, operator);
			smt.registerOutParameter(3, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(3);

			System.out.println("OPERATOR  REGION  PHONE_NUMBER MESSAGE");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
						+ rs.getString(4));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	void incompleteNumber(Connection con) {
		try {
			System.out.println("Enter Number");
			String phone = sc.next();

			CallableStatement smt = con.prepareCall("{call incomplete_number(?,?)}");
			smt.setString(1, phone);
			smt.registerOutParameter(2, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(2);

			System.out.println("PHONE_NUMBER  MESSAGE");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	void failedMessagePunjab(Connection con) {
		try {
			System.out.println("Enter region");
			String region = sc.next();

			CallableStatement smt = con.prepareCall("{call not_delivered(?,?)}");
			smt.setString(1, region);
			smt.registerOutParameter(2, OracleTypes.CURSOR);
			smt.execute();

			OracleResultSet rs = (OracleResultSet) smt.getObject(2);

			System.out.println("REGION PHONE_NUMBER  MESSAGE STATUS ");
			while (rs.next())
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
						+ rs.getString(4));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

}
