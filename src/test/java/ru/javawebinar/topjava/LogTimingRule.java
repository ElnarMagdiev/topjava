package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogTimingRule extends ExternalResource {

    private static final Logger logger = LoggerFactory.getLogger(LogTimingRule.class);
    private static long time = System.currentTimeMillis();

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
               Long currentTime = System.currentTimeMillis();
               statement.evaluate();
               logger.info("{} took {} ms" , description.getMethodName(), System.currentTimeMillis() - currentTime);
            }
        };
    }
}
