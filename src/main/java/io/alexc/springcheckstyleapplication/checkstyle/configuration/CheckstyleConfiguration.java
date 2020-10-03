package io.alexc.springcheckstyleapplication.checkstyle.configuration;

import org.xml.sax.InputSource;

public class CheckstyleConfiguration {

    private static final String DEFAULT_CHECKSTYLE_CONFIGURATION = "checkstyle.xml";

    public InputSource getInputSource() {
        // Default configuration
        return new InputSource(this.getClass()
                .getClassLoader()
                .getResourceAsStream(DEFAULT_CHECKSTYLE_CONFIGURATION));
    }

}
