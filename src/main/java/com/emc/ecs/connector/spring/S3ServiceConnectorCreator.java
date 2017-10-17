package com.emc.ecs.connector.spring;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.emc.ecs.connector.S3ServiceInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;

/**
 * Provides an implementation of {@link AbstractServiceConnectorCreator} that detects S3 service information
 * and creates an {@link S3Connector} instance with embedded {@link AmazonS3 Amazon Web Services S3 SDK Client}.
 */
public class S3ServiceConnectorCreator
        extends AbstractServiceConnectorCreator<S3Connector, S3ServiceInfo> {
    private static Log log = LogFactory.getLog(S3ServiceConnectorCreator.class);

    /**
     * Creates an {@link S3Connector} instance  with embedded {@link AmazonS3 Amazon Web Services S3 SDK Client} from
     * {@link S3ServiceInfo}.
     * @param serviceInfo S3ServiceInfo provided by {@link org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator}
     *                    implementation included within the application.
     * @param serviceConnectorConfig
     * @return
     */
    @Override
    public S3Connector create(S3ServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfig) {
        AWSCredentials credentials = new BasicAWSCredentials(serviceInfo.getAccessKey(), serviceInfo.getSecretKey());
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(
                serviceInfo.getEndpoint(), Region.getRegion(Regions.DEFAULT_REGION).getName());
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfig)
                .withCredentials(credentialsProvider)
                .enablePathStyleAccess()
                .build();
        if (serviceInfo.getBucket() != null) {
            log.debug("Creating connector addressing ECS bucket: " + serviceInfo.getBucket());
            return new S3Connector(amazonS3, serviceInfo.getEndpoint(), serviceInfo.getBucket());
        } else {
            log.debug("Creating connector addressing ECS namespace.");
            return new S3Connector(amazonS3, serviceInfo.getEndpoint());
        }
    }
}