package com.github.analytics.context;

import java.util.HashMap;

public class SessionContext {
    private static HashMap<String, TestExecutionContext> allTestsExecutionContext;

    static synchronized void addContext(long threadId, TestExecutionContext testExecutionContext) {
        allTestsExecutionContext.put(String.valueOf(threadId), testExecutionContext);
    }

    public static synchronized TestExecutionContext getTestExecutionContext(long threadId) {
        return (TestExecutionContext) allTestsExecutionContext.get(String.valueOf(threadId));
    }

    public static synchronized void remove(long threadId) {
        allTestsExecutionContext.remove(String.valueOf(threadId));
    }
}