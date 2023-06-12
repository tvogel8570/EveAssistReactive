package com.eveassist.api.user.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@DataR2dbcTest
@Testcontainers
class EveAssistUserDaoImplTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> dbCont = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    EveAssistUserDaoImpl cut;

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
    void findUserWithPrivileges() {
        assertThat(cut).isNotNull();
        StepVerifier.create(cut.findUserAndRoles(anyString()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void findAllUserWithRoles() {
        StepVerifier.create(cut.findAllUsersAndRoles())
                .expectSubscription()
                .expectNextMatches(eau -> eau.getEmail().equals("test@test.com") && eau.getRoles().size() == 1)
                .expectNextMatches(eau -> eau.getEmail().equals("test2@test.com") && eau.getRoles().size() == 2)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findUserAndRoles() {
        StepVerifier.create(cut.findUserAndRoles("123456789012345678901234567890"))
                .expectSubscription()
                .expectNextMatches(eau -> eau.getEmail().equals("test@test.com") && eau.getRoles().size() == 1)
                .verifyComplete();
    }
}
