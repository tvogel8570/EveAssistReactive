package com.eveassist.api.user.impl;

import com.eveassist.api.user.EveAssistUserDao;
import com.eveassist.api.user.EveAssistUserService;
import com.eveassist.api.user.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class EveAssistUserServiceImpl implements EveAssistUserService {
    private final EveAssistUserDao repository;

    public EveAssistUserServiceImpl(EveAssistUserDao repository) {
        this.repository = repository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository.findEveAssistUserByUserUnique(username).map(SecurityUser::new);
    }
}
