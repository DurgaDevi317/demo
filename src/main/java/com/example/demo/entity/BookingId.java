package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.Type;

/**
 * BookingId denotes the composite key for TicketBookingEntity
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
public class BookingId implements Serializable{
	/**
	 * Mobile Number of an user
	 */
	private String mobileNumber;
	
	/**
	 * Seats selected by the user
	 */
	private List<String> bookedSeats;
	
	public BookingId() {
    }

    public BookingId(String mobileNumber, List<String> bookedSeats) {
        this.mobileNumber = mobileNumber;
        this.bookedSeats = bookedSeats;
    }
	
    /**
     * Method to compare the primary key on duplication
     */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return mobileNumber.equals(bookingId.mobileNumber) &&
        		bookedSeats.equals(bookingId.bookedSeats);
    }
	
}
