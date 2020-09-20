package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * TicketBookingEntity holds details of seats
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Entity
@Table(name="seats", schema = "public")
public class TicketSeatEntity {
	/**
	 * Seat No ,which is a primary key
	 */
	@Id
	@Column(
	    name = "seat_no",
	    columnDefinition = "text"
	)
	private String seatNo;
	
	/**
	 * Booking status of the seat
	 */
	private String bookedStatus;
	
	public TicketSeatEntity() {
		
	}
	public TicketSeatEntity(String seatNo, String bookedStatus) {
		this.seatNo = seatNo;
		this.bookedStatus = bookedStatus;
	}
	public String getBookedStatus() {
		return this.bookedStatus;
	}
	public void setBookedStatus(String bookedStatus) {
		this.bookedStatus = bookedStatus;
	}
	public String getSeatNo() {
		return this.seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
}

