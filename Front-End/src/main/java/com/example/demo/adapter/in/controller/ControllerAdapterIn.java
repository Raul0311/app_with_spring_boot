package com.example.demo.adapter.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.application.ports.in.UserPortIn;

@Controller
public class ControllerAdapterIn {
	
	@Autowired	
	private UserPortIn userPortIn;

    // Redirige "/" a "/login"
    @GetMapping("/")
    public String root() {
        return "redirect:/auth";
    }

    // Página de login
    @GetMapping("/auth/**")
    public ModelAndView auth(@RequestParam(required = false) String error,
                              @RequestParam(required = false) String logout,
                              @RequestParam(required = false) String registered,
                              Authentication auth) {
    	
    	String view = userPortIn.load(auth);
    	
    	ModelAndView model = new ModelAndView(view);
    	
    	if ("true".equals(registered)) {
    		model.addObject("msg", "La cuenta se ha creado correctamente.");
        }

        if ("true".equals(error)) {
        	model.addObject("error", "Usuario o contraseña incorrectos.");
        }

        if ("userexists".equals(error)) {
        	model.addObject("error", "El nombre de usuario ya existe.");
        }
    	
        return model;
    }
    
    // Página privada
    @GetMapping("/private/home")
    public String home() {
        return "private/home"; 
    }
    
    @GetMapping("/register")
    public ModelAndView register(@RequestParam(required = false) String error,
                              Authentication auth) {
    	
    	return new ModelAndView("redirect:/auth/register");
    }
    
    @GetMapping("/forgot-password")
    public ModelAndView forgotPassword(@RequestParam(required = false) String error,
            Authentication auth) {
    	
        return new ModelAndView("redirect:/auth/forgot-password");
    }
    
    @GetMapping("/reset-password")
    public ModelAndView resetPassword(@RequestParam(required = false) String error,
            Authentication auth) {

        return new ModelAndView("redirect:/auth/reset-password");
    }
}
