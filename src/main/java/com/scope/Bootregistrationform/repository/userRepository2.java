package com.scope.Bootregistrationform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.scope.Bootregistrationform.model.Users;


@Repository
public interface userRepository2 extends JpaRepository<Users,Integer>{

	
	public Users findByEmail(String email);
	
}


 