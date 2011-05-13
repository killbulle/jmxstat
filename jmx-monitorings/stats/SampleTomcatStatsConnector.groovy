/** Alpha sample stats collector buit no good formater for the moment **/
collector.statsMonitoring(pattern: 'Catalina:type=RequestProcessor,worker="http-bio-8080"', namekey: "name", joinName: "stage")
        {
            metric(name: "stage")
            metric(name: "maxTime")
            metric(name: "requestCount")
            metric(name: "processingTime")
        }