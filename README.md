# Spring-cloud-ECS-connectors
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/94ddb01c6b664d8ea04092521364b3f6)](https://www.codacy.com/app/spiegela/spring-cloud-ecs-connector?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=spiegela/spring-cloud-ecs-connector&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/94ddb01c6b664d8ea04092521364b3f6)](https://www.codacy.com/app/spiegela/spring-cloud-ecs-connector?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=spiegela/spring-cloud-ecs-connector&amp;utm_campaign=Badge_Coverage)
[![Build Status](https://travis-ci.org/spiegela/spring-cloud-ecs-connector.svg?branch=master)](https://travis-ci.org/spiegela/spring-cloud-ecs-connector)
[![Apache Version 2 Licence](http://img.shields.io/:license-Apache%20v2-blue.svg)](LICENSE)

Spring cloud ECS service connectors to use EMC ECS in CloudFoundry

### Add a connector to your project

Here the bootstrap for your spring boot app to get a `S3Connector` including bucket name, the HTTP endpoint and a pre-configured AWS S3 SDK Client.

```java
@Configuration
public class S3Config extends AbstractCloudConfig {

	@Bean
    public S3Connector s3() {
        return connectionFactory().service(S3Connector.class);
    }
}
```

Usage example:

```java
@Component
public class ExampleS3 {

    @Autowired
    S3Connector s3;

    private final static String filename = "mysuperfile.txt";

    public void pushData(){
        s3.getClient().putObject(new PutObjectRequest(s3.getBucket(), filename, newFile(file)));
    }

    public void retrieveData(){
       s3.getClient().getObejct(new GetObjectRequest(s3.getBucket(), filename));
    }
}
```

### Deploy and run

#### Cloud Foundry

1. Create an ECS service from the marketplace using the [ECS CF Service Broker](http://github.com/emccode/ecs-cf-service-broker).
2. Push your app with `cf push`
3. After the app has been pushed bind your new created service to your app (e.g: `cf bs nameofmyapp nameofmyservice`)
4. Restage your app: `cf restage nameofmyapp`

## Contributing

Report any issues or pull request on this repo.

 using the [ECS CF Service Broker](http://github.com/emccode/ecs-cf-service-broker)All feedbacks is welcome!
