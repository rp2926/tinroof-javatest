package com.tinroof.jpa.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tinroof.jpa.exception.ResourceNotFoundException;
import com.tinroof.jpa.model.Calendar;
import com.tinroof.jpa.repository.CalendarRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@RestController
@RequestMapping("/api/1.0/")
public class CalendarController {
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);

	@Autowired
	CalendarRepository calendarRepository;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() throws IOException {
		return IOUtils.toString(CalendarController.class.getResourceAsStream("/index.html"));
	}
	
	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @HystrixCommand(fallbackMethod = "fallBackcreateCalendar", commandKey =
	// "createCalendar", groupKey = "createCalendar")
	@ApiOperation(value = "Get All Calendars", notes = "Get All Calendars")
	@GetMapping("/calendar")
	public List<Calendar> getAllCalendar() {
		return calendarRepository.findAll();
	}

	// @PreAuthorize("#oauth2.hasScope('create')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	// @HystrixCommand(fallbackMethod = "fallBackcreateCalendar", commandKey =
	// "createCalendar", groupKey = "createCalendar")
	@ApiOperation(value = "Create Calendar", notes = "Create Calendar")
	@PostMapping("/calendar")
	public Calendar createCalendar(@Valid @ApiParam(value = "Create Calendar") @RequestBody Calendar calendar)
			throws Exception {
		logger.info("Begin createCalendar...");
		return calendarRepository.save(calendar);
	}

	// @PreAuthorize("#oauth2.hasScope('update')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Update a Calendar", notes = "Update a Calendar")
	@PutMapping("/calendar/{calendarId}")
	public Calendar updateCalendar(@ApiParam(value = "Update Calendar Id") @PathVariable Long calendarId,
			@Valid @ApiParam(value = "Update a Calendar") @RequestBody Calendar calendarRequest) {
		logger.info("Begin updateCalendar...");
		return calendarRepository.findById(calendarId).map(calendar -> {
			calendar.setName(calendarRequest.getName());
			calendar.setUser(calendarRequest.getUser());
			return calendarRepository.save(calendar);
		}).orElseThrow(() -> new ResourceNotFoundException("CalendarId " + calendarId + " not found"));
	}

	// @PreAuthorize("#oauth2.hasScope('delete')")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ApiOperation(value = "Delete a Calendar", notes = "Delete a Calendar")
	@DeleteMapping("/calendar/{calendarId}")
	public ResponseEntity<?> deleteCalendar(@ApiParam(value = "Delete a Calendar Id") @PathVariable Long calendarId) {
		logger.info("Begin deleteCalendar...");
		return calendarRepository.findById(calendarId).map(calendar -> {
			calendarRepository.delete(calendar);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("CalendarId " + calendarId + " not found"));
	}

	/**
	 * fallBackTransportService
	 * 
	 * @param MultipartFile
	 *            file
	 * @param boolean
	 *            replace
	 * @return ResponseEntity.
	 */
	public ResponseEntity<?> fallBackCreateCalendar(Calendar calendar, Throwable t) throws Exception {
		logger.debug("begin fallBackCreateCalendar. ");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode error = null;

		Map<String, Object> log = new HashMap<>();
		log.put("transportService",
				"Fall Back CreateCalendar API Service Initiated.  Circuit Breaker pattern is enabled.  Error = "
						+ t.toString());
		error = mapper.readTree(new JSONObject(log).toString());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
