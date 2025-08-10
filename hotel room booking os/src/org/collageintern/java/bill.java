package org.collageintern.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class bill {

	public void generateBillToFile(int bookingId, int customerId, int roomNum, Timestamp checkIn, Timestamp checkOut,
			double amount) {
		String filename = "bill_" + bookingId + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write("------------------------------\n");
			writer.write("     HOTEL BOOKING RECEIPT\n");
			writer.write("------------------------------\n\n");

			writer.write("Booking ID : " + bookingId + "\n");
			writer.write("Customer ID: " + customerId + "\n");
			writer.write("Room No    : " + roomNum + "\n");
			writer.write("Check-In   : " + checkIn.toString() + "\n");
			writer.write("Check-Out  : " + checkOut.toString() + "\n");
			writer.write("Amount Paid: ₹" + String.format("%.2f", amount) + "\n\n");

			writer.write("Thank you for choosing our hotel!\n");

			System.out.println("Bill generated successfully: " + filename);
		} catch (IOException e) {
			System.err.println("Error writing bill: " + e.getMessage());
		}
	}
}
