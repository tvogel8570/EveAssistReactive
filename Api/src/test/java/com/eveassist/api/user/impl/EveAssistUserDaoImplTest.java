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
        StepVerifier.create(cut.findUserWithPrivileges(anyString()))
                .expectSubscription()
                .expectComplete();
    }
}
