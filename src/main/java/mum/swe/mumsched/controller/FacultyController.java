package mum.swe.mumsched.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.Faculty;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.CourseService;
import mum.swe.mumsched.service.FacultyService;
import mum.swe.mumsched.validator.UserValidator;

/**
 * @author Rachel
 * @date May 10, 2020

 */

@Controller
public class FacultyController {
	@Autowired
	private FacultyService facultyService;
    @Autowired
    private UserValidator userValidator;

	@Autowired
	private CourseService courseService;

	@RequestMapping(value="/faculties", method=RequestMethod.GET)
	public String facultyProfile(Model model,Pageable pageable, Principal currentUser) {
		model.addAttribute("faculties", facultyService.findAll());
		return "faculty/facultyList";
	}

	@RequestMapping(value="/faculty", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("faculty", new Faculty());
        model.addAttribute("courses",courseService.findAll());
		return "faculty/facultyFormAdmin";
	}

    @RequestMapping(value="/faculty/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model){
        facultyService.delete(id);
        return "redirect:/faculties";
    }

    @RequestMapping(value="/faculty/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("faculty", facultyService.findOne(id));
        model.addAttribute("courses",courseService.findAll());
        return "faculty/facultyFormAdmin";
    }

	@RequestMapping(value= {"/updateFaculty"}, method = RequestMethod.POST)
	public String updateFacultyProfile(@ModelAttribute("faculty") Faculty faculty, BindingResult result, Model model) {

        userValidator.validateFaculty(faculty.getUser(), result);

        if (result.hasErrors()) {
            model.addAttribute("faculty", faculty);
            return "faculty/facultyFormAdmin";
        }
        Faculty facultyDB= null;

	    if(faculty.getId() == null)
        {
            User user = new User();
            user.setUsername(faculty.getUser().getUsername());
            user.setFirstName(faculty.getUser().getFirstName());
            user.setLastName(faculty.getUser().getLastName());
            user.setPassword(faculty.getUser().getPassword());
            user.setPasswordConfirm(faculty.getUser().getPasswordConfirm());
            user.setRole(RoleEnum.ROLE_FACULTY);
            //user = userService.save(user);

            facultyDB = new Faculty();
            //facultyDB = facultyService.findByUsername(faculty.getUser().getUsername());
            facultyDB.setUser(user);
        }else {
            facultyDB = facultyService.findOne(faculty.getId());
            facultyDB.getUser().setFirstName(faculty.getUser().getFirstName());
            facultyDB.getUser().setLastName(faculty.getUser().getLastName());
            facultyDB.getUser().setPasswordConfirm(facultyDB.getUser().getPasswordConfirm());
        }
        facultyDB.setCourses(faculty.getCourses());
        facultyDB.setMonthEnums(faculty.getMonthEnums());
        facultyDB.setNumberOfSectionPerEntry(faculty.getNumberOfSectionPerEntry());
        facultyService.save(facultyDB);

		return "redirect:/faculties";
	}
	
	@RequestMapping(value="/facultyprofile", method=RequestMethod.GET)
	public String facultyProfileM(Model model,Pageable pageable, Principal currentUser) {	
		model.addAttribute("faculty", facultyService.findByUsername(currentUser.getName()));
		model.addAttribute("courses",courseService.findAll());
		return "faculty/facultyForm";
	}
	
	@RequestMapping(value= {"/facultyprofile"}, method = RequestMethod.POST)
	public String updateFacultyProfile(@ModelAttribute("faculty") Faculty faculty, Model model, BindingResult result) {

		userValidator.validateFaculty(faculty.getUser(), result);
    	
        if (result.hasErrors()) {
			model.addAttribute("faculty", faculty);
			return "faculty/facultyFormAdmin";
		}
		Faculty facultyDB = facultyService.findOne(faculty.getId());
		facultyDB.getUser().setFirstName(faculty.getUser().getFirstName());
		facultyDB.getUser().setLastName(faculty.getUser().getLastName());
		facultyDB.getUser().setPasswordConfirm(faculty.getUser().getPasswordConfirm());
		facultyDB.setCourses(faculty.getCourses());
		facultyDB.setMonthEnums(faculty.getMonthEnums());
		facultyDB.setNumberOfSectionPerEntry(faculty.getNumberOfSectionPerEntry());
		facultyService.save(facultyDB);
		return "redirect:/facultyprofile";
	}
}
