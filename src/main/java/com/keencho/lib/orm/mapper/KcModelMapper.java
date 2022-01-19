package com.keencho.lib.orm.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        return sourceList
                .stream()
                .map(s -> this.mapOne(s, destinationType))
                .collect(Collectors.toList());
    }
}
