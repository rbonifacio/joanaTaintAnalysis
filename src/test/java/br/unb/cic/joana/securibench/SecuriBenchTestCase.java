package br.unb.cic.joana.securibench;

import br.unb.cic.joana.JoanaTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import securibench.micro.MicroTestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SecuriBenchTestCase extends JoanaTestCase {

    @Override
    public String configurationFile() {
        return "securibench.yaml";
    }

    public abstract String entryPointMethod();

    public abstract String basePackage();

    protected Set<Class<? extends MicroTestCase>> findClassesInBasePackage() {
        Reflections reflections = new Reflections(basePackage(), new SubTypesScanner(false));
        return reflections.getSubTypesOf(MicroTestCase.class).stream().collect(Collectors.toSet());
    }

    @Test
    public final void testSuite() throws Exception {
        Set<Class<? extends MicroTestCase>> classes = findClassesInBasePackage();

        int totalOfExpectedVulnerabilities = 0;
        int totalOfVulnerabilitiesFound = 0;
        List<String> report = new ArrayList<>();

        boolean failure = false;

        for (Class c: classes) {
            Object instance = c.newInstance();

            if (! (instance instanceof MicroTestCase)) {
                throw new RuntimeException("Could not instantiate " + c.getName() + " as a MicroTestCase");
            }

            MicroTestCase microTestCase = (MicroTestCase) instance;
            int expected = microTestCase.getVulnerabilityCount();
            int found = 0;
            
            try {
                setUpConfiguration(c.getName() + "." + entryPointMethod());
                found = driver.execute().size();
            } catch(Throwable e) {
                report.add(String.format("- %s failure to execute. Error = %s", c.getName(), e.getMessage()));
                failure = true;
            }

            if (expected == found) {
                report.add(String.format(" - %s (ok)", c.getName()));
            }
            else {
                report.add(String.format("- %s error. Expecting %d but found %d vulnerabilities.", c.getName(), expected, found));
            }
            totalOfExpectedVulnerabilities += expected;
            totalOfVulnerabilitiesFound += found;
        }

        Collections.sort(report);
        for(String s: report) {
            System.out.println(s);
        }

        if (totalOfExpectedVulnerabilities == totalOfVulnerabilitiesFound) {
            Assert.assertTrue(true);
        }
        else {
            System.err.println(String.format("Error. Expecting %d but found %d warnings.", totalOfExpectedVulnerabilities, totalOfVulnerabilitiesFound));
        }

        if (failure) {
            System.err.println("We found errors in the Joana execution or configuration.");
        }
    }

}
