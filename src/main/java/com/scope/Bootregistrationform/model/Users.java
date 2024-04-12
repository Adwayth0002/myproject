package com.scope.Bootregistrationform.model;

import java.sql.Blob;
import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name="Registration-Details")

public class Users {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="userid")
private int id;


@Column(name="userFirstname")
private String firstname;

//@NotBlank(message="lastname can't be blank")
@Column(name="userLastname")
private String lastname;


@Column(name="Gender")
private String gender;


@Column(name="Date-of-Birth")
private Date dateofbirth;



@Column(name="email")
private String email;


@Column(name="user-Phone-Number")
private String phonenumber;


@ManyToOne
@JoinColumn(name="Country")
private Country country;


@ManyToOne
@JoinColumn(name="State")
private State state;


@ManyToOne
@JoinColumn(name="City")
private City city;


@Column(name="Hobbies")
private String hobbie;

@Lob
@Column(name="UserAvatar" ,columnDefinition="longblob")
private byte[] avatar;

@Column(name="userverification")
private String verificationCode;


@Column(name="user-enabled")
private boolean enabled;

@Column(name="user-otp")
private String otp;

@Column(name="user-verified")
private boolean verified;

@Column(name="user-password")
private String password;

@Column(name="user-confirmpassword")
private String confirmPassword;

@Column(name="joined-course-id")
private Integer joinedcourse;



public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public Date getDateofbirth() {
	return dateofbirth;
}

public void setDateofbirth(Date dateofbirth) {
	this.dateofbirth = dateofbirth;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPhonenumber() {
	return phonenumber;
}

public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}

public Country getCountry() {
	return country;
}

public void setCountry(Country country) {
	this.country = country;
}



public State getState() {
	return state;
}

public void setState(State state) {
	this.state = state;
}

public City getCity() {
	return city;
}

public void setCity(City city) {
	this.city = city;
}

public String getHobbie() {
	return hobbie;
}

public void setHobbie(String hobbie) {
	this.hobbie = hobbie;
}

public byte[] getAvatar() {
	return avatar;
}

public void setAvatar(byte[] avatar) {
	this.avatar = avatar;
}

public String getVerificationCode() {
	return verificationCode;
}

public void setVerificationCode(String verificationCode) {
	this.verificationCode = verificationCode;
}

public boolean isEnabled() {
	return enabled;
}

public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getOtp() {
	return otp;
}

public void setOtp(String otp) {
	this.otp = otp;
}

public boolean isVerified() {
	return verified;
}

public void setVerified(boolean verified) {
	this.verified = verified;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getConfirmPassword() {
	return confirmPassword;
}

public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}

public Integer getJoinedcourse() {
	return joinedcourse;
}

public void setJoinedcourse(Integer joinedcourse) {
	this.joinedcourse = joinedcourse;
}

@Override
public String toString() {
    return email; // Assuming email is a field in the Users class
}

//public int getJoinedcourse() {
//	return joinedcourse;
//}
//
//public void setJoinedcourse(int joinedcourse) {
//	this.joinedcourse = joinedcourse;
//}

//public String toString() {
//	return country+" "+state+" "+city;
//}

 


}
