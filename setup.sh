echo "Configuring JoanaTaintAnalysis."

mkdir lib
cd lib
wget https://pp.ipd.kit.edu/projects/joana/joana.ui.ifc.wala.cli.jar
mvn install:install-file -Dfile=joana.ui.ifc.wala.cli.jar -DgroupId=org.joana -DartifactId=ifc -Dversion=0.0.1 -Dpackaging=jar

echo "Done."
