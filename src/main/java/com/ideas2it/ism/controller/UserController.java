package com.ideas2it.ism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ideas2it.ism.common.Constant;
import com.ideas2it.ism.entity.User;
import com.ideas2it.ism.exception.IsmException;
import com.ideas2it.ism.service.UserService;

/**
 * Allows a visitor to self-register a new User account, independent of
 * Admin, Employee, and Candidate.
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * The registration form is dispatched.
	 *
	 * @param model - Used to send an empty User object along with the request to the jsp.
	 * @return REGISTER_JSP - Display the registration form.
	 */
	@RequestMapping(value = Constant.REGISTER, method = RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute(Constant.USER, new User());
		return Constant.REGISTER_JSP;
	}

	/**
	 * The submitted username/password are registered as a new User. On
	 * success, the registration form re-renders with a confirmation
	 * message; on a duplicate username, it re-renders with an error
	 * message.
	 *
	 * @param user - Submitted username/password to register.
	 * @param model - Used to send the result back to the jsp.
	 * @return REGISTER_JSP - Display the registration form with the result.
	 */
	@RequestMapping(value = Constant.REGISTER, method = RequestMethod.POST)
	public String register(@ModelAttribute(Constant.USER) User user, Model model) {
		try {
			userService.registerUser(user);
			model.addAttribute(Constant.REGISTRATION_SUCCESS, true);
		} catch (IsmException exception) {
			user.setPassword(null);
			model.addAttribute(Constant.ERROR, exception.getMessage());
			model.addAttribute(Constant.USER, user);
		}
		return Constant.REGISTER_JSP;
	}

}
