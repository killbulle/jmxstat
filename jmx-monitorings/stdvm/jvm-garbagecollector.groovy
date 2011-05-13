collector.patternMonitoring(pattern: "java.lang:type=GarbageCollector", namekey: "name") {
    metric(name: "CollectionCount", alias: "nb")
    metric(name: "CollectionTime",alias:"tms")
    metric(name: "LastGcInfo.duration", alias: "lstgctime")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Eden Space.used", alias: "EdAft")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Old Gen.used", alias: "OldAft")
    metric(name: "LastGcInfo.memoryUsageAfterGc.PS Perm Gen.used",alias: "PermAft")
}