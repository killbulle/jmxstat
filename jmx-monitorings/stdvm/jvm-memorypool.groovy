collector.patternMonitoring(pattern: "java.lang:type=MemoryPool", namekey: "name")
        {
            metric(name: "Usage.committed")
            metric(name: "Usage.init")
            metric(name: "Usage.used")
            metric(name: "Usage.max");
        }