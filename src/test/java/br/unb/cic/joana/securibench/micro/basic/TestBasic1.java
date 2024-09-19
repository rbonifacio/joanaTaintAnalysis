package br.unb.cic.joana.securibench.micro.basic;

import br.unb.cic.joana.securibench.SecuriBenchTestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestBasic1 extends SecuriBenchTestCase {
    @Override
    public String entryPoint() {
        return "securibench.micro.basic.Basic1.main([Ljava/lang/String;)V";
    }

    @Override
    public int expectedViolations() {
        return 1;
    }

}
