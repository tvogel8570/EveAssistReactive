/*
 * Copyright 2021 EveAssist Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eveassist.api.user;

import com.eveassist.api.user.entity.EveAssistPrivilege;
import com.eveassist.api.user.entity.EveAssistRole;
import com.eveassist.api.user.entity.EveAssistUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = 2860340013061061671L;
    private final EveAssistUser eveAssistUser;

    public SecurityUser(EveAssistUser eveAssistUser) {
        super();
        this.eveAssistUser = eveAssistUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(eveAssistUser.getRoles()));
    }

    @Override
    public String getUsername() {
        return eveAssistUser.getEmail();
    }

    @Override
    public String getPassword() {
        return eveAssistUser.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<String> getPrivileges(final Collection<EveAssistRole> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<EveAssistPrivilege> collection = new ArrayList<>();
        for (final EveAssistRole role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (final EveAssistPrivilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
