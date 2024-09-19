## Joana Taint Analysis

A small benchmark for testin the Joana capabilities for taint analysis.

### Setup

In the very first moment, execute the `setup.sh` script to download the Joana 
library and configure it as a local maven dependency. 

More information soon ...

### Running

cd /usr/app/joanaTaintAnalysis/

mvn dependency:resolve
mvn test

javac -cp $CLASSPATH:/usr/app/joanaTaintAnalysis/lib/joana-ifc.jar:/usr/app/joanaTaintAnalysis/lib/servlet-api.jar /usr/app/joanaTaintAnalysis/src/main/java/br/unb/cic/joana/Driver.java

cd /usr/app/joanaTaintAnalysis/src/main/java
java -cp $CLASSPATH:/usr/app/joanaTaintAnalysis/lib/joana-ifc.jar:/usr/app/joanaTaintAnalysis/lib/servlet-api.jar br.unb.cic.joana.Driver