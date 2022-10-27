package com.unity.reactfullstack;

import com.unity.reactfullstack.dto.Users;
import com.unity.reactfullstack.exception.RestTemplateResponseErrorHandler;
import com.unity.reactfullstack.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ChangeSetPersister.NotFoundException.class, Users.class})
@RestClientTest
class RestTemplateResponseErrorHandlerIntegrationTest {


    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private RestTemplateBuilder builder;


    @Test
    void givenRemoteApiCall_when404Error_thenThrowNotFound() {
        Assertions.assertNotNull(this.builder);
        Assertions.assertNotNull(this.server);

        RestTemplate restTemplate = this.builder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

        this.server
                .expect(ExpectedCount.once(), requestTo("https://jsonplaceholder.typicode.com/users/100"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            Users response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/100", Users.class);
        });
    }


}