package mum.swe.mumsched.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.DashboardService;
import mum.swe.mumsched.service.UserService;
import mum.swe.mumsched.view.DashboardView;

/**
 * @author Smith 
 * @date May 14, 2020
 */
@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private DashboardService dashboardService;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Principal currentUser, HttpServletRequest request) {     
    	
		ModelAndView modelAndView = new ModelAndView();
		
    	if(currentUser != null)
    	{
			User user = userService.findByUsername(currentUser.getName());		
			modelAndView.addObject("userName", "Welcome " + user.getUsername() + "!");    		
    	}

    	DashboardView dv = new DashboardView();
    	dv.setCountFaculties(dashboardService.countFaculties());
    	dv.setCountStudents(dashboardService.countStudents());
    	dv.setCountCourses(dashboardService.countCourses());
    	dv.setCountSections(dashboardService.countSections());
    	

		modelAndView.addObject("dashboard", dv);    
		modelAndView.setViewName("home/index");
		return modelAndView;
    }
}
