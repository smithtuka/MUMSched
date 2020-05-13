package mum.swe.mumsched.controller;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.model.Block;
import mum.swe.mumsched.model.Schedule;
import mum.swe.mumsched.service.ScheduleViewService;

/**
 * @author Rachel

 * @date May 08, 2020

 */

@Controller
public class ScheduleViewController {

	@Autowired
	private ScheduleViewService scheduleViewService;
	
	@GetMapping("/scheduleView")
	public String view(Model model) {
		Schedule schedule = scheduleViewService.findOneByEntryId(1L);
		schedule.getBlockList().stream().sorted(Comparator.comparing(Block::getMonth));
		model.addAttribute("schedule", schedule);
		return "schedule/viewschedule";
	}
}
