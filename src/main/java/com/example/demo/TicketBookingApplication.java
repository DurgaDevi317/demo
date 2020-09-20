package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TicketBookingEntity;
import com.example.demo.entity.TicketSeatEntity;
import com.example.demo.entity.TicketUserEntity;
import com.example.demo.service.ITicketBookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TicketBookingApplication is a REST controller, which
 * accepts the requests to book tickets for movies
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */

/* Accepts the requests from the specified origin only */
@CrossOrigin(origins = "http://localhost:3000")
@SpringBootApplication
@EnableAutoConfiguration
@RestController
@EntityScan("com.example.demo.entity")
public class TicketBookingApplication {

	@Autowired
	private ITicketBookingService ticketBookingService;
	
	public static void main(String[] args) {
		SpringApplication.run(TicketBookingApplication.class, args);
	}
	
	/**
	 * setUserDetails method is used to add user details
	 * @param mobileNumber
	 * @param name
	 */
	private void setUserDetails(String mobileNumber, String name) {
		TicketUserEntity userEntity = new TicketUserEntity();
		userEntity.setMobileNumber(mobileNumber);
		userEntity.setName(name);
		userEntity = ticketBookingService.addUser(userEntity);
	}
	
	/**
	 * getBookings method is used to retrieve the booking details
	 * @return bookingDetails
	 */
	@GetMapping("/getBookings")
	public String getBookings() {
		List<TicketBookingEntity> bookingEntity = ticketBookingService.getBookingDetails();
		String bookings = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			bookings = objectMapper.writeValueAsString(bookingEntity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return bookings;
	}
	
	/**
	 * getSeatStatus method is used to retrieve all the seats along their booking status
	 * @return seatStatus
	 */
	@GetMapping("/getSeatStatus")
	public String getSeatStatus() {
		List<TicketSeatEntity> seatEntity = ticketBookingService.getSeatStatus();
		String seatStatus = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			seatStatus = objectMapper.writeValueAsString(seatEntity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return seatStatus;
	}
	
	/**
	 * bookSeats method is used to book seats
	 * mobileNumber - Mobile Number of user 
	 * selectedSeats - Seats selected by the user
	 * name - Name of the User
	 * timeStamp - Time of request sent
	 * 
	 * @param requestParams {mobileNumber, selectedSeats, name, timeStamp}
	 * @return Booking Status
	 */
	@PostMapping("/bookSeats")
	public synchronized String bookSeats(@RequestBody Map<String, Object> requestParams) {
		Map<String, String> resultMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();

		Date requestDate = new Date(Long.parseLong(requestParams.get("timeStamp").toString().replaceAll(",", "")));
		List<String> selectedSeats = (List<String>) requestParams.get("selectedSeats");
		String mobileNumber = requestParams.get("mobileNumber").toString();
		String name = requestParams.get("name").toString();
		
		/**
		 * Count total no. of seats booked by the user
		 */
		List<TicketBookingEntity> bookedEntities = ticketBookingService.getBookingDetails(mobileNumber);
		int totalBookedSeats = 0;
		for(TicketBookingEntity entity : bookedEntities) {
			totalBookedSeats += entity.getBookedSeats().size();
		}
		
		
		/**
		 * Allows the user to book only 6 tickets 
		 */
		if(totalBookedSeats + selectedSeats.size() <= 6) {
			
			/**
			 * Schedule a task, to check ticket booking status
			 */
			LocalDateTime twoMinsLater = LocalDateTime.ofInstant(requestDate.toInstant(), ZoneId.systemDefault()).plusSeconds(120);
			Date twoMinsLaterAsDate = Date.from(twoMinsLater.atZone(ZoneId.systemDefault()).toInstant());
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Boolean isPaid = ticketBookingService.checkPaymentStatus(mobileNumber, selectedSeats);
					if(isPaid) {
						ticketBookingService.updateSeatStatus(selectedSeats, "Booked");
						resultMap.put("status", "success");
					} else {
						ticketBookingService.updateSeatStatus(selectedSeats, "Available");
						ticketBookingService.deleteFailedBooking(mobileNumber, selectedSeats);
						resultMap.put("status", "failure");
					}
				}
			}, twoMinsLaterAsDate);
			setUserDetails(mobileNumber, name);
			ticketBookingService.updateSeatStatus(selectedSeats, "In Progress");
			ticketBookingService.bookSeats(mobileNumber, selectedSeats);
		} else {
			resultMap.put("status", "failure");
		}
		try {
			return objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * payment method is used to update the payment status which is done by different API
	 * mobileNumber - Mobile Number of user 
	 * selectedSeats - Seats selected by the user
	 * paidStatus - Payment Status
	 * 
	 * @param requestParams {mobileNumber, selectedSeats, paidStatus}
	 */
	@PostMapping("/payment")
	public void payment(@RequestBody Map<String, Object> requestParams) {
		String mobileNumber = requestParams.get("mobileNumber").toString();
		List<String> selectedSeats = (List<String>) requestParams.get("selectedSeats");
		String paidStatus = requestParams.get("paidStatus").toString();
		ticketBookingService.updatePaymentStatus(mobileNumber, selectedSeats, paidStatus);
	}

}
