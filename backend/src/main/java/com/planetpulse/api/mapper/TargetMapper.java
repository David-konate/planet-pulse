package com.planetpulse.api.mapper;

import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.entity.Targets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TargetMapper {

    @Mapping(target = "idTarget", ignore = true)
    Targets toEntity(TargetCreateRequest request);

}