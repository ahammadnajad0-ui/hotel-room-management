package org.collageintern.java;

import java.sql.*;

public class BookingManager {

	public void checkAvailableRooms() {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "SELECT * FROM room WHERE status = 'available'";
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("Available Rooms:");
			while (rs.next()) {
				System.out.println("Room Number: " + rs.getInt("room_num") + ", Type: " + rs.getString("room_type")
						+ ", Price: " + rs.getInt("price"));
			}

			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public int bookRoom(String name, long ph, String email, int roomNum) {
		try {
			Connection conn = DBConnection.getConnection();
			
			String insertCustomer = "INSERT INTO customer (name, ph, email) VALUES ( ?, ?, ?)";
			try (PreparedStatement psCust = conn.prepareStatement(insertCustomer)) {
				psCust.setString(1, name);
				psCust.setLong(2, ph);
				psCust.setString(3, email);
				psCust.executeUpdate();
			} catch (SQLException e) {
				if (e.getMessage().contains("Duplicate entry")) {
					System.out.println("Customer already exists. Continuing...");
				} else {
					throw e;
				}
			}
			String custom = "SELECT customer_id FROM customer WHERE email = ?";
			PreparedStatement ps = conn.prepareStatement(custom);
			ps.setString(1, email);
			ResultSet rs2 = ps.executeQuery();

			int customerId = -1;
			if (rs2.next()) {
			    customerId = rs2.getInt("customer_id");
			} else {
			    System.out.println("Customer not found! Cannot proceed with booking.");
			    conn.close();
			    return 0;
			}

			
			String updateRoom = "UPDATE room SET status = 'booked' WHERE room_num = ?";
			PreparedStatement ps1 = conn.prepareStatement(updateRoom);
			ps1.setInt(1, roomNum);
			ps1.executeUpdate();

			String insertBooking = "INSERT INTO booking (customer_id, room_num, check_in) VALUES (?, ?, NOW())";
			PreparedStatement ps2 = conn.prepareStatement(insertBooking, Statement.RETURN_GENERATED_KEYS);
			ps2.setInt(1, customerId);
			ps2.setInt(2, roomNum);
			ps2.executeUpdate();

			System.out.println("Room booked!");

			ResultSet rs = ps2.getGeneratedKeys();
			if (rs.next()) {
				int bookingId = rs.getInt(1);
				System.out.println("Room booked successfully!");
				System.out.println("Your Booking ID is: " + bookingId);
				return bookingId;
			}

			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return 0;
	}

	public void checkOut(int bookingId) {
		try {
			Connection conn = DBConnection.getConnection();

		
			String updateBooking = "UPDATE booking SET check_out = NOW() WHERE booking_id = ?";
			PreparedStatement ps1 = conn.prepareStatement(updateBooking);
			ps1.setInt(1, bookingId);
			ps1.executeUpdate();

			
			String getRoomNum = "SELECT room_num FROM booking WHERE booking_id = ?";
			PreparedStatement ps2 = conn.prepareStatement(getRoomNum);
			ps2.setInt(1, bookingId);
			ResultSet rs = ps2.executeQuery();
			if (rs.next()) {
				int roomNum = rs.getInt("room_num");
				String updateRoom = "UPDATE room SET status = 'available' WHERE room_num = ?";
				PreparedStatement ps3 = conn.prepareStatement(updateRoom);
				ps3.setInt(1, roomNum);
				ps3.executeUpdate();
			}

			System.out.println("Checked out successfully!");

			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
