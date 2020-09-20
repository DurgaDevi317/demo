package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TicketBookingEntity;
import com.example.demo.entity.TicketSeatEntity;
import com.example.demo.entity.TicketUserEntity;
import com.example.demo.repository.TicketBookingRepository;
import com.example.demo.repository.TicketSeatRepository;
import com.example.demo.repository.TicketUserRepository;

/**
 * TicketBookingService is a service to process the requets
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Service
public class TicketBookingService implements ITicketBookingService{
	
	@Autowired
	private TicketBookingRepository bookingRepository;
	@Autowired
	private TicketUserRepository userRepository;
	@Autowired
	private TicketSeatRepository seatRepository;
	
	/**
	 * Returns all booking details
	 * @return booking details
	 */
	@Override
	public List<TicketBookingEntity> getBookingDetails(){
		return (List<TicketBookingEntity>) bookingRepository.findAll();
	}
	
	/**
	 * Returns booking details for the specified user
	 * @param mobileNumber is a mobile number of an user
	 * @return bookingDetails
	 */
	public List<TicketBookingEntity> getBookingDetails(String mobileNumber){
		return (List<TicketBookingEntity>) bookingRepository.findByMobileNumber(mobileNumber);
	}
	
	/**
	 * Returns the seats with their booking status
	 * @return Status of Seats
	 */
	@Override
	public List<TicketSeatEntity> getSeatStatus() {
        return seatRepository.getSeatStatus();
	}
	
	/**
	 * Adds the new user
	 * @param userEntity is the user details
	 * @return Saved User Entity
	 */
	@Override
	public TicketUserEntity addUser(TicketUserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	/**
	 * Updates the status of selected seats
	 * @param selectedSeats is the list of seats selected by the user
	 * @param bookedStatus is the booking status of seats
	 */
	@Override
	public void updateSeatStatus(List<String> selectedSeats, String bookedStatus) {
		seatRepository.updateSeatStatus(selectedSeats, bookedStatus);
	}

	/**
	 * Checks the payment status of user for the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 */
	@Override
	public Boolean checkPaymentStatus(String mobileNumber, List<String> selectedSeats) {
		return bookingRepository.checkPaymentStatus(mobileNumber, selectedSeats, "Paid") > 0 ? true : false;
	}

	/**
	 * Books the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 */
	@Override
	public void bookSeats(String mobileNumber, List<String> selectedSeats) {
		bookingRepository.bookSeats(mobileNumber, selectedSeats, "Not Paid");
	}

	/**
	 * Deletes the unsuccessful bookings
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 */
	@Override
	public void deleteFailedBooking(String mobileNumber, List<String> selectedSeats) {
		bookingRepository.deleteFailedBooking(mobileNumber, selectedSeats);
	}

	/**
	 * Updates payment status of the user for the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 * @param paidStatus is the payment status
	 */
	@Override
	public void updatePaymentStatus(String mobileNumber, List<String> selectedSeats, String paidStatus) {
		bookingRepository.updatePaymentStatus(mobileNumber, selectedSeats, paidStatus);
	}
}
