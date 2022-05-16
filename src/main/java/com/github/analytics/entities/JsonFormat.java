package entities;

import exception.InvalidTestDataException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

public class JsonFormat {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public JSONObject parseJsonStringToObject(String jsonString) {
        try {
//            System.out.println(ANSI_PURPLE + "parseJsonStringToObject: " + ANSI_CYAN + jsonString + ANSI_RESET);
            return (JSONObject) new JSONParser().parse(jsonString);

        } catch (ParseException e) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(os));
            e.printStackTrace();
            System.out.println("Error in parsing json: " + jsonString);
        }
        return new JSONObject();
    }

    public JsonFormat putTheListOfRecords(List<String> keyStore, JSONObject givenJsonObject, JSONObject transformedObject) {
        for (String key : keyStore) {
            this.copyAndPutTheRecord(key, givenJsonObject, transformedObject);
        }
        return this;
    }

    public JsonFormat copyAndPutTheRecord(String key, JSONObject givenJsonObject, JSONObject transformedObject) {
        if (null != givenJsonObject.get(key)) {
            transformedObject.put(key, String.valueOf(givenJsonObject.get(key)));
        } else {
            throw new InvalidTestDataException("copyAndPutTheRecord: Error in getting value of the key: " + key);
        }
        return this;
    }

    public JsonFormat copyAndPutTheObjectOfKey(String key, JSONObject givenJsonObject, JSONObject transformedObject) {
        if (null != givenJsonObject.get(key)) {
            JSONObject objectValueOfKey = this.getJsonObjectOfKey(key, givenJsonObject);

            for (String currentKey : (Set<String>) objectValueOfKey.keySet()) {
                transformedObject.put(currentKey, objectValueOfKey.get(currentKey));
            }

        } else {
            throw new InvalidTestDataException("copyAndPutTheObjectOfKey: Error in getting value of the key: " + key);
        }
        return this;
    }

    public JSONObject getJsonObjectOfKey(String key, JSONObject givenJsonObject) {
        JSONObject objectValueOfKey = new JsonFormat().parseJsonStringToObject(givenJsonObject.get(key).toString());
        return objectValueOfKey;
    }

    public String getTheRecordValueIfNotNull(String key, JSONObject givenJsonObject) {
        if (null != givenJsonObject.get(key)) {
            return String.valueOf(givenJsonObject.get(key));
        }

        throw new InvalidTestDataException("getTheRecordValueIfNotNull: Error in getting value of the key: " + key);
    }

    public JsonFormat putTheRecord(String key, String valueOfTheKey, JSONObject jsonObject) {
        if (null != valueOfTheKey) {
            jsonObject.put(key, valueOfTheKey);
        }
        return this;
    }

    public JsonFormat copyAndPutTheRecordWithAlias(String key, String alias, JSONObject givenJsonObject, JSONObject transformedObject) {
        if (null != givenJsonObject.get(key)) {
            transformedObject.put(alias, String.valueOf(givenJsonObject.get(key)));
        } else {
            throw new InvalidTestDataException("copyAndPutTheRecordWithAlias: Error in getting value of the key: " + key);
        }
        return this;
    }
}