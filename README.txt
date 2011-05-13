Tools for testing dsl;=) and monitoring jmx server GLOBALLY EXPIREMENTAL but useful ;)
-TODO enhance all comments and headers
-TODO refactoring for better design
-TODO optimize Jmx getAttirbutes calls
-TODO Nagios support to be tested
-TODO BETTER Cummulative stats integration 
	



Monitoring are defined in a dsl in the jmx directory
somes examples show closure use to rename or alter metric value ouptut
Sample usage for the moment...
For a group of monitoring file
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx/tomcat7-bio/ -csv
For a single file
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx/tomcat7-bio/appstatus.groovy -csv

Ex multi-format
Column mode is genraly used for one or two file max you
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/tomcat7-bio/thread.groovy
Csv mode ouptut for graphing use livegraph http://www.live-graph.org/ 
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k100 -f ./jmx-monitorings/tomcat7-bio/thread.groovy -csv
Nagios ouptut REALLY EXPIRMENTAL
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/nagios/NagiosJvmStandard.groovy -m
CUMULATIVE STATS EXPERIMENTAL
java -jar ./target/jmxstat-1.0-SNAPSHOT-jar-with-dependencies.jar -server 127.0.01 -port 9004  -i1000 -k1 -f ./jmx-monitorings/SampleTomcatStatsConnector.groovy -b


