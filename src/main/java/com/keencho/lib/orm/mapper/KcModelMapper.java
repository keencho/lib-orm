package com.keencho.lib.orm.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class KcModelMapper {
    private final ModelMapper modelMapper;

    public KcModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <VO> VO mapOne(Object source, Class<VO> destinationType) {
        return this.modelMapper.map(source, destinationType);
    }

    public <SOURCE, VO> List<VO> mapList(List<SOURCE> sourceList, Class<VO> destinationType) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }

        List<VO> results = new ArrayList<>();

        for (SOURCE source : sourceList) {
            results.add(this.mapOne(source, destinationType));
        }

        return results;
    }
}
