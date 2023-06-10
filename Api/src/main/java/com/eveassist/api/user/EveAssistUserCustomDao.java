package com.eveassist.api.user;

import com.eveassist.api.user.entity.EveAssistUser;
import reactor.core.publisher.Mono;

public interface EveAssistUserCustomDao {
    Mono<EveAssistUser> findUserWithPrivileges(String userName);
}
