package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * TicketBookingEntity holds the booking details of the users
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Entity
@Table(name="bookings", schema = "public")
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    )
})
@IdClass(BookingId.class)
public class TicketBookingEntity implements Serializable{
	/**
	 * Mobile Number of the user, which is one among the primary key
	 */
	@Id
	private String mobileNumber;
	
	/**
	 * Seats selected by the user, which is one among the primary key
	 */
	@Id
	@Type(type = "string-array")
    @Column(
        name = "booked_seats",
        columnDefinition = "text[]"
    )
	private List<String> bookedSeats;
	
	/**
	 * Payment Status of the user for the selected seats
	 */
	private String paidStatus;
	
	public TicketBookingEntity() {
		
	}
	public TicketBookingEntity(String mobileNumber, List<String> bookedSeats, String paidStatus) {
		this.mobileNumber = mobileNumber;
		this.bookedSeats = bookedSeats;
		this.paidStatus = paidStatus;
	}
	public String getMobileNumber() {
		return this.mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public List<String> getBookedSeats() {
		return this.bookedSeats;
	}
	public void setBookedSeats(List<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public String getPaidStatus() {
		return this.paidStatus;
	}
	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}
}
