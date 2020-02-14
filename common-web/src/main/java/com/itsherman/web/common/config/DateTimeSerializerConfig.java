package com.itsherman.web.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.itsherman.web.common.date.DateTimeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DateTimeProperties.class)
public class DateTimeSerializerConfig {

    @Resource
    private DateTimeProperties dateTimeProperties;

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeProperties.getDateTime()));
    }

    @Bean
    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(dateTimeProperties.getDate()));
    }

    @Bean
    public LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateTimeProperties.getDate()));
    }

    @Bean
    public ZonedDateTimeSerializer zonedDateTimeSerializer() {
        return new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeProperties.getDateTime()).withZone(ZoneId.of(dateTimeProperties.getZoneId())));
    }

    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeProperties.getDateTime()));
    }

    @Bean
    public InstantDeserializer<ZonedDateTime> instantDeserializer() {
        ZoneId zoneId = ZoneId.of(dateTimeProperties.getZoneId());
        return new InstantDeserializer<ZonedDateTime>(
                ZonedDateTime.class,
                DateTimeFormatter.ofPattern(dateTimeProperties.getDateTime()),
                t -> ZonedDateTime.of(LocalDateTime.from(t), zoneId),
                ms -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(ms.value), ms.zoneId),
                ns -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(ns.integer, ns.fraction), ns.zoneId),
                ZonedDateTime::withZoneSameInstant, false) {
            private static final long serialVersionUID = 1L;
        };
    }

    @Bean
    @ConditionalOnMissingBean(Jackson2ObjectMapperBuilderCustomizer.class)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> serializerMap = new HashMap<>();
        serializerMap.put(LocalDateTime.class, localDateTimeSerializer());
        serializerMap.put(LocalDate.class, localDateSerializer());
        serializerMap.put(ZonedDateTime.class, zonedDateTimeSerializer());
        Map<Class<?>, JsonDeserializer<?>> deserializerMap = new HashMap<>();
        deserializerMap.put(LocalDateTime.class, localDateTimeDeserializer());
        deserializerMap.put(LocalDate.class, localDateDeserializer());
        deserializerMap.put(ZonedDateTime.class, instantDeserializer());
        return builder ->
                builder
                        .simpleDateFormat(dateTimeProperties.getDateTime())
                        .timeZone(dateTimeProperties.getTimeZone())
                        .serializersByType(serializerMap)
                        .deserializersByType(deserializerMap)
                        .featuresToDisable(
                                SerializationFeature.WRITE_DATES_WITH_ZONE_ID,
                                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
                        )
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }
}
