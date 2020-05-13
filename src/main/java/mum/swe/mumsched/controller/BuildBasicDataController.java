package mum.swe.mumsched.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.Course;
import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.model.Faculty;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.CourseService;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.FacultyService;
import mum.swe.mumsched.service.UserService;

/**
 * @author Smith
 * @date May 11, 2020
 */
@Controller
@Secured("ROLE_ADMIN")
@RequestMapping(path = "build")
public class BuildBasicDataController {

	@Autowired
	private UserService userService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private EntryService entryService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/generate-data", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity users(Model model, Principal currentUser, Pageable pageable) {

		generateFaculties();

		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("serial")
	private void generateFaculties() {
		
		/*
		 * delete from user_role where user_id>1; delete from faculties;delete from users where id>1;
		 */
		
		// Course
		// CS 390 Fundamental Programming Practices
		Course CS390 = new Course();
		CS390.setCode("CS390");
		CS390.setName("Fundamental Programming Practices");
		CS390.setDescription("");
		courseService.save(CS390);
		
		// CS 401 Modern Programming Practices
		Course CS401 = new Course();
		CS401.setCode("CS401");
		CS401.setName("Modern Programming Practices");
		CS401.setDescription("");
		courseService.save(CS401);

		// CS 422 Database Management Systems
		Course CS422 = new Course();
		CS422.setCode("CS422");
		CS422.setName("Database Management Systems");
		CS422.setDescription("");
		courseService.save(CS422);

		// CS 425 Software Engineering
		Course CS425 = new Course();
		CS425.setCode("CS425");
		CS425.setName("Software Engineering");
		CS425.setDescription("");
		courseService.save(CS425);

		// CS 435 Algorithms
		Course CS435 = new Course();
		CS435.setCode("CS435");
		CS435.setName("Algorithms");
		CS435.setDescription("");
		courseService.save(CS435);

		// CS 440 Compiler Construction
		Course CS440 = new Course();
		CS440.setCode("CS440");
		CS440.setName("Compiler Construction");
		CS440.setDescription("");
		courseService.save(CS440);

		// CS 450 Computer Networks
		Course CS450 = new Course();
		CS450.setCode("CS450");
		CS450.setName("Computer Network");
		CS450.setDescription("");
		courseService.save(CS450);

		// CS 465 Operating Systems
		Course CS465 = new Course();
		CS465.setCode("CS465");
		CS465.setName("Operating Systems");
		CS465.setDescription("");
		courseService.save(CS465);
		// Prerequisite: CS 401

		// CS 466 Computer Security
		Course CS466 = new Course();
		CS466.setCode("CS466");
		CS466.setName("Computer Security");
		CS466.setDescription("");
		courseService.save(CS466);
		// Prerequisite: CS 401

		// CS 471 Parallel Programming
		Course CS471 = new Course();
		CS471.setCode("CS471");
		CS471.setName("Parallel Programming");
		CS471.setDescription("");
		courseService.save(CS471);

		// CS 472 Web Application Programming
		Course CS472 = new Course();
		CS472.setCode("CS472");
		CS472.setName("Web Application Programming");
		CS472.setDescription("");
		courseService.save(CS472);

		// CS 473 Mobile Device Programming
		Course CS473 = new Course();
		CS473.setCode("CS473");
		CS473.setName("Mobile Device Programming");
		CS473.setDescription("");
		courseService.save(CS473);

		// CS 475 Computer Graphics
		Course CS475 = new Course();
		CS475.setCode("CS475");
		CS475.setName("Computer Graphics");
		CS475.setDescription("");
		courseService.save(CS475);

		// CS 488 Big Data Analytics
		Course CS488 = new Course();
		CS488.setCode("CS488");
		CS488.setName("Big Data Analytics");
		CS488.setDescription("");
		courseService.save(CS488);

		// CS 505 Advanced Programming Languages
		Course CS505 = new Course();
		CS505.setCode("CS505");
		CS505.setName("Advanced Programming Languages");
		CS505.setDescription("");
		courseService.save(CS505);

		// CS 522 Big Data
		Course CS522 = new Course();
		CS522.setCode("CS522");
		CS522.setName("Big Data");
		CS522.setDescription("");
		courseService.save(CS522);

		// CS 523 Big Data Technology
		Course CS523 = new Course();
		CS523.setCode("CS523");
		CS523.setName("Big Data Technology");
		CS523.setDescription("");
		courseService.save(CS523);

		// CS 525 Advanced Software Development
		Course CS525 = new Course();
		CS525.setCode("CS525");
		CS525.setName("Advanced Software Development");
		CS525.setDescription("");
		courseService.save(CS525);

		// CS 544 Enterprise Architecture
		Course CS544 = new Course();
		CS544.setCode("CS544");
		CS544.setName("Enterprise Architecture");
		CS544.setDescription("");
		courseService.save(CS544);

		// CS 545 Web Application Architecture and Frameworks
		Course CS545 = new Course();
		CS545.setCode("CS545");
		CS545.setName("Web Application Architecture and Frameworks");
		CS545.setDescription("");
		courseService.save(CS545);

		// CS 572 Modern Web Applications
		Course CS572 = new Course();
		CS572.setCode("CS572");
		CS572.setName("Modern Web Applications");
		CS572.setDescription("");
		courseService.save(CS572);

		// CS 575 Practicum in Software Development
		Course CS575 = new Course();
		CS575.setCode("CS575");
		CS575.setName("Practicum in Software Development");
		CS575.setDescription("");
		courseService.save(CS575);

		// CS 582 Machine Learning
		Course CS582 = new Course();
		CS582.setCode("CS582");
		CS582.setName("Machine Learning");
		CS582.setDescription("");
		courseService.save(CS582);

		List<Course> listAllCourse = new ArrayList<Course>() {
			{
				add(CS390);
				add(CS401);
				add(CS422);
				add(CS425);
				add(CS435);
				add(CS440);
				add(CS450);
				add(CS465);
				add(CS466);
				add(CS471);
				add(CS472);
				add(CS473);
				add(CS475);
				add(CS488);
				add(CS505);
				add(CS522);
				add(CS523);
				add(CS525);
				add(CS544);
				add(CS545);
				add(CS572);
				add(CS575);
				add(CS582);
			}
		};
		
		List<MonthEnum> listAllMonth =new ArrayList<MonthEnum>() {
			{
				add(MonthEnum.January);
				add(MonthEnum.February);
				add(MonthEnum.March);
				add(MonthEnum.April);
				add(MonthEnum.May);
				add(MonthEnum.June);
				add(MonthEnum.July);
				add(MonthEnum.August);
				add(MonthEnum.September);
				add(MonthEnum.October);
				add(MonthEnum.November);
				add(MonthEnum.December);
			}
		};

		// Greg Guthrie
		User guthrieUser = new User();
		guthrieUser.setFirstName("Greg");
		guthrieUser.setLastName("Guthrie");
		guthrieUser.setPassword("1");
		guthrieUser.setPasswordConfirm("1");
		guthrieUser.setUsername("guthrie@mum.edu");
		guthrieUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(guthrieUser);

		Faculty guthrie =  new Faculty();
		guthrie.setUser(guthrieUser);
		guthrie.setCourses(listAllCourse);
		guthrie.setMonthEnums(listAllMonth);
		guthrie.setNumberOfSectionPerEntry(4);
		facultyService.save(guthrie);

		// Keith Levi
		User leviUser = new User();
		leviUser.setFirstName("Keith");
		leviUser.setLastName("Levi");
		leviUser.setPassword("1");
		leviUser.setPasswordConfirm("1");
		leviUser.setUsername("levi@mum.edu");
		leviUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(leviUser);

		Faculty levi =  new Faculty();
		levi.setUser(leviUser);
		levi.setCourses(listAllCourse);
		levi.setMonthEnums(listAllMonth);
		levi.setNumberOfSectionPerEntry(4);
		facultyService.save(levi);

		// Steve Nolle
		User nolleUser = new User();
		nolleUser.setFirstName("Steve");
		nolleUser.setLastName("Nolle");
		nolleUser.setPassword("1");
		nolleUser.setPasswordConfirm("1");
		nolleUser.setUsername("nolle@mum.edu");
		nolleUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(nolleUser);

		Faculty nolle =  new Faculty();
		nolle.setUser(nolleUser);
		nolle.setCourses(listAllCourse);
		nolle.setMonthEnums(listAllMonth);
		nolle.setNumberOfSectionPerEntry(4);
		facultyService.save(nolle);

		// Bruce Leste
		User lesteUser = new User();
		lesteUser.setFirstName("Bruce");
		lesteUser.setLastName("Leste");
		lesteUser.setPassword("1");
		lesteUser.setPasswordConfirm("1");
		lesteUser.setUsername("leste@mum.edu");
		lesteUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(lesteUser);

		Faculty leste =   new Faculty();
		leste.setUser(lesteUser);
		leste.setCourses(listAllCourse);
		leste.setMonthEnums(new ArrayList<MonthEnum>() {{
			add(MonthEnum.January);
		}});
		leste.setNumberOfSectionPerEntry(1);
		facultyService.save(leste);

		// Paul Corazza
		User corazzaUser = new User();
		corazzaUser.setFirstName("Paul");
		corazzaUser.setLastName("Corazza");
		corazzaUser.setPassword("1");
		corazzaUser.setPasswordConfirm("1");
		corazzaUser.setUsername("corazza@mum.edu");
		corazzaUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(corazzaUser);

		Faculty corazza =  new Faculty();
		corazza.setUser(corazzaUser);
		corazza.setCourses(listAllCourse);
		corazza.setMonthEnums(listAllMonth);
		corazza.setNumberOfSectionPerEntry(4);
		facultyService.save(corazza);

		// Premchand Nair
		User nairUser = new User();
		nairUser.setFirstName("Premchand");
		nairUser.setLastName("Nair");
		nairUser.setPassword("1");
		nairUser.setPasswordConfirm("1");
		nairUser.setUsername("nair@mum.edu");
		nairUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(nairUser);

		Faculty nair =  new Faculty();
		nair.setUser(nairUser);
		nair.setCourses(listAllCourse);
		nair.setMonthEnums(listAllMonth);
		nair.setNumberOfSectionPerEntry(4);
		facultyService.save(nair);

		// Emdad Khan
		User khanUser = new User();
		khanUser.setFirstName("Emdad");
		khanUser.setLastName("Khan");
		khanUser.setPassword("1");
		khanUser.setPasswordConfirm("1");
		khanUser.setUsername("khan@mum.edu");
		khanUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(khanUser);

		Faculty khan =  new Faculty();
		khan.setUser(khanUser);
		khan.setCourses(listAllCourse);
		khan.setMonthEnums(listAllMonth);
		khan.setNumberOfSectionPerEntry(4);
		facultyService.save(khan);

		// Joe Bruen
		User bruenUser = new User();
		bruenUser.setFirstName("Joe");
		bruenUser.setLastName("Bruen");
		bruenUser.setPassword("1");
		bruenUser.setPasswordConfirm("1");
		bruenUser.setUsername("bruen@mum.edu");
		bruenUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(bruenUser);

		Faculty bruen = new Faculty();
		bruen.setUser(bruenUser);
		bruen.setCourses(listAllCourse);
		bruen.setMonthEnums(listAllMonth);
		bruen.setNumberOfSectionPerEntry(4);
		facultyService.save(bruen);

		// Ralph Bunker
		User bunkerUser = new User();
		bunkerUser.setFirstName("Ralph");
		bunkerUser.setLastName("Bunker");
		bunkerUser.setPassword("1");
		bunkerUser.setPasswordConfirm("1");
		bunkerUser.setUsername("bunker@mum.edu");
		bunkerUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(bunkerUser);

		Faculty bunker = new Faculty();
		bunker.setUser(bunkerUser);
		bunker.setCourses(listAllCourse);
		bunker.setMonthEnums(listAllMonth);
		bunker.setNumberOfSectionPerEntry(4);
		facultyService.save(bunker);
		
		// Clyde Ruby
		User rubyUser = new User();
		rubyUser.setFirstName("Clyde");
		rubyUser.setLastName("Ruby");
		rubyUser.setPassword("1");
		rubyUser.setPasswordConfirm("1");
		rubyUser.setUsername("ruby@mum.edu");
		rubyUser.setRole(RoleEnum.ROLE_FACULTY);
		userService.save(rubyUser);

		Faculty ruby = new Faculty();
		ruby.setUser(rubyUser);
		ruby.setCourses(listAllCourse);
		ruby.setMonthEnums(listAllMonth);
		ruby.setNumberOfSectionPerEntry(4);
		facultyService.save(ruby);
		
		
		//Entry
		Entry Oct2017 = new Entry();
		
		Oct2017.setCourseList(new HashSet<Course>(listAllCourse) {
			{
				remove(CS390);
				remove(CS401);
			}
		});
		Oct2017.setFacultyList(new HashSet<Faculty>() {
			{
				add(guthrie);
				add(nair);
				add(nolle);
				add(leste);
				add(levi);
				add(bruen);
				add(ruby);
				add(bunker);
				add(khan);
			}
		});
		
		Oct2017.setFpp(30);
		Oct2017.setFppCPT(15);
		Oct2017.setFppOPT(15);
		Oct2017.setMpp(90);
		Oct2017.setMppCPT(60);
		Oct2017.setMppOPT(30);
		
		Oct2017.setName("October 2017");
		Oct2017.setEntryDate(LocalDate.of(2017, 10, 1));
		entryService.save(Oct2017);
	}

}
