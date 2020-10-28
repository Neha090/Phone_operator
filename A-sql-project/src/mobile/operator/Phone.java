package mobile.operator;

import java.util.*;
import java.sql.*;
import oracle.jdbc.*;

public class Phone {

	Scanner sc = new Scanner(System.in);

	void addOperator(Connection con) {
		try {
			System.out.println("Enter Operator name");
			String operator = sc.next();

			CallableStatement smt = con.prepareCall("{call add_operator(?)}");
			smt.setString(1, operator);
			int r = smt.executeUpdate();
			if (r > 0)
				System.out.println("operator added successfully");

			smt.close();

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	void addRegion(Connection con) {
		try {
			System.out.println("Enter Region name");
			String region = sc.next();

			CallableStatement smt = con.prepareCall("{call add_region(?)}");
			smt.setString(1, region);
			int r = smt.executeUpdate();
			if (r > 0)
				System.out.println("region added successfully");

			smt.close();

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	void addLineRange(Connection con) {
		try {
			System.out.println("Enter Details fror adding Line Range");
			System.out.println("Enter range starting at");
			String start = sc.next();
			System.out.println("Enter range ending at");
			String end = sc.next();
			System.out.println("Enter region for which this range is ");
			String region = sc.next();
			System.out.println("Enter operator for which this range belongs to ");
			String operator = sc.next();

			CallableStatement smt = con.prepareCall("{call add_line_range(?,?,?,?)}");
			smt.setString(1, start);
			smt.setString(2, end);
			smt.setString(3, region);
			smt.setString(4, operator);

			int r = smt.executeUpdate();
			if (r > 0)
				System.out.println("Range added successfully");

			smt.close();

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	void addMessage(Connection con) {
		try {

			System.out.println("Enter your Number");
			String send = sc.next();
			System.out.println("Enter Send to number");
			String receive = sc.next();
			System.out.println("Enter message ");
			String message = sc.next();

			CallableStatement smt = con.prepareCall("{call add_message(?,?,?)}");
			smt.setString(1, send);
			smt.setString(2, receive);
			smt.setString(3, message);

			int r = smt.executeUpdate();
			if (r > 0)
				System.out.println("message added successfully");

			smt.close();

		}

		catch (Exception e) {
			System.out.println(e);
		}

	}
}
