package io.github.darkkronicle.Konstruct;

import lombok.Value;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        String gitLastTag;
        String gitHash;
        String gitBranchName;
        String gitIsCleanTag;
        Properties versionProperties = new Properties();

        private Info() {
            String gitProperties = "";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("classpath:/version.properties");

            if(inputStream == null)
            {
                // When running unit tests, no jar is built, so we load a copy of the file that we saved during build.gradle.
                // Possibly this also is the case during debugging, therefore we save in bin/main instead of bin/test.
                try {
                    inputStream = new FileInputStream("core/bin/main/version.properties");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try {
                versionProperties.load(inputStream);
            } catch (IOException e) {
            }
            version = versionProperties.getProperty("version", "1.0.0");
            gitLastTag = versionProperties.getProperty("gitLastTag","last-tag-not-found");
            gitHash = versionProperties.getProperty("gitHash","git-hash-not-found");
            gitBranchName = versionProperties.getProperty("gitBranchName","git-branch-name-not-found");
            gitIsCleanTag = versionProperties.getProperty("gitIsCleanTag","git-isCleanTag-not-found");

        }

    }

}
