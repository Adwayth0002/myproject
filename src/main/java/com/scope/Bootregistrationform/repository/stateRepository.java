package com.scope.Bootregistrationform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.Bootregistrationform.model.Country;
import com.scope.Bootregistrationform.model.State;

@Repository
public interface stateRepository extends JpaRepository<State,Integer> {
	List<State> findByCountry(Country countryid);
}
