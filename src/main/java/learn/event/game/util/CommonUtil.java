package learn.event.game.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public final class CommonUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    // JavaTimeModule is bundled in Jackson 3 core — no manual registration needed
    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private CommonUtil() {
        throw new UnsupportedOperationException("CommonUtil is a utility class");
    }

    /**
     * Converts a Java object to its JSON String representation.
     *
     * @param requestObject the object to serialize
     * @return JSON string, or {@code null} if the input is {@code null}
     */
    public static String convertObjectToJsonString(Object requestObject) {
        if (requestObject == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(requestObject);
        } catch (JacksonException exception) {
            log.error("Error occurred while converting object [{}] to JSON string", requestObject, exception);
            return requestObject.toString();
        }
    }

    /**
     * Converts a JSON String to a Java object of the given type.
     *
     * @param jsonAsString the JSON string to deserialize
     * @param valueType    the target class
     * @param <T>          the target type
     * @return deserialized object of type {@code T}
     * @throws RuntimeException if deserialization fails
     */
    public static <T> T convertJsonStringToObject(String jsonAsString, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonAsString, valueType);
        } catch (JacksonException exception) {
            log.error("Unable to convert JSON string to Java object of type [{}]: {}",
                    valueType.getSimpleName(), exception.getMessage(), exception);
            throw new RuntimeException("Unable to convert JSON String to Java Object of type: "
                    + valueType.getSimpleName(), exception);
        }
    }
}