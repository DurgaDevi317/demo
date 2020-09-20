package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TicketBookingEntity;


/**
 * TicketBookingRepository, which is used to access booking entity
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Repository
public interface TicketBookingRepository extends CrudRepository<TicketBookingEntity, String>{
	
	/**
	 * Checks the payment status of user for the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 * @param paidStatus is the payment status
	 * @return no. of matched users
	 */
	@Query(value = "SELECT COUNT(b.mobile_number) FROM bookings AS b WHERE b.mobile_number = :mobileNumber AND b.paid_status = :paidStatus AND ARRAY[booked_seats] &&  CAST (ARRAY[:selectedSeats] AS text[]", nativeQuery=true)
	int checkPaymentStatus(@Param("mobileNumber") String mobileNumber, @Param("selectedSeats") List<String> selectedSeats, @Param("paidStatus") String paidStatus);

	/**
	 * Deletes the unsuccessful bookings
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM bookings WHERE mobile_number = :mobileNumber AND ARRAY[booked_seats] &&  CAST (ARRAY[:selectedSeats] AS text[])", nativeQuery=true)
	void deleteFailedBooking(@Param("mobileNumber") String mobileNumber, @Param("selectedSeats") List<String> selectedSeats);

	/**
	 * Updates payment status of the user for the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 * @param paidStatus is the payment status
	 */
	@Transactional
	@Modifying
	@Query(value = "UPDATE bookings SET paid_status = :paidStatus WHERE mobile_number = :mobileNumber AND ARRAY[booked_seats] && CAST (ARRAY[:selectedSeats] AS text[])", nativeQuery=true)
	void updatePaymentStatus(@Param("mobileNumber") String mobileNumber, @Param("selectedSeats") List<String> selectedSeats, @Param("paidStatus") String paidStatus);

	/**
	 * Books the selected seats
	 * @param mobileNumber is a mobile number of an user
	 * @param selectedSeats is the list of seats selected by the user
	 * @param paidStatus is the payment status
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO bookings (mobile_number, booked_seats, paid_status) VALUES(:mobileNumber, ARRAY[:selectedSeats], :paidStatus) ON CONFLICT (mobile_number, booked_seats) DO NOTHING", nativeQuery=true)
	void bookSeats(@Param("mobileNumber") String mobileNumber,@Param("selectedSeats")  List<String> selectedSeats,@Param("paidStatus")  String paidStatus);

	/**
	 * Returns booking details for the specified user
	 * @param mobileNumber is a mobile number of an user
	 * @return booking details for the specified user
	 */
	@Query(value = "SELECT * FROM bookings AS b WHERE b.mobile_number = :mobileNumber", nativeQuery=true)
	List<TicketBookingEntity> findByMobileNumber(String mobileNumber);
}
