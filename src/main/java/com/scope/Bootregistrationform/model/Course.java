package com.scope.Bootregistrationform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Course-Details")
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="course-id")
	private int courseid;


	@Column(name="course-name")
	private String coursename;
	

	@Column(name="course-duration")
	private String courseduration;
	
	@Column(name="course-fee")
	private String coursefee;

	public Integer getCourseid() {
		return courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCourseduration() {
		return courseduration;
	}

	public void setCourseduration(String courseduration) {
		this.courseduration = courseduration;
	}

	public String getCoursefee() {
		return coursefee;
	}

	public void setCoursefee(String coursefee) {
		this.coursefee = coursefee;
	}
	
	
	
}
