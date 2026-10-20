package com.planetpulse.api.mapper;


import com.planetpulse.api.dto.IndicatorDto;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Indicator;
import com.planetpulse.api.payload.request.IndicatorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IndicatorMapper {

    @Mapping(target = "country", source = "country")
    Indicator toEntity(IndicatorRequest request, Country country);

    // entity -> dto: the country id is taken inside the country object
    @Mapping(target = "countryId", source = "country.idCountry")
    IndicatorDto toDto(Indicator indicator);

}
