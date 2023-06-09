package com.eveassist.api.user.dto;

import com.eveassist.api.user.EveAssistUserKey;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;

@Builder
public record EveAssistUserDto(
        @NotNull(groups = {UniqueInfo.class})
        @Size(min = 30, max = 30, message = "User Unique must be exactly 30 characters", groups = {UniqueInfo.class})
        String userUnique,
        @NotEmpty(groups = {BaseInfo.class})
        @NotNull(groups = {BaseInfo.class})
        @Email(groups = {BaseInfo.class})
        String email,
        @NotNull(groups = {BaseInfo.class})
        @Size(min = 2, max = 100, message = "Screen Name must be between 2 and 100 characters", groups = {BaseInfo.class})
        String screenName,
        Instant createDate
//        ,EvePilotDto pilots
) implements Serializable, EveAssistUserKey {
    @Override
    public String getUserUnique() {
        return userUnique;
    }
}
