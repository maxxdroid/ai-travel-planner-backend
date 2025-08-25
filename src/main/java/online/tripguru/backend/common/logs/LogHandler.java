package online.tripguru.backend.common.logs;

import lombok.extern.slf4j.Slf4j;
import online.tripguru.backend.common.exception.ApiException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogHandler {

    public static void log(String message) {
        log.info(message);
    }

    public static void log(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    public static void log(ApiException ex) {
        log.error(ex.getMessage(), ex.getCause());
    }

    public static void log(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }

    public static void logError(String message, Throwable throwable) {
        log.error(message, throwable.getMessage(), throwable);
    }
}
