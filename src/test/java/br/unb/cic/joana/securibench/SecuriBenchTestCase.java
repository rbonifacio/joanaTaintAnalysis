package br.unb.cic.joana.securibench;

import br.unb.cic.joana.JoanaTestCase;

public abstract class SecuriBenchTestCase extends JoanaTestCase {
    @Override
    public String configurationFile() {
        return "securibench.yaml";
    }
}
