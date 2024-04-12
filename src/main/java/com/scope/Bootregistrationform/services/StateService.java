package com.scope.Bootregistrationform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scope.Bootregistrationform.model.Country;
import com.scope.Bootregistrationform.model.State;
import com.scope.Bootregistrationform.repository.stateRepository;

@Service
public class StateService {
@Autowired
private stateRepository staterepo;

public List<State>getstate(){
	return staterepo.findAll();
}
public List<State> getstateBy( Country countryid){
	return staterepo.findByCountry(countryid);
}
}
