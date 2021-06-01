package com.logintegra.wsbbugtracker.files;

import com.logintegra.wsbbugtracker.issues.Issue;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileStorageService {

    final FileDBRepository fileDBRepository;


    public FileStorageService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB store(MultipartFile file, Issue issue) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), issue);

        return fileDBRepository.save(FileDB);
    }

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public List<FileDB> getFilesByIssue(Issue issue) {
        return fileDBRepository.findAllByIssue(issue);
    }
}
