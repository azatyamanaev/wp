package ru.itis.workproject.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.workproject.dto.InformationDto;
import ru.itis.workproject.models.Document;
import ru.itis.workproject.models.FileInfo;
import ru.itis.workproject.models.User;
import ru.itis.workproject.repositories.DocumentsRepository;
import ru.itis.workproject.repositories.FilesRepository;
import ru.itis.workproject.repositories.UsersJpaRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Slf4j
public class FilesServiceImpl implements FilesService {
    private static String STORAGE_PATH = File.separator + "home" + File.separator + "monitor" + File.separator + "Рабочий стол" + File.separator + "yamanaev11-802(workdr)"
            + File.separator + "JavaLab" + File.separator + "work-project" + File.separator + "storage";

    @Autowired
    private EmailService emailService;

    @Override
    public String saveFile(MultipartFile file, String email, String login) {
        String type = "." + file.getOriginalFilename().split("\\.")[1];
        Long size = file.getSize();
        String name = file.getOriginalFilename().split("\\.")[0];
        String storageFileName = nameGenerate();
        try {
            file.transferTo(new File(STORAGE_PATH + File.separator + storageFileName + type));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return storageFileName + type;
    }

    @Override
    public Resource getFile(String fileName) {
        File file = fileFor(fileName);
        Resource fileSystemResource = new FileSystemResource(file);
        return fileSystemResource;
    }

    private String nameGenerate() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        int length = 40;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    private File fileFor(String fileName) {
        return new File(STORAGE_PATH, fileName);
    }

    private final static String FILES_PATH = File.separator + "home" + File.separator + "monitor" + File.separator + "Рабочий стол" + File.separator + "yamanaev11-802(workdr)"
            + File.separator + "JavaLab" + File.separator + "work-project" + File.separator + "files";
    private final static String CONVERTED_FILES_PATH = File.separator + "home" + File.separator + "monitor" + File.separator + "Рабочий стол" + File.separator + "yamanaev11-802(workdr)"
            + File.separator + "JavaLab" + File.separator + "work-project" + File.separator + "converted_files";

    @Autowired
    private UsersJpaRepository usersRepository;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Override
    public void init() {
        User admin = usersRepository.getOne(1L);

        try (Stream<Path> filesPaths = Files.walk(Paths.get(FILES_PATH))) {
            filesPaths.filter(filePath -> filePath.toFile().isFile()).forEach(
                    filePath -> {
                        File file = filePath.toFile();
                        Document document = null;
                        try {
                            document = Document.builder()
                                    .owner(admin)
                                    .path(filePath.toString())
                                    .size(file.length())
                                    .type(Files.probeContentType(filePath))
                                    .build();
                        } catch (IOException e) {
                            throw new IllegalArgumentException(e);
                        }
                        documentsRepository.save(document);
                    }
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Transactional
    @Override
    public void convert() {
        List<Document> documents = documentsRepository.findAll();

        for (Document document : documents) {
            String newFileName = CONVERTED_FILES_PATH + File.separator + document.getFileName() + ".jpg";

            if (document.getType().equals("application/pdf")) {
                convertPdfToJpg(document, newFileName);
                document.setType("image/jpeg");
//                documentsRepository.save(document);
            }
        }
    }

    @Transactional
    @Override
    public void convertPngByUser(Long userId) {
        User admin = usersRepository.getOne(userId);
        List<Document> documents = admin.getPngDocuments();

        for (Document document : documents) {
            String newFileName = CONVERTED_FILES_PATH + File.separator + document.getFileName() + ".jpg";

            convertPngToJpg(document, newFileName);
            document.setType("image/jpeg");
        }

        log.info("Get png - " + documents.size());
    }

    @Override
    public InformationDto getInformation(Long userId) {
        return usersRepository.getInformationByUser(userId);
    }

    @SneakyThrows
    private void convertPngToJpg(Document document, String newFileName) {
        BufferedImage image = ImageIO.read(document.getSourceFile());
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        File resultFile = new File(newFileName);
        ImageIO.write(result, "jpg", resultFile);
        document.setSourceFile(resultFile);
    }

    @SneakyThrows
    private void convertPdfToJpg(Document document, String newFileName) {
        PDDocument pdf = PDDocument.load(document.getSourceFile());
        PDFRenderer renderer = new PDFRenderer(pdf);
        BufferedImage image = renderer.renderImageWithDPI(0, 300, ImageType.RGB);
        ImageIOUtil.writeImage(image, newFileName, 300);
        pdf.close();
        File resultFile = new File(newFileName);
        document.setSourceFile(resultFile);
    }

}
