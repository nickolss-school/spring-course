package com.nickolss.rest_with_spring_boot.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination) {
        return mapper.map(origin, destination);
    }

    public static <Origin, Destination> List<Destination> parseListObjects(List<Origin> origins, Class<Destination> destination) {
        List<Destination> destinationObjects = new ArrayList<Destination>();

        for (Origin origin : origins) {
            destinationObjects.add(mapper.map(origin, destination));
        }

        return destinationObjects;
    }
}