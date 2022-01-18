package com.keencho.lib.orm.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class KcModelMapper {
    private final ModelMapper modelMapper;

    public KcModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <V> V mapOne(Object source, Class<V> destinationType) {
        if (source == null) {
            return null;
        }

        return this.modelMapper.map(source, destinationType);
    }

    public <S, V> List<V> mapList(List<S> sourceList, Class<V> destinationType) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }

        List<V> results = new ArrayList<>();

        for (S s : sourceList) {
            results.add(this.mapOne(s, destinationType));
        }

        return results;
    }
}
