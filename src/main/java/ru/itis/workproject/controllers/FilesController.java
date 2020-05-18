package ru.itis.workproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import ru.itis.workproject.dto.InformationDto;
import ru.itis.workproject.models.User;
import ru.itis.workproject.security.details.UserDetailsImpl;
import ru.itis.workproject.services.FilesService;


@Controller
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/files")
    @ResponseBody
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        String email = user.getEmail();
        String login = user.getLogin();
        return ResponseEntity.ok().body(filesService.saveFile(multipartFile, email, login));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/files/{file-name:.+}")
    @ResponseBody
    public ResponseEntity<Resource> read(@PathVariable("file-name") String fileName) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(filesService.getFile(fileName));
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/storage")
    public String getStoragePage() {
        return "uploadFiles";
    }

    @GetMapping("/files/init")
    @ResponseBody
    public ResponseEntity<?> init() {
        filesService.init();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/files/convert")
    @ResponseBody
    public ResponseEntity<?> convert() {
        filesService.convert();
        return ResponseEntity.ok().build();
    }

    @GetMapping("users/{user-id}/files/png/convert")
    @ResponseBody
    public ResponseEntity<?> convertPngByUser(@PathVariable("user-id") Long userId) {
        filesService.convertPngByUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("users/{user-id}/files/information")
    @ResponseBody
    public ResponseEntity<InformationDto> getInformation(@PathVariable("user-id") Long userId) {
        InformationDto result = filesService.getInformation(userId);
        return ResponseEntity.ok(result);
    }

}
