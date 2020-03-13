import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {
    private static Properties properties;

    private GetProperties() {
    }

    public static Properties getProperties() throws IOException {
        if (properties == null) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("/home/mamba/IdeaProjects/AvitoParser/src/main/resources/config.properties");
            properties.load(fis);
            return properties;
        } else
            return properties;
    }
}
