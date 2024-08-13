package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
public class MinioService {
    @Autowired
    private UserRepository userRepository;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    //    public String uploadPdf(String bucketName, String fileName, byte[] data) throws Exception {
//        MinioClient minioClient = getMinioClient();
//        ByteArrayInputStream bais = new ByteArrayInputStream(data);
//        minioClient.putObject(
//                PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(fileName)
//                        .stream(bais, data.length, -1)
//                        .contentType("application/pdf")
//                        .build()
//        );
//
//        // Verify the object was uploaded
//        minioClient.getObject(
//                GetObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(fileName)
//                        .build()
//        );
//
//        // Build and return the URL manually
//        return buildObjectUrl(bucketName, fileName);
//    }
    private String uploadFileToMinIO(MultipartFile file) throws Exception {
        String bucketName = "contracts";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        MinioClient minioClient = getMinioClient();

        // Create the bucket if it doesn't exist
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Upload the file
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType("application/pdf")
                        .build()
        );

        // Return the file URL
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .expiry(60 * 60 * 24) // 1 day expiry
                .build());
    }

    private String buildObjectUrl(String bucketName, String fileName) {
        // Assuming your MinIO server URL is something like http://localhost:9000
        String minioUrl = "http://localhost:9000";
        return minioUrl + "/" + bucketName + "/" + fileName;
    }

    public String uploadFile(MultipartFile file, String email) throws IOException {
        MinioClient minioClient = getMinioClient();
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            String fileUrl = minioUrl + "/" + bucketName + "/" + fileName;
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setImage(fileUrl);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User not found with email: " + email);
            }
            return fileUrl;
        } catch (MinioException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
}
