package context;

import java.util.HashMap;

public class SessionContext {
    private static final HashMap<String, TestExecutionContext> allTestsExecutionContext;

    static {
        System.out.println("SessionContext default constructor");
        new SessionContext();
        allTestsExecutionContext = new HashMap();
    }

    public SessionContext() {
    }
    
    // SessionContext.addContext(threadId, new TestExecutionContext(String.valueOf(threadId)));
    public static synchronized void addContext(long threadId, TestExecutionContext testExecutionContext) {
        allTestsExecutionContext.put(String.valueOf(threadId), testExecutionContext);
        System.out.println(String.format("Adding context for thread - %s", threadId));
    }

    public static synchronized TestExecutionContext getTestExecutionContext(long threadId) {
        return allTestsExecutionContext.get(String.valueOf(threadId));
    }
}
