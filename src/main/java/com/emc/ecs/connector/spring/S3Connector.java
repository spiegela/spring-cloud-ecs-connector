package com.emc.ecs.connector.spring;

import com.amazonaws.services.s3.AmazonS3;
import lombok.Getter;

/**
 * Provides an AWS SDK S3 client & relevant configuration parameters that an application
 * could need when addressing an S3 service.
 */
@Getter
public class S3Connector {
    private AmazonS3 client;
    private String bucket;
    private String endpoint;

    /**
     * Genereates an S3Connector with AWS SDK S3 client, the S3 endpoint and a baseUrl for
     * public access to the same.  In most cases the endpoint & baseUrl are the same, but there
     * there are cases, when they will be different.
     * @param client {@link AmazonS3 AmazonS3 client} which has been configured to connect to the
     *               S3 endpoint bound by the cloud to the application.
     * @param endpoint String of the S3 API URL endpoint used to configure the client.  This is
     *                 provided as a convenience to the developer in cases where the application
     *                 must reference it directly.
     */
    public S3Connector(AmazonS3 client, String endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

  /**
   * Genereates an S3Connector with AWS SDK S3 client, a specific S3 bucket, the S3 endpoint and a
   * baseUrl for * public access to the same.  In most cases the endpoint & baseUrl are the same,
   * but there there are cases, when they will be different.
   * @param client {@link AmazonS3 AmazonS3 client} which has been configured to connect to the
   *               S3 endpoint bound by the cloud to the application.
   * @param endpoint String of the S3 API URL endpoint used to configure the client.  This is
   *                 provided as a convenience to the developer in cases where the application
   *                 must reference it directly.
   * @param bucket String of the bucket that is bound by the cloud and intended for use in the
   *               application.  The bucket need not be provided in cases where the application is
   *               expected to create it's own buckets.
   */
    public S3Connector(AmazonS3 client, String endpoint, String bucket) {
        this.client = client;
        this.endpoint = endpoint;
        this.bucket = bucket;
    }
}