package ru.itis.workproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.workproject.dto.ProfileForm;
import ru.itis.workproject.models.User;
import ru.itis.workproject.security.details.UserDetailsImpl;
import ru.itis.workproject.services.UsersService;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getProfilePage(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");
        modelAndView.addObject("profileForm", new ProfileForm());
        return modelAndView;
    }

    @PostMapping("/profile")
    public String updateProfile(Authentication authentication, @Valid ProfileForm form, BindingResult bindingResult, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("profileForm", form);
        if (!bindingResult.hasErrors()) {
            user.setLogin(form.getLogin());
            user.setEmail(form.getEmail());
            usersService.updateUser(user);
        }
        return "profile";
    }
}
