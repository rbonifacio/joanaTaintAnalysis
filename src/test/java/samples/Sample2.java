package samples;

import java.io.IOException;
import java.io.PrintWriter;

public class Sample2 {
    public static void doGet(String[] args) {
        try {
            String str = source();
            sink(str);    /* BAD */
        }catch(Exception e) {

        }
    }

    private static String source() {
        return "secret";
    }

    private static void sink(String v) {

    }
    
    public String getDescription() {
        return "very simple sink with custom main method";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}