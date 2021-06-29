package com.roboautomator.app;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainApplicationTestIT {

    @LocalManagementPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    void testHealthEndpointRouting() throws Exception {
        //var response = template.getForEntity("http://doneux-website-db.cfxlcyr0taj5.eu-west-2.rds.amazonaws.com:" + port + "/doneux_website_backend", String.class);

        //assertThat(response.getStatusCodeValue()).isEqualTo(200);
        //assertThat(response.getBody()).contains("UP");
    }
}