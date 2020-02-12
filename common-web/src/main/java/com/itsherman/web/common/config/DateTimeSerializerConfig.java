package com.itsherman.web.common.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DateTimeSerializerConfig {

    private static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static String DATE_PATTERN = "yyyy-MM-dd";

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    @Bean
    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    @Bean
    public ZonedDateTimeSerializer zonedDateTimeSerializer() {
        return new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }

    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> serializerMap = new HashMap<>();
        serializerMap.put(LocalDateTime.class, localDateTimeSerializer());
        serializerMap.put(ZonedDateTime.class, zonedDateTimeSerializer());
        serializerMap.put(LocalDate.class, localDateSerializer());


        Map<Class<?>, JsonDeserializer<?>> deserializerMap = new HashMap<>();
        deserializerMap.put(LocalDateTime.class, localDateTimeDeserializer());
        deserializerMap.put(LocalDate.class, localDateDeserializer());
        return builder -> builder.serializersByType(serializerMap)
                .deserializersByType(deserializerMap)
                .build();
    }
}
