package com.bookjeok.bookdarak.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequiredArgsConstructor
@Service
public class S3UploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;


    public String upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), metadata);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void deleteImage(String originalFilename)  {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}
