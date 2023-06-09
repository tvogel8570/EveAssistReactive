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
package com.eveassist.api.user.dto;


import com.eveassist.api.user.EveAssistUserKey;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public record EveAssistListUserDto(
        @NotNull(groups = {UniqueInfo.class})
        @Size(min = 30, max = 30, message = "User Unique must be exactly 30 characters", groups = {UniqueInfo.class})
        String userUnique,
        @NotEmpty(groups = {BaseInfo.class})
        @NotNull(groups = {BaseInfo.class})
        @Email(groups = {BaseInfo.class})
        String email,
        @NotNull(groups = {BaseInfo.class})
        @Size(min = 2, max = 100, message = "User Name must be between 2 and 100 characters", groups = {BaseInfo.class})
        String screenName,
        Date createDate) implements Serializable, EveAssistUserKey {
    @Override
    public String getUserUnique() {
        return userUnique;
    }
}
