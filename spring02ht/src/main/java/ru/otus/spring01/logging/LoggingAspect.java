package ru.otus.spring01.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(pointcut = "execution(* ru.otus.spring01.dao.CSVSurvayDao.*(..))", throwing = "ex")
    public void logException(Exception ex) throws Throwable {
        LOGGER.error(ex.getMessage(), ex);
    }

    @Before("execution(* ru.otus.spring01.dao.CSVSurvayDao.*(..))")
    public void logBefore(JoinPoint jp) {
        LOGGER.debug(jp.getTarget() + ":" + jp.getSignature().getName());
    }
}