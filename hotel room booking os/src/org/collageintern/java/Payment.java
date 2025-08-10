package org.collageintern.java;

import java.sql.*;
import java.time.*;

public class Payment {
	public void generateBill(int bookingId, String meth) {
		try {
			Connection conn = DBConnection.getConnection();

			String bookingSql = "SELECT customer_id,room_num, check_in, check_out FROM booking WHERE booking_id = ?";
			PreparedStatement ps1 = conn.prepareStatement(bookingSql);
			ps1.setInt(1, bookingId);
			ResultSet rs1 = ps1.executeQuery();

			if (!rs1.next()) {
				System.out.println("Booking not found.");
				conn.close();
				return;
			}

			int roomNum = rs1.getInt("room_num");
			Timestamp checkInTs = rs1.getTimestamp("check_in");
			Timestamp checkOutTs = rs1.getTimestamp("check_out");

			if (checkOutTs == null) {
				System.out.println("Guest has not checked out yet. Cannot generate bill.");
				conn.close();
				return;
			}

			String roomSql = "SELECT price FROM room WHERE room_num = ?";
			PreparedStatement ps2 = conn.prepareStatement(roomSql);
			ps2.setInt(1, roomNum);
			ResultSet rs2 = ps2.executeQuery();

			if (!rs2.next()) {
				System.out.println("Room not found.");
				conn.close();
				return;
			}

			int pricePerNight = rs2.getInt("price");

			LocalDateTime checkIn = checkInTs.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDateTime checkOut = checkOutTs.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			long durationHours = Duration.between(checkIn, checkOut).toHours();
			long nights = (durationHours + 23) / 24;

			if (nights == 0)
				nights = 1;

			int total = (int) (nights * pricePerNight);

			String insrtpayment_details = "INSERT INTO payment (booking_id, amount,payment_method,time) VALUES (?, ?, ? , NOW())";
			PreparedStatement pspay = conn.prepareStatement(insrtpayment_details);
			pspay.setInt(1, bookingId);
			pspay.setFloat(2, total);
			pspay.setString(3, meth);
			pspay.executeUpdate();

			int customer_id = rs1.getInt("customer_id");
			bill billtxt = new bill();
			billtxt.generateBillToFile(bookingId, customer_id, roomNum, checkInTs, checkOutTs, total);

			System.out.println("\n===== BILL SUMMARY =====");
			System.out.println("Booking ID   : " + bookingId);
			System.out.println("Room Number  : " + roomNum);
			System.out.println("Check-in     : " + checkIn);
			System.out.println("Check-out    : " + checkOut);
			System.out.println("Nights Stayed: " + nights);
			System.out.println("Price/Night  : ₹" + pricePerNight);
			System.out.println("Total Amount : ₹" + total);
			System.out.println("=========================\n");

			conn.close();

		} catch (SQLException e) {
			System.out.println("Error generating bill: " + e.getMessage());
		}
	}
}
