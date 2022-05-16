package entities;

import exception.InvalidTestDataException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Runner {
    private final static String configFileAbsolutePath = new File("configs/config.properties").getAbsolutePath();

    public JSONObject readTestData() {
        try {
            String absolutePath = new File("src/test/resources/testData.json").getAbsolutePath();
            FileReader reader = new FileReader(absolutePath);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);

            String targetEnvironment = this.readPropertiesFile(configFileAbsolutePath).getProperty("TARGET_ENVIRONMENT");
            return (JSONObject) jsonObject.get(targetEnvironment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in ReadFile.java ");
        }
    }

    public Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fileInputStream = null;
        Properties prop = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fileInputStream);

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTestDataException("Error in ReadFile: readPropertiesFile");

        } finally {
            fileInputStream.close();
        }

        return prop;
    }
}
