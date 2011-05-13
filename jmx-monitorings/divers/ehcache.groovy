collector.patternMonitoring(pattern: "net.sf.ehcache:type=CacheStatistics", namekey: "name")
        {
            metric(name: "CacheHits")
            metric(name: "InMemoryHits")
            metric(name: "CacheMisses")
            metric(name: "OnDiskHits")
            metric(name: "StatisticsAccuracy")
            metric(name: "DiskStoreObjectCount")
            metric(name: "ObjectCount")
        }

