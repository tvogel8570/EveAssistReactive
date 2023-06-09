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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record EveAssistFullUserDto(
        EveAssistUserDto baseUser,
        @NotNull(groups = {BaseInfo.class})
        @NotEmpty(groups = {BaseInfo.class})
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters", groups = {BaseInfo.class})
        String password,
        @NotNull(groups = PasswordInfo.class)
        @NotEmpty(groups = PasswordInfo.class)
        @Size(min = 8, max = 100, message = "Old password must be between 8 and 100 characters", groups = {PasswordInfo.class})
        String oldPassword)
        implements Serializable, EveAssistUserKey {
    @Override
    public String getUserUnique() {
        return baseUser().userUnique();
    }
}
