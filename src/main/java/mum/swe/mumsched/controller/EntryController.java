package mum.swe.mumsched.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mum.swe.mumsched.helper.AjaxResult;
import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.service.CourseService;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.FacultyService;
import mum.swe.mumsched.service.MessageByLocaleService;



@Secured("ROLE_ADMIN")
@RequestMapping(path = "/entry")
@Controller
public class EntryController {
	
	@Autowired
	EntryService service;
	
	@Autowired
	FacultyService facultyService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	MessageByLocaleService msgService;

	@GetMapping("/")
	public String entryList(Model model) {
		model.addAttribute("entryList", service.getList());
		return "entry/entryList";
	}
	
	@GetMapping("/add")
	public String newEntry(Model model) {
		model.addAttribute("allFacultyList", facultyService.findAll());
		model.addAttribute("allCourseList",courseService.findAll());
		model.addAttribute("entry", new Entry());
		return "entry/update";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allFacultyList",facultyService.findAll());
		model.addAttribute("allCourseList",courseService.findAll());
		model.addAttribute("entry", service.findEntryById(id));
		return "entry/update";
	}
	
	@PostMapping("/save")
	public String save(@Valid Entry entry, BindingResult bindingResult, Model model, RedirectAttributes ra) {
		boolean hasError = bindingResult.hasErrors();
		
		// validate business rules
		if(!hasError) {
			// check exists if update
			if(entry.getId() > 0 && service.findEntryById(entry.getId()) == null){
				bindingResult.reject("name", null,
						msgService.getMessage(MessageByLocaleService.NOT_FOUND_MESSAGE, new Object[] {msgService.getMessage("field.entry")}));
				hasError = true;
			}
			
			// check total fpp
			if(entry.getFpp() != entry.getFppCPT() + entry.getFppOPT()){
				bindingResult.reject("fpp", null, msgService.getMessage(MessageByLocaleService.INVALID_MESSAGE));
				hasError = true;
			}
			
			// check total mpp
			if(entry.getMpp() != entry.getMppCPT() + entry.getMppOPT()){
				bindingResult.reject("mpp", null, msgService.getMessage(MessageByLocaleService.INVALID_MESSAGE));
				hasError = true;
			}
			
			//valid unique entry name
			if(service.hasExistsEntryName(entry.getName(), entry.getId())) {
				bindingResult.rejectValue("name", null,  msgService.getMessage(MessageByLocaleService.ALREADY_EXISTS_MESSAGE));
				hasError = true;
			}
		}
		
		// has error
		if(hasError)
		{
			model.addAttribute("allFacultyList", facultyService.findAll());
			return "entry/update"; 
		}
		
		// redirect message
		msgService.addRedirectMessage(ra, entry.getId() == 0 ? 
				 MessageByLocaleService.MSG_CreateSuccess: MessageByLocaleService.MSG_UpdateSuccess, null);
		
		// save entry
		service.save(entry);
		
		return "redirect:/entry/";
	}
	
	@PostMapping("/delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable Long id) {
		Entry entry = service.findEntryById(id);
		
		if(entry == null) {
			return AjaxResult.fail(msgService.getMessage(MessageByLocaleService.NOT_FOUND_MESSAGE, new Object[] {msgService.getMessage("field.entry")}));
		}
		
		//  valid reference
		if(service.hasScheduleRef(entry)) {
			return AjaxResult.fail(msgService.getMessage(MessageByLocaleService.HAS_REF_MESSAGE, 
					new Object[] {msgService.getMessage("field.schedule")}));
		}
		
		service.delete(entry);
		
        return AjaxResult.success(msgService.getRemoveSuccess());
    }
}
