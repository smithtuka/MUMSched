package mum.swe.mumsched.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.model.Student;
import mum.swe.mumsched.service.StudentService;

/**
 * @author Rachel

 * @date May 08, 2020

 */
@Controller
public class RegisterSectionController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private mum.swe.mumsched.subsystem.sectionreg.ISectionRegSubsystem ISectionRegSubsystem;

	@RequestMapping(value = "/registerforsection", method = RequestMethod.GET)
	public String registerForSection(Model model, Pageable pageable, Principal currentUser) {
		Student student = studentService.findByUsername(currentUser.getName());		
		
		model.addAttribute("sectionList", ISectionRegSubsystem.getSections(student.getEntry().getId()));

		List<Long> mySections = student.getSectionList().stream()
				.mapToLong(x->x.getId()).boxed()
				.collect(Collectors.toList());
		
		model.addAttribute("mySections", mySections);
		return "section/registersectionlist";
	}

	@RequestMapping(value = "/registerforsectionpost/{id}", method = RequestMethod.GET)
	public String registerForSectionPost(@ModelAttribute("section") Section section, Model model, Principal currentUser,
			@PathVariable Long id) {

		Student student = studentService.findByUsername(currentUser.getName());		
		Section sectionDB = ISectionRegSubsystem.signInSection(student, section.getId());

		if (student.getSectionList() == null)
			student.setSectionList(new HashSet<Section>());
		
		student.getSectionList().add(sectionDB);
		studentService.save(student);
		
		return "redirect:/registerforsection";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/registerforsection/signout/{id}", method = RequestMethod.GET)
	public String registerForSectionSignout(@ModelAttribute("section") Section section, Model model, Principal currentUser,
			@PathVariable Long id) {
		
		Student student = studentService.findByUsername(currentUser.getName());		
		ISectionRegSubsystem.signOutSection(student, section.getId());
		
		if (student.getSectionList() != null) {
			List<Section> sections = new ArrayList<Section>(student.getSectionList());
			int len = student.getSectionList().size();
			for(int i = len-1; i >= 0; i--) {
				if(sections.get(i).getId() == section.getId() ) {
					sections.remove(i);
				}
			}
			student.setSectionList(new HashSet(sections));
		}
		studentService.save(student);	
		
		return "redirect:/registerforsection";
	}
}
