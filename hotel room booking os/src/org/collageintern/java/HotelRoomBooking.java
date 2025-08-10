package org.collageintern.java;
import java.util.Scanner;

public class HotelRoomBooking {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BookingManager bm = new BookingManager();
		Payment billing = new Payment();
		while (true) {
			System.out.println("====== Hotel Booking System ======");
			System.out.println("1. Check Available Rooms");
			System.out.println("2. Book a Room");
			System.out.println("3. Check Out");
			System.out.println("4. bill");
			System.out.println("5. Exit");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();

			if (choice == 1) {
				bm.checkAvailableRooms();
			} else if (choice == 2) {
				
				System.out.print("Enter Custome name: ");
				String nami = sc.next();
				System.out.print("Enter phone numer: ");
				long pho = sc.nextLong();
				System.out.print("Enter email: ");
				String emaily = sc.next();
				System.out.print("Enter Room Number: ");
				int roomNum = sc.nextInt();
				bm.bookRoom( nami, pho, emaily, roomNum);
			} else if (choice == 3) {
				System.out.print("Enter Booking ID: ");
				int bid = sc.nextInt();
				bm.checkOut(bid);
			} else if (choice == 4) {
				System.out.print("enter booking id: ");
				int bidd = sc.nextInt();
				System.out.print("enter ur payment method: ");
				String pay_meth = sc.next();
				billing.generateBill(bidd, pay_meth);
			} else if (choice == 5) {
				System.out.println("Goodbye!");
				break;
			} else {
				System.out.println("Invalid option");
			}
		}

		sc.close();
	}
}