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
package com.eveassist.api.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table //(name = "eve_assist_role")
public class EveAssistRole {
    /*
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eve_assist_role_generator")
        @SequenceGenerator(name = "eve_assist_role_generator", sequenceName = "eve_assist_role_id_seq", allocationSize = 1)
    */
    @Id
    private Long id;

    @Column //(name = "role_name")
    private String name;

    //    @ManyToMany(mappedBy = "roles")
    private Collection<EveAssistUser> users;

    /*
        @ManyToMany
        @JoinTable(name = "roles_privileges",
                joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    */
    private Collection<EveAssistPrivilege> privileges;
}
