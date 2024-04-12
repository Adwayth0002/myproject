package com.scope.Bootregistrationform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scope.Bootregistrationform.model.Users;


@Repository
public interface userRepository extends JpaRepository<Users,Integer>{

	public Users findByVerificationCode(String verificationCode);

	
}


 