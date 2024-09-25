package br.unb.cic.joana.securibench.micro.suite;

import br.unb.cic.joana.securibench.SecuriBenchTestCase;
import securibench.micro.MicroTestCase;

import java.util.HashSet;
import java.util.Set;

public class SpecificTest extends SecuriBenchTestCase {
    @Override
    public String basePackage() {
        return "securibench.micro.basic";
    }

    @Override
    public String entryPointMethod() {
        return "doGet(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";
    }

    @Override
    protected Set<Class<? extends MicroTestCase>> findClassesInBasePackage() {
        try {
            Class c = Class.forName("securibench.micro.basic.Basic42");
            Set<Class<? extends MicroTestCase>> res = new HashSet<>();
            res.add(c);
            return res;
        }catch(Exception e) {
            return null;
        }
    }
}
