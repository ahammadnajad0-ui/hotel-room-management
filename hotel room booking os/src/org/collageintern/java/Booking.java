package org.collageintern.java;

import java.sql.*;

public class Booking {
	public int bookingId;
	public int customerId;
	public int roomNum;
	public Timestamp checkIn;
	public Timestamp checkOut;

	public Booking(int bookingId, int customerId, int roomNum, Timestamp checkIn, Timestamp checkOut) {
		this.bookingId = bookingId;
		this.customerId = customerId;
		this.roomNum = roomNum;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
}
