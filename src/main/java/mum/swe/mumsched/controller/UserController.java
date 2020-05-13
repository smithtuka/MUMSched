package mum.swe.mumsched.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mum.swe.mumsched.enums.RoleEnum;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.service.UserService;
import mum.swe.mumsched.validator.UserValidator;

/**
 * @author Smith 
 * @date May 10, 2020
 */
@Controller
@Secured("ROLE_ADMIN")
public class UserController {

	@Autowired
	private UserService userService;
    @Autowired
    private UserValidator userValidator;
	
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model, Principal currentUser) {
		model.addAttribute("users", userService.findByRole(RoleEnum.ROLE_ADMIN));
		model.addAttribute("username", currentUser.getName());
		return "user/list";
    }

	
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "user/edit";
	}
	
	@RequestMapping(value="/user/{id}", method = RequestMethod.GET)
	public String view(@PathVariable Long id, Model model){	
		model.addAttribute("user", userService.findOne(id));
		return "user/edit";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String edit(@ModelAttribute("user") User user, 
			BindingResult result, Model model)  {

    	userValidator.validate(user, result);
    	
        if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/edit";
		}
        user.setRole(RoleEnum.ROLE_ADMIN);
		user = userService.save(user);
		return "redirect:/users";
	}	
	
	@RequestMapping(value="/user/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, Model model){		
		userService.delete(id);
		return "redirect:/users";
	}	
	
	@RequestMapping(value="/adminprofile", method = RequestMethod.GET)
	public String adminProfile(Principal currentUser, Model model) {
		model.addAttribute("user", userService.findByUsername(currentUser.getName()));
		return "user/adminprofile";
	}
	
	@RequestMapping(value = "/adminprofile", method = RequestMethod.POST)
	public String adminProfile(@ModelAttribute("user") User user, 
			BindingResult result, Principal currentUser, Model model)  {

		User userRecord = userService.findByUsername(currentUser.getName());
		
    	userValidator.validateAdmin(user, result);
    	
        if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/adminprofile";
		}
        
        userRecord.setFirstName(user.getFirstName());
        userRecord.setLastName(user.getLastName());
        userRecord.setPassword(user.getPassword());
        
		user = userService.save(userRecord);
		model.addAttribute("user", userRecord);
		return "user/adminprofile";
	}
}
