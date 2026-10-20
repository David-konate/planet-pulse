package com.planetpulse.api.mapper;


import com.planetpulse.api.dto.request.CreateTargetRequestDto;
import com.planetpulse.api.entity.Targets;
import org.springframework.stereotype.Component;

@Component
public class TargetMapper {

    public Targets toEntity(CreateTargetRequestDto dto) {
        Targets target = new Targets();
        target.setCountryId(dto.getCountryId());
        target.setYear(dto.getYear());
        target.setCo2Emissions(dto.getCo2Emissions());
        target.setPm25Emissions(dto.getPm25Emissions());
        target.setForestArea(dto.getForestArea());
        target.setRenewableEnergyShare(dto.getRenewableEnergyShare());
        return target;
    }


}