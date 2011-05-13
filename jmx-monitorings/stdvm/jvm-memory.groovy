collector.exactMonitoring(name: "java.lang:type=Memory", namekey: "type") {
    metric(name: "HeapMemoryUsage.used")
    metric(name: "HeapMemoryUsage.init")
    metric(name: "HeapMemoryUsage.max")
    metric(name: "HeapMemoryUsage.committed")
}