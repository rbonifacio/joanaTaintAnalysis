package br.unb.cic.joana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;

public class Main {

    private Options options;
    public static void main(String[] args) throws Exception
    {
        Main program = new Main();

        program.createCommandLineOptions();
        String configurationFile = program.parseConfigurationFileOption(args);

        if(configurationFile != null) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            Config c = mapper.readValue(new File(configurationFile), Config.class);

            Driver d = new Driver(c);

            d.configure();

            List<String> violations = d.execute();

            for (String s : violations) {
                System.out.println(s);
            }
        }
    }

    private void createCommandLineOptions() {
        options = new Options();

        Option configFile = Option.builder("configFile")
                .argName("file")
                .hasArg()
                .desc("use given yaml file as configuration")
                .build();

        options.addOption(configFile);
    }

    private String parseConfigurationFileOption(String[] args) throws Exception {
        try {
            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("configFile")) {
                return cmd.getOptionValue("configFile");
            }
            printHelp();
            return null;
        }catch(Exception e) {
            System.out.println("Error parsing command line arguments.");
            printHelp();
            return null;
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("joana", options);
    }
}
