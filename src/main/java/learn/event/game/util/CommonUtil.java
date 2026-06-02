package learn.event.game.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommonUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private CommonUtil() {
        throw new UnsupportedOperationException("CommonUtil is a utility class");
    }

    public static String convertObjectToJsonString(Object requestObject) {
        if (requestObject == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(requestObject);
        } catch (JsonProcessingException exception) {
            log.error("Error occurred while converting object [{}] to JSON string",
                    requestObject, exception);
            return requestObject.toString();
        }
    }

    public static <T> T convertJsonStringToObject(
            String jsonAsString,
            Class<T> valueType) {

        try {
            return objectMapper.readValue(jsonAsString, valueType);
        } catch (JsonProcessingException exception) {
            log.error("Unable to convert JSON string to Java object of type [{}]",
                    valueType.getSimpleName(), exception);

            throw new RuntimeException(
                    "Unable to convert JSON String to Java Object of type: "
                            + valueType.getSimpleName(),
                    exception);
        }
    }
}