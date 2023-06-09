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

import com.eveassist.api.user.EveAssistUserKey;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
@Entity
@Table(name = "eve_assist_user",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "eau_user_unique" }, name = "eve_assist_user_business_key"),
                @UniqueConstraint(columnNames = { "email" }, name = "eve_assist_user_email_key") })
*/
@Table
public class EveAssistUser implements Serializable, EveAssistUserKey {
    private static final long serialVersionUID = 9029797325151444231L;

    @NotNull
    @Size(min = 30, max = 30, message = "User Unique must be exactly 30 characters")
    @EqualsAndHashCode.Include
    @Column //(name = "eau_user_unique", nullable = false, length = 30)
    private String userUnique;
    /*
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eve_assist_user_generator")
        @SequenceGenerator(name = "eve_assist_user_generator", sequenceName = "eve_assist_user_id_seq", allocationSize = 1)
    */
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Email
    @Column //(nullable = false, length = 200)
    private String email;

    @NotNull
    @Size(min = 2, max = 100, message = "User Name must be between 2 and 100 characters")
    @Column //(name = "eau_screen_name", nullable = false, length = 100)
    private String screenName;

    @NotNull
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Column //(name = "eau_password", nullable = false, length = 50)
    private String password;

/*
    @Builder.Default
    @Valid
    @OneToMany(mappedBy = "eaUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvePilot> pilots = new ArrayList<EvePilot>();
*/

    //        @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    @Column //(name = "create_date", nullable = false)
    private Date createDate = new Date();

    /*
        @Builder.Default
        @ManyToMany
        @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    */
    private Collection<EveAssistRole> roles = new ArrayList<EveAssistRole>();
}
