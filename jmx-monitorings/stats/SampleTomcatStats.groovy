/* Alpha sample stats collector buit no good formater for the moment*/
collector.statsMonitoring(pattern: 'Catalina:type=RequestProcessor,worker="http-bio-8080"', namekey: "name", joinName: "maxRequestUri")
        {
            metric(name: "maxRequestUri")
            metric(name: "maxTime")
            metric(name: "requestCount")
            metric(name: "processingTime")
        }