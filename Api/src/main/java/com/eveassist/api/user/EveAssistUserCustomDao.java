package com.eveassist.api.user;

import com.eveassist.api.user.entity.EveAssistUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EveAssistUserCustomDao<T> {
    Mono<EveAssistUser> findUserAndRoles(String userName);

    Flux<EveAssistUser> findAllUsersAndRoles();

//    <S extends T> Mono<S> save(S entity);
}
