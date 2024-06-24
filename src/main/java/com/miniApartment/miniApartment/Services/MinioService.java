package com.miniApartment.miniApartment.Services;
import com.miniApartment.miniApartment.Entity.User;
import com.miniApartment.miniApartment.Repository.UserRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MinioService {
    @Autowired
    private UserRepository userRepository;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String uploadFile(MultipartFile file,String email) throws IOException {
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
