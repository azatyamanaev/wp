package ru.itis.workproject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadFilesController {

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.GET)
    public ModelAndView getUploadFilesPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadFiles");
        return modelAndView;
    }
}
