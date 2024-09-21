package br.unb.cic.joana.securibench.micro.suite;

import br.unb.cic.joana.securibench.SecuriBenchTestCase;

public class BasicTestSuite extends SecuriBenchTestCase {

    @Override
    public String basePackage() {
        return "securibench.micro.basic";
    }

    @Override
    public String entryPointMethod() {
        return "doGet(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";
    }
}
