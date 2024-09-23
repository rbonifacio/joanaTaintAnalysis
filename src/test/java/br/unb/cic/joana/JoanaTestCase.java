package br.unb.cic.joana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import securibench.micro.MicroTestCase;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JoanaTestCase extends TestCase {

    public abstract String configurationFile();

    private Config loadConfiguration(String entryPointMethod) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        ClassLoader classLoader = getClass().getClassLoader();

        File configurationFile = new File(classLoader.getResource(configurationFile()).getFile());
        File applicationClassPath = new File(classLoader.getResource(".").getPath());

        Config config = mapper.readValue(configurationFile, Config.class);

        /* we override the application class path and the entrypoint. */
        config.setApplicationClassPath(applicationClassPath.getAbsolutePath());
        config.setEntryPointMethod(entryPointMethod);

        List<String> thirdPartyLibraries = config.getThirdPartyLibraries();

        return config;
    }

    protected Driver driver;

    public void setUpConfiguration(String entryPointMethod) throws Exception {
        Config config = loadConfiguration(entryPointMethod);
        driver = new Driver(config);
        driver.configure();
    }
}
