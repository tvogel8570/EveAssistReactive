package com.eveassist.api.user;

import com.eveassist.api.user.entity.EveAssistUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EveAssistUserDao extends EveAssistUserCustomDao<EveAssistUser>, ReactiveCrudRepository<EveAssistUser, Long> {
    Mono<EveAssistUser> findEveAssistUserByUserUnique(String userUnique);
}
