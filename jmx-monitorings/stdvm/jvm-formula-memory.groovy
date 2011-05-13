collector.exactMonitoring(name: "java.lang:type=Memory", namekey: "type") {
    metric(name: "HeapMemoryUsage.used",formula:{bean,results,name,id,value ->return value})
    //metric(name: "HeapMemoryUsage.init")
    //metric(name: "HeapMemoryUsage.max")
    metric(name: "HeapMemoryUsage.committed",formula:{bean,results,name,id,value ->return value}
		)
}