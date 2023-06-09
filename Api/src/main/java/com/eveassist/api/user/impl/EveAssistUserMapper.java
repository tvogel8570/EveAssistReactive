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
package com.eveassist.api.user.impl;

import com.eveassist.api.user.dto.EveAssistFullUserDto;
import com.eveassist.api.user.dto.EveAssistUserDto;
import com.eveassist.api.user.entity.EveAssistUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EveAssistUserMapper {
    EveAssistUserDto eveAssistUserEntityToDto(EveAssistUser source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    EveAssistUser eveAssistUserDtoToEntity(EveAssistUserDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    EveAssistUser eveAssistUserDtoToEntity(EveAssistFullUserDto source);

/*
    EvePilotDto evePilotEntityToDto(EvePilot source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eaUser", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    EvePilot evePilotDtoToEntity(EvePilotDto source);
*/

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateUserDtoToEntity(EveAssistUserDto dto, @MappingTarget EveAssistUser entity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateUserDtoToEntity(EveAssistFullUserDto dto, @MappingTarget EveAssistUser entity);

/*
    List<EvePilotDto> evePilotDtoToEntity(List<EvePilot> source);

    List<EvePilot> evePilotEntityToDto(List<EvePilotDto> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eaUser", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePilotDtoToEntity(EvePilotDto dto, @MappingTarget EvePilot entity);
*/
}
