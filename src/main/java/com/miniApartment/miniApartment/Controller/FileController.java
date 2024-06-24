package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Services.MinioService;
import kotlin.jvm.Throws;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/file")
@CrossOrigin
public class FileController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws Exception {
        try {
            return minioService.uploadFile(file,email);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
