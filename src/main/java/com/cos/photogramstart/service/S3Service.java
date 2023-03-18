package com.cos.photogramstart.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cos.photogramstart.util.ImageUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.fileDir}")
    private String fileDir;
    
    @PostConstruct	
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))	
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file, String userId) throws IOException {
    	

    	byte[] bytes = file.getBytes();    	
        String fileName = file.getOriginalFilename();
    	
    	byte[] resizedImageBytes  = ImageUtil.resize(bytes, fileName);	
        UUID uuid = UUID.randomUUID();
        
        InputStream stream = new ByteArrayInputStream(resizedImageBytes);
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(resizedImageBytes.length);
        metadata.setContentType(file.getContentType());
        
        
        s3Client.putObject(new PutObjectRequest(bucket, fileDir + userId + "/" + uuid + fileName, stream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        String uuidex = uuid.toString();
        System.out.println(fileDir + " 파일네임 못가져오는듯? ");
//        System.out.println(s3Client.getUrl(bucket+"test/",  uuidex + fileName).toString() + " : 이미지 이름 어떻게해? ");
//        System.out.println(s3Client.getBucketLocation(bucket) + " 테스트 location 1");
//        System.out.println(s3Client.getObject(bucket, uuidex + fileName) + "테스트 object get ");
        return s3Client.getUrl(bucket, fileDir  + userId + "/" + uuidex + fileName).toString();
    }
}
