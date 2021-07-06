package Core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("unused")
public class PropertyReader {

    private static PropertyReader prop;
    private final Properties properties;

    private PropertyReader() {
        properties = new Properties();
    }

    public static synchronized PropertyReader getInstance() {
        if (prop == null) {
            prop = new PropertyReader();
        }
        return prop;
    }

    public void loader(String file) {
        InputStream inp;
        inp = getClass().getClassLoader().getResourceAsStream(file);
        try {
            properties.load(inp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key).trim();
    }

    public List<String> getValues(String key) {
        String[] prop = properties.getProperty(key).split(",");
        return new ArrayList<>(Arrays.asList(prop));
    }
}
