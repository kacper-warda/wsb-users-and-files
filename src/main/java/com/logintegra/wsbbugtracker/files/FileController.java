package com.logintegra.wsbbugtracker.files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileController {
    final FileStorageService storageService;
    final FileDBRepository fileDBRepository;

    public FileController(FileStorageService storageService, FileDBRepository fileDBRepository) {
        this.storageService = storageService;
        this.fileDBRepository = fileDBRepository;
    }

    @GetMapping("/add")
    public String addFile() {
        return "file/upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
        } else {
            try {
                storageService.store(file);

                attributes.addFlashAttribute("message", "Uploaded the file successfully: " + file.getOriginalFilename());
            } catch (Exception e) {
                attributes.addFlashAttribute("message", "Could not upload the file: " + file.getOriginalFilename());
            }
        }

        return "redirect:/file/add";

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


}
