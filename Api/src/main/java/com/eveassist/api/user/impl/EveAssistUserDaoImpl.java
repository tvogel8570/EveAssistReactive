package com.eveassist.api.user.impl;

import com.eveassist.api.user.EveAssistUserCustomDao;
import com.eveassist.api.user.entity.EveAssistRole;
import com.eveassist.api.user.entity.EveAssistUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.repository.query.RelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EveAssistUserDaoImpl extends SimpleR2dbcRepository<EveAssistUser, Long> implements EveAssistUserCustomDao<EveAssistUser> {

    @Autowired
    private DatabaseClient dbc;

    public EveAssistUserDaoImpl() {
        dbc.getConnectionFactory().getMetadata().
    }

    public EveAssistUserDaoImpl(RelationalEntityInformation<EveAssistUser, Long> entity, R2dbcEntityOperations entityOperations, R2dbcConverter converter) {
        super(entity, entityOperations, converter);
    }

    public EveAssistUserDaoImpl(RelationalEntityInformation<EveAssistUser, Long> entity, DatabaseClient databaseClient, R2dbcConverter converter, ReactiveDataAccessStrategy accessStrategy) {
        super(entity, databaseClient, converter, accessStrategy);
    }

    public EveAssistUserDaoImpl(DatabaseClient dbc) {
        super();
        this.dbc = dbc;
    }


    @Override
    public Mono<EveAssistUser> findUserAndRoles(String userName) {
        return dbc.sql("""
                            select eau.id, eau.email, eau.user_unique, eau.screen_name, ear.role_name
                            from eve_assist_user eau
                                inner join users_roles ur on eau.id = ur.user_id
                                inner join eve_assist_role ear on ear.id = ur.role_id
                            where eau.user_unique = :user_unique
                            order by eau.id, ear.role_name
                        """)
                .bind("user_unique", userName)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("id"))
                .map(EveAssistUserDaoImpl::buildRoles).next();
    }

    @Override
    public Flux<EveAssistUser> findAllUsersAndRoles() {
        return dbc.sql("""
                            select eau.id, eau.email, eau.user_unique, eau.screen_name, ear.role_name
                            from eve_assist_user eau
                                inner join users_roles ur on eau.id = ur.user_id
                                inner join eve_assist_role ear on ear.id = ur.role_id
                            order by eau.id, ear.role_name
                        """)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("id"))
                .map(EveAssistUserDaoImpl::buildRoles);
    }

    @Override
    public <S extends EveAssistUser> Mono<S> save(S entity) {

        return Mono.empty();
    }


    private static EveAssistUser buildRoles(@NotNull List<Map<String, Object>> eau) {
        return EveAssistUser.builder()
                .id((Long) eau.get(0).get("id"))
                .email(String.valueOf(eau.get(0).get("email")))
                .roles(eau.stream().map(roles -> EveAssistRole.builder()
                                .name((String) roles.get("role_name")).build())
                        .collect(Collectors.toList()))
                .build();
    }
}
