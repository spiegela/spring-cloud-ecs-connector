package com.emc.ecs.connector;

import org.springframework.cloud.service.BaseServiceInfo;
import org.springframework.cloud.service.ServiceInfo;

/**
 * Provides a normalized implementation of S3 service details which can be gleaned
 * in multiple cloud environments. This class includes all of the necessary parameters
 * to create a valid {@link com.emc.ecs.connector.spring.S3Connector}.
 */
public class S3ServiceInfo extends BaseServiceInfo {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;

    /**
     * Default Constructor.
     * The default constructor requires all fields to be provided; however, in some cases fields
     * fields will be provided as null values.
     * @param id the service ID of the provisioned service
     * @param accessKey the user access-key used to authenticate a connection or requests
     * @param secretKey the user secret-key used to authenticate a connection or requests
     * @param endpoint (optional) the S3 compatible URL or endpoint of the S3 service
     * @param bucket (optional) a specific bucket the client is expected to address calls to storage
     */
    public S3ServiceInfo(String id, String accessKey, String secretKey, String endpoint, String bucket) {
        super(id);
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
        this.endpoint = endpoint;
    }

    @ServiceProperty
    public String getAccessKey() {
        return accessKey;
    }

    @ServiceProperty
    public String getSecretKey() {
        return secretKey;
    }

    @ServiceProperty
    public String getBucket() {
        return bucket;
    }

    @ServiceProperty
    public String getEndpoint() {
        return endpoint;
    }
}