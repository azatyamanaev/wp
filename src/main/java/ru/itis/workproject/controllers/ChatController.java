package ru.itis.workproject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.workproject.models.User;
import ru.itis.workproject.security.details.UserDetailsImpl;

@Controller
public class ChatController {

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView getChatPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("chat");
        return modelAndView;
    }
}
