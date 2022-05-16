package context;

import java.util.HashMap;

public class TestExecutionContext {
    private final String testName;
    private final HashMap<String, Object> testExecutionState;
    private final String NOT_SET = "NOT-YET-SET";

    public TestExecutionContext(String testName) {
        SessionContext.addContext(Thread.currentThread().getId(), this);
        this.testName = testName;
        this.testExecutionState = new HashMap();
        System.out.println(String.format("%s - TestExecution context created", testName));
    }

    public String getTestName() {
        return this.testName;
    }

    public void addTestState(String key, Object details) {
        this.testExecutionState.put(key, details);
    }

    public Object getTestState(String key) {
        return this.testExecutionState.get(key);
    }

    public String getTestStateAsString(String key) {
        return (String) this.testExecutionState.get(key);
    }
}
