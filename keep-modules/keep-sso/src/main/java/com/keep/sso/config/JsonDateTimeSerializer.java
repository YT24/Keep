package com.keep.sso.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.seata.rm.datasource.undo.parser.spi.JacksonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateTimeSerializer implements JacksonSerializer<LocalDateTime> {
 
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
    @Override
    public Class<LocalDateTime> type() {
        return LocalDateTime.class;
    }
 
    @Override
    public JsonSerializer<LocalDateTime> ser() {
        return new LocalDateTimeSerializer(DATETIME_FORMAT);
    }
 
    @Override
    public JsonDeserializer<? extends LocalDateTime> deser() {
        return new LocalDateTimeDeserializer(DATETIME_FORMAT);
    }
}
