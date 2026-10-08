package com.ideas2it.ism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ideas2it.ism.common.Constant;

@Controller
public class HomeController {
    
	/**
     * All the newly assigned schedules for the employee are fetched from DB.
     * 
     * @param model - Used to send candidate object along with request to jsp.
     * @return VIEW_CANDIDATES_JSP - Display all the fetched candidates information.
     */
    @RequestMapping(value = Constant.INDEX, method = RequestMethod.GET)  
    private String dispatchIndexPage() {
        return Constant.INDEX_JSP;
    }

	/**
     * The Admin login form is dispatched.
     *
     * @return LOGIN_JSP - Display the login form.
     */
    @RequestMapping(value = Constant.LOGIN, method = RequestMethod.GET)
    private String dispatchLoginPage() {
        return Constant.LOGIN_JSP;
    }

}
