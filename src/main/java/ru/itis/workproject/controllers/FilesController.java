package ru.itis.workproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import ru.itis.workproject.services.FileLoadService;


@Controller
public class FilesController {

    @Autowired
    private FileLoadService fileLoadService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        ModelAndView modelAndView = new ModelAndView();
        if (!multipartFile.isEmpty()) {
            fileLoadService.uploadFile(multipartFile);
            modelAndView.setViewName("uploadSuccess");
        } else {
            modelAndView.setViewName("emptyFile");
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public ModelAndView getFile(@PathVariable("file-name") String filename) {
        String name = filename.substring(10);
        fileLoadService.downloadFile(filename);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filename", name);
        modelAndView.setViewName("redirect:/downloadedFile");
        RedirectAttributesModelMap attributesModelMap = new RedirectAttributesModelMap();
        attributesModelMap.addFlashAttribute("filename", name);
        return modelAndView;
    }

    @RequestMapping(value = "/downloadedFile", method = RequestMethod.GET)
    public ModelAndView getDownloadSuccessPage(@RequestParam("filename") String filename) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("downloadedFile");
        modelAndView.addObject("filename", filename);
        return modelAndView;
    }
}
