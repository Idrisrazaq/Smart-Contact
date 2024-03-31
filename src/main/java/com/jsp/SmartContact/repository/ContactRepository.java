package com.jsp.SmartContact.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jsp.SmartContact.entity.Contact;
import com.jsp.SmartContact.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	
	@Query("Select c from Contact c where c.user=:user")
	public Page<Contact> findAllContactsByUserId(@Param("user") User user,Pageable pagable);
	
}
