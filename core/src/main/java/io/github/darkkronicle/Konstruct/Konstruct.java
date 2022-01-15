package io.github.darkkronicle.Konstruct;

import lombok.Value;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;

public class Konstruct {

    /**
     * Stores version info about the currently loaded Konstruct
     */
    public final static Info INFO = new Info();

    // https://stackoverflow.com/a/65830610
    @Value
    public static class Info {

        String version;
        Properties versionProperties = new Properties();

        private Info() {
            version = getInfo();
        }

        private String getInfo() {
            try {
                InputStream inputStream;
                try {
                    inputStream = getResource("version.properties");
                } catch (URISyntaxException | IOException e) {
                    return "Error"
                }

                try {
                    versionProperties.load(inputStream);
                } catch (IOException e) {
                }
                return versionProperties.getProperty("version", "1.0.0");
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

    }

    private static InputStream getResource(String path) throws URISyntaxException, IOException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource(path).toURI();
        if (uri.getScheme().contains("jar")) {
            // Not IDE
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        } else {
            // IDE
            return new FileInputStream(Paths.get(uri).toFile());
        }
    }

}
