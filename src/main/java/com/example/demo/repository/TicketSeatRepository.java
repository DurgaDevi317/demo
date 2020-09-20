package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TicketSeatEntity;

/**
 * TicketSeatRepository, which is used to access seat entity 
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Repository
public interface TicketSeatRepository extends CrudRepository<TicketSeatEntity, String>{
	
	/**
	 * Updates the status of selected seats
	 * @param selectedSeats is the list of seats selected by the user
	 * @param bookedStatus is the booking status of seats
	 * @return no. of seats updated 
	 */
	@Transactional
	@Modifying
    @Query(value = "UPDATE seats SET booked_status = :bookedStatus WHERE seat_no IN (:selectedSeats) AND booked_status != 'Booked'", nativeQuery=true)
	public int updateSeatStatus(@Param("selectedSeats") List<String> selectedSeats, @Param("bookedStatus") String bookedStatus);

	/**
	 * Returns the seats with their booking status
	 * @return list of seats with their booking status
	 */
	@Query(value = "SELECT * FROM seats", nativeQuery=true)
	public List<TicketSeatEntity> getSeatStatus();

}
