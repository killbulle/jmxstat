//executor
collector.patternMonitoring(pattern: 'Catalina:type=ThreadPool,name="http-bio-8080"', namekey: "name")
        {
            metric(name: "currentThreadCount")
            metric(name: "currentThreadsBusy")
            metric(name: "maxThreads")
            metric(name: "minSpareThreads")
        }