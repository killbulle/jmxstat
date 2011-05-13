Outils JMX de monitoring des serveurs
-TODO refactoring pour un code moins crado
-TODO optim sur la recup des valeur jmx via getAttibutes
-TODO Support nagios à tester
-TODO Mieux intégrer les stats cumulative ne plus a specifer un sortie spécifique ?
	
Ex actuel d'utilisation
Pour un ensemble de fichiers de monitoring:
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx/tomcat7-bio/ -csv
Pour un fichier:
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx/tomcat7-bio/appstatus.groovy -csv

Ex multi-format
Mode colonne pour juste un fichier custom
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/tomcat7-bio/thread.groovy
Mode csv pour grapher avec livegraph avec 100 sample à 1 sec
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k100 -f ./jmx-monitorings/tomcat7-bio/thread.groovy -csv
Sortie nagios intégration encore a tester
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/nagios/NagiosJvmStandard.groovy -m
Sortie de statistique sur les processors tomcats
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/SampleTomcatStatsConnector.groovy -b
