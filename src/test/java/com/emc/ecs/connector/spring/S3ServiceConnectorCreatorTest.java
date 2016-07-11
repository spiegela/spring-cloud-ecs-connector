package com.emc.ecs.connector.spring;

import com.emc.ecs.connector.S3ServiceInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class S3ServiceConnectorCreatorTest {
    private S3ServiceConnectorCreator s3ServiceConnectorCreator = new S3ServiceConnectorCreator();

    @Test
    public void createBucketConnector() throws Exception {
        String myBucket = "myBucket";
        String endpoint = "http://10.20.40.3:9020";
        S3ServiceInfo serviceInfo = new S3ServiceInfo("s3-1", "myPublicKey", "mySecretKey", endpoint, myBucket);
        S3Connector connector = s3ServiceConnectorCreator.create(serviceInfo, null);
        assertEquals(myBucket, connector.getBucket());
        assertEquals(endpoint, connector.getEndpoint());
        assertNotNull(connector.getClient());
    }

    @Test
    public void createNamespaceConnector() throws Exception {
        String endpoint = "http://10.20.40.3:9020";
        S3ServiceInfo serviceInfo = new S3ServiceInfo("s3-1", "myPublicKey", "mySecretKey", endpoint, null);
        S3Connector connector = s3ServiceConnectorCreator.create(serviceInfo, null);
        assertNull(connector.getBucket());
        assertEquals(endpoint, connector.getEndpoint());
        assertNotNull(connector.getClient());
    }
}