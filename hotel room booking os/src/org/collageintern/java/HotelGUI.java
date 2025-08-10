package org.collageintern.java;


import javax.swing.*;
import java.awt.*;

public class HotelGUI extends JFrame {

    private BookingManager bookingManager;
    private Payment paymentManager;

    public HotelGUI() {
        bookingManager = new BookingManager();
        paymentManager = new Payment();

        setTitle("Hotel Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Book Room
        JPanel bookPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JButton availableBtn = new JButton("Show Available Rooms");
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField roomField = new JTextField();
        JButton bookBtn = new JButton("Book Room");
        JLabel bookingResult = new JLabel("");

        // Button to show available rooms in console
        availableBtn.addActionListener(e -> {
            bookingManager.checkAvailableRooms(); // prints in console
        });

        bookPanel.add(availableBtn);
        bookPanel.add(new JLabel(""));
        bookPanel.add(new JLabel("Customer Name:"));
        bookPanel.add(nameField);
        bookPanel.add(new JLabel("Phone Number:"));
        bookPanel.add(phoneField);
        bookPanel.add(new JLabel("Email:"));
        bookPanel.add(emailField);
        bookPanel.add(new JLabel("Room Number:"));
        bookPanel.add(roomField);
        bookPanel.add(bookBtn);
        bookPanel.add(bookingResult);

        bookBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                long phone = Long.parseLong(phoneField.getText());
                String email = emailField.getText();
                int roomNum = Integer.parseInt(roomField.getText());

                int bookingId = bookingManager.bookRoom(name, phone, email, roomNum);
                if (bookingId != -1) {
                    bookingResult.setText("Booking successful! ID: " + bookingId);
                } else {
                    bookingResult.setText("Booking failed!");
                }
            } catch (Exception ex) {
                bookingResult.setText("Error: " + ex.getMessage());
            }
        });

        // Tab 2: Check Out
        JPanel checkoutPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField bookingIdCheckout = new JTextField();
        JButton checkoutBtn = new JButton("Check Out");
        JLabel checkoutMsg = new JLabel("");

        checkoutPanel.add(new JLabel("Booking ID:"));
        checkoutPanel.add(bookingIdCheckout);
        checkoutPanel.add(checkoutBtn);
        checkoutPanel.add(checkoutMsg);

        checkoutBtn.addActionListener(e -> {
            try {
                int bookingId = Integer.parseInt(bookingIdCheckout.getText());
                bookingManager.checkOut(bookingId);
                checkoutMsg.setText("Check-out completed!");
            } catch (Exception ex) {
                checkoutMsg.setText("Error: " + ex.getMessage());
            }
        });

        // Tab 3: Generate Bill
        JPanel billPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField bookingIdBill = new JTextField();
        JTextField methodField = new JTextField();
        JButton billBtn = new JButton("Generate Bill");
        JLabel billMsg = new JLabel("");

        billPanel.add(new JLabel("Booking ID:"));
        billPanel.add(bookingIdBill);
        billPanel.add(new JLabel("Payment Method:"));
        billPanel.add(methodField);
        billPanel.add(billBtn);
        billPanel.add(billMsg);

        billBtn.addActionListener(e -> {
            try {
                int bookingId = Integer.parseInt(bookingIdBill.getText());
                String method = methodField.getText();
                paymentManager.generateBill(bookingId, method);
                billMsg.setText("Bill generated for Booking ID " + bookingId);
            } catch (Exception ex) {
                billMsg.setText("Error: " + ex.getMessage());
            }
        });

        tabbedPane.addTab("Book Room", bookPanel);
        tabbedPane.addTab("Check Out", checkoutPanel);
        tabbedPane.addTab("Generate Bill", billPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelGUI gui = new HotelGUI();
            gui.setVisible(true);
        });
    }
}


		