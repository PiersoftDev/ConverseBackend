package com.piersoft.converse.vendor.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.piersoft.converse.vendor.persistence.repository")
public class DynamoDBConfig {

//    //@Value("${amazon.dynamodb.endpoint}")
//    private String amazonDynamoDBEndpoint;
//
//    @Value("${amazon.aws.accesskey}")
//    private String amazonAWSAccessKey;
//
//    @Value("${amazon.aws.secretkey}")
//    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentials())
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentialsProvider amazonAWSCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                "AKIARDJRCSMYCTBIRJWI", "ejDoYOtsfbbjwxIN7RZPIflUjJjYgzruGN7o6IGC"));
    }
}
