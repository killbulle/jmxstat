collector.patternMonitoring(pattern: "java.lang:type=GarbageCollector", namekey: "name") {
    metric(name: "CollectionCount")
    metric(name: "CollectionTime")
    metric(name: "LastGcInfo.duration")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Eden Space.used")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Old Gen.used")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Perm Gen.used")
}