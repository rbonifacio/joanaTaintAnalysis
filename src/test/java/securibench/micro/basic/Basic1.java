/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic1.java,v 1.4 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/** 
 *  @servlet description="very simple XSS" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic1 extends BasicTestCase implements MicroTestCase {
    public static void main(String[] args) {
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
        return "very simple XSS";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}