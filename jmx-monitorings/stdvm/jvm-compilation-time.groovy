collector.patternMonitoring(pattern: "java.lang:type=Compilation", namekey: "type")
        {
            metric(name: "TotalCompilationTime")
        }