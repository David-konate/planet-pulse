// conversion of data
package com.planetpulse.api.mapper;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(target = "noteTimestamp", ignore = true)
    @Mapping(target = "country", ignore = true)
    Note toEntity(NoteCreateRequest request);
}
