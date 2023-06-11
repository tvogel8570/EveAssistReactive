package com.eveassist.api.user.impl;

import com.eveassist.api.user.EveAssistUserCustomDao;
import com.eveassist.api.user.entity.EveAssistRole;
import com.eveassist.api.user.entity.EveAssistUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EveAssistUserDaoImpl implements EveAssistUserCustomDao {
    private final DatabaseClient dbc;

    public EveAssistUserDaoImpl(DatabaseClient dbc) {
        this.dbc = dbc;
    }


    @Override
    public Mono<EveAssistUser> findUserAndRoles(String userName) {
        return dbc.sql("""
                            select eau.id, eau.email, eau.eau_user_unique, eau.eau_screen_name, ear.role_name
                            from eve_assist_user eau
                                inner join users_roles ur on eau.id = ur.user_id
                                inner join eve_assist_role ear on ear.id = ur.role_id
                            where eau.eau_user_unique = :eau_user_unique
                            order by eau.id, ear.role_name
                        """)
                .bind("eau_user_unique", userName)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("id"))
                .map(EveAssistUserDaoImpl::buildRoles).next();
    }

    @Override
    public Flux<EveAssistUser> findAllUsersAndRoles() {
        return dbc.sql("""
                            select eau.id, eau.email, eau.eau_user_unique, eau.eau_screen_name, ear.role_name
                            from eve_assist_user eau
                                inner join users_roles ur on eau.id = ur.user_id
                                inner join eve_assist_role ear on ear.id = ur.role_id
                            order by eau.id, ear.role_name
                        """)
                .fetch().all()
                .bufferUntilChanged(result -> result.get("id"))
                .map(EveAssistUserDaoImpl::buildRoles);
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
