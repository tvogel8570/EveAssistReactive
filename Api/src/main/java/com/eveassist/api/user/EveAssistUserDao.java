package com.eveassist.api.user;

import com.eveassist.api.user.entity.EveAssistUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EveAssistUserDao extends ReactiveCrudRepository<EveAssistUser, Long> {
    Mono<EveAssistUser> findEveAssistUserByUserUnique(String userUnique);
}
