package com.tinroof.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@Entity
@Table(name = "calendarevents")
public class CalendarEvents implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 250)
	private String title;

	private String location;
	private String hasReminderSent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "calendar_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Calendar calendar;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eventDateTime", nullable = false, updatable = false)
	@CreatedDate
	private Date eventDateTime;

	@Column(name = "remiderTime", nullable = false)
	private Long remiderTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHasReminderSent() {
		return hasReminderSent;
	}

	public void setHasReminderSent(String hasReminderSent) {
		this.hasReminderSent = hasReminderSent;
	}

	public Long getRemiderTime() {
		return remiderTime;
	}

	public void setRemiderTime(Long remiderTime) {
		this.remiderTime = remiderTime;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
}
