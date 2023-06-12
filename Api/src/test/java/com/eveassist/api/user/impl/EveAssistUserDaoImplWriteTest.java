package com.eveassist.api.user.impl;

import com.eveassist.api.user.EveAssistUserDao;
import com.eveassist.api.user.entity.EveAssistUser;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
class EveAssistUserDaoImplWriteTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> dbCont = new PostgreSQLContainer<>("postgres:15-alpine");
    @Autowired
    EveAssistUserDao cut;
    @Autowired
    ConnectionFactory connectionFactory;

    private void executeSqlScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript)
                        .then(Mono.from(connection.close())))
                .block();
    }

    @BeforeEach
    void setupData(@Value("classpath:/fixture/user/user.sql") Resource sqlScript) {
        executeSqlScriptBlocking(sqlScript);
    }

    @Test
    void whenSaveDuplicateUser_thenDataIntegrityViolationException() {
        EveAssistUser duplicateUser = EveAssistUser.builder().id(1L)
                .userUnique("123456789012345678901234567890")
                .email("test@test.com")
                .password("secret")
                .build();

        // verify that the record exists in the test data by Id
        StepVerifier.create(cut.findById(duplicateUser.getId()))
                .expectNextCount(1)
                .verifyComplete();

        // verify that the record exists in the test data by UserUnique
        StepVerifier.create(cut.findEveAssistUserByUserUnique(
                        duplicateUser.getUserUnique()))
                .expectNextCount(1)
                .verifyComplete();

        // verify failure
        StepVerifier.create(cut.save(duplicateUser))
                .verifyError(DataIntegrityViolationException.class);
    }

    @Test
    void whenSaveValidUser_thenCanBeRetrieved() {
        EveAssistUser newUser = EveAssistUser.builder().id(null)
                .userUnique("000000000000000000000000000001")
                .email("new@test.com")
                .screenName("new")
                .password("secret")
                .build();

        // verify that the record doesn't exist in the test data by UserUnique
        StepVerifier.create(cut.findEveAssistUserByUserUnique(
                        newUser.getUserUnique()))
                .verifyComplete();

        // verify success
        StepVerifier.create(cut.save(newUser))
                .expectNextMatches(eau -> newUser.getEmail().equals(eau.getEmail())
                        && newUser.getUserUnique().equals(eau.getUserUnique())
                        && eau.getId() != null)
                .verifyComplete();
    }
}
