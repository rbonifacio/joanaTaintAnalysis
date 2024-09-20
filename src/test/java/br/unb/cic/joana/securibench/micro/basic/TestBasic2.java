package br.unb.cic.joana.securibench.micro.basic;

import br.unb.cic.joana.securibench.SecuriBenchTestCase;

public class TestBasic2 extends SecuriBenchTestCase {
    @Override
    public String entryPoint() {
        return "securibench.micro.basic.Basic2.doGet(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";
    }

    @Override
    public int expectedViolations() {
        return 1;
    }
}
