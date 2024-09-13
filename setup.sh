echo "Configuring JoanaTaintAnalysis."

wget https://pp.ipd.kit.edu/projects/joana/joana.ui.ifc.wala.cli.jar
mvn install:install-file -Dfile=joana.ui.ifc.wala.cli.jar -DgroupId=org.joana -DartifactId=ifc -Dversion=0.0.1

echo "Done."
