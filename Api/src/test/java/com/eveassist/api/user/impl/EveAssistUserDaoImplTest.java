package com.eveassist.api.user.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@DataR2dbcTest
@ActiveProfiles("test")
class EveAssistUserDaoImplTest {

    @Autowired
    EveAssistUserDaoImpl cut;

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
                .expectNextMatches(eau->eau.getEmail().equals("test@test.com") && eau.getRoles().size() == 1 )
                .expectNextMatches(eau->eau.getEmail().equals("test2@test.com") && eau.getRoles().size() == 2 )
                .verifyComplete();
    }

    @Test
    void findUserAndRoles() {
        StepVerifier.create(cut.findUserAndRoles("123456789012345678901234567890"))
                .expectSubscription()
                .expectNextMatches(eau->eau.getEmail().equals("test@test.com") && eau.getRoles().size() == 1 )
                .verifyComplete();
    }
}
