package com.scope.Bootregistrationform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.Bootregistrationform.model.Country;

@Repository
public interface countryRepository extends JpaRepository<Country,Integer> {

}
