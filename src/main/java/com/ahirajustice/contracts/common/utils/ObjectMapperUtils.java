package com.ahirajustice.contracts.common.utils;

import com.ahirajustice.contracts.common.exceptions.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ObjectMapperUtils {

    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("Could not serialize object of class: " + object.getClass().getSimpleName());
            throw new ServerErrorException();
        }
    }

    public static <TResult> TResult deserialize(Resource resource, Class<TResult> resultClass) {
        if (resource == null)
            return null;

        try {
            return objectMapper.readValue(resource.getFile(), resultClass);
        } catch (IOException ex) {
            log.error("Could not deserialize json to class: " + resultClass.getSimpleName());
            throw new ServerErrorException();
        }
    }

    public static <TResult> TResult deserialize(String resultJson, Class<TResult> resultClass) {
        if (StringUtils.isBlank(resultJson))
            return null;

        try {
            return objectMapper.readValue(resultJson, resultClass);
        } catch (JsonProcessingException ex) {
            log.error("Could not deserialize json to class: " + resultClass.getSimpleName());
            throw new ServerErrorException();
        }
    }

    public static <TResult> TResult deserialize(String resultJson, TypeReference<TResult> typeReference) {
        if (StringUtils.isBlank(resultJson))
            return null;

        try {
            return objectMapper.readValue(resultJson, typeReference);
        } catch (JsonProcessingException ex) {
            log.error("Could not deserialize json to class: " + typeReference.getType().getTypeName());
            throw new ServerErrorException();
        }
    }

    public static <TResult> TResult deserialize(Resource resource, TypeReference<TResult> typeReference) {
        if (resource == null)
            return null;

        try {
            return objectMapper.readValue(resource.getInputStream(), typeReference);
        } catch (IOException ex) {
            log.error("Could not deserialize json to class: " + typeReference.getType().getTypeName());
            throw new ServerErrorException();
        }
    }

}