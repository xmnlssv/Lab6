package serializer;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.LogUtil;

public class ServerJsonSerializer {
    public static final ObjectMapper mapper = new ObjectMapper();
    private static Logger logger = LogUtil.getLogger("server");

    static {
        mapper.findAndRegisterModules();
    }

    public static String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            logger.error("Could not serialize object {}", exception);
            return null;
        }
    }

    public static Object deserialize(String json, Class<?> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException exception) {
            logger.error("Could not deserialize string {}", json);
            return null;
        }
    }
}
