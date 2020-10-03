package io.alexc.springcheckstyleapplication.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import io.alexc.springcheckstyleapplication.checkstyle.listener.CheckstyleResultListener;
import io.alexc.springcheckstyleapplication.checkstyle.result.CheckstyleResult;
import org.apache.commons.io.FileUtils;
import org.xml.sax.InputSource;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class CheckstyleRunner {
    private static final String DEFAULT_CHECKSTYLE_CONFIGURATION = "checkstyle.xml";

    private Checker checker;
    private final List<File> files;

    public CheckstyleRunner(Path path) {

        try {
            checker = new Checker();

            InputSource inputSource = new InputSource(this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(DEFAULT_CHECKSTYLE_CONFIGURATION));

            checker.setModuleClassLoader(Checker.class.getClassLoader());
            checker.configure(ConfigurationLoader.loadConfiguration(inputSource,
                    new PropertiesExpander(System.getProperties()),
                    null));
        } catch (CheckstyleException e) {
            e.printStackTrace();
        }

        // list all the java files of the temp directory for the checkstyle to check
        files = (List<File>) FileUtils.listFiles(path.toFile(), new String[]{"java"}, true);

        // Set base directory
        checker.setBasedir(path.toFile().getAbsolutePath());

    }

    /**
     * run the current style check
     * @return the result of the check
     */
    public CheckstyleResult run() {
        final CheckstyleResultListener listener = new CheckstyleResultListener();
        checker.addListener(listener);

        try {
            // Process the provided files
            checker.process(files);
        } catch (CheckstyleException ex) {
            ex.printStackTrace();
        }

        // Clean up
        checker.destroy();

        // return the final results
        return listener.getResult();
    }

}
