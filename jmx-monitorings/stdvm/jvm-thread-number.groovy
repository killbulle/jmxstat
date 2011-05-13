//thread
collector.patternMonitoring(pattern: "java.lang:type=Threading", namekey: "type")
        {
            metric(name: "ThreadCount")
            metric(name: "DaemonThreadCount")
            metric(name: "PeakThreadCount")
        }