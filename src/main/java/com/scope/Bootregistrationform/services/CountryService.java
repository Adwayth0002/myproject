package com.scope.Bootregistrationform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.Bootregistrationform.model.Country;
import com.scope.Bootregistrationform.repository.countryRepository;

@Service
public class CountryService {
	 @Autowired
	 private countryRepository countryrepo;
	 
	 public List<Country>countrylist(){
		 return countryrepo.findAll();
	 }
}
