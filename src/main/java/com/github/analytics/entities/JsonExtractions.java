package entities;

import exception.InvalidTestDataException;
import org.json.simple.JSONObject;

public class JsonExtractions {

    public JSONObject getJsonBeforeAfterObject(JSONObject jsonObject) {
        String opType = jsonObject.get("op_type").toString().trim();

        if (opType.equals("I") || opType.equals("U")) {
            return (JSONObject) jsonObject.get("after");

        } else if (opType.equals("D")) {
            return (JSONObject) jsonObject.get("before");
        }
        throw new InvalidTestDataException("Error in JsonExtractions: getJsonBeforeAfterObject");
    }

    public Object getValueOfTheKeyFromData(String givenJsonObject, String key, String className) {
        try {
            // System.out.println("Select Key / Group By value from the data");
            JSONObject jsonObject = new JsonFormat().parseJsonStringToObject(givenJsonObject);
            return String.valueOf(jsonObject.get(key));

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTestDataException("Error in: " + className + ": getValueOfTheKeyFromData");
        }
    }
}