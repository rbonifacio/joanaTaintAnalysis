## Joana Taint Analysis

A small benchmark for testin the Joana capabilities for taint analysis.

### Setup

In the very first moment, execute the `setup.sh` script to download the Joana 
library and configure it as a local maven dependency. 

### Executing the micro securibench

Currently, you might run the 'securibench' benchmark using JUnit test cases. Such as:

   * Basic test suite: `mvn test -Dtest="br.unb.cic.joana.securibench.micro.suite.BasicTestSuite"`
   * Aliasing test suite: `mvn test -Dtest="br.unb.cic.joana.securibench.micro.suite.AliasingTestSuite`
   
