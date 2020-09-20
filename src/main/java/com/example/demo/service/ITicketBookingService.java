package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TicketBookingEntity;
import com.example.demo.entity.TicketSeatEntity;
import com.example.demo.entity.TicketUserEntity;

/**
 * ITicketBookingService is an interface for service
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
public interface ITicketBookingService {
	/*
	 * Returns all booking details
	 */
	List<TicketBookingEntity> getBookingDetails();
	
	/*
	 * Returns booking details for the specified user
	 */
	List<TicketBookingEntity> getBookingDetails(String mobileNumber);
	
	/*
	 * Returns the seats with their booking status
	 */
	List<TicketSeatEntity> getSeatStatus();
	
	/*
	 * Adds the new user
	 */
	TicketUserEntity addUser(TicketUserEntity userEntity);
	
	/*
	 * Updates the status of selected seats
	 */
	void updateSeatStatus(List<String> seats, String bookedStatus);
	
	/*
	 * Checks the payment status of user for the selected seats
	 */
	Boolean checkPaymentStatus(String mobileNumber, List<String> selectedSeats);
	
	/*
	 * Books the selected seats
	 */
	void bookSeats(String mobileNumber, List<String> selectedSeats);
	
	/*
	 * Deletes the unsuccessful bookings 
	 */
	void deleteFailedBooking(String mobileNumber, List<String> selectedSeats);
	
	/*
	 * Updates payment status of the user for the selected seats
	 */
	void updatePaymentStatus(String mobileNumber, List<String> selectedSeats, String paidStatus);
	
}
