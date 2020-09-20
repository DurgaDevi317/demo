package com.example.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * TicketUserEntity holds the user details
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Entity
@Table(name="users", schema = "public")
public class TicketUserEntity {
	/**
	 * Mobile Number of the user, which is a primary key
	 */
	@Id
	private String mobileNumber;
	
	/**
	 * Name of the user
	 */
	private String name;
	
	public TicketUserEntity() {
		
	}
	public TicketUserEntity(String mobileNumber, String name) {
		this.mobileNumber = mobileNumber;
		this.name = name;
	}
	public String getMobileNumber() {
		return this.mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
