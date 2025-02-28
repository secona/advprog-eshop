package id.ac.ui.cs.advprog.eshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogServiceConsole implements LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogServiceConsole.class);

    @Override
    public void log(String level, String message) {
        logger.info("{}: {}", level, message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
}
