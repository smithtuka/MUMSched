package mum.swe.mumsched.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.service.BlockService;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.BlockService;
import mum.swe.mumsched.service.MessageByLocaleService;
import mum.swe.mumsched.service.ScheduleService;
import mum.swe.mumsched.service.impl.MessageByLocaleServiceImpl;

/**
 * @author Fahad
 * @date May, 08 2020
 */
@Secured("ROLE_ADMIN")
@RequestMapping(path = "/block")
@Controller
public class BlockController {
	@Autowired
	BlockService service;
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	MessageByLocaleService msgService;
	
	@GetMapping("/")
	public String blockList(Model model) {
		model.addAttribute("blockList", service.getList());
		return "block/blockList";
	}
	
	@GetMapping("/add")
	public String newBlock(Model model) {
		model.addAttribute("allSchedule", scheduleService.findAll());
		model.addAttribute("block", new Block());
		return "block/update";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allSchedule", scheduleService.findAll());
		model.addAttribute("block", service.findBlockById(id));
		return "block/update";
	}
	
	@PostMapping("/save")
	public String save(@Valid Block block, BindingResult bindingResult, Model model, RedirectAttributes ra) {
		boolean hasError = bindingResult.hasErrors();
		
		// validate business rules
		if(!hasError) {
			// check exists if update
			if(block.getId() > 0 && service.findBlockById(block.getId()) == null){
				bindingResult.reject("month", null,
						msgService.getMessage(MessageByLocaleService.NOT_FOUND_MESSAGE, new Object[] {msgService.getMessage("field.block")}));
				hasError = true;
			}
			
			// valid from date < to date
			if(block.getToDate().compareTo(block.getFromDate()) <= 0) {
				bindingResult.rejectValue("toDate", null, 
						msgService.getMessage(MessageByLocaleService.MUST_BE_GREATER_THAN_MESSAGE, new Object[] {msgService.getMessage("field.fromDate")}));
				hasError = true;
			}
			
			//valid unique block name
			if(service.hasExistsBlock(block.getSchedule().getId(), block.getMonth(), block.getId())) {
				bindingResult.rejectValue("month", null, msgService.getMessage(MessageByLocaleService.ALREADY_EXISTS_MESSAGE));
				hasError = true;
			}
		}
		
		// has error
		if(hasError)
		{
			model.addAttribute("allSchedule", scheduleService.findAll());
			model.addAttribute("block", block);
			return "block/update"; 
		}
		
		// redirect message
		msgService.addRedirectMessage(ra, block.getId() == 0 ? 
				 MessageByLocaleService.MSG_CreateSuccess: MessageByLocaleService.MSG_UpdateSuccess, null);
		
		// save block
		service.save(block);
		
		return "redirect:/block/";
	}
	
	@PostMapping("/delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable Long id) {
		Block block = service.findBlockById(id);
		
		if(block == null) {
			return AjaxResult.fail(msgService.getMessage(MessageByLocaleService.NOT_FOUND_MESSAGE, 
					new Object[] {msgService.getMessage("field.block")}));
		}
		
		// has section ref
		if(service.hasSectionRef(block)) {
			return AjaxResult.fail(msgService.getMessage(MessageByLocaleService.HAS_REF_MESSAGE, 
					new Object[] {msgService.getMessage("field.section")}));
		}
		
		service.delete(block);
		
        return AjaxResult.success(msgService.getRemoveSuccess());
    }
}
