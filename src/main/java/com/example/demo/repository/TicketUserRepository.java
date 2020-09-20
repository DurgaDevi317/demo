package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TicketUserEntity;

/**
 * TicketUserRepository which is used to access the User Entity
 * 
 * @author Durga Devi Venkadesh
 * @version 1.0
 *
 */
@Repository
public interface TicketUserRepository  extends CrudRepository<TicketUserEntity, String>{

}
