package utils;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    public static Logger getLogger(String name) {
        return (Logger) LoggerFactory.getLogger(name);
    }
}
