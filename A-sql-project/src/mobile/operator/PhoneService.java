
package mobile.operator;

import java.util.*;
import java.sql.*;
import oracle.jdbc.*;

public class PhoneService {

	public static void main(String[] args) {
		Phone phone = new Phone();
		Features features = new Features();
		Scanner sc = new Scanner(System.in);
		System.out.println("press 1 to add new operator\n" + "Press 2 to add aa region \n"
				+ "Press 3 to add a Line Range\n" + "Press 4 to add a Message\n"
				+ "Press 5 to print all the messages sent from a number to any number\n"
				+ "Press 6 to print all the messages received by a number from any number\n"
				+ "Press 7 to print all the mesages from a number to another number\n"
				+ "Press 8 to print all messaged received by a number from a Region\n"
				+ "Press 9 to print all messaged received by a number from a specific region and operator\n"
				+ "Press 10 to print all messages received from a number whose last 2 digits are missing\n"
				+ "Press 11 to print all failed messages from a specific region.\n"

				+ "Press 0 to exit");
		int choice;
		do {
			choice = sc.nextInt();
			try {

				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
						"system", "sysadmin");

				switch (choice) {
					case 0:
						System.out.println("Exit");
						break;
					case 1:
						phone.addOperator(con);
						break;
					case 2:
						phone.addRegion(con);
						break;
					case 3:
						phone.addLineRange(con);
						break;
					case 4:
						phone.addMessage(con);
						break;
					case 5:
						features.messageSent(con);
						break;
					case 6:
						features.messageReceived(con);
						break;
					case 7:
						features.messageBetweenTwoNumbers(con);
						break;
					case 8:
						features.messageReceivedFromPunjab(con);
						break;
					case 9:
						features.messageReceivedFromAirtelPunjab(con);
						break;
					case 10:
						features.incompleteNumber(con);
						break;
					case 11:
						features.failedMessagePunjab(con);
						break;
					default:
						System.out.println("wrong choice");
						break;
				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		} while (choice != 0);
	}

}
