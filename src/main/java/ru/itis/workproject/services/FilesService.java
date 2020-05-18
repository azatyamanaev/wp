package ru.itis.workproject.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.workproject.dto.InformationDto;

public interface FilesService {
    String saveFile(MultipartFile multipartFile, String email, String login);
    Resource getFile(String filename);
    void init();
    void convert();
    void convertPngByUser(Long userId);
    InformationDto getInformation(Long userId);
}
