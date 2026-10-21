package com.ideas2it.ism.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	/**
	 * The User login form is dispatched.
	 *
	 * @return USER_LOGIN_JSP - Display the User login form.
	 */
	@RequestMapping(value = Constant.USER_LOGIN, method = RequestMethod.GET)
	public String userLoginForm() {
		return Constant.USER_LOGIN_JSP;
	}

	/**
	 * The placeholder home page is dispatched to a signed-in User.
	 *
	 * @return USER_HOME_JSP - Display the User home page.
	 */
	@RequestMapping(value = Constant.USER_HOME, method = RequestMethod.GET)
	public String userHome() {
		return Constant.USER_HOME_JSP;
	}

	/**
	 * The profile screen is dispatched for the authenticated User, showing
	 * their current username and email.
	 *
	 * @param principal - the authenticated User's Spring Security principal.
	 * @param model - Used to send the User object along with the request to the jsp.
	 * @return USER_PROFILE_JSP - Display the profile screen.
	 * @throws IsmException - if the authenticated User's row is not found.
	 */
	@RequestMapping(value = Constant.USER_PROFILE, method = RequestMethod.GET)
	public String userProfile(Principal principal, Model model) throws IsmException {
		model.addAttribute(Constant.USER, userService.findByUsername(principal.getName()));
		return Constant.USER_PROFILE_JSP;
	}

	/**
	 * The submitted email is validated and saved for the authenticated
	 * User. On success, the profile screen re-renders with a confirmation
	 * message; on an invalid email, it re-renders with an error message and
	 * the submitted (invalid) email so the form can re-render it.
	 *
	 * @param principal - the authenticated User's Spring Security principal.
	 * @param email - the submitted email to update.
	 * @param model - Used to send the result back to the jsp.
	 * @return USER_PROFILE_JSP - Display the profile screen with the result.
	 * @throws IsmException - if the authenticated User's row is not found.
	 */
	@RequestMapping(value = Constant.USER_PROFILE_EMAIL, method = RequestMethod.POST)
	public String updateEmail(Principal principal, @RequestParam(Constant.EMAIL) String email, Model model)
			throws IsmException {
		try {
			User user = userService.updateEmail(principal.getName(), email);
			model.addAttribute(Constant.USER, user);
			model.addAttribute(Constant.EMAIL_UPDATED, true);
		} catch (IsmException exception) {
			User user = userService.findByUsername(principal.getName());
			user.setEmail(email);
			model.addAttribute(Constant.USER, user);
			model.addAttribute(Constant.ERROR, exception.getMessage());
		}
		return Constant.USER_PROFILE_JSP;
	}

	/**
	 * The submitted current password is verified and the new password is
	 * saved for the authenticated User. On success, the profile screen
	 * re-renders with a confirmation message; on failure, it re-renders
	 * with an error message. Neither password value is ever echoed back to
	 * the view.
	 *
	 * @param principal - the authenticated User's Spring Security principal.
	 * @param currentPassword - the submitted current password.
	 * @param newPassword - the submitted new password.
	 * @param model - Used to send the result back to the jsp.
	 * @return USER_PROFILE_JSP - Display the profile screen with the result.
	 * @throws IsmException - if the authenticated User's row is not found.
	 */
	@RequestMapping(value = Constant.USER_PROFILE_PASSWORD, method = RequestMethod.POST)
	public String changePassword(Principal principal, @RequestParam(Constant.CURRENT_PASSWORD) String currentPassword,
			@RequestParam(Constant.NEW_PASSWORD) String newPassword, Model model) throws IsmException {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute(Constant.USER, user);
		try {
			userService.changePassword(principal.getName(), currentPassword, newPassword);
			model.addAttribute(Constant.PASSWORD_UPDATED, true);
		} catch (IsmException exception) {
			model.addAttribute(Constant.ERROR, exception.getMessage());
		}
		return Constant.USER_PROFILE_JSP;
	}

}
