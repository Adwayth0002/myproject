package com.scope.Bootregistrationform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contact-Details")

public class contactmodel {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="userid")
private int id;


@Column(name="username")
private String name;

//@NotBlank(message="lastname can't be blank")
@Column(name="usernumber")
private String number;


@Column(name="usermessage")
private String message;


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getNumber() {
	return number;
}


public void setNumber(String number) {
	this.number = number;
}


public String getMessage() {
	return message;
}


public void setMessage(String message) {
	this.message = message;
}




}