package br.unb.cic.joana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        Config c = mapper.readValue(new File("config/config01.yaml"), Config.class);

        Driver d = new Driver(c);

        d.configure();

        List<String> violations = d.execute();

        for(String s: violations) {
            System.out.println(s);
        }
    }
}
