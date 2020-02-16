package com.deltaa.superduper.web;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ch.qos.logback.classic.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final String LOG_MESSAGE = "Service Error : %s ";

    private final Logger logger = (Logger) LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
        IllegalArgumentException.class, MethodArgumentTypeMismatchException.class,
        ConstraintViolationException.class})
    @ResponseBody
    String malformedRequest(final HttpServletRequest req, final Exception ex) {
        logError(ex);
        return getRootCauseMessage(ex);
    }

    private void logError(final Exception ex) {
        logger.error(String.format(LOG_MESSAGE, ex.getMessage()), ex);
    }
}
