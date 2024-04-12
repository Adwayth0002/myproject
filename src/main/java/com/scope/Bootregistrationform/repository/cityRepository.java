package com.scope.Bootregistrationform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.Bootregistrationform.model.City;

import com.scope.Bootregistrationform.model.State;

@Repository
public interface cityRepository extends JpaRepository<City,Integer> {
	List<City> findByState(State stateid);
}
