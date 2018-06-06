package com.tinroof.jpa.controller;

import com.tinroof.jpa.exception.ResourceNotFoundException;
import com.tinroof.jpa.model.CalendarEvents;
import com.tinroof.jpa.repository.CalendarEventsRepository;
import com.tinroof.jpa.repository.CalendarRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@RestController
@RequestMapping("/api/1.0/")
public class CalendarEventsController {
	private static final Logger logger = LoggerFactory.getLogger(CalendarEventsController.class);

	@Autowired
	private CalendarEventsRepository calendarEventsRepository;

	@Autowired
	private CalendarRepository calendarRepository;

	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Get All Calendar Events", notes = "Get All Calendar Events")
	@GetMapping("/calendar/{calendarId}/events")
	public List<CalendarEvents> getAllEventsByPostId(
			@ApiParam(value = "Get all Calendar Events") @PathVariable(value = "calendarId") Long calendarId) {
		logger.info("Begin getAllEventsByPostId...");
		return calendarEventsRepository.findByCalendarId(calendarId);
	}

	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Create a Calendar Event", notes = "Create a Calendar Event")
	@PostMapping("/calendar/{calendarId}/events")
	public CalendarEvents createCalendarEvent(
			@ApiParam(value = "Create a Calendar Id") @PathVariable(value = "calendarId") Long calendarId,
			@Valid @ApiParam(value = "Create a Calendar Event") @RequestBody CalendarEvents event) {
		logger.info("Begin createCalendarEvent...");
		return calendarRepository.findById(calendarId).map(calendar -> {
			event.setCalendar(calendar);
			return calendarEventsRepository.save(event);
		}).orElseThrow(() -> new ResourceNotFoundException("CalendarId " + calendarId + " not found"));
	}

	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Update a Calendar Event", notes = "Update a Calendar Event")
	@PutMapping("/calendar/{calendarId}/events/{eventId}")
	public CalendarEvents updateCalendarEvent(
			@ApiParam(value = "Update a Calendar") @PathVariable(value = "calendarId") Long calendarId,
			@ApiParam(value = "Update a Calendar Event Id") @PathVariable(value = "eventId") Long eventId,
			@Valid @ApiParam(value = "Update a Calendar Event") @RequestBody CalendarEvents commentRequest) {
		logger.info("Begin updateCalendarEvent...");
		if (!calendarRepository.existsById(calendarId)) {
			throw new ResourceNotFoundException("CalendarId " + calendarId + " not found");
		}

		return calendarEventsRepository.findById(eventId).map(event -> {
			event.setTitle(commentRequest.getTitle());
			return calendarEventsRepository.save(event);
		}).orElseThrow(() -> new ResourceNotFoundException("CommentId " + eventId + "not found"));
	}

	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Delete a Calendar Event", notes = "Delete a Calendar Event")
	@DeleteMapping("/calendar/{calendarId}/events/{eventId}")
	public ResponseEntity<?> deleteCalendarEvent(
			@ApiParam(value = "Delete a Calendar ID") @PathVariable(value = "calendarId") Long calendarId,
			@ApiParam(value = "Delete a Calendar Event ID") @PathVariable(value = "eventId") Long eventId) {
		logger.info("Begin deleteCalendarEvent...");
		if (!calendarRepository.existsById(calendarId)) {
			throw new ResourceNotFoundException("CalendarId " + calendarId + " not found");
		}

		return calendarEventsRepository.findById(eventId).map(event -> {
			calendarEventsRepository.delete(event);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("CommentId " + eventId + " not found"));
	}
}
