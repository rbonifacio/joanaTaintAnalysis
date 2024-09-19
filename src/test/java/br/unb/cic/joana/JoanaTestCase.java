package br.unb.cic.joana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public abstract class JoanaTestCase extends TestCase {

    public abstract String entryPoint();
    public abstract String configurationFile();
    public abstract int expectedViolations();

    private Config loadConfiguration() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        ClassLoader classLoader = getClass().getClassLoader();

        File configurationFile = new File(classLoader.getResource(configurationFile()).getFile());
        File applicationClassPath = new File(classLoader.getResource(".").getPath());

        Config config = mapper.readValue(configurationFile, Config.class);

        /* we override the application class path and the entrypoint. */
        config.setApplicationClassPath(applicationClassPath.getAbsolutePath());
        config.setEntryPointMethod(entryPoint());

        List<String> thirdPartyLibraries = config.getThirdPartyLibraries();

        return config;
    }
    protected Driver driver;
    @Before
    public void setUp() throws Exception {
        Config config = loadConfiguration();
        driver = new Driver(config);
        driver.configure();
    }

    @Test
    public void testExecution() throws Exception {
        try {
            Assert.assertEquals(expectedViolations(), driver.execute().size());
        }
        catch (Throwable e) {
            Assert.fail("Error running the test case: " + e.getMessage());
        }
    }
}
