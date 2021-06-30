package ru.lepescin.studentbookslibrary.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.experimental.UtilityClass;
import ru.lepescin.studentbookslibrary.config.WebSecurityConfig;

import java.io.IOException;

@UtilityClass
public class JsonDeserializers {
    public static class PasswordDeserializer extends JsonDeserializer<String> {
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            String rawPassword = node.asText();
            return WebSecurityConfig.PASSWORD_ENCODER.encode(rawPassword);
        }
    }
}
