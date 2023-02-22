package com.piersoft.converse.vendor.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

@Configuration
public class RekognitionConfig {

    @Bean
    public RekognitionClient rekognitionClient(){
       return RekognitionClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(amazonAwsCredentials())
                .build();
    }

    public AwsCredentialsProvider amazonAwsCredentials() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                "AKIARDJRCSMYOWNWIZ77", "s+Pzrx9je4hslssmrAyI8d544xvq7OMItm8RCpew"));
    }
}
