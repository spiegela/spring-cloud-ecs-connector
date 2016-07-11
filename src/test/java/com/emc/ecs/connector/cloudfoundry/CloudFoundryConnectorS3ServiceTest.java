package com.emc.ecs.connector.cloudfoundry;

import com.emc.ecs.connector.S3ServiceInfo;
import org.junit.Test;
import org.springframework.cloud.cloudfoundry.AbstractCloudFoundryConnectorTest;
import org.springframework.cloud.service.ServiceInfo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CloudFoundryConnectorS3ServiceTest extends AbstractCloudFoundryConnectorTest {
    private String accessKeyId = "mypublickey";
    private String secretAccessKey = "mysecretkey";

    public CloudFoundryConnectorS3ServiceTest() {
    }

    private static String getUrl() {
        return "https://" + "10.20.30.40" + ":" + 80;
    }

    @Test
    public void s3BucketServiceCreation() {
        String bucketName1 = "bucket-1";
        String bucketName2 = "bucket-2";
        when(this.mockEnvironment.getEnvValue("VCAP_SERVICES"))
                .thenReturn(
                        getServicesPayload(
                                getS3BucketServicePayload("s3-1", "10.20.30.40", 80, accessKeyId, secretAccessKey, bucketName1),
                                getS3BucketServicePayload("s3-2", "10.20.30.40", 80, accessKeyId, secretAccessKey, bucketName2)
                        )
                );
        List<ServiceInfo> serviceInfos = this.testCloudConnector.getServiceInfos();
        S3ServiceInfo info1 = (S3ServiceInfo) getServiceInfo(serviceInfos, "s3-1");
        S3ServiceInfo info2 = (S3ServiceInfo) getServiceInfo(serviceInfos, "s3-2");
        assertServiceFoundOfType(info1, S3ServiceInfo.class);
        assertEquals(bucketName1, info1.getBucket());
        assertEquals(bucketName2, info2.getBucket());
        assertEquals(accessKeyId, info1.getAccessKey());
        assertEquals(accessKeyId, info2.getAccessKey());
        assertEquals(secretAccessKey, info1.getSecretKey());
        assertEquals(secretAccessKey, info2.getSecretKey());
        assertEquals(getUrl(), info1.getEndpoint());
        assertEquals(getUrl(), info2.getEndpoint());
    }

    @Test
    public void s3NamespaceServiceCreation() {
        when(this.mockEnvironment.getEnvValue("VCAP_SERVICES"))
                .thenReturn(
                        getServicesPayload(
                                getS3NamespaceServicePayload("s3-ns-1", "10.20.30.40", 80, accessKeyId, secretAccessKey)
                        )
                );
        List<ServiceInfo> serviceInfos = this.testCloudConnector.getServiceInfos();
        S3ServiceInfo info1 = (S3ServiceInfo) getServiceInfo(serviceInfos, "s3-ns-1");
        assertServiceFoundOfType(info1, S3ServiceInfo.class);
        assertEquals(null, info1.getBucket());
        assertEquals(accessKeyId, info1.getAccessKey());
        assertEquals(secretAccessKey, info1.getSecretKey());
        assertEquals(getUrl(), info1.getEndpoint());
    }

    private String getS3BucketServicePayload(String serviceName, String hostname, int port, String accessKeyId, String secretAccessKey, String bucketName) {
        return getRelationalPayload("test-s3-bucket-info.json", serviceName, hostname, port, accessKeyId, secretAccessKey, bucketName);
    }

    private String getS3NamespaceServicePayload(String serviceName, String hostname, int port, String accessKeyId, String secretAccessKey) {
        return getRelationalPayload("test-s3-namespace-info.json", serviceName, hostname, port, accessKeyId, secretAccessKey, "");
    }

    private String getRelationalPayload(String templateFile, String serviceName, String hostname, int port, String accessKey, String secretKey, String bucketName) {
        String payload = this.readTestDataFile(templateFile);
        payload = payload.replace("$serviceName", serviceName);
        payload = payload.replace("$endpoint", getUrl());
        payload = payload.replace("$hostname", hostname);
        payload = payload.replace("$port", Integer.toString(port));
        payload = payload.replace("$accessKey", accessKey);
        payload = payload.replace("$secretKey", secretKey);
        payload = payload.replace("$bucketName", bucketName);
        return payload;
    }

}