package com.scope.Bootregistrationform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.Bootregistrationform.model.City;
import com.scope.Bootregistrationform.model.State;
import com.scope.Bootregistrationform.repository.cityRepository;

@Service
public class CityService {
@Autowired
private cityRepository cityrepo;

public List<City>getcity(){
	return cityrepo.findAll();
}
public List<City>getcityBy( State stateid){
	return cityrepo.findByState(stateid);
}
}
