package mum.swe.mumsched.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Entry;
import mum.swe.mumsched.model.Schedule;
import mum.swe.mumsched.model.Section;
import mum.swe.mumsched.service.BlockService;
import mum.swe.mumsched.service.CourseService;
import mum.swe.mumsched.service.EntryService;
import mum.swe.mumsched.service.FacultyService;
import mum.swe.mumsched.service.ScheduleService;
import mum.swe.mumsched.service.SectionService;
import mum.swe.mumsched.service.UserService;
import mum.swe.mumsched.validator.UserValidator;

/**
 * @author Smith
 * @date May 11, 2020
 */
@Secured("ROLE_ADMIN")
@RequestMapping(path = "/schedule")
@Controller
public class ScheduleController {
	@Autowired
	BlockService service;

	@Autowired
	EntryService entryService;
	@Autowired
	SectionService sectionService;

	@Autowired
	private UserService userService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private BlockService blockService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private UserValidator userValidator;

	@GetMapping("/generate/{id}")
	public String generate(@PathVariable("id") Long entryId, Model model) throws Exception {

		Entry entry = entryService.findEntryById(entryId);
		Schedule scheduleDB = scheduleService.save(new Schedule());
		Schedule scheduleGen = scheduleService.generateSchedule(entry);
		scheduleGen.setId(scheduleDB.getId());
		
		scheduleGen.getBlockList().stream().forEach(block -> {
			block.getSectionList().stream().forEach(s -> s.setBlock(block));
		});
		
		for(Block b: scheduleGen.getBlockList()) {
			sectionService.saveAll(new ArrayList<Section>(b.getSectionList()));
		}

		scheduleGen.getBlockList().stream().forEach(block -> {
			block.setSchedule(scheduleGen);
		});
		blockService.saveAll(new ArrayList<Block>(scheduleGen.getBlockList()));
		scheduleService.save(scheduleGen);

		model.addAttribute("schedule", scheduleGen);
		return "redirect:/schedule/view/" + scheduleGen.getId();
	}

	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {

		Schedule schedule = scheduleService.findOneById(id);
		LinkedHashSet<Block> reOrderedBlock = new LinkedHashSet<Block>(schedule.getBlockList().stream().sorted(Comparator.comparing(Block::getId)).collect(Collectors.toList()));
		schedule.setBlockList(reOrderedBlock);
		model.addAttribute("schedule", schedule);
		return "schedule/schedule";
	}
	
	
	@RequestMapping(value = "/edit/", method = RequestMethod.POST)
	public String edit(@ModelAttribute("schedule") Schedule schedule, 
			BindingResult result, Model model)  {
		Schedule scheduleDB = scheduleService.findOneByEntryId(schedule.getId());
		scheduleDB.setStatus(schedule.getStatus());
		scheduleService.save(scheduleDB);
		return "redirect:/schedule/view/" + scheduleDB.getId();
	}	

}
