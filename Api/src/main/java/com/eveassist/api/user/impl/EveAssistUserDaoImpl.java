package com.eveassist.api.user.impl;

import com.eveassist.api.user.EveAssistUserCustomDao;
import com.eveassist.api.user.entity.EveAssistUser;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EveAssistUserDaoImpl implements EveAssistUserCustomDao {
    @Override
    public Mono<EveAssistUser> findUserWithPrivileges(String userName) {
        return Mono.empty();
    }
}
