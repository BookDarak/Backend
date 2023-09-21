//package com.bookjeok.bookdarak.controller;
//
//import com.bookjeok.bookdarak.base.BaseResponse;
//import com.bookjeok.bookdarak.service.S3UploadService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@RestController
//public class FileUploadController {
//    private final S3UploadService s3UploadService;
//
//    @PostMapping("/upload")
//    public BaseResponse<String> uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
//        return new BaseResponse<>(s3UploadService.upload(multipartFile));
//    }
//}