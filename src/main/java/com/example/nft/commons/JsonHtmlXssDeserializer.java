package com.example.nft.commons;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;

/**
 * @author ：bbdxgg
 * @date ：Created By 2022/6/29 下午1:38
 * @description：
 * @modified By：
 * @version: $
 */ // 入参的json转义
public class JsonHtmlXssDeserializer extends JsonDeserializer<String> {
    public JsonHtmlXssDeserializer(Class<String> string) {
        super();
    }

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if (value != null) {
            return StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }
}
