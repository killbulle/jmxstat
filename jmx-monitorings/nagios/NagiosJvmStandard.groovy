//nagios test support//
collector.exactMonitoring(name: "java.lang:type=Memory", namekey: "type") {
    nagiosMetric(name: "HeapMemoryUsage.used", nmin: "100", nunit: "Kb", nclass: "memory heap")
    nagiosMetric(name: "HeapMemoryUsage.committed", nclass: "commited")
}


collector.patternMonitoring(pattern: "java.lang:type=GarbageCollector", namekey: "name") {
    nagiosMetric(name: "CollectionCount", rename:{name->return "count"}, nclass: "gc count",nunit:"c")
    nagiosMetric(name: "CollectionTime", nclass: "gc time")

}

//tomcat7 only
collector.patternMonitoring(pattern: "Catalina:j2eeType=WebModule", namekey: "name",rename:{id,name-> return name.replaceAll("//localhost","")})
{
nagiosMetric(name: "stateName",nclass:"state",rename:{name->return "state"},formula:{bean,results,name,id,value -> if(value.equals("STARTED")){return 1} else {return 0}});

}


collector.patternMonitoring(pattern: "Catalina:type=DataSource", namekey: "name")
{
nagiosMetric(name: "numActive",nclass:"active");

}