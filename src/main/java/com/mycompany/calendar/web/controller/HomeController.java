package com.mycompany.calendar.web.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.calendar.dao.CalendarUserDao;
import com.mycompany.calendar.dao.EventAttendeeDao;
import com.mycompany.calendar.dao.EventDao;
import com.mycompany.calendar.domain.CalendarUser;
import com.mycompany.calendar.domain.Event;
import com.mycompany.calendar.domain.EventAttendee;
import com.mycompany.calendar.service.CalendarService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired
	private CalendarUserDao calendarUserDao;	
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private EventAttendeeDao eventAttendeeDao;
	
	@Autowired
	private CalendarService calendarService;	
	
	private CalendarUser[] calendarUsers = null;
	private Event[] events = null;
	private EventAttendee[] eventAttentees = null;
	
	private Random random = new Random(System.currentTimeMillis());

	private static final int numInitialNumUsers = 12;
	private static final int numInitialNumEvents = 4;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		calendarUsers = new CalendarUser[numInitialNumUsers];
		events = new Event[numInitialNumEvents];
		eventAttentees = new EventAttendee[numInitialNumEvents];
		
		this.calendarService.deleteAllUsers();
		this.calendarService.deleteAllEvents();
		this.calendarService.deleteAllEventAttendees();
		
		for (int i = 0; i < numInitialNumUsers; i++) {
			calendarUsers[i] = new CalendarUser();
			calendarUsers[i].setEmail("user" + i + "@example.com");
			calendarUsers[i].setPassword("user" + i);
			calendarUsers[i].setName("User" + i);
			calendarUsers[i].setId(calendarService.createUser(calendarUsers[i]));
		}
		
		for (int i = 0; i < numInitialNumEvents; i++) {
			events[i] = new Event();
			events[i].setSummary("Event Summary - " + i);
			events[i].setDescription("Event Description - " + i);
			events[i].setOwner(calendarUsers[random.nextInt(numInitialNumUsers)]);
			switch (i) {				          /* Updated by Assignment 3 */
				case 0:
					events[i].setNumLikes(0);  							
					break;
				case 1:
					events[i].setNumLikes(9);
					break;
				case 2:
					events[i].setNumLikes(10);
					break;
				case 3:
					events[i].setNumLikes(10);
					break;
			}
			events[i].setId(calendarService.createEvent(events[i]));
		}
		
		for (int i = 0; i < numInitialNumEvents; i++) {
			eventAttentees[i] = new EventAttendee();
			eventAttentees[i].setEvent(events[i]);
			eventAttentees[i].setAttendee(calendarUsers[3 * i ]);
			eventAttentees[i].setAttendee(calendarUsers[3 * i + 1]);
			eventAttentees[i].setAttendee(calendarUsers[3 * i + 2]);
			eventAttentees[i].setId(calendarService.createEventAttendee(eventAttentees[i]));
		}
		
		//TODO model에 calendarUsers, events, eventAttentees 배열 객체 추가 
		List<CalendarUser> userList = new ArrayList<CalendarUser>();
		userList = this.calendarUserDao.findAllusers();
		
		List<Event> eventList = new ArrayList<Event>();
		eventList = this.eventDao.findAllEvents();
		//this.eventDao.findAllEvents();
		
		List<EventAttendee> eventAttendeeList = new ArrayList<EventAttendee>();
		eventAttendeeList = this.eventAttendeeDao.findAllEventAttendees();
		
		model.addAttribute("UserList", userList);
		model.addAttribute("EventList", eventList);
		model.addAttribute("EventAttendeeList", eventAttendeeList);
	
		return "home";
	}
}
