package mum.swe.mumsched.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.Student;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.StudentService;
import mum.swe.mumsched.validator.UserValidator;


@Controller
public class StudentAdminController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private EntryService entryService;
    @Autowired
    private UserValidator userValidator;


	@RequestMapping(value="/students", method=RequestMethod.GET)
	public String students(Model model, Principal currentUser) {
		model.addAttribute("students", studentService.findAll());
		return "student/studentList";
	}

	@RequestMapping(value="/student", method = RequestMethod.GET)
	public String create(Model model){
		model.addAttribute("student", new Student());
        model.addAttribute("entries",entryService.getList());
		return "student/studentFormAdmin";
	}

    @RequestMapping(value="/student/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model){
    	studentService.delete(id);
        return "redirect:/students";
    }

    @RequestMapping(value="/student/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("student", studentService.findOne(id));
        model.addAttribute("entries",entryService.getList());
        return "student/studentFormAdmin";
    }

	@RequestMapping(value= {"/student"}, method = RequestMethod.POST)
	public String edit(@ModelAttribute("student") Student student, BindingResult result, Model model) {

        userValidator.validateStuden(student.getUser(), result);

        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return "student/studentFormAdmin";
        }
        
        Student studentDB= null;

	    if(student.getId() == null)
        {
            User user = new User();
            user.setUsername(student.getUser().getUsername());
            user.setFirstName(student.getUser().getFirstName());
            user.setLastName(student.getUser().getLastName());
            user.setPassword(student.getUser().getPassword());
            user.setPasswordConfirm(student.getUser().getPasswordConfirm());
            user.setRole(RoleEnum.ROLE_STUDENT);

            studentDB = new Student();
            studentDB.setUser(user);
        }else {
        	studentDB = studentService.findOne(student.getId());
        	
        	studentDB.getUser().setFirstName(student.getUser().getFirstName());
        	studentDB.getUser().setLastName(student.getUser().getLastName());
        	studentDB.getUser().setPassword(student.getUser().getPassword());
    		studentDB.getUser().setPasswordConfirm(student.getUser().getPasswordConfirm());
        }
	    
	    studentDB.setEntry(student.getEntry());
	    studentDB.setSectionList(student.getSectionList());
        studentService.save(studentDB);

		return "redirect:/students";
	}
}
